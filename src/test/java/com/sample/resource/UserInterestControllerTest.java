package com.sample.resource;

import com.sample.entity.UserInterest;
import com.sample.resource.dto.UserInterestDto;
import com.sample.service.UserInterestService;
import com.sample.util.JacksonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;

import static com.sample.entity.InterestType.CASH;
import static com.sample.entity.InterestType.CREDIT;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
class UserInterestControllerTest {

  private MockMvc mockMvc;

  @Mock
  private UserInterestService userInterestService;

  @InjectMocks
  private UserInterestController userInterestController;

  @BeforeEach
  public void setup() {
    mockMvc = standaloneSetup(userInterestController).setControllerAdvice(new CommonExceptionHandler()).build();
  }

  @Test
  void getInterests() throws Exception {
    Long userId = 3432L;
    List<UserInterest> expected = asList(anInterest(userId), anInterest(userId));
    when(userInterestService.getInterestsForUser(userId)).thenReturn(expected);

    mockMvc.perform(get(format("/user/%s/interests", userId)))
        .andExpect(content().json(JacksonUtils.toJson(expected)))
        .andExpect(status().is(HTTP_OK));
  }

  @Test
  void createInterest() throws Exception {
    Long userId = new Random().nextLong();
    UserInterestDto dto = UserInterestDto.builder().type(CREDIT).name(randomAlphabetic(25)).region(randomAlphabetic(10)).language(randomAlphabetic(12)).build();
    UserInterest expected = UserInterest.builder().id(1234L).userId(userId).name(dto.getName())
        .language(dto.getLanguage()).type(dto.getType()).region(dto.getRegion()).build();
    when(userInterestService.createInterest(userId, dto)).thenReturn(expected);

    mockMvc.perform(post(format("/user/%s/interest", userId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JacksonUtils.toJson(dto)))
        .andExpect(status().is(HTTP_OK))
        .andExpect(content().json(JacksonUtils.toJson(expected)));
  }

  @Test
  void saveInterest() throws Exception {
    Long interestId = new Random().nextLong();
    UserInterestDto dto = UserInterestDto.builder().type(CASH).name(randomAlphabetic(25)).region(randomAlphabetic(10)).language(randomAlphabetic(12)).build();
    UserInterest expected = UserInterest.builder().id(interestId).userId(76756L).name(dto.getName())
        .language(dto.getLanguage()).type(dto.getType()).region(dto.getRegion()).build();
    when(userInterestService.updateInterest(interestId, dto)).thenReturn(expected);

    mockMvc.perform(put(format("/user-interest/%s", interestId))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JacksonUtils.toJson(dto)))
        .andExpect(status().is(HTTP_OK))
        .andExpect(content().json(JacksonUtils.toJson(expected)));
  }

  @Test
  void deleteInterest() throws Exception {
    Long interestId = new Random().nextLong();

    mockMvc.perform(delete(format("/user-interest/%s", interestId)))
        .andExpect(status().is(HTTP_NO_CONTENT));
    verify(userInterestService).deleteInterest(interestId);
  }

  private UserInterest anInterest(Long userId) {
    Random random = new Random();
    return UserInterest.builder().id(random.nextLong()).name(randomAlphabetic(20))
        .language("English").type(CASH).region(randomAlphabetic(10)).userId(userId).build();
  }
}

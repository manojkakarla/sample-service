package com.sample.resource;

import com.sample.entity.UserInterest;
import com.sample.resource.dto.UserInterestDto;
import com.sample.service.UserInterestService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserInterestController {
  private final UserInterestService userInterestService;

  @Autowired
  public UserInterestController(UserInterestService userInterestService) {
    this.userInterestService = userInterestService;
  }

  @GetMapping(path = "/user/{userId}/interests", produces = APPLICATION_JSON_VALUE)
  @ApiOperation(value = "get interests for a user")
  public List<UserInterest> getInterests(@PathVariable Long userId) {
    return userInterestService.getInterestsForUser(userId);
  }

  @PostMapping(path = "/user/{userId}/interest", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Create an interest for the user")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "interest saved"),
      @ApiResponse(code = 409, message = "if there is an interest already with type + language + region"),
      @ApiResponse(code = 422, message = "if the type is unknown")
  })
  public UserInterest createInterest(@PathVariable Long userId, @RequestBody UserInterestDto dto) {
    return userInterestService.createInterest(userId, dto);
  }

  @PutMapping(path = "/user-interest/{interestId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Updates an interest")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "case successfully submitted"),
      @ApiResponse(code = 409, message = "if there is an interest already with type + language + region"),
      @ApiResponse(code = 422, message = "if the type is unknown")
  })
  public UserInterest saveInterest(@PathVariable Long interestId, @RequestBody UserInterestDto dto) {
    return userInterestService.updateInterest(interestId, dto);
  }

  @DeleteMapping(path = "/user-interest/{interestId}")
  @ApiOperation(value = "Deletes an interest")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Interest successfully deleted")
  })
  public ResponseEntity<Void> deleteInterest(@PathVariable Long interestId) {
    userInterestService.deleteInterest(interestId);
    return new ResponseEntity<>(NO_CONTENT);
  }

}
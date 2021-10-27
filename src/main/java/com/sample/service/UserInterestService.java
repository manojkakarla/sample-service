package com.sample.service;

import com.sample.entity.UserInterest;
import com.sample.exceptions.InterestExistsException;
import com.sample.exceptions.InterestNotFoundException;
import com.sample.repository.UserInterestRepository;
import com.sample.resource.dto.UserInterestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserInterestService {

  private final UserInterestRepository userInterestRepository;

  @Autowired
  public UserInterestService(UserInterestRepository userInterestRepository) {
    this.userInterestRepository = userInterestRepository;
  }

  public List<UserInterest> getInterestsForUser(Long userId) {
    return userInterestRepository.findByUserId(userId);
  }

  public UserInterest createInterest(Long userId, UserInterestDto dto) {
    UserInterest userInterest = UserInterest.builder().type(dto.getType())
        .language(dto.getLanguage()).name(dto.getName()).region(dto.getRegion()).userId(userId).build();
    return saveInterest(userInterest);
  }

  public UserInterest updateInterest(Long interestId, UserInterestDto dto) {
    Optional<UserInterest> interest = userInterestRepository.findById(interestId);
    return interest.map(i -> {
      UserInterest updated = i.toBuilder().type(dto.getType()).language(dto.getLanguage())
          .name(dto.getName()).region(dto.getRegion()).build();
      return saveInterest(updated);
    }).orElseThrow(() -> new InterestNotFoundException("No interest found with the id"));
  }

  private UserInterest saveInterest(UserInterest userInterest) {
    try {
      return userInterestRepository.save(userInterest);
    } catch (DataIntegrityViolationException e) {
      throw new InterestExistsException("Interest already exists with the given data");
    }
  }

  public void deleteInterest(Long interestId) {
    try {
      userInterestRepository.deleteById(interestId);
    } catch (EmptyResultDataAccessException e) {
      throw new InterestNotFoundException("No interest found with the id");
    }
  }
}

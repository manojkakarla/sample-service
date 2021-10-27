package com.sample.repository;

import com.sample.entity.UserInterest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInterestRepository extends CrudRepository<UserInterest, Long> {

  List<UserInterest> findByUserId(Long userId);
}
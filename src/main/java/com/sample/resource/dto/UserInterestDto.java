package com.sample.resource.dto;

import com.sample.entity.InterestType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInterestDto {
  @NonNull private InterestType type;
  @NonNull private String name;
  @NonNull private String region;
  @NonNull private String language;
}

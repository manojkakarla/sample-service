package com.sample.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInterest {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NonNull
  private Long userId;
  @NonNull
  @Enumerated(EnumType.STRING)
  private InterestType type;
  @NonNull
  private String region;
  @NonNull
  private String language;
  @NonNull
  private String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    UserInterest that = (UserInterest) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
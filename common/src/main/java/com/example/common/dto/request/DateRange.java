package com.example.common.dto.request;

import java.time.LocalDateTime;

import com.example.common.validator.FieldLessThan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@FieldLessThan(first = "fromValue", second = "toValue")
public class DateRange {
  LocalDateTime fromValue;
  LocalDateTime toValue;
}

package com.example.common.dto.response;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class SuccessResponse {
  @NotNull
  private int status = 1;
}

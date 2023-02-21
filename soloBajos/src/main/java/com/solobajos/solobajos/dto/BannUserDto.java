package com.solobajos.solobajos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertFalse;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannUserDto {
    @AssertFalse(message = "{bannUserDto.banned.assertfalse}")
    Boolean enabled;
}




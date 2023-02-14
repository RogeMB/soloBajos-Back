package com.solobajos.solobajos.dto;

import lombok.Builder;

@Builder
public record ChangePasswordRequest (String oldPassword, String newPassword, String verifyNewPassword) {

}
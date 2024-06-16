package com.demo.oauth2.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class changePasswordDto {

    String newPassword;
    String confirmNewPassword;

}

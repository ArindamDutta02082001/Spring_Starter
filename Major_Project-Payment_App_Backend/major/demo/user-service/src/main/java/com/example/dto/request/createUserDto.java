package com.example.dto.request;



import com.example.models.User;
import com.example.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class createUserDto {

    private String name;

    @NotBlank
    private String mobile;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}

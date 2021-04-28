package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

    @Email
    private String email;

    private String username;

    @NotNull
    private String password;

}

package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RegisterDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String firstname;

    @NotNull
    @Length(min = 3, max = 50)
    private String lastname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Set<Integer> roleIdList;

    @NotNull
    private boolean isPhysicalPerson;

    @NotNull
    private PassportDto passportDto;

    private Integer companyId;

}

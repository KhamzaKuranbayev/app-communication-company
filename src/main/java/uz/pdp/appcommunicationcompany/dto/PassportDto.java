package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PassportDto {

    private String number;
    private String givenBy;
    private Date dateOfIssue;
    private Date dateOfExpire;
    private Integer userId;

}

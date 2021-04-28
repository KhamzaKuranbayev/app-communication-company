package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SimCardDto {

    private Integer code;           // simkarta kodi : 94, 93, 97, ...
    private UUID customerId;
    private Integer companyId;
}

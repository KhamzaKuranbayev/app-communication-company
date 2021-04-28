package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

@Data
public class SimCardTariffDto {

    private Integer simCardId;
    private Integer tariffId;
    private boolean status;
}

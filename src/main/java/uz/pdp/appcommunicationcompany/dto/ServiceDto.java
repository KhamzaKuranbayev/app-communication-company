package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

@Data
public class ServiceDto {

    private String title;
    private String description;
    private Integer detailId;
    private String ussd;
    private double price;
    private Integer countDateOfExpire;
}

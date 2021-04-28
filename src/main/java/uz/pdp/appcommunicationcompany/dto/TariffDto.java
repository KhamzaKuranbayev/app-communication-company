package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

import java.util.Set;

@Data
public class TariffDto {

    private String title;
    private String description;
    private Set<Integer> detailIdSet;
    private String ussd;
    private double price;
    private Integer countDateOfExpire;
}

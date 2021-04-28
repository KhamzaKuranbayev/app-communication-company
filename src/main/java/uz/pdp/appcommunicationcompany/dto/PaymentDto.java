package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private String phoneNumber;
    private double amount;
    private String paymentType;
    private String owner;
}

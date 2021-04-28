package uz.pdp.appcommunicationcompany.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {

    private Integer parentCompanyId;

    @NotNull
    private String name;

    @NotNull
    private AddressDto addressDto;
}

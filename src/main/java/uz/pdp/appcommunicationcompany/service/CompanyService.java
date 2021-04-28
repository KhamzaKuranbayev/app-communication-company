package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.CompanyDto;
import uz.pdp.appcommunicationcompany.entity.Response;

@Service
public interface CompanyService {

    Response addCompany(CompanyDto companyDto);
}

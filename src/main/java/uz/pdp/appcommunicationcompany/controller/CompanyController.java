package uz.pdp.appcommunicationcompany.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcommunicationcompany.dto.CompanyDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.service.CompanyService;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // Companyani va filialni faqat HEAD_OF_COMPANY qo'shadigan qildim
    @PostMapping
    public HttpEntity<?> addCompany(@RequestBody CompanyDto companyDto) {
        Response response = companyService.addCompany(companyDto);
        return ResponseEntity.ok(response);
    }
}

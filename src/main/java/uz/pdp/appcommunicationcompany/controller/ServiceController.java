package uz.pdp.appcommunicationcompany.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.dto.DetailDto;
import uz.pdp.appcommunicationcompany.dto.ServiceDto;
import uz.pdp.appcommunicationcompany.dto.TariffDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.entity.ServiceType;
import uz.pdp.appcommunicationcompany.service.ServiceSvc;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    final ServiceSvc serviceSvc;

    public ServiceController(ServiceSvc serviceSvc) {
        this.serviceSvc = serviceSvc;
    }

    @PostMapping
    public HttpEntity<?> saveService(@RequestBody ServiceDto serviceDto) {
        Response response = serviceSvc.saveService(serviceDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @PostMapping("/tariff")
    public HttpEntity<?> saveTariff(@RequestBody TariffDto tariffDto) {
        Response response = serviceSvc.saveTariff(tariffDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @PostMapping("/serviceType")
    public HttpEntity<?> saveServiceType(@RequestBody ServiceType serviceType) {
        Response response = serviceSvc.saveServiceType(serviceType);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @PutMapping("/serviceType/{id}")
    public HttpEntity<?> editServiceType(@PathVariable(name = "id") Integer serviceTypeId, @RequestBody ServiceType serviceType) {
        Response response = serviceSvc.editServiceType(serviceTypeId, serviceType);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED).body(response);
    }

    @DeleteMapping("/serviceType/{id}")
    public HttpEntity<?> deleteServiceType(@PathVariable(name = "id") Integer serviceTypeId) {
        Response response = serviceSvc.deleteServiceType(serviceTypeId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/serviceDetail")
    public HttpEntity<?> saveServiceDetail(@RequestBody DetailDto detailDto) {
        Response response = serviceSvc.saveServiceDetail(detailDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
}

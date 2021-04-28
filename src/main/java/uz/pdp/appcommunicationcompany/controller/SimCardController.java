package uz.pdp.appcommunicationcompany.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appcommunicationcompany.dto.SimCardDto;
import uz.pdp.appcommunicationcompany.dto.SimCardServiceDto;
import uz.pdp.appcommunicationcompany.dto.SimCardTariffDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.service.SimCardService;

@RestController
@RequestMapping("/api/simcard")
public class SimCardController {

    final SimCardService simCardService;

    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PostMapping
    public HttpEntity<?> registerSimCard(@RequestBody SimCardDto simCardDto) {
        Response response = simCardService.registerSimCard(simCardDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @PostMapping("/service")
    public HttpEntity<?> connectServiceToSimCard(@RequestBody SimCardServiceDto simCardServiceDto) {
        Response response = simCardService.connectServiceToSimCard(simCardServiceDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @PostMapping("/tariff")
    public HttpEntity<?> connectTariffToSimCard(@RequestBody SimCardTariffDto simCardTariffDto) {
        Response response = simCardService.connectTariffToSimCard(simCardTariffDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }

    @GetMapping("/service")
    public HttpEntity<?> findAllConnectedService() {
        return ResponseEntity.ok(simCardService.findAllConnectedService());
    }

    @GetMapping("/tariff")
    public HttpEntity<?> findAllConnectedTariff() {
        return ResponseEntity.ok(simCardService.findAllConnectedTariff());
    }

}

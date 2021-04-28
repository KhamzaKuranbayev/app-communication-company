package uz.pdp.appcommunicationcompany.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcommunicationcompany.dto.TurniketDto;
import uz.pdp.appcommunicationcompany.dto.TurniketHistoryDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.service.TurniketService;

@RestController
@RequestMapping("/api/turniket")
public class TurniketController {

    final TurniketService turniketService;

    public TurniketController(TurniketService turniketService) {
        this.turniketService = turniketService;
    }

    // Create new turniket
    @PostMapping
    public HttpEntity<?> addTurniket(@RequestBody TurniketDto turniketDto) {
        Response response = turniketService.addTurniket(turniketDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }


    // enter - exit operations
    @PostMapping("/operation")
    public HttpEntity<?> doOperation(@RequestBody TurniketHistoryDto turniketHistoryDto) {
        Response response = turniketService.doOperation(turniketHistoryDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 401).body(response);
    }
}

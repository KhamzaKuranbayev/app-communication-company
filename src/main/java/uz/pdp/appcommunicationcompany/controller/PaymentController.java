package uz.pdp.appcommunicationcompany.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcommunicationcompany.dto.PaymentDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping
    public HttpEntity<?> doTransaction(@RequestBody PaymentDto paymentDto) {
        Response response = paymentService.doTransaction(paymentDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
}

package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.PaymentDto;
import uz.pdp.appcommunicationcompany.entity.Response;

@Service
public interface PaymentService {

    Response doTransaction(PaymentDto paymentDto);
}

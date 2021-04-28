package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.PaymentDto;
import uz.pdp.appcommunicationcompany.entity.Payment;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.entity.SimCard;
import uz.pdp.appcommunicationcompany.repository.PaymentRepository;
import uz.pdp.appcommunicationcompany.repository.SimCardRepository;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    final PaymentRepository paymentRepository;
    final SimCardRepository simCardRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              SimCardRepository simCardRepository) {
        this.paymentRepository = paymentRepository;
        this.simCardRepository = simCardRepository;
    }

    @Override
    public Response doTransaction(PaymentDto paymentDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(paymentDto.getPhoneNumber());
        if(!optionalSimCard.isPresent())
            return new Response("Such phone number was not found!", false);

        double balance = optionalSimCard.get().getBalance();
        double summa = balance + paymentDto.getAmount();
        optionalSimCard.get().setBalance(summa);
        simCardRepository.save(optionalSimCard.get());

        Payment payment = new Payment();
        payment.setSimCard(optionalSimCard.get());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setOwner(paymentDto.getOwner());

        paymentRepository.save(payment);
        return new Response("Successfully completed!", true);
    }
}

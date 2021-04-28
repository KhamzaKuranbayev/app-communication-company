package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.SimCardDto;
import uz.pdp.appcommunicationcompany.dto.SimCardServiceDto;
import uz.pdp.appcommunicationcompany.dto.SimCardTariffDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.entity.SimCardTariff;

import java.util.List;

@Service
public interface SimCardService {

    Response registerSimCard(SimCardDto simCardDto);

    Response connectServiceToSimCard(SimCardServiceDto simCardServiceDto);

    Response connectTariffToSimCard(SimCardTariffDto simCardTariffDto);

    List<uz.pdp.appcommunicationcompany.entity.SimCardService> findAllConnectedService();

    List<SimCardTariff> findAllConnectedTariff();
}

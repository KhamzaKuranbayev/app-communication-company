package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.TurniketDto;
import uz.pdp.appcommunicationcompany.dto.TurniketHistoryDto;
import uz.pdp.appcommunicationcompany.entity.Response;

@Service
public interface TurniketService {

    Response addTurniket(TurniketDto turniketDto);

    Response doOperation(TurniketHistoryDto turniketHistoryDto);


}

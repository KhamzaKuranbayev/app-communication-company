package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.DetailDto;
import uz.pdp.appcommunicationcompany.dto.ServiceDto;
import uz.pdp.appcommunicationcompany.dto.TariffDto;
import uz.pdp.appcommunicationcompany.entity.Response;
import uz.pdp.appcommunicationcompany.entity.ServiceType;

@Service
public interface ServiceSvc {

    Response saveService(ServiceDto serviceDto);

    Response saveTariff(TariffDto tariffDto);

    Response saveServiceType(ServiceType serviceType);

    Response editServiceType(Integer serviceTypeId, ServiceType serviceType);

    Response deleteServiceType(Integer serviceTypeId);

    Response saveServiceDetail(DetailDto detailDto);

}

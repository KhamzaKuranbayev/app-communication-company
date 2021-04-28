package uz.pdp.appcommunicationcompany.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.DetailDto;
import uz.pdp.appcommunicationcompany.dto.ServiceDto;
import uz.pdp.appcommunicationcompany.dto.TariffDto;
import uz.pdp.appcommunicationcompany.entity.*;
import uz.pdp.appcommunicationcompany.entity.enums.RoleName;
import uz.pdp.appcommunicationcompany.repository.*;

import java.util.*;

@Service
public class ServiceImpl implements ServiceSvc {


    final ServiceTypeRepository serviceTypeRepository;
    final ServiceDetailRepository serviceDetailRepository;
    final ServiceRepository serviceRepository;
    final TariffRepository tariffRepository;

    public ServiceImpl(ServiceTypeRepository serviceTypeRepository,
                       ServiceDetailRepository serviceDetailRepository,
                       ServiceRepository serviceRepository,
                       TariffRepository tariffRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceDetailRepository = serviceDetailRepository;
        this.serviceRepository = serviceRepository;
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Response saveService(ServiceDto serviceDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {

                    Optional<Detail> optionalDetail = serviceDetailRepository.findById(serviceDto.getDetailId());
                    if (!optionalDetail.isPresent())
                        return new Response("Detail id was not found!", false);

                    if (serviceRepository.existsByUssd(serviceDto.getUssd()))
                        return new Response("Such USSD code already exists!", false);

                    uz.pdp.appcommunicationcompany.entity.Service service = new uz.pdp.appcommunicationcompany.entity.Service();
                    service.setTitle(serviceDto.getTitle());
                    service.setDescription(serviceDto.getDescription());
                    service.setPrice(serviceDto.getPrice());
                    service.setCountDateOfExpire(serviceDto.getCountDateOfExpire());
                    service.setDetail(optionalDetail.get());
                    service.setUssd(serviceDto.getUssd());
                    serviceRepository.save(service);
                    return new Response("New Service was successfully created!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public Response saveTariff(TariffDto tariffDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {

                    if (tariffRepository.existsByUssd(tariffDto.getUssd()))
                        return new Response("Such USSD code already exists!", false);

                    Tariff tariff = new Tariff();
                    tariff.setTitle(tariffDto.getTitle());
                    tariff.setDescription(tariffDto.getDescription());
                    tariff.setPrice(tariffDto.getPrice());
                    tariff.setCountDateOfExpire(tariffDto.getCountDateOfExpire());

                    Set<Integer> detailIdSet = tariffDto.getDetailIdSet();
                    Set<Detail> detailSet = new HashSet<>();
                    for (Integer detailId : detailIdSet) {
                        Optional<Detail> optionalDetail = serviceDetailRepository.findById(detailId);
                        optionalDetail.ifPresent(detailSet::add);
                    }
                    tariff.setDetail(detailSet);
                    tariff.setUssd(tariffDto.getUssd());
                    tariffRepository.save(tariff);
                    return new Response("New Tariff was successfully created!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public Response saveServiceType(ServiceType serviceType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {
                    serviceTypeRepository.save(serviceType);
                    return new Response("Service Type saved!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }

    @Override
    public Response editServiceType(Integer serviceTypeId, ServiceType serviceType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {

                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceTypeId);
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    optionalServiceType.get().setName(serviceType.getName());
                    optionalServiceType.get().setDescription(serviceType.getDescription());
                    serviceTypeRepository.save(optionalServiceType.get());

                    return new Response("Service Type updated!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }

    @Override
    public Response deleteServiceType(Integer serviceTypeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {
                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceTypeId);
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    serviceTypeRepository.deleteById(serviceTypeId);
                    return new Response("Service Type deleted!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public Response saveServiceDetail(DetailDto detailDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.TARIFF_MANAGER)) {
                    Detail detail = new Detail();
                    detail.setAmount(detailDto.getAmount());

                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(detailDto.getServiceTypeId());
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    detail.setServiceType(optionalServiceType.get());
                    serviceDetailRepository.save(detail);
                    return new Response("Service Detail saved!", true);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }
}

package uz.pdp.appcommunicationcompany.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.SimCardDto;
import uz.pdp.appcommunicationcompany.dto.SimCardServiceDto;
import uz.pdp.appcommunicationcompany.dto.SimCardTariffDto;
import uz.pdp.appcommunicationcompany.entity.*;
import uz.pdp.appcommunicationcompany.entity.enums.RoleName;
import uz.pdp.appcommunicationcompany.repository.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class SimCardServiceImpl implements SimCardService{

    final SimCardRepository simCardRepository;
    final UserRepository userRepository;
    final CompanyRepository companyRepository;
    final ServiceRepository serviceRepository;
    final SimCardServiceRepository simCardServiceRepository;
    final TariffRepository tariffRepository;
    final SimCardTariffRepository simCardTariffRepository;

    public SimCardServiceImpl(SimCardRepository simCardRepository,
                              UserRepository userRepository,
                              CompanyRepository companyRepository,
                              ServiceRepository serviceRepository,
                              SimCardServiceRepository simCardServiceRepository,
                              TariffRepository tariffRepository,
                              SimCardTariffRepository simCardTariffRepository) {
        this.simCardRepository = simCardRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.serviceRepository = serviceRepository;
        this.simCardServiceRepository = simCardServiceRepository;
        this.tariffRepository = tariffRepository;
        this.simCardTariffRepository = simCardTariffRepository;
    }

    @Override
    public Response registerSimCard(SimCardDto simCardDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.PHONE_NUMBERS_MANAGER)) {
                    SimCard simCard = new SimCard();
                    String generatePhoneNumber = generatePhoneNumber(simCardDto.getCode().toString());
                    simCard.setPhoneNumber(generatePhoneNumber);

                    Optional<User> optionalCustomer = userRepository.findById(simCardDto.getCustomerId());
                    if(!optionalCustomer.isPresent())
                        return new Response("Customer Id was not found!", false);
                    simCard.setCustomer(optionalCustomer.get());

                    Optional<Company> optionalCompany = companyRepository.findById(simCardDto.getCompanyId());
                    if(!optionalCompany.isPresent())
                        return new Response("Company Id was not found!", false);
                    simCard.setCompany(optionalCompany.get());

                    simCardRepository.save(simCard);
                    return new Response("Simcard saved! Phone number: " + generatePhoneNumber + " to " + optionalCustomer.get().getFirstname(), true);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public Response connectServiceToSimCard(SimCardServiceDto simCardServiceDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.PHONE_NUMBERS_MANAGER) || role.getRoleName().equals(RoleName.CUSTOMER)) {
                    uz.pdp.appcommunicationcompany.entity.SimCardService simCardService =
                            new uz.pdp.appcommunicationcompany.entity.SimCardService();
                    Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardServiceDto.getSimCardId());
                    if(!optionalSimCard.isPresent())
                        return new Response("SimCard id was not found!", false);
                    simCardService.setSimCard(optionalSimCard.get());

                    Optional<uz.pdp.appcommunicationcompany.entity.Service> optionalService
                            = serviceRepository.findById(simCardServiceDto.getServiceId());
                    if(!optionalService.isPresent())
                        return new Response("Service id was not found!", false);
                    simCardService.setService(optionalService.get());

                    // xarid qilingan xizmat narxi tekshirilib, hisobdan mablag' yechilishi
                    double service_price = optionalService.get().getPrice();
                    double balance = optionalSimCard.get().getBalance();
                    if(service_price >= balance) {
                        double newBalance = balance - service_price;
                        optionalSimCard.get().setBalance(newBalance);
                        simCardRepository.save(optionalSimCard.get());
                    } else {
                        return new Response("You don't have enough balance for buy this service!", false);
                    }


                    simCardServiceRepository.save(simCardService);
                    return new Response("To " + optionalSimCard.get().getPhoneNumber() + " connected " + optionalService.get().getTitle(), true);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public Response connectTariffToSimCard(SimCardTariffDto simCardTariffDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.PHONE_NUMBERS_MANAGER) || role.getRoleName().equals(RoleName.CUSTOMER)) {
                    SimCardTariff simCardTariff =
                            new SimCardTariff();
                    Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardTariffDto.getSimCardId());
                    if(!optionalSimCard.isPresent())
                        return new Response("SimCard id was not found!", false);
                    simCardTariff.setSimCard(optionalSimCard.get());

                    Optional<Tariff> optionalTariff
                            = tariffRepository.findById(simCardTariffDto.getTariffId());
                    if(!optionalTariff.isPresent())
                        return new Response("Tariff id was not found!", false);
                    simCardTariff.setTariff(optionalTariff.get());

                    // xarid qilingan tariff narxi tekshirilib, hisobdan mablag' yechilishi
                    double tariff_price = optionalTariff.get().getPrice();
                    double balance = optionalSimCard.get().getBalance();
                    if(tariff_price >= balance) {
                        double newBalance = balance - tariff_price;
                        optionalSimCard.get().setBalance(newBalance);
                        simCardRepository.save(optionalSimCard.get());
                    } else {
                        return new Response("You don't have enough balance for buy this tariff!", false);
                    }

                    simCardTariffRepository.save(simCardTariff);
                    return new Response("To " + optionalSimCard.get().getPhoneNumber() + " connected " + optionalTariff.get().getTitle(), true);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    @Override
    public List<uz.pdp.appcommunicationcompany.entity.SimCardService> findAllConnectedService() {
        return simCardServiceRepository.findAll();
    }

    @Override
    public List<SimCardTariff> findAllConnectedTariff() {
        return simCardTariffRepository.findAll();
    }


    // Telefon raqamni tizimda takrorlanmas generatsiya qilinadigan qildim
    private String generatePhoneNumber(String code) {
        String nums = "0123456789";
        Random random = new Random();

        String result = "";

        for(int i = 1; i <= 7; i++) {
            char c = nums.charAt(random.nextInt(10));
            result += c;

            if(i == 7) {
                String full_phone_number = "+998" + code + result;
                boolean b = simCardRepository.existsByPhoneNumber(full_phone_number);
                if(b) {
                    i = 0;
                } else {
                  return full_phone_number;
                }
            }
        }
        return "";

    }
}

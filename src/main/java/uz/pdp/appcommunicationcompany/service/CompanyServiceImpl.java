package uz.pdp.appcommunicationcompany.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.AddressDto;
import uz.pdp.appcommunicationcompany.dto.CompanyDto;
import uz.pdp.appcommunicationcompany.entity.*;
import uz.pdp.appcommunicationcompany.entity.enums.RoleName;
import uz.pdp.appcommunicationcompany.repository.AddressRepository;
import uz.pdp.appcommunicationcompany.repository.CompanyRepository;
import uz.pdp.appcommunicationcompany.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class CompanyServiceImpl implements CompanyService {

    final CompanyRepository companyRepository;
    final AddressRepository addressRepository;
    final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Response addCompany(CompanyDto companyDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.HEAD_OF_COMPANY)) {
                    Company company = new Company();

                    if (companyDto.getParentCompanyId() != null) {
                        Optional<Company> optionalParentCompany = companyRepository.findById(companyDto.getParentCompanyId());
                        if (!optionalParentCompany.isPresent())
                            return new Response("Parent Company id was not found!", false);

                        company.setParentCompany(optionalParentCompany.get());
                    }


                    AddressDto addressDto = companyDto.getAddressDto();
                    Address address = new Address();
                    address.setCity(addressDto.getCity());
                    address.setStreet(addressDto.getStreet());
                    address.setCompany(company);

                    company.setAddress(address);
                    company.setName(companyDto.getName());
                    Company savedCompany = companyRepository.save(company);

                    if (companyDto.getParentCompanyId() == null && userContext.getCompany() == null) {
                        Optional<User> optionalUser = userRepository.findById(userContext.getId());
                        if(optionalUser.isPresent()){
                            optionalUser.get().setCompany(savedCompany);
                            userRepository.save(optionalUser.get());
                        }
                    }

                    return new Response("Company saved!", true);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }
}

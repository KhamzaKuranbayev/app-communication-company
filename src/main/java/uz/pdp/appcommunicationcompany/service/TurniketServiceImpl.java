package uz.pdp.appcommunicationcompany.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.TurniketDto;
import uz.pdp.appcommunicationcompany.dto.TurniketHistoryDto;
import uz.pdp.appcommunicationcompany.entity.*;
import uz.pdp.appcommunicationcompany.entity.enums.RoleName;
import uz.pdp.appcommunicationcompany.entity.enums.TurniketType;
import uz.pdp.appcommunicationcompany.repository.AddressRepository;
import uz.pdp.appcommunicationcompany.repository.TurniketHistoryRepository;
import uz.pdp.appcommunicationcompany.repository.TurniketRepository;
import uz.pdp.appcommunicationcompany.repository.UserRepository;

import java.util.Optional;
import java.util.Set;


@Service
public class TurniketServiceImpl implements TurniketService {


    final UserRepository userRepository;
    final TurniketHistoryRepository turniketHistoryRepository;
    final TurniketRepository turniketRepository;
    final AddressRepository addressRepository;

    public TurniketServiceImpl(UserRepository userRepository,
                               TurniketHistoryRepository turniketHistoryRepository,
                               TurniketRepository turniketRepository,
                               AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.turniketHistoryRepository = turniketHistoryRepository;
        this.turniketRepository = turniketRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Response addTurniket(TurniketDto turniketDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {

            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleName.HEAD_OF_COMPANY)) {
                    Turniket turniket = new Turniket();

                    Optional<Address> optionalAddress = addressRepository.findById(turniketDto.getLocationId());
                    if (!optionalAddress.isPresent())
                        return new Response("Address id was not found!", false);

                    turniket.setLocation(optionalAddress.get());
                    turniketRepository.save(turniket);
                    return new Response("New Turniket added!", true);
                }
            }
        }

        return new Response("Authorization empty!", false);
    }

    @Override
    public Response doOperation(TurniketHistoryDto turniketHistoryDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.isAuthenticated()
                && !authentication.getPrincipal().equals("anonymousUser")) {
            User user = (User) authentication.getPrincipal();

            TurniketHistory turniketHistory = new TurniketHistory();
            turniketHistory.setCreatedBy(user.getId());

            Optional<Turniket> optionalTurniket = turniketRepository.findById(turniketHistoryDto.getTurniketId());
            if (!optionalTurniket.isPresent())
                return new Response("Turniket id was not found!", false);

            turniketHistory.setTurniket(optionalTurniket.get());
            turniketHistory.setType(TurniketType.values()[turniketHistoryDto.getType()]);

            turniketHistoryRepository.save(turniketHistory);
            return new Response("Operation saved! Employee:" + user.getFirstname() + " Operation Type: " + TurniketType.values()[turniketHistoryDto.getType()], true);
        }

        return new Response("Authorization empty!", false);
    }
}

package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.Address;
import uz.pdp.appcommunicationcompany.entity.SimCard;

import java.util.Optional;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Integer> {

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<SimCard> findByPhoneNumber(String phoneNumber);

}

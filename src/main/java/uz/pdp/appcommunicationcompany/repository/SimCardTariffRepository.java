package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.Address;
import uz.pdp.appcommunicationcompany.entity.SimCardTariff;

@Repository
public interface SimCardTariffRepository extends JpaRepository<SimCardTariff, Integer> {
}

package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.Address;
import uz.pdp.appcommunicationcompany.entity.SimCardService;

@Repository
public interface SimCardServiceRepository extends JpaRepository<SimCardService, Integer> {
}

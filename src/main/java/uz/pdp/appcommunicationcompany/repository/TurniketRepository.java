package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.Turniket;
import uz.pdp.appcommunicationcompany.entity.TurniketHistory;

@Repository
public interface TurniketRepository extends JpaRepository<Turniket, Integer> {
}

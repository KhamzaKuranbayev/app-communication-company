package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.TurniketHistory;

@Repository
public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, Integer> {
}

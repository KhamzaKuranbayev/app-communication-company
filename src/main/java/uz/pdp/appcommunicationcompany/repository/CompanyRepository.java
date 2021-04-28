package uz.pdp.appcommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcommunicationcompany.entity.Company;
import uz.pdp.appcommunicationcompany.entity.Passport;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}

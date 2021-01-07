package karolh95.classicmodels.repositories;

import karolh95.classicmodels.models.Productline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductlineRepository extends JpaRepository<Productline, String> {
}

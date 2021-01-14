package karolh95.classicmodels.repositories;

import karolh95.classicmodels.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAllByProductLine(String productLine, Pageable pageable);
}

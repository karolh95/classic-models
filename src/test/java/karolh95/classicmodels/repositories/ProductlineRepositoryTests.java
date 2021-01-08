package karolh95.classicmodels.repositories;

import karolh95.classicmodels.models.Productline;
import karolh95.classicmodels.utils.ProductlineFactory;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductlineRepositoryTests {

    @Autowired
    private ProductlineRepository repository;

    @Test
    public void shouldSaveProductline(){
        Productline productline = ProductlineFactory.getProductline();

        productline = repository.save(productline);

        assertNotNull(productline);
    }
}

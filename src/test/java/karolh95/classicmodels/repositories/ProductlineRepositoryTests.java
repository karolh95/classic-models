package karolh95.classicmodels.repositories;

import karolh95.classicmodels.models.Productline;
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
        String PRODUCTLINE = "Classic Cars";
        String TEXT = "Attention car enthusiasts: Make your wildest car ownership dreams come true. Whether you are looking for classic muscle cars, dream sports cars or movie-inspired miniatures, you will find great choices in this category. These replicas feature superb attention to detail and craftsmanship and offer features such as working steering system, opening forward compartment, opening rear trunk with removable spare wheel, 4-wheel independent spring suspension, and so on. The models range in size from 1:10 to 1:24 scale and include numerous limited edition and several out-of-production vehicles. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.";
        String HTML = null;
        byte[] IMAGE = new byte[0];

        Productline productline = new Productline();
        productline.setProductLine(PRODUCTLINE);
        productline.setTextDescription(TEXT);
        productline.setHtmlDescription(HTML);
        productline.setImage(IMAGE);

        productline = repository.save(productline);

        assertNotNull(productline);
    }
}

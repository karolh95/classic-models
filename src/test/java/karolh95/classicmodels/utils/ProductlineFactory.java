package karolh95.classicmodels.utils;

import karolh95.classicmodels.dto.ProductlineDTO;
import karolh95.classicmodels.models.Productline;

public class ProductlineFactory {

    final static String PRODUCTLINE = "Classic Cars";
    final static String TEXT = "Attention car enthusiasts: Make your wildest car ownership dreams come true. Whether you are looking for classic muscle cars, dream sports cars or movie-inspired miniatures, you will find great choices in this category. These replicas feature superb attention to detail and craftsmanship and offer features such as working steering system, opening forward compartment, opening rear trunk with removable spare wheel, 4-wheel independent spring suspension, and so on. The models range in size from 1:10 to 1:24 scale and include numerous limited edition and several out-of-production vehicles. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.";
    final static String HTML = "<p>Attention car enthusiasts...</p>";
    final static byte[] IMAGE = new byte[0];

    public static Productline getProductline(){
        Productline productline = new Productline();
        productline.setProductLine(PRODUCTLINE);
        productline.setTextDescription(TEXT);
        productline.setHtmlDescription(HTML);
        productline.setImage(IMAGE);

        return productline ;
    }

    public static ProductlineDTO getPoductlineDto(){
        ProductlineDTO dto = new ProductlineDTO();
        dto.setProductLine(PRODUCTLINE);
        dto.setTextDescription(TEXT);
        dto.setHtmlDescription(HTML);
        dto.setImage(IMAGE);

        return dto ;
    }
}

package karolh95.classicmodels.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(length = 15)
    private String productCode;

    @Column(length = 70)
    private String productName;

    @Column(insertable = false, updatable = false)
    private String productLine;

    @Column(length = 10)
    private String productScale;

    @Column(length = 50)
    private String productVendor;

    @Column(columnDefinition = "text")
    private String productDescription;

    private Short quantityInStock;

    @Column(precision = 10, scale = 2)
    private BigDecimal buyPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal MSRP;

    @ManyToOne
    @JoinColumn(name = Product_.PRODUCT_LINE)
    private Productline productline;
}

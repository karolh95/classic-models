package karolh95.classicmodels.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productlines")
public class Productline {

    @Id
    @Column(length = 50)
    private String productLine;

    @Column(length = 4000)
    private String textDescription;

    @Column(columnDefinition = "mediumtext")
    private String htmlDescription;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] image;

    @OneToMany(mappedBy = Product_.PRODUCTLINE)
    private List<Product> products;
}

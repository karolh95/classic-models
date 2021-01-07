package karolh95.classicmodels.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Productline {

    private String productLine;
    private String textDescription;
    private String htmlDescription;
    private byte[] image;
}

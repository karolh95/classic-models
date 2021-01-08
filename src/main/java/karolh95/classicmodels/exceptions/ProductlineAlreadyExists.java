package karolh95.classicmodels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ProductlineAlreadyExists.MESSAGE)
public class ProductlineAlreadyExists extends RuntimeException {

    public static final String MESSAGE = "Productline already exists";

    public ProductlineAlreadyExists(){
        super(MESSAGE);
    }
}

package karolh95.classicmodels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ProductlineNotFoundException.MESSAGE)
public class ProductlineNotFoundException extends RuntimeException {

    static final String MESSAGE = "Productline not found";

    public ProductlineNotFoundException(){
        super(MESSAGE);
    }
}

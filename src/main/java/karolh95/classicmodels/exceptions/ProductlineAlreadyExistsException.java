package karolh95.classicmodels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ProductlineAlreadyExistsException.MESSAGE)
public class ProductlineAlreadyExistsException extends RuntimeException {

    public static final String MESSAGE = "Productline already exists";

    public ProductlineAlreadyExistsException(){
        super(MESSAGE);
    }
}

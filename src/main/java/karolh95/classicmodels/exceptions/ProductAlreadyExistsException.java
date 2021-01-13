package karolh95.classicmodels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ProductAlreadyExistsException.MESSAGE)
public class ProductAlreadyExistsException extends RuntimeException {

    public static final String MESSAGE = "Product already exists";

    public ProductAlreadyExistsException() {
        super(MESSAGE);
    }
}

package karolh95.classicmodels.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ProductNotFoundException.MESSAGE)
public class ProductNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Product not found";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}

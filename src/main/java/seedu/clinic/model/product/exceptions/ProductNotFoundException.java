package seedu.clinic.model.product.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("The product is not found!");
    }
}

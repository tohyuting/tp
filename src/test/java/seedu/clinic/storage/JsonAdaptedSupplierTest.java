package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.storage.JsonAdaptedSupplier.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;

public class JsonAdaptedSupplierTest {
    private static final String INVALID_NAME = "*!Rachel @";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PRODUCT_NAME = " ";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_PRODUCT_TAG = "friend";
    private static final List<JsonAdaptedProduct> VALID_PRODUCTS = BENSON.getProducts().stream()
            .map(JsonAdaptedProduct::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validSupplierDetails_returnsSupplier() throws Exception {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(BENSON);
        assertEquals(BENSON, supplier.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(null, VALID_PHONE, VALID_EMAIL,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, null, VALID_EMAIL,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, null,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedSupplier supplier = new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidProductsTag_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        JsonAdaptedProduct invalidProduct = new JsonAdaptedProduct(VALID_NAME,
                List.of(new JsonAdaptedTag(INVALID_TAG)), 0);
        invalidProducts.add(invalidProduct);
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_REMARK, invalidProducts);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }

    @Test
    public void toModelType_invalidProductsName_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        JsonAdaptedProduct invalidProduct = new JsonAdaptedProduct(INVALID_PRODUCT_NAME,
                List.of(new JsonAdaptedTag(VALID_PRODUCT_TAG)), 0);
        invalidProducts.add(invalidProduct);
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_REMARK, invalidProducts);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }

    @Test
    public void toModelType_nullProductsName_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        JsonAdaptedProduct invalidProduct = new JsonAdaptedProduct(null,
                List.of(new JsonAdaptedTag(INVALID_TAG)), 0);
        invalidProducts.add(invalidProduct);
        JsonAdaptedSupplier supplier =
                new JsonAdaptedSupplier(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_REMARK, invalidProducts);
        assertThrows(IllegalValueException.class, supplier::toModelType);
    }
}

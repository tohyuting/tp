package seedu.clinic.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.storage.JsonAdaptedWarehouse.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalWarehouse.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.exceptions.IllegalValueException;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;

public class JsonAdaptedWarehouseTest {
    private static final String INVALID_NAME = "*Rachel @";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_ADDRESS = " example.com";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final List<JsonAdaptedProduct> VALID_PRODUCTS = BENSON.getProducts().stream()
            .map(JsonAdaptedProduct::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWarehouseDetails_returnsWarehouse() throws Exception {
        JsonAdaptedWarehouse warehouse = new JsonAdaptedWarehouse(BENSON);
        assertEquals(BENSON, warehouse.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(INVALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse = new JsonAdaptedWarehouse(null, VALID_PHONE, VALID_ADDRESS,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(VALID_NAME, INVALID_PHONE, VALID_ADDRESS, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse = new JsonAdaptedWarehouse(VALID_NAME, null, VALID_ADDRESS,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, INVALID_ADDRESS, VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse = new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, null,
                VALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_invalidRemark_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, VALID_ADDRESS, INVALID_REMARK, VALID_PRODUCTS);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        JsonAdaptedWarehouse warehouse = new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, VALID_ADDRESS, null,
                VALID_PRODUCTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, warehouse::toModelType);
    }

    @Test
    public void toModelType_invalidProductsQuantity_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        JsonAdaptedProduct invalidProduct = new JsonAdaptedProduct(VALID_NAME,
                new ArrayList<>(), -1);
        invalidProducts.add(invalidProduct);
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_REMARK, invalidProducts);
        assertThrows(IllegalValueException.class, warehouse::toModelType);
    }

    @Test
    public void toModelType_nullProductsName_throwsIllegalValueException() {
        List<JsonAdaptedProduct> invalidProducts = new ArrayList<>(VALID_PRODUCTS);
        JsonAdaptedProduct invalidProduct = new JsonAdaptedProduct(null,
                new ArrayList<>(), 10);
        invalidProducts.add(invalidProduct);
        JsonAdaptedWarehouse warehouse =
                new JsonAdaptedWarehouse(VALID_NAME, VALID_PHONE, VALID_ADDRESS, VALID_REMARK, invalidProducts);
        assertThrows(IllegalValueException.class, warehouse::toModelType);
    }
}

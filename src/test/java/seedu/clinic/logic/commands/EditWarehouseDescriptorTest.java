package seedu.clinic.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_A;
import static seedu.clinic.logic.commands.CommandTestUtil.DESC_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_ADDRESS_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_NAME_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PHONE_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_NAME_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_PRODUCT_QUANTITY_B;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_WAREHOUSE_REMARK_B;

import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.EditCommand.EditWarehouseDescriptor;
import seedu.clinic.testutil.EditWarehouseDescriptorBuilder;

public class EditWarehouseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditWarehouseDescriptor descriptorWithSameValues = new EditWarehouseDescriptor(DESC_A);
        assertTrue(DESC_A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_A.equals(DESC_A));

        // null -> returns false
        assertFalse(DESC_A.equals(null));

        // different types -> returns false
        assertFalse(DESC_A.equals(5));

        // different values -> returns false
        assertFalse(DESC_A.equals(DESC_B));

        // different name -> returns false
        EditWarehouseDescriptor editedA = new EditWarehouseDescriptorBuilder(DESC_A)
                .withName(VALID_WAREHOUSE_NAME_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different phone -> returns false
        editedA = new EditWarehouseDescriptorBuilder(DESC_A).withPhone(VALID_WAREHOUSE_PHONE_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different email -> returns false
        editedA = new EditWarehouseDescriptorBuilder(DESC_A).withAddress(VALID_WAREHOUSE_ADDRESS_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different remark -> returns false
        editedA = new EditWarehouseDescriptorBuilder(DESC_A).withRemark(VALID_WAREHOUSE_REMARK_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different products -> returns false
        editedA = new EditWarehouseDescriptorBuilder(DESC_A)
                .withProducts(Map.of(VALID_WAREHOUSE_PRODUCT_NAME_B, VALID_WAREHOUSE_PRODUCT_QUANTITY_B)).build();
        assertFalse(DESC_A.equals(editedA));
    }
}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSupplier.ALICE;
import static seedu.address.testutil.TypicalSupplier.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.exceptions.DuplicateSupplierException;
import seedu.address.testutil.SupplierBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getSupplierList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateSuppliers_throwsDuplicateSupplierException() {
        // Two suppliers with the same identity fields
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Supplier> newSuppliers = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newSuppliers);

        assertThrows(DuplicateSupplierException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasSupplier_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSupplier(null));
    }

    @Test
    public void hasSupplier_supplierNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        assertTrue(addressBook.hasSupplier(ALICE));
    }

    @Test
    public void hasSupplier_supplierWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSupplier(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSupplier(editedAlice));
    }

    @Test
    public void getSupplierList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSupplierList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose suppliers list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Supplier> suppliers = FXCollections.observableArrayList();

        AddressBookStub(Collection<Supplier> suppliers) {
            this.suppliers.setAll(suppliers);
        }

        @Override
        public ObservableList<Supplier> getSupplierList() {
            return suppliers;
        }
    }

}

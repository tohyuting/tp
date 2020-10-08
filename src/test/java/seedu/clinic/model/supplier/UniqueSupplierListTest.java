package seedu.clinic.model.supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_PRODUCT_NAME_ASPIRIN;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.clinic.logic.commands.CommandTestUtil.VALID_TAG_ANTIBIOTICS;
import static seedu.clinic.testutil.Assert.assertThrows;
import static seedu.clinic.testutil.TypicalSupplier.ALICE;
import static seedu.clinic.testutil.TypicalSupplier.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.supplier.exceptions.DuplicateSupplierException;
import seedu.clinic.model.supplier.exceptions.SupplierNotFoundException;
import seedu.clinic.testutil.SupplierBuilder;

public class UniqueSupplierListTest {

    private final UniqueSupplierList uniqueSupplierList = new UniqueSupplierList();

    @Test
    public void contains_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.contains(null));
    }

    @Test
    public void contains_supplierNotInList_returnsFalse() {
        assertFalse(uniqueSupplierList.contains(ALICE));
    }

    @Test
    public void contains_supplierInList_returnsTrue() {
        uniqueSupplierList.add(ALICE);
        assertTrue(uniqueSupplierList.contains(ALICE));
    }

    @Test
    public void contains_supplierWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSupplierList.add(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        assertTrue(uniqueSupplierList.contains(editedAlice));
    }

    @Test
    public void add_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.add(null));
    }

    @Test
    public void add_duplicateSupplier_throwsDuplicateSupplierException() {
        uniqueSupplierList.add(ALICE);
        assertThrows(DuplicateSupplierException.class, () -> uniqueSupplierList.add(ALICE));
    }

    @Test
    public void setSupplier_nullTargetSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSupplier(null, ALICE));
    }

    @Test
    public void setSupplier_nullEditedSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSupplier(ALICE, null));
    }

    @Test
    public void setSupplier_targetSupplierNotInList_throwsSupplierNotFoundException() {
        assertThrows(SupplierNotFoundException.class, () -> uniqueSupplierList.setSupplier(ALICE, ALICE));
    }

    @Test
    public void setSupplier_editedSupplierIsSameSupplier_success() {
        uniqueSupplierList.add(ALICE);
        uniqueSupplierList.setSupplier(ALICE, ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(ALICE);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasSameIdentity_success() {
        uniqueSupplierList.add(ALICE);
        Supplier editedAlice = new SupplierBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withProducts(Map.of(VALID_PRODUCT_NAME_ASPIRIN, new String[]{VALID_TAG_ANTIBIOTICS}))
                .build();
        uniqueSupplierList.setSupplier(ALICE, editedAlice);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(editedAlice);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasDifferentIdentity_success() {
        uniqueSupplierList.add(ALICE);
        uniqueSupplierList.setSupplier(ALICE, BOB);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(BOB);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSupplier_editedSupplierHasNonUniqueIdentity_throwsDuplicateSupplierException() {
        uniqueSupplierList.add(ALICE);
        uniqueSupplierList.add(BOB);
        assertThrows(DuplicateSupplierException.class, () -> uniqueSupplierList.setSupplier(ALICE, BOB));
    }

    @Test
    public void remove_nullSupplier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.remove(null));
    }

    @Test
    public void remove_supplierDoesNotExist_throwsSupplierNotFoundException() {
        assertThrows(SupplierNotFoundException.class, () -> uniqueSupplierList.remove(ALICE));
    }

    @Test
    public void remove_existingSupplier_removesSupplier() {
        uniqueSupplierList.add(ALICE);
        uniqueSupplierList.remove(ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_nullUniqueSupplierList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSuppliers((UniqueSupplierList) null));
    }

    @Test
    public void setSuppliers_uniqueSupplierList_replacesOwnListWithProvidedUniqueSupplierList() {
        uniqueSupplierList.add(ALICE);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(BOB);
        uniqueSupplierList.setSuppliers(expectedUniqueSupplierList);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSupplierList.setSuppliers((List<Supplier>) null));
    }

    @Test
    public void setSuppliers_list_replacesOwnListWithProvidedList() {
        uniqueSupplierList.add(ALICE);
        List<Supplier> supplierList = Collections.singletonList(BOB);
        uniqueSupplierList.setSuppliers(supplierList);
        UniqueSupplierList expectedUniqueSupplierList = new UniqueSupplierList();
        expectedUniqueSupplierList.add(BOB);
        assertEquals(expectedUniqueSupplierList, uniqueSupplierList);
    }

    @Test
    public void setSuppliers_listWithDuplicateSuppliers_throwsDuplicateSupplierException() {
        List<Supplier> listWithDuplicateSuppliers = Arrays.asList(ALICE, ALICE);
        assertThrows(
                DuplicateSupplierException.class, () -> uniqueSupplierList.setSuppliers(listWithDuplicateSuppliers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueSupplierList.asUnmodifiableObservableList().remove(0));
    }
}

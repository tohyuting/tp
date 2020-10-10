package seedu.clinic.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IndexTest {

    @Test
    public void createOneBasedIndex() {
        // invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromOneBased(0));

        // check equality using the same base
        assertEquals(1, Index.fromOneBased(1).getOneBased());
        assertEquals(5, Index.fromOneBased(5).getOneBased());

        // convert from one-based index to zero-based index
        assertEquals(0, Index.fromOneBased(1).getZeroBased());
        assertEquals(4, Index.fromOneBased(5).getZeroBased());
    }

    @Test
    public void createZeroBasedIndex() {
        // invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> Index.fromZeroBased(-1));

        // check equality using the same base
        assertEquals(0, Index.fromZeroBased(0).getZeroBased());
        assertEquals(5, Index.fromZeroBased(5).getZeroBased());

        // convert from zero-based index to one-based index
        assertEquals(1, Index.fromZeroBased(0).getOneBased());
        assertEquals(6, Index.fromZeroBased(5).getOneBased());
    }

    @Test
    public void equals() {
        final Index fifthSupplierIndex = Index.fromOneBased(5);
        final Index ninthWarehouseIndex = Index.fromOneBased(9);

        // same values -> returns true
        assertTrue(fifthSupplierIndex.equals(Index.fromOneBased(5)));
        assertTrue(fifthSupplierIndex.equals(Index.fromZeroBased(4)));
        assertTrue(ninthWarehouseIndex.equals(Index.fromOneBased(9)));
        assertTrue(ninthWarehouseIndex.equals(Index.fromZeroBased(8)));

        // same object -> returns true
        assertTrue(fifthSupplierIndex.equals(fifthSupplierIndex));
        assertTrue(ninthWarehouseIndex.equals(ninthWarehouseIndex));

        // null -> returns false
        assertFalse(fifthSupplierIndex.equals(null));

        // different types -> returns false
        assertFalse(fifthSupplierIndex.equals(5.0f));
        assertFalse(ninthWarehouseIndex.equals(9.0f));

        // different index -> returns false
        assertFalse(fifthSupplierIndex.equals(Index.fromOneBased(1)));
        assertFalse(ninthWarehouseIndex.equals(Index.fromOneBased(2)));
    }
}

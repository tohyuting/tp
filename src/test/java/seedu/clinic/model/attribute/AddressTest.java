package seedu.clinic.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress(" dsfas")); // spaces at the start only
        assertFalse(Name.isValidName("/")); // no forward slash
        assertFalse(Address.isValidAddress("CCK/Yew Tee")); // contains slash within string
        assertFalse(Address.isValidAddress("Kidzania z/Weekend")); // contains slash as a non-matching prefix

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    void testToString() {
        Address address = new Address("Address Name");
        String expected = "Address Name";
        assertEquals(address.toString(), expected);
    }

    @Test
    void testEquals() {
        // same instance
        Address addressFirst = new Address("address");
        assertTrue(addressFirst.equals(addressFirst));

        // same remark value
        Address addressSecond = new Address("address");
        assertTrue(addressFirst.equals(addressSecond));

        // different remark
        Address addressThird = new Address("remark 2");
        assertFalse(addressFirst.equals(addressThird));

        // different class types
        String addressString = "address";
        assertFalse(addressFirst.equals(addressString));

    }
}

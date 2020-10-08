<<<<<<< HEAD:src/test/java/seedu/clinic/model/attribute/NameTest.java
package seedu.address.model.attribute;
=======
package seedu.clinic.model.supplier;
>>>>>>> upstream/master:src/test/java/seedu/clinic/model/supplier/NameTest.java

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("*peter")); // starts with a non-alphanumeric character

        // valid name
        assertTrue(Name.isValidName("McDonald")); // alphabets only
        assertTrue(Name.isValidName("M & M \\~`[]{}|;':\",./<>?")); // alphabets with various printable characters
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("Starbucks.ltd")); // alphanumeric characters
        assertTrue(Name.isValidName("123Cat Pte.Ltd")); // with capital letters
        assertTrue(Name.isValidName("Blah !@#$%^&*()_+-=")); // with various printable characters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd Ltd")); // long names
    }
}

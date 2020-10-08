<<<<<<< HEAD:src/test/java/seedu/clinic/model/attribute/TagTest.java
package seedu.address.model.attribute;
=======
package seedu.clinic.model.tag;
>>>>>>> upstream/master:src/test/java/seedu/clinic/model/tag/TagTest.java

import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}

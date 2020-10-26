package seedu.clinic.model.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.clinic.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class RemarkTest {
    private String remarkWithMoreThanOneHundredCharacters =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a augue quis dui efficitur "
                    + "facilisis. Nullam eget libero ultricies, consequat purus eget, fermentum nisl. Aliquam "
                    + "tempor ante ut quam blandit feugiat. Nulla ac dui egestas, porttitor neque vitae, suscipit "
                    + "neque. Proin dapibus condimentum erat, non pretium sem dignissim et. Integer egestas nunc "
                    + "vel lectus sagittis gravida. Morbi sit amet pharetra sem, et feugiat est. Sed a felis."
                    + "Morbi pulvinar tortor eu arcu commodo ultricies. Suspendisse quis ornare velit. Vivamus ut "
                    + "convallis arcu. Ut vel blandit urna. Mauris ac felis erat. Aliquam cursus nunc quis egestas "
                    + "dictum. Duis ac metus nec leo viverra congue. Nulla a accumsan turpis. Phasellus mollis "
                    + "sollicitudin turpis, vel pretium mauris. Proin ut iaculis arcu. Maecenas tristique id "
                    + "sapien id viverra. Integer malesuada molestie ex id convallis. Donec sed tempor nulla"
                    + "placerat urna non augue dignissim, a dictum elit bibendum. Nunc sollicitudin, est vitae "
                    + "viverra dapibus, sem ante rutrum orci, quis ullamcorper massa tellus sit amet lorem. "
                    + "Curabitur at mi id erat venenatis elementum ut congue lectu";
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Remark(remarkWithMoreThanOneHundredCharacters));
    }

    @Test
    void isValidWarehouse() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid remarks: spaces only
        assertFalse(Remark.isValidRemark(" "));
        // invalid remarks: contains forward slash
        assertFalse(Name.isValidName("/")); // no forward slash
        // invalid remarks: contains forward slash within string
        assertFalse(Remark.isValidRemark("Monday/Tuesday"));
        // invalid remarks: contains forward slash as a non-matching prefix
        assertFalse(Remark.isValidRemark("Remark z/Weekend"));
        // invalid remarks: remark exceed the 100 char limit
        assertFalse(Remark.isValidRemark(remarkWithMoreThanOneHundredCharacters));

        // valid remarks
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark("Some remark"));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("Some longer remark: blah blah blah blah")); // long remark
    }

    @Test
    void testToString() {
        Remark remark = new Remark("Warehouse Name");
        String expected = "Warehouse Name";
        assertEquals(remark.toString(), expected);
    }

    @Test
    void testEquals() {
        // same instance
        Remark remarkFirst = new Remark("remark");
        assertTrue(remarkFirst.equals(remarkFirst));

        // same remark value
        Remark remarkSecond = new Remark("remark");
        assertTrue(remarkFirst.equals(remarkSecond));

        // different remark
        Remark remarkThird = new Remark("remark 2");
        assertFalse(remarkFirst.equals(remarkThird));

        // different class types
        String remarkString = "remark";
        assertFalse(remarkFirst.equals(remarkString));

    }
}

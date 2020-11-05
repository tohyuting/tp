package seedu.clinic.model.macro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.clinic.model.macro.exceptions.DuplicateMacroException;
import seedu.clinic.model.macro.exceptions.MacroNotFoundException;
import seedu.clinic.testutil.MacroBuilder;

class UniqueMacroListTest {
    private static final Macro ALPHA = new MacroBuilder().withAlias("alpha").build();
    private static final Macro BETA = new MacroBuilder().withAlias("beta").build();

    private UniqueMacroList uniqueMacroList = new UniqueMacroList();

    @Test
    void addMacro_hasMacro_throwsDuplicateMacroException() {
        uniqueMacroList.add(ALPHA);
        assertThrows(DuplicateMacroException.class, () -> uniqueMacroList.add(ALPHA));
    }

    @Test
    public void setMacro_nullTargetMacro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMacroList.setMacro((Macro) null, ALPHA));
    }

    @Test
    public void setMacro_nullEditedMacro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMacroList.setMacro(ALPHA, null));
    }

    @Test
    public void setMacro_targetMacroNotInList_throwsMacroNotFoundException() {
        assertThrows(MacroNotFoundException.class, () -> uniqueMacroList.setMacro(ALPHA, ALPHA));
    }

    @Test
    public void setMacro_editedMacroIsSameMacro_success() {
        uniqueMacroList.add(ALPHA);
        uniqueMacroList.setMacro(ALPHA, ALPHA);
        UniqueMacroList expectedUniqueMacroList = new UniqueMacroList();
        expectedUniqueMacroList.add(ALPHA);
        assertEquals(expectedUniqueMacroList, uniqueMacroList);
    }

    @Test
    public void setMacro_editedMacroHasSameIdentity_throwsDuplicateMacroException() {
        uniqueMacroList.add(ALPHA);
        uniqueMacroList.add(BETA);
        Macro sameMacro = new MacroBuilder().withAlias("alpha").build();
        assertThrows(DuplicateMacroException.class, () -> uniqueMacroList.setMacro(sameMacro, BETA));
    }

    @Test
    public void setMacro_editedMacroIsDifferentMacro_success() {
        uniqueMacroList.add(ALPHA);
        uniqueMacroList.setMacro(ALPHA, BETA);
        UniqueMacroList expectedUniqueMacroList = new UniqueMacroList();
        expectedUniqueMacroList.add(BETA);
        assertEquals(expectedUniqueMacroList, uniqueMacroList);
    }

    @Test
    public void removeMacro_targetMacroNotInList_throwsMacroNotFoundException() {
        assertThrows(MacroNotFoundException.class, () -> uniqueMacroList.remove(ALPHA));
    }

    @Test
    public void setMacros_nullEditedMacros_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMacroList.setMacros((UniqueMacroList) null));
    }

    @Test
    public void setMacros_nullListOfEditedMacros_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMacroList.setMacros((List<Macro>) null));
    }

    @Test
    public void setMacros_validEditedMacros_success() {
        uniqueMacroList.add(ALPHA);
        UniqueMacroList expectedUniqueMacroList = new UniqueMacroList();
        expectedUniqueMacroList.add(BETA);
        uniqueMacroList.setMacros(expectedUniqueMacroList);
        assertEquals(expectedUniqueMacroList, uniqueMacroList);
    }

    @Test
    public void setMacros_duplicateMacrosInList_throwsDuplicateMacroException() {
        uniqueMacroList.add(ALPHA);
        List<Macro> listWithDuplicateMacros = Arrays.asList(ALPHA, ALPHA);
        assertThrows(DuplicateMacroException.class, () -> uniqueMacroList.setMacros(listWithDuplicateMacros));
    }
}

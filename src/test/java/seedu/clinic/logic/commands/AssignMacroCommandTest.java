package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.testutil.MacroBuilder;
import seedu.clinic.testutil.ModelStub;

class AssignMacroCommandTest {

    @Test
    public void constructor_nullMacro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignMacroCommand((Macro) null));
    }

    @Test
    public void execute_macroIsNew_success() throws Exception {
        ModelStubAcceptingMacroAdded modelStub = new ModelStubAcceptingMacroAdded();
        Macro validMacro = new MacroBuilder().build();

        CommandResult commandResult = new AssignMacroCommand(validMacro).execute(modelStub);

        assertEquals(String.format(AssignMacroCommand.MESSAGE_SUCCESS, validMacro),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMacro), modelStub.macrosAdded);
    }

    @Test
    public void execute_duplicateSupplier_throwsCommandException() {
        Macro validMacro = new MacroBuilder().build();
        AssignMacroCommand assignMacroCommand = new AssignMacroCommand(validMacro);
        ModelStub modelStub = new ModelStubWithMacro(validMacro);

        assertThrows(CommandException.class,
                AssignMacroCommand.MESSAGE_DUPLICATE_MACRO, () -> assignMacroCommand.execute(modelStub));
    }

    @Test
    void testEquals() {
        Macro alpha = new MacroBuilder().withAlias("alpha").build();
        Macro beta = new MacroBuilder().withAlias("beta").build();
        AssignMacroCommand assignAlphaCommand = new AssignMacroCommand(alpha);
        AssignMacroCommand assignBetaCommand = new AssignMacroCommand(beta);

        // same object -> returns true
        assertEquals(assignAlphaCommand, assignAlphaCommand);

        // same values -> returns true
        AssignMacroCommand assignAlphaCommandCopy = new AssignMacroCommand(alpha);
        assertEquals(assignAlphaCommandCopy, assignAlphaCommand);

        // different types -> returns false
        assertNotEquals(assignAlphaCommand, 1);

        // null -> returns false
        assertNotEquals(assignAlphaCommand, null);

        // different macro -> returns false
        assertNotEquals(assignAlphaCommand, assignBetaCommand);
    }

    /*
     * A Model stub that contains a single macro.
     */
    private static class ModelStubWithMacro extends ModelStub {
        private final Macro macro;

        ModelStubWithMacro(Macro macro) {
            requireNonNull(macro);
            this.macro = macro;
        }

        @Override
        public boolean hasMacro(Macro macro) {
            requireNonNull(macro);
            return this.macro.isSameMacro(macro);
        }
    }

    /*
     * A Model stub that always accept the macro being added.
     */
    private static class ModelStubAcceptingMacroAdded extends ModelStub {
        final ArrayList<Macro> macrosAdded = new ArrayList<>();

        @Override
        public boolean hasMacro(Macro macro) {
            requireNonNull(macro);
            return macrosAdded.stream().anyMatch(macro::isSameMacro);
        }

        @Override
        public void addMacro(Macro macro) {
            requireNonNull(macro);
            macrosAdded.add(macro);
        }

        @Override
        public ReadOnlyUserMacros getUserMacros() {
            return new UserMacros();
        }
    }
}

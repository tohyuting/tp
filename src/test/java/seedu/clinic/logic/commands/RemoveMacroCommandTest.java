package seedu.clinic.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.clinic.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.model.ReadOnlyUserMacros;
import seedu.clinic.model.UserMacros;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.testutil.MacroBuilder;
import seedu.clinic.testutil.ModelStub;

class RemoveMacroCommandTest {

    @Test
    public void constructor_nullMacro_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RemoveMacroCommand((Alias) null));
    }

    @Test
    public void execute_removeMacro_success() throws CommandException {
        Macro macro = new MacroBuilder().withAlias("alice").build();
        ModelStub modelStub = new ModelStubWithMacro(macro);

        CommandResult commandResult = new RemoveMacroCommand(macro.getAlias()).execute(modelStub);
        UserMacros userMacros = new UserMacros();

        assertEquals(String.format(RemoveMacroCommand.MESSAGE_REMOVE_MACRO_SUCCESS, macro.getAlias()),
                commandResult.getFeedbackToUser());
        assertEquals(userMacros, modelStub.getUserMacros());
    }

    @Test
    public void execute_macroNotPresent_throwsCommandException() {
        Macro macro = new MacroBuilder().withAlias("alice").build();
        RemoveMacroCommand removeMacroCommand = new RemoveMacroCommand(macro.getAlias());
        ModelStub modelStub = new ModelStubWithoutMacro();

        assertThrows(CommandException.class,
                RemoveMacroCommand.MESSAGE_MACRO_DOES_NOT_EXIST, () -> removeMacroCommand.execute(modelStub));
    }

    @Test
    public void testEquals() {
        Alias alpha = new Alias("alpha");
        Alias beta = new Alias("beta");
        RemoveMacroCommand removeAlphaCommand = new RemoveMacroCommand(alpha);
        RemoveMacroCommand removeBetaCommand = new RemoveMacroCommand(beta);

        // same object -> returns true
        assertEquals(removeAlphaCommand, removeAlphaCommand);

        // same values -> returns true
        RemoveMacroCommand removeAlphaCommandCopy = new RemoveMacroCommand(alpha);
        assertEquals(removeAlphaCommandCopy, removeAlphaCommand);

        // different types -> returns false
        assertNotEquals(removeAlphaCommand, 1);

        // null -> returns false
        assertNotEquals(removeAlphaCommand, null);

        // different macro -> returns false
        assertNotEquals(removeAlphaCommand, removeBetaCommand);
    }

    /*
     * A Model stub that contains a single macro within a userMacros.
     */
    private static class ModelStubWithMacro extends ModelStub {
        private UserMacros userMacros = new UserMacros();

        ModelStubWithMacro(Macro macro) {
            requireNonNull(macro);
            this.userMacros.addMacro(macro);
        }

        @Override
        public Optional<Macro> getMacro(Alias alias) {
            requireNonNull(alias);
            return userMacros.getMacro(alias);
        }

        @Override
        public void deleteMacro(Macro macro) {
            requireNonNull(macro);
            userMacros.removeMacro(macro);
        }

        @Override
        public ReadOnlyUserMacros getUserMacros() {
            return userMacros;
        }
    }

    /*
     * A Model stub that always accept the macro being added.
     */
    private static class ModelStubWithoutMacro extends ModelStub {

        @Override
        public Optional<Macro> getMacro(Alias alias) {
            requireNonNull(alias);
            return Optional.empty();
        }
    }
}

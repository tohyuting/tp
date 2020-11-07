package seedu.clinic.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.Macro;
import seedu.clinic.model.macro.UniqueMacroList;

/**
 * Represents user-defined macros.
 * Duplicates are not allowed (by .isSameMacro comparison)
 */
public class UserMacros implements ReadOnlyUserMacros {
    private final UniqueMacroList macros;

    public UserMacros() {
        macros = new UniqueMacroList();
    }

    /**
     * Creates a {@code UserMacros} with the macros in {@code userMacros}.
     */
    public UserMacros(ReadOnlyUserMacros userMacros) {
        this();
        resetData(userMacros);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the macro list with {@code macros}.
     * {@code macros} must not contain duplicate macros.
     */
    public void setMacros(List<Macro> macros) {
        this.macros.setMacros(macros);
    }

    /**
     * Resets the existing data of this {@code UserMacros} with {@code newData}.
     */
    public void resetData(ReadOnlyUserMacros newData) {
        requireNonNull(newData);

        setMacros(newData.getMacroList());
    }

    //// macro-level operations

    /**
     * Returns true if a macro with the same alias as {@code macro} exists in the model.
     */
    public boolean hasMacro(Macro macro) {
        requireNonNull(macro);
        return macros.contains(macro);
    }

    /**
     * Returns the macro corresponding to the alias string in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    public Optional<Macro> getMacro(String aliasString) {
        requireNonNull(aliasString);
        return macros.getMacro(aliasString);
    }

    /**
     * Returns the macro corresponding to the alias in an optional wrapper if it exists,
     * and an empty optional otherwise
     */
    public Optional<Macro> getMacro(Alias alias) {
        requireNonNull(alias);
        return macros.getMacro(alias);
    }

    /**
     * Adds a macro to the CLI-nic app.
     * The macro must not already exist in userMacros.
     */
    public void addMacro(Macro p) {
        macros.add(p);
    }

    /**
     * Replaces the given macro {@code target} in the list with {@code editedMacro}.
     * {@code target} must exist in userMacros.
     * The macro identity of {@code editedMacro} must not be the same as another existing macro
     * in the CLI-nic app.
     */
    public void setMacro(Macro target, Macro editedMacro) {
        requireNonNull(editedMacro);

        macros.setMacro(target, editedMacro);
    }

    /**
     * Removes {@code key} from this {@code UserMacros}.
     * {@code key} must exist in userMacros.
     */
    public void removeMacro(Macro key) {
        macros.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return macros.asUnmodifiableObservableList().size() + " macros\n";
        // TODO: refine later
    }

    @Override
    public ObservableList<Macro> getMacroList() {
        return macros.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserMacros // instanceof handles nulls
                && macros.equals(((UserMacros) other).macros));
    }

    @Override
    public int hashCode() {
        return Objects.hash(macros);
    }
}

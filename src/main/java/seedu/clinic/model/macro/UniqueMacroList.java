package seedu.clinic.model.macro;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinic.model.macro.exceptions.DuplicateMacroException;
import seedu.clinic.model.macro.exceptions.MacroNotFoundException;

/**
 * A list of macros that enforces uniqueness between its elements and does not allow nulls. A macro is considered
 * unique by comparing using {@code Macro#isSameMacro(Macro)}. As such, adding and updating of macros uses
 * Macro#isSameMacro(Macro) for equality so as to ensure that the macro being added or updated is unique in
 * terms of identity in the UniqueMacroList. However, the removal of a macro uses Macro#equals(Object) so as to
 * ensure that the macro with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Macro#isSameMacro(Macro)
 */
public class UniqueMacroList implements Iterable<Macro> {

    private final ObservableList<Macro> internalList = FXCollections.observableArrayList();
    private final ObservableList<Macro> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the macro list contains an equivalent macro as the given macro argument.
     */
    public boolean contains(Macro toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMacro);
    }

    /**
     * Returns the macro corresponding to the alias string in an optional wrapper if it exists,
     * and an empty optional otherwise.
     */
    public Optional<Macro> getMacro(String aliasString) {
        requireNonNull(aliasString);
        Alias alias;

        try {
            alias = new Alias(aliasString);
        } catch (IllegalArgumentException e) { // Invalid Alias
            return Optional.empty();
        }

        return getMacro(alias);
    }

    /**
     * Returns the macro corresponding to the alias in an optional wrapper if it exists,
     * and an empty optional otherwise.
     */
    public Optional<Macro> getMacro(Alias alias) {
        requireNonNull(alias);
        return internalList.stream().filter((Macro macro) -> macro.getAlias().equals(alias)).findFirst();
    }

    /**
     * Adds a macro to the list.
     * The macro must not already exist in the list.
     */
    public void add(Macro toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMacroException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the macro {@code target} in the list with {@code editedMacro}.
     * {@code target} must exist in the list.
     * The macro identity of {@code editedMacro} must not be the same as another existing macro in the list.
     */
    public void setMacro(Macro target, Macro editedMacro) {
        requireAllNonNull(target, editedMacro);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MacroNotFoundException();
        }

        if (!target.isSameMacro(editedMacro) && contains(editedMacro)) {
            throw new DuplicateMacroException();
        }

        internalList.set(index, editedMacro);
    }

    /**
     * Removes the equivalent macro from the list.
     * The macro must exist in the list.
     */
    public void remove(Macro toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MacroNotFoundException();
        }
    }

    public void setMacros(UniqueMacroList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code macros}.
     * {@code macros} must not contain duplicate macros.
     */
    public void setMacros(List<Macro> macros) {
        requireAllNonNull(macros);
        if (!macrosAreUnique(macros)) {
            throw new DuplicateMacroException();
        }

        internalList.setAll(macros);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Macro> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Macro> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMacroList // instanceof handles nulls
                && internalList.equals(((UniqueMacroList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code macros} contains only unique macros.
     */
    private boolean macrosAreUnique(List<Macro> macros) {
        for (int i = 0; i < macros.size() - 1; i++) {
            for (int j = i + 1; j < macros.size(); j++) {
                if (macros.get(i).isSameMacro(macros.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}


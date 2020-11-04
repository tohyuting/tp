package seedu.clinic.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.clinic.logic.commands.CommandResult;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.CommandHistoryList;
import seedu.clinic.model.ReadOnlyCommandHistory;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final ReadOnlyCommandHistory commandHistory;
    private final CommandHistoryList history;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, ReadOnlyCommandHistory commandHistory) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = commandHistory;
        this.history = commandHistory.getCommandHistoryList();

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            String textEntered = commandTextField.getText();
            commandExecutor.execute(textEntered);
            commandTextField.setText("");

            history.updateHistory(textEntered);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            String prevHistory = commandHistory.readPreviousHistory();
            setCommandTextFieldText(prevHistory);
            break;

        case DOWN:
            String nextHistory = commandHistory.readNextHistory();
            setCommandTextFieldText(nextHistory);
            break;

        default:
            break;
        }
    }

    private void setCommandTextFieldText(String newText) {
        commandTextField.setText(newText);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.clinic.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

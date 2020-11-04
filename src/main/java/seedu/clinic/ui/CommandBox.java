package seedu.clinic.ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private StackPane commandBox;

    private AutoCompleteTextField autoCompleteTextField;

    /**
     * The text box in the UI.
     * @param commandExecutor To execute the command.
     * @param commandHistory To read the command history.
     */
    public CommandBox(CommandExecutor commandExecutor, ReadOnlyCommandHistory commandHistory) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = commandHistory;
        this.history = commandHistory.getCommandHistoryList();

        autoCompleteTextField = new AutoCompleteTextField();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        autoCompleteTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autoCompleteTextField.getStyleClass().add("commandTextField");

        /*
         * With help from https://stackoverflow
         * .com/questions/48720101/how-to-pass-scrollpane-event-key-pressed
         * -keycode-left-to-parent-pane
         */
        autoCompleteTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
            case UP:
                String prevHistory = commandHistory.readPreviousHistory();
                setCommandTextFieldText(prevHistory);
                break;

            case DOWN:
                String nextHistory = commandHistory.readNextHistory();
                setCommandTextFieldText(nextHistory);
                break;

            case ENTER:
                handleCommandEntered();
                break;

            default:
                break;
            }
        });
        commandBox.getChildren().add(autoCompleteTextField);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        Platform.runLater(() -> {
            try {
                String textEntered = autoCompleteTextField.getText();
                commandExecutor.execute(textEntered);
                autoCompleteTextField.setText("");
                history.updateHistory(textEntered);
            } catch (CommandException | ParseException | IOException e) {
                setStyleToIndicateCommandFailure();
            }
        });
    }

    private void setCommandTextFieldText(String newText) {
        autoCompleteTextField.setText(newText);
        autoCompleteTextField.positionCaret(autoCompleteTextField.getText().length());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        autoCompleteTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass;
        styleClass = autoCompleteTextField.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    public AutoCompleteTextField getAutoCompleteTextField() {
        return autoCompleteTextField;
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
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }

}

package seedu.clinic.ui;

import java.io.IOException;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.clinic.logic.Logic;
import seedu.clinic.logic.commands.CommandResult;
import seedu.clinic.logic.commands.exceptions.CommandException;
import seedu.clinic.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    @FXML
    private StackPane commandBox;

    private AutoCompleteTextField autoCompleteTextField;

    /**
     * The text box in the UI.
     * @param commandExecutor To execute the command.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        autoCompleteTextField = new AutoCompleteTextField();
        autoCompleteTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        autoCompleteTextField.getStyleClass().add("commandTextField");

        autoCompleteTextField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleCommandEntered();
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
                commandExecutor.execute(autoCompleteTextField.getText());
                autoCompleteTextField.setText("");
            } catch (CommandException | ParseException | IOException e) {
                setStyleToIndicateCommandFailure();
            }
        });
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

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            String prevHistory = "readPreviousHistory";
            setCommandTextFieldText(prevHistory);
            break;

        case DOWN:
            String nextHistory = "readNextHistory";
            setCommandTextFieldText(nextHistory);
            break;

        default:
            break;
        }
    }

    private void setCommandTextFieldText(String newText) {
        autoCompleteTextField.setText(newText);
        autoCompleteTextField.positionCaret(autoCompleteTextField.getText().length());
    }

    public TextField getCommandTextField() {
        return commandTextField;
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
         * @see Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException, IOException;
    }

}

package seedu.clinic.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.clinic.commons.util.FileUtil;
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
    private List<String> commandHistory = new ArrayList<>();
    private int commandHistoryIndex = 0;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        try {
            String result = FileUtil.readFromFile(Paths.get("data" , "commandHistory.txt"));

            Scanner scanner = new Scanner(new File("data/commandHistory.txt"));
            ArrayList<String> commandHistory = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                commandHistory.add(line);
            }

            this.commandHistory = commandHistory;
            this.commandHistoryIndex = commandHistory.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            if (commandHistoryIndex > 0) {
                setCommandTextFieldText(commandHistoryIndex - 1);
            }
            break;

        case DOWN:
            if (commandHistoryIndex < this.commandHistory.size() - 1) {
                setCommandTextFieldText(commandHistoryIndex + 1);
            }
            break;

        default:
            break;
        }
    }

    private void setCommandTextFieldText(int newCommandHistoryIndex) {
        commandTextField.setText(commandHistory.get(newCommandHistoryIndex));
        commandHistoryIndex = newCommandHistoryIndex;
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

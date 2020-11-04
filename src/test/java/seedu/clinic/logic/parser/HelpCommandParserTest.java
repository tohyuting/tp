package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_TOO_MANY_ARGUMENTS;
import static seedu.clinic.logic.commands.HelpCommand.MESSAGE_WRONG_ARGUMENT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.HelpCommand;


/**
 * Contains unit tests for {@code HelpCommandParser}.
 */
public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArg_showGenericMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("all");
        assertParseSuccess(parser, "", expectedHelpCommand);
        assertParseSuccess(parser, "       ", expectedHelpCommand);
    }

    @Test
    public void parse_addAsArg_showAddMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("add");
        assertParseSuccess(parser, "add", expectedHelpCommand);
        assertParseSuccess(parser, "add       ", expectedHelpCommand);
        assertParseSuccess(parser, "Add       ", expectedHelpCommand);
        assertParseSuccess(parser, "Add", expectedHelpCommand);
        assertParseSuccess(parser, "ADD       ", expectedHelpCommand);
        assertParseSuccess(parser, "ADD", expectedHelpCommand);
        assertParseSuccess(parser, "aDd", expectedHelpCommand);
        assertParseSuccess(parser, "aDd       ", expectedHelpCommand);
    }

    @Test
    public void parse_clearAsArg_showClearMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("clear");
        assertParseSuccess(parser, "clear", expectedHelpCommand);
        assertParseSuccess(parser, "clear       ", expectedHelpCommand);
        assertParseSuccess(parser, "CLEAR", expectedHelpCommand);
        assertParseSuccess(parser, "CLEAR       ", expectedHelpCommand);
        assertParseSuccess(parser, "Clear", expectedHelpCommand);
        assertParseSuccess(parser, "Clear       ", expectedHelpCommand);
        assertParseSuccess(parser, "CLEar", expectedHelpCommand);
        assertParseSuccess(parser, "CLEar       ", expectedHelpCommand);
    }

    @Test
    public void parse_deleteAsArg_showDeleteMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("delete");
        assertParseSuccess(parser, "delete", expectedHelpCommand);
        assertParseSuccess(parser, "delete       ", expectedHelpCommand);
        assertParseSuccess(parser, "DELETE", expectedHelpCommand);
        assertParseSuccess(parser, "DELETE       ", expectedHelpCommand);
        assertParseSuccess(parser, "DELete", expectedHelpCommand);
        assertParseSuccess(parser, "DELete       ", expectedHelpCommand);
    }

    @Test
    public void parse_exitAsArg_showExitMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("exit");
        assertParseSuccess(parser, "exit", expectedHelpCommand);
        assertParseSuccess(parser, "exit       ", expectedHelpCommand);
        assertParseSuccess(parser, "EXIT", expectedHelpCommand);
        assertParseSuccess(parser, "EXIT       ", expectedHelpCommand);
        assertParseSuccess(parser, "EXit", expectedHelpCommand);
        assertParseSuccess(parser, "EXit       ", expectedHelpCommand);
    }

    @Test
    public void parse_findAsArg_showFindMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("find");
        assertParseSuccess(parser, "find", expectedHelpCommand);
        assertParseSuccess(parser, "find       ", expectedHelpCommand);
        assertParseSuccess(parser, "FIND", expectedHelpCommand);
        assertParseSuccess(parser, "FiNd       ", expectedHelpCommand);
    }

    @Test
    public void parse_listAsArg_showlistMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("list");
        assertParseSuccess(parser, "list", expectedHelpCommand);
        assertParseSuccess(parser, "list       ", expectedHelpCommand);
        assertParseSuccess(parser, "LIST", expectedHelpCommand);
        assertParseSuccess(parser, "LIST       ", expectedHelpCommand);
        assertParseSuccess(parser, "LIst", expectedHelpCommand);
        assertParseSuccess(parser, "LIst       ", expectedHelpCommand);
    }

    @Test
    public void parse_viewAsArg_showViewMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("view");
        assertParseSuccess(parser, "view", expectedHelpCommand);
        assertParseSuccess(parser, "view       ", expectedHelpCommand);
        assertParseSuccess(parser, "VIEW", expectedHelpCommand);
        assertParseSuccess(parser, "VIEW       ", expectedHelpCommand);
        assertParseSuccess(parser, "vIEw", expectedHelpCommand);
        assertParseSuccess(parser, "vIEw       ", expectedHelpCommand);
    }

    @Test
    public void parse_updateAsArg_showUpdateMessage() {
        HelpCommand expectedHelpCommand = new HelpCommand("update");
        assertParseSuccess(parser, "update", expectedHelpCommand);
        assertParseSuccess(parser, "update       ", expectedHelpCommand);
        assertParseSuccess(parser, "UPDATE", expectedHelpCommand);
        assertParseSuccess(parser, "UPDATE       ", expectedHelpCommand);
        assertParseSuccess(parser, "UpDATe", expectedHelpCommand);
        assertParseSuccess(parser, "UpDATe       ", expectedHelpCommand);
    }

    @Test
    public void parse_invalidCommandsAsArg_throwsParseException() {
        assertParseFailure(parser, "hello", String.format(MESSAGE_WRONG_ARGUMENT,
                HelpCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "adding", String.format(MESSAGE_WRONG_ARGUMENT,
                HelpCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "findingWAREhouses", String.format(MESSAGE_WRONG_ARGUMENT,
                HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleCommandsAsArg_throwsParseException() {
        assertParseFailure(parser, "add find", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_TOO_MANY_ARGUMENTS));
        assertParseFailure(parser, "list delete", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_TOO_MANY_ARGUMENTS));
        assertParseFailure(parser, "DELETE EXIT", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_TOO_MANY_ARGUMENTS));
        assertParseFailure(parser, "fakecommand EXIT", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_TOO_MANY_ARGUMENTS));
        assertParseFailure(parser, "I WANT TO ADD", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_TOO_MANY_ARGUMENTS));
    }
}

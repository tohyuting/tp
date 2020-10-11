package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private final Parser parser = new ViewCommandParser();

    @Test
    public void parse_zeroKeywords_throwParsesException() {
        assertParseFailure(parser, "", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyTypeNoKeywords_throwParsesException() {
        assertParseFailure(parser, "supplier ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        assertParseFailure(parser, "suPPLIEr ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        assertParseFailure(parser, "WAREHOUSE ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        assertParseFailure(parser, "WAREhouse ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        assertParseFailure(parser, "warehouse ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
    }

    @Test
    public void parse_onlyOneKeywordNoType_throwParsesException() {
        assertParseFailure(parser, "alice", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
        assertParseFailure(parser, "bENson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_TOO_FEW_ARGUMENTS));
    }

    @Test
    public void parse_onlyMultipleKeywordsNoType_throwParsesException() {
        assertParseFailure(parser, "alice benson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        assertParseFailure(parser, "ALIce bENson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
    }

    @Test
    public void parse_wrongTypeCorrectMultipleKeyWords_throwParsesException() {
        assertParseFailure(parser, "VIEWWArehouse alice benson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        assertParseFailure(parser, "VIEWSupplier ALIce bENson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        assertParseFailure(parser, "See ALIce bENson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
    }

    @Test
    public void parse_wrongTypeCorrectOneKeyWord_throwParsesException() {
        assertParseFailure(parser, "VIEWWArehouse alice", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        assertParseFailure(parser, "VIEWSupplier bENson", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
        assertParseFailure(parser, "See ALIce", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_INVALID_TYPE));
    }

    @Test
    public void parse_correctTypeCorrectKeyWords_success() {
        ViewCommand expectedViewCommand1 = new ViewCommand("supplier", Arrays.asList(new String[]
            {"alice", "benson"}));
        assertParseSuccess(parser, "supplier alice benson", expectedViewCommand1);

        ViewCommand expectedViewCommand2 = new ViewCommand("supplier", Arrays.asList(new String[]
            {"ALIce", "bENson"}));
        assertParseSuccess(parser, "Supplier ALIce bENson", expectedViewCommand2);

        ViewCommand expectedViewCommand3 = new ViewCommand("warehouse", Arrays.asList(new String[]
            {"ALIce", "bENson"}));
        assertParseSuccess(parser, "warehouse ALIce bENson", expectedViewCommand3);

        ViewCommand expectedViewCommand4 = new ViewCommand("warehouse", Arrays.asList(new String[]
            {"alice", "benson"}));
        assertParseSuccess(parser, "wArehouse alice benson", expectedViewCommand4);
    }


}

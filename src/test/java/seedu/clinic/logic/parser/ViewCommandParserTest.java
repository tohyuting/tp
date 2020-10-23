package seedu.clinic.logic.parser;

import seedu.clinic.commons.core.index.Index;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_INVALID_TYPE_VIEW;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_INDEX;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_MISSING_TYPE;
import static seedu.clinic.logic.commands.ViewCommand.MESSAGE_NO_PREFIX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private final Parser parser = new ViewCommandParser();
    @Test
    public void parse_zeroKeywords_throwParsesException() {
        assertParseFailure(parser, "", String.format(MESSAGE_NO_PREFIX, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_NO_PREFIX, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyTypeNoIndex_throwParsesException() {
        assertParseFailure(parser, " " + PREFIX_TYPE + "s ", String.format(
                MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w ", String.format(
                MESSAGE_MISSING_INDEX, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyIndexNoType_throwParsesException() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "3 ", String.format(
                MESSAGE_MISSING_TYPE, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 ", String.format(
                MESSAGE_MISSING_TYPE, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongTypeIndexPresent_throwParsesException() {
        assertParseFailure(parser, " " + PREFIX_TYPE + "z " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "2 " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "pw " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "ps " + PREFIX_INDEX + "3",
                String.format(MESSAGE_INVALID_TYPE_VIEW, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_correctTypeCorrectIndex_success() {
        ViewCommand expectedViewCommand1 = new ViewCommand(Type.SUPPLIER, Index.fromOneBased(1));
        assertParseSuccess(parser, PREFIX_TYPE + "s " + PREFIX_INDEX + "1", expectedViewCommand1);

        ViewCommand expectedViewCommand3 = new ViewCommand(Type.WAREHOUSE, Index.fromOneBased(2));
        assertParseSuccess(parser, PREFIX_TYPE + "s " + PREFIX_INDEX + "2", expectedViewCommand3);

    }


}

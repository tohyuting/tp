package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_PREFIX;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.MESSAGE_INVALID_USAGE;

import org.junit.jupiter.api.Test;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.ViewCommand;

public class ViewCommandParserTest {

    private final Parser parser = new ViewCommandParser();
    @Test
    public void parse_zeroKeywords_throwParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyTypeNoIndex_throwParseException() {
        assertParseFailure(parser, " " + PREFIX_TYPE + "s ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "w ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_onlyIndexNoType_throwParseException() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "3 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_wrongTypeIndexPresent_throwParseException() {
        assertParseFailure(parser, " " + PREFIX_TYPE + "z " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "2 " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "pw " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_TYPE, ViewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " " + PREFIX_TYPE + "ps " + PREFIX_INDEX + "3",
                String.format(MESSAGE_INVALID_TYPE, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_correctTypeCorrectIndexExtraArguments_throwParseException() {
        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "1" + " testing",
                String.format(MESSAGE_INVALID_USAGE, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_TYPE + "w" + " testing " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_USAGE, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + " z/testing " + PREFIX_INDEX + "2",
                String.format(MESSAGE_INVALID_PREFIX, ViewCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " " + PREFIX_TYPE + "s " + PREFIX_INDEX + "2" + " z/testing",
                String.format(MESSAGE_INVALID_PREFIX, ViewCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_correctTypeCorrectIndex_success() {
        ViewCommand expectedViewCommand1 = new ViewCommand(Type.SUPPLIER, Index.fromOneBased(1));
        assertParseSuccess(parser, PREFIX_TYPE + "s " + PREFIX_INDEX + "1", expectedViewCommand1);

        ViewCommand expectedViewCommand3 = new ViewCommand(Type.WAREHOUSE, Index.fromOneBased(2));
        assertParseSuccess(parser, PREFIX_TYPE + "s " + PREFIX_INDEX + "2", expectedViewCommand3);

    }


}

package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_COMMAND_STRING;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.AssignMacroCommand;

class AssignMacroCommandParserTest {
    private AssignMacroCommandParser parser = new AssignMacroCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(parser, PREFIX_ALIAS + "umw",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignMacroCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_ALIAS + "umw" + PREFIX_COMMAND_STRING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignMacroCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREFIX_COMMAND_STRING + "list",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignMacroCommand.MESSAGE_USAGE));
    }
}

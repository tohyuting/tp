package seedu.clinic.logic.parser;

import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.clinic.logic.commands.RemoveMacroCommand;

class RemoveMacroCommandParserTest {
    private RemoveMacroCommandParser parser = new RemoveMacroCommandParser();

    @Test
    void parse() {
        assertParseFailure(parser, "list",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveMacroCommand.MESSAGE_USAGE));

    }
}

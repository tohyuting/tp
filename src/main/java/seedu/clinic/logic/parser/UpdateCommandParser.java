package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.logic.commands.UpdateCommand.UpdateProductDescriptor;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Tag;

public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX, PREFIX_PRODUCT_NAME,
                        PREFIX_PRODUCT_QUANTITY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_INDEX, PREFIX_PRODUCT_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Type entityType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Name productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        UpdateProductDescriptor updateProductDescriptor = new UpdateProductDescriptor();

        if (argMultimap.getValue(PREFIX_PRODUCT_QUANTITY).isPresent()) {
            updateProductDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap
                    .getValue(PREFIX_PRODUCT_QUANTITY).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(updateProductDescriptor::setTags);

        return new UpdateCommand(entityType, index, productName, updateProductDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}

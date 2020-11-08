package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.clinic.logic.parser.ParserUtil.checkInvalidArguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_INDEX,
                PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_TYPE, PREFIX_PRODUCT_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            ParserUtil.checkInvalidArgumentsInPreamble(argMultimap.getPreamble(),
                    UpdateCommand.MESSAGE_USAGE);
        }

        Type entityType;
        Index index;

        //Current Prefix to Parse
        Prefix currentPrefix = PREFIX_TYPE;

        try {
            entityType = ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get());
            currentPrefix = PREFIX_INDEX;
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(currentPrefix, argMultimap, UpdateCommand.MESSAGE_USAGE);
        }


        UpdateProductDescriptor updateProductDescriptor = new UpdateProductDescriptor();

        if (argMultimap.getValue(PREFIX_PRODUCT_QUANTITY).isPresent()) {
            try {
                updateProductDescriptor.setQuantity(ParserUtil
                        .parseQuantity(argMultimap.getValue(PREFIX_PRODUCT_QUANTITY).get()));
            } catch (ParseException pe) {
                throw checkInvalidArguments(PREFIX_PRODUCT_QUANTITY, argMultimap,
                        UpdateCommand.MESSAGE_USAGE);
            }
        }

        Name productName;
        try {
            productName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PRODUCT_NAME).get());
        } catch (ParseException pe) {
            throw checkInvalidArguments(PREFIX_PRODUCT_NAME, argMultimap, UpdateCommand.MESSAGE_USAGE);
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            try {
                List<String> tags = Arrays.asList(argMultimap.getValue(PREFIX_TAG).get().trim().split("\\s"));
                Optional<Set<Tag>> tagSet = parseTagsForUpdate(tags);
                tagSet.ifPresent(updateProductDescriptor::setTags);
            } catch (ParseException pe) {
                throw checkInvalidArguments(PREFIX_TAG, argMultimap, UpdateCommand.MESSAGE_USAGE);
            }
        }

        return new UpdateCommand(entityType, index, productName, updateProductDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForUpdate(Collection<String> tags) throws ParseException {
        assert tags != null;
        if (tags.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> tagSet;

        if (tags.size() == 1 && tags.contains("")) {
            tagSet = Collections.emptySet();
        } else {
            tagSet = tags;
        }
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}

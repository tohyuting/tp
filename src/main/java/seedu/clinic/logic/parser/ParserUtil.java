package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinic.logic.parser.CliSyntax.ALLOWED_PREFIXES;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_ADD;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_ASSIGNMACRO;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_DELETE;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_EDIT;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_FIND;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_UPDATE;
import static seedu.clinic.logic.parser.CliSyntax.NOT_ALLOWED_PREFIXES_VIEW;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_PRODUCT_QUANTITY;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinic.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.logic.commands.AddCommand;
import seedu.clinic.logic.commands.AssignMacroCommand;
import seedu.clinic.logic.commands.DeleteCommand;
import seedu.clinic.logic.commands.EditCommand;
import seedu.clinic.logic.commands.FindCommand;
import seedu.clinic.logic.commands.UpdateCommand;
import seedu.clinic.logic.commands.ViewCommand;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.SavedCommandString;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided is not a non-zero unsigned integer."
            + "The integer value should be less than 2147483648\n\n%1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "Quantity provided is not an unsigned integer."
            + "\n\n%1$s";
    public static final String MESSAGE_INVALID_TYPE_DELETE = "You used an invalid type! Type for this command "
            + "should be either ct/s, ct/w, ct/ps or ct/pw only.\n\n%1$s";
    public static final String MESSAGE_INVALID_TYPE = "You used an invalid type! Type for this command "
            + "should be either ct/s or ct/w only.\n\n%1$s";
    public static final String MESSAGE_INVALID_PREFIX = "One of the prefix specified is not recognised "
            + "(either in this command, or in CLI-nic). Otherwise, you have used a forward slash "
            + "as input (along with spaces) to one of these prefixes (e.g. r/, n/, addr/, t/, pd/)\n\n%1$s";
    public static final String MESSAGE_INVALID_USAGE = "The input contains unnecessary arguments. Please "
            + "ensure that you only include the required arguments specified in the User Guide.\n\n%1$s";
    private static final String INVALID_TYPE_PREFIX_ASSERTION = "The prefix here should be of Type type!";
    private static final String INVALID_MESSAGE_USAGE_ASSERTION = "The message usage here should be"
            + " of View Command!";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code typeKeyword} into an {@code Type} and returns it. Leading and trailing whitespaces will be
     * trimmed and the string is converted to lower cases.
     * @throws ParseException if the specified type is invalid (neither supplier or warehouse).
     */
    public static Type parseType(String typeKeyword) throws ParseException {
        requireNonNull(typeKeyword);
        String trimmedType = typeKeyword.trim().toLowerCase();
        try {
            return Type.getType(trimmedType);
        } catch (IllegalArgumentException e) {
            throw new ParseException(MESSAGE_INVALID_TYPE_DELETE);
        }
    }

    /**
     * Parses {@code quantity} into an {@code int} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified quantity is invalid (not unsigned integer).
     */
    public static int parseQuantity(String quantity) throws ParseException {
        String trimmedQuantity = quantity.trim();
        if (!StringUtil.isUnsignedInteger(trimmedQuantity)) {
            throw new ParseException(MESSAGE_INVALID_QUANTITY);
        }
        return Integer.parseInt(trimmedQuantity);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String alias} into an {@code Alias}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Alias parseAlias(String alias) throws ParseException {
        requireNonNull(alias);
        String trimmedAlias = alias.trim();
        if (!Alias.isValidAlias(trimmedAlias)) {
            throw new ParseException(Alias.MESSAGE_CONSTRAINTS);
        }
        return new Alias(trimmedAlias);
    }

    /**
     * Parses a {@code String commandString} into a {@code SavedCommandString}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static SavedCommandString parseCommandString(String commandString) throws ParseException {
        requireNonNull(commandString);
        String trimmedCommandString = commandString.trim();
        if (!SavedCommandString.isValidSavedCommandString(trimmedCommandString)) {
            throw new ParseException(SavedCommandString.MESSAGE_CONSTRAINTS);
        }
        return new SavedCommandString(trimmedCommandString);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if there are any invalid arguments, such as invalid prefixes or unnecessary arguments used in
     * given {@code preamble}. Invalid prefixes includes prefixes that are not supposed to be used in certain
     * commands. E.g. i/ prefix should not be used in Add command input.
     */
    public static void checkInvalidArgumentsInPreamble(String preamble, String messageUsage)
            throws ParseException {
        assert !preamble.isEmpty() : "The preamble should not be empty here!";
        String trimmedCommandString = preamble;
        if (!trimmedCommandString.contains("/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_USAGE, messageUsage));
        }

        if (trimmedCommandString.contains(("/"))) {
            int slashIndex = trimmedCommandString.indexOf("/");
            String stringOfPrefixUsed = trimmedCommandString.substring(0, slashIndex + 1);
            Prefix prefixUsed = new Prefix(stringOfPrefixUsed);
            checkPrefixValid(prefixUsed, messageUsage);
        }
    }

    /**
     * Helper function for {@code checkInvalidArgumentsInPreamble} to check if there are any
     * invalid arguments, such as invalid prefixes or unnecessary arguments used in given {@code preamble}.
     */
    private static void checkPrefixValid(Prefix prefixUsed, String messageUsage) throws ParseException {
        Prefix[] prefixesNotAccepted;
        if (messageUsage.equals(AddCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_ADD;
        } else if (messageUsage.equals(AssignMacroCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_ASSIGNMACRO;
        } else if (messageUsage.equals(DeleteCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_DELETE;
        } else if (messageUsage.equals(EditCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_EDIT;
        } else if (messageUsage.equals(FindCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_FIND;
        } else if (messageUsage.equals(UpdateCommand.MESSAGE_USAGE)) {
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_UPDATE;
        } else {
            assert messageUsage.equals(ViewCommand.MESSAGE_USAGE) : INVALID_MESSAGE_USAGE_ASSERTION;
            prefixesNotAccepted = NOT_ALLOWED_PREFIXES_VIEW;
        }

        if (Arrays.asList(prefixesNotAccepted).contains(prefixUsed)
                || !Arrays.asList(ALLOWED_PREFIXES).contains(prefixUsed)) {
            throw new ParseException(String.format(MESSAGE_INVALID_PREFIX, messageUsage));
        }
    }

    /**
     * Checks if there are any invalid arguments, such as invalid prefixes or unnecessary arguments used in
     * given {@code prefix}. This allows a more specific error to be thrown, instead of detecting it as a
     * violation of any variable constraints.
     */
    public static ParseException checkInvalidArguments(Prefix prefix, ArgumentMultimap argMultimap,
           String messageUsage) {
        if (argMultimap.getValue(prefix).get().split("\\s+").length != 1
                && argMultimap.getValue(prefix).get().contains("/")) {
            return new ParseException(String.format(MESSAGE_INVALID_PREFIX, messageUsage));
        } else if (argMultimap.getValue(prefix).get().split("\\s+").length != 1
                && !prefix.equals(PREFIX_EMAIL) && !prefix.equals(PREFIX_ALIAS)) {
            return new ParseException(String.format(MESSAGE_INVALID_USAGE, messageUsage));
        }

        if (prefix.equals(PREFIX_INDEX)) {
            return new ParseException(String.format(MESSAGE_INVALID_INDEX, messageUsage));
        } else if (prefix.equals(PREFIX_PHONE)) {
            return new ParseException(Phone.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_TAG)) {
            return new ParseException(Tag.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_PRODUCT_QUANTITY)) {
            return new ParseException(String.format(MESSAGE_INVALID_QUANTITY, messageUsage));
        } else if (prefix.equals(PREFIX_NAME)) {
            return new ParseException(Name.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            return new ParseException(Address.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_REMARK)) {
            return new ParseException(Remark.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return new ParseException(Email.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_TAG)) {
            return new ParseException(Tag.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_ALIAS)) {
            return new ParseException(Alias.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else if (prefix.equals(PREFIX_PRODUCT_NAME)) {
            return new ParseException(Name.MESSAGE_CONSTRAINTS + "\n\n" + messageUsage);
        } else {
            assert prefix.equals(PREFIX_TYPE) : INVALID_TYPE_PREFIX_ASSERTION;

            if (messageUsage.equals(DeleteCommand.MESSAGE_USAGE)) {
                return new ParseException(String.format(MESSAGE_INVALID_TYPE_DELETE, messageUsage));
            }
            return new ParseException(String.format(MESSAGE_INVALID_TYPE, messageUsage));
        }
    }


    /**
     * Returns true if at least one of the prefixes does not have empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

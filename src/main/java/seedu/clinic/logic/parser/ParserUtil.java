package seedu.clinic.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.commons.util.StringUtil;
import seedu.clinic.logic.parser.exceptions.ParseException;
import seedu.clinic.model.attribute.Address;
import seedu.clinic.model.attribute.Email;
import seedu.clinic.model.attribute.Name;
import seedu.clinic.model.attribute.Phone;
import seedu.clinic.model.attribute.Remark;
import seedu.clinic.model.attribute.Tag;
import seedu.clinic.model.macro.Alias;
import seedu.clinic.model.macro.SavedCommandString;
import seedu.clinic.model.product.Product;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index provided is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_QUANTITY = "Quantity provided is not an unsigned integer.";
    public static final String MESSAGE_INVALID_TYPE = "Type is invalid, must be one of s/w/ps/pw.";
    public static final String MESSAGE_INVALID_PREFIX = "One of the prefix specified is not recognised.";

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
            throw new ParseException(MESSAGE_INVALID_TYPE);
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
     * Parses a {@code String productName} and a {@code Collection<String> tags} into a {@code Product}.
     * Leading and trailing whitespaces will be trimmed.
     * TODO: change the signature or overload the method. Add in the test cases for parseProduct
     *
     * @throws ParseException if the given {@code productName} or the given {@code tags} is invalid.
     */
    public static Product parseProduct(String productName, Collection<String> tags) throws ParseException {
        requireNonNull(productName);
        requireNonNull(tags);
        Set<Tag> tagSet = parseTags(tags);
        Name trimmedName = parseName(productName);
        return new Product(trimmedName, tagSet);
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
     * Parses a {@code String commandString} into an {@code SavedCommandString}.
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
     * Returns true if at least one of the prefixes does not have empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

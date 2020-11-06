package seedu.clinic.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_SUPPLIER_NAME = new Prefix("s/");
    public static final Prefix PREFIX_WAREHOUSE_NAME = new Prefix("w/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_PRODUCT_NAME = new Prefix("pd/");
    public static final Prefix PREFIX_PRODUCT_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_TYPE = new Prefix("ct/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_ALIAS = new Prefix("a/");
    public static final Prefix PREFIX_COMMAND_STRING = new Prefix("cs/");
    public static final Prefix[] ALLOWED_PREFIXES = new Prefix[] {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK,
        PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_TYPE, PREFIX_INDEX,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_ADD = new Prefix[] {
        PREFIX_TAG, PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_INDEX,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_ASSIGNMACRO = new Prefix[] {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK,
        PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_TYPE, PREFIX_INDEX,
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_DELETE = new Prefix[] {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK,
        PREFIX_PRODUCT_QUANTITY, PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_EDIT = new Prefix[] {
        PREFIX_TAG, PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_FIND = new Prefix[] {
        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
        PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_INDEX,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_UPDATE = new Prefix[] {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK,
        PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY, PREFIX_TYPE,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

    public static final Prefix[] NOT_ALLOWED_PREFIXES_VIEW = new Prefix[] {
        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK,
        PREFIX_PRODUCT_NAME, PREFIX_PRODUCT_QUANTITY,
        PREFIX_ALIAS, PREFIX_COMMAND_STRING
    };

}

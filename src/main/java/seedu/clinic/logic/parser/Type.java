package seedu.clinic.logic.parser;

public enum Type {
    SUPPLIER("s"),
    WAREHOUSE("w"),
    SUPPLIER_PRODUCT("ps"),
    WAREHOUSE_PRODUCT("pw");

    private final String code;

    Type(String code) {
        this.code = code;
    }

    private String getCode() {
        return code;
    }

    /**
     * Find the matching type given the value if {@code value} passed in.
     * @return The correctly matched one among the 4 types.
     * @throws IllegalArgumentException if the value passed in does not match any of the type format.
     */
    public static Type getType(String value) throws IllegalArgumentException {
        for (Type c: Type.values()) {
            if (c.getCode().equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return getCode();
    }
}

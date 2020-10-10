package seedu.clinic.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.clinic.commons.core.index.Index;
import seedu.clinic.model.Model;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the supplier in the {@code model}'s supplier list.
     */
    public static Index getMidIndexSupplier(Model model) {
        return Index.fromOneBased(model.getFilteredSupplierList().size() / 2);
    }

    /**
     * Returns the last index of the supplier in the {@code model}'s supplier list.
     */
    public static Index getLastIndexSupplier(Model model) {
        return Index.fromOneBased(model.getFilteredSupplierList().size());
    }

    /**
     * Returns the supplier in the {@code model}'s supplier list at {@code index}.
     */
    public static Supplier getSupplier(Model model, Index index) {
        return model.getFilteredSupplierList().get(index.getZeroBased());
    }

    /**
     * Returns the middle index of the warehouse in the {@code model}'s warehouse list.
     */
    public static Index getMidIndexWarehouse(Model model) {
        return Index.fromOneBased(model.getFilteredWarehouseList().size() / 2);
    }

    /**
     * Returns the last index of the warehouse in the {@code model}'s warehouse list.
     */
    public static Index getLastIndexWarehouse(Model model) {
        return Index.fromOneBased(model.getFilteredWarehouseList().size());
    }

    /**
     * Returns the warehouse in the {@code model}'s warehouse list at {@code index}.
     */
    public static Warehouse getWarehouse(Model model, Index index) {
        return model.getFilteredWarehouseList().get(index.getZeroBased());
    }
}

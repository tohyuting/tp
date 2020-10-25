package seedu.clinic.testutil;

import seedu.clinic.model.Clinic;
import seedu.clinic.model.supplier.Supplier;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * A utility class to help with building Clinic objects.
 * Example usage: <br>
 *     {@code Clinic clinic = new ClinicBuilder().withSupplier(ALICE).build();}
 */
public class ClinicBuilder {

    private Clinic clinic;

    public ClinicBuilder() {
        clinic = new Clinic();
    }

    public ClinicBuilder(Clinic clinic) {
        this.clinic = clinic;
    }

    /**
     * Adds a new {@code Supplier} to the {@code Clinic} that we are building.
     */
    public ClinicBuilder withSupplier(Supplier supplier) {
        clinic.addSupplier(supplier);
        return this;
    }

    /**
     * Adds a new {@code Warehouse} to the {@code Clinic} that we are building.
     */
    public ClinicBuilder withWarehouse(Warehouse warehouse) {
        clinic.addWarehouse(warehouse);
        return this;
    }

    public Clinic build() {
        return clinic;
    }
}

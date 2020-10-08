package seedu.clinic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinic.model.supplier.Supplier;

/**
 * An UI component that displays information of a {@code Supplier}.
 */
public class SupplierCard extends UiPart<Region> {

    private static final String FXML = "supplierListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Supplier supplier;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label remark;
    @FXML
    private Label email;
    @FXML
    private FlowPane products;

    /**
     * Creates a {@code supplierCode} with the given {@code Supplier} and index to display.
     */
    public SupplierCard(Supplier supplier, int displayedIndex) {
        super(FXML);
        this.supplier = supplier;
        id.setText(displayedIndex + ". ");
        name.setText(supplier.getName().fullName);
        phone.setText(supplier.getPhone().value);
        remark.setText(supplier.getRemark().value);
        email.setText(supplier.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SupplierCard)) {
            return false;
        }

        // state check
        SupplierCard card = (SupplierCard) other;
        return id.getText().equals(card.id.getText())
                && supplier.equals(card.supplier);
    }
}

package seedu.clinic.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinic.model.warehouse.Warehouse;

import java.util.Comparator;

/**
 * An UI component that displays information of a {@code Warehouse}.
 */
public class WarehouseCard extends UiPart<Region> {

    private static final String FXML = "warehouseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Warehouse warehouse;

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
    private Label address;
    @FXML
    private FlowPane products;

    /**
     * Creates a {@code WarehouseCard} with the given {@code Warehouse} and index to display.
     */
    public WarehouseCard(Warehouse warehouse, int displayedIndex) {
        super(FXML);
        this.warehouse = warehouse;
        id.setText(displayedIndex + ". ");
        name.setText(warehouse.getName().fullName);
        phone.setText(warehouse.getPhone().value);
        remark.setText(warehouse.getRemark().value);
        address.setText(warehouse.getAddress().value);
        warehouse.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.toStringForWareHouse()))
                .forEach(product -> products.getChildren().add(new Label(product.toStringForWareHouse())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WarehouseCard)) {
            return false;
        }

        // state check
        WarehouseCard card = (WarehouseCard) other;
        return id.getText().equals(card.id.getText())
                && warehouse.equals(card.warehouse);
    }
}

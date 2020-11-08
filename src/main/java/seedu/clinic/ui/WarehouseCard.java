package seedu.clinic.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * An UI component that displays information of a {@code Warehouse}.
 */
public class WarehouseCard extends UiPart<Region> {

    private static final String FXML = "WarehouseListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
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
    private Label noProductsLabel;
    @FXML
    private VBox products;
    @FXML
    private TitledPane productsTitledPane;

    /**
     * Creates a {@code WarehouseCard} with the given {@code Warehouse} and index to display.
     */
    public WarehouseCard(Warehouse warehouse, int displayedIndex) {
        super(FXML);
        this.warehouse = warehouse;
        id.setText(displayedIndex + ". ");
        name.setText(warehouse.getName().fullName);
        name.setWrapText(true);
        phone.setText(warehouse.getPhone().value);
        phone.setWrapText(true);
        String remarkText = warehouse.getRemark().value == "" ? "No Remark" : warehouse.getRemark().value;
        remark.setText(remarkText);
        remark.setWrapText(true);
        address.setText(warehouse.getAddress().value);
        address.setWrapText(true);

        //Keep products pane closed by default
        productsTitledPane.setExpanded(false);
        int productIndex = 1;
        if (!warehouse.getProducts().isEmpty()) {
            noProductsLabel.setVisible(false);
            for (Product product : warehouse.getProducts()) {
                VBox productBox = new VBox();
                productBox.setMaxWidth(400);
                FlowPane productTags = new FlowPane();
                productTags.setId("tags");
                Label productName = new Label(productIndex + ". " + product.toString());
                productName.setWrapText(true);
                productName.setMaxWidth(400);
                productBox.getChildren().add(productName);
                product.getProductTags().stream()
                        .sorted(Comparator.comparing(tag -> tag.tagName))
                        .forEach(tag -> {
                            Label tagLabel = new Label(tag.tagName);
                            tagLabel.setWrapText(true);
                            tagLabel.setMaxWidth(80);
                            productTags.getChildren().add(tagLabel);
                        });
                productBox.getChildren().add(productTags);
                products.getChildren().add(productBox);
                productIndex++;
            }
        }
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

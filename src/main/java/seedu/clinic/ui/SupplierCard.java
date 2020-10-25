package seedu.clinic.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.clinic.model.product.Product;
import seedu.clinic.model.supplier.Supplier;

/**
 * An UI component that displays information of a {@code Supplier}.
 */
public class SupplierCard extends UiPart<Region> {

    private static final String FXML = "SupplierListCard.fxml";

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
    private FlowPane remark;
    @FXML
    private Label email;
    @FXML
    private Label noProductsLabel;
    @FXML
    private VBox products;
    @FXML
    private TitledPane productsTitledPane;

    /**
     * Creates a {@code supplierCode} with the given {@code Supplier} and index to display.
     */
    public SupplierCard(Supplier supplier, int displayedIndex) {
        super(FXML);
        this.supplier = supplier;
        id.setText(displayedIndex + ". ");
        name.setText(supplier.getName().fullName);
        phone.setText(supplier.getPhone().value);
        remark.getChildren().add(new Label(supplier.getRemark().value));
        email.setText(supplier.getEmail().value);
        /*
        supplier.getProducts().stream()
                .sorted(Comparator.comparing(product -> product.getProductName().fullName))
                .forEach(product -> {
                    products.getChildren().add(new Label(product.getProductName().fullName));
                    product.getProductTags().stream()
                            .sorted(Comparator.comparing(tag -> tag.tagName))
                            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
                });
        */
        //Keep products pane closed by default
        productsTitledPane.setExpanded(false);
        int productIndex = 1;
        if (!supplier.getProducts().isEmpty()) {
            noProductsLabel.setVisible(false);
            for (Product product : supplier.getProducts()) {
                VBox productBox = new VBox();
                productBox.setMaxWidth(500);
                FlowPane productTags = new FlowPane();
                productTags.setId("tags");
                Label productName = new Label(productIndex + ". " + product.getProductName().fullName);
                productName.setWrapText(true);
                productName.setMaxWidth(500);
                productBox.getChildren().add(productName);
                product.getProductTags().stream()
                        .sorted(Comparator.comparing(tag -> tag.tagName))
                        .forEach(tag -> {
                            Label tagLabel = new Label(tag.tagName);
                            tagLabel.setWrapText(true);
                            tagLabel.setMaxWidth(500);
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
        if (!(other instanceof SupplierCard)) {
            return false;
        }

        // state check
        SupplierCard card = (SupplierCard) other;
        return id.getText().equals(card.id.getText())
                && supplier.equals(card.supplier);
    }
}

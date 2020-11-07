package seedu.clinic.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.clinic.commons.core.LogsCenter;
import seedu.clinic.model.warehouse.Warehouse;

/**
 * Panel containing the list of Warehouses.
 */
public class WarehouseListPanel extends UiPart<Region> {
    private static final String FXML = "WarehouseListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WarehouseListPanel.class);

    @javafx.fxml.FXML
    private ListView<Warehouse> warehouseListView;

    /**
     * Creates a {@code WarehouseListPanel} with the given {@code ObservableList}.
     */
    public WarehouseListPanel(ObservableList<Warehouse> warehouseList) {
        super(FXML);
        warehouseListView.setFocusTraversable(false);
        warehouseListView.setItems(warehouseList);
        warehouseListView.setCellFactory(listView -> new WarehouseListViewCall());
        warehouseListView.setPlaceholder(new Label("There are no warehouses yet."));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Warehouse} using a {@code WarehouseCard}.
     */
    class WarehouseListViewCall extends ListCell<Warehouse> {
        @Override
        protected void updateItem(Warehouse warehouse, boolean empty) {
            super.updateItem(warehouse, empty);

            if (empty || warehouse == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WarehouseCard(warehouse, getIndex() + 1).getRoot());
            }
        }
    }

}

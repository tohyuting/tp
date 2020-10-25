---
layout: page
title: User Guide
Team: W14-4
---

CLI-nic is **an application to help medical supply managers keep track of medical products and storage.** It is optimized
for these managers to **update product supply conditions and access critical product information quickly** via fast typing
and efficient Graphical User Interface interaction.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `CLInic.jar` from [here](https://github.com/AY2021S1-CS2103-W14-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your CLI-nic system.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will display instructions on various commands.<br>
   Some example commands you can try:

   * **`add`** `w/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse` : Adds a warehouse
    with the name warehouseA located at John street, block 123, #01-01. The warehouse is noted to be the “First warehouse”.

   * **`add`** `s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor` : Adds a
    supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is
    philipsPharm@gmail.com. The supplier is noted to be the “largest contractor”.

   * **`add`** `s/SupplierA pd/PANADOL SUSP t/FEVER` : Adds the product PANADOL SUSP to list of products from
    supplierA. This indicates that supplierA is selling this product. PANADOL SUSP also has a tag of FEVER.

   * **`clear`** : Deletes all contacts.

   * **`delete`** `delete supplier 12` : Removes supplier at index 12 from the list of suppliers.

   * **`edit`** `edit ct/s i/1 n/Alice p/68574214` : Edits supplier at index 1 in supplier list to have name Alice and phone number 685742141.

   * **`exit`** : Exits the app.

   * **`find`** `PANADOL warehouse` : Displays all the warehouses managed by the manager that has a product
    named PANADOL.

   * **`list`** `list`: Displays all the warehouses and suppliers in CLI-nic.

   * **`update`** `w/WarehouseA pd/Panadol q/10` : Updates the quantity of PANADOL in WarehouseA to 10. The quantity of PANADOL in WarehouseA can be more than 10 or lesser than 10 before the update is done.

   * **`view ct/w i/3`** : Displays all the information associated with warehouse at index 3 of warehouse list e.g. name, address, contact, products sold by the warehouse etc.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `delete TYPE INDEX`, `TYPE` is a parameter which can be used as `delete warehouse 1`.

* Items in square brackets are optional.<br>
  e.g `add s/SUPPLIER_NAME p/PHONE [e/EMAIL_ADDRESS]` can be used as `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com` or as `add s/Philips Pharmaceutical p/00000000`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[t/TAG]…​` can be used as `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

</div>

### Viewing help : `help`

Displays a list of available commands and their utility description.
Narrows down to a specific command and its actual input format and samples if specified.
The user guide can also be accessed by pressing F1 to copy the webpage link. Internet connection is needed to view the user guide.

![help message](images/helpMessage.png)

Format: `help [COMMAND]`

Examples:

* `help`  Display entire list of `COMMAND` and their description.
* `help add` Displays the detailed description, input format and an input example of add command.

### Adding a warehouse : `add`

Adds warehouse to the CLI-nic application.

Format: `add w/WAREHOUSE_NAME p/PHONE addr/ADDRESS [r/WAREHOUSE_REMARK]`

Examples:

* `add w/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse` Adds a warehouse with the name
 warehouseA located at John street, block 123, #01-01. The warehouse's contact number is 00000000. The warehouse is noted to be the “First warehouse”.

### Adding a supplier : `add`

Adds a supplier to the CLI-nic application.

Format: `add s/SUPPLIER_NAME p/PHONE [e/EMAIL_ADDRESS] [r/SUPPLIER_REMARK]`

Examples:

* `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor` Adds a
 supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is philipsPharm@gmail.com. The supplier is noted to be the “largest contractor”.

### Adding a product to a supplier : `add`

Adds product information to a supplier; associates a particular product with the supplier in the CLI-nic application.

Format: `addp s/SUPPLIER_NAME pd/PRODUCT_NAME [t/TAG]…​`

Examples:

* `addp s/SupplierA pd/PANADOL SUSP t/FEVER` Adds the product PANADOL SUSP to list of products from supplierA.
* This indicates that supplierA is selling this product. PANADOL SUSP also has a tag of FEVER.

### Clearing all entries : `clear`

Clears all entries (Suppliers and Warehouses) from the CLI-nic.

Format: `clear`

### Deletes a particular warehouse or supplier : `delete`

Deletes entries of warehouses or suppliers that are not needed anymore.

Format: `delete ct/TYPE i/INDEX`

* The TYPE specified should be one of these values: w / s.
* The INDEX must be a positive integer, not exceeding the total number of items.

Examples:

* `delete ct/w i/1` Removes the warehouse at index 1.
* `delete ct/s i/12` Removes supplier at index 12 from the list of suppliers.

### Deletes a product in a certain warehouse or supplier : `delete`

Deletes a product entry no longer stored by a certain warehouse or sold by a specific supplier.

Format: `delete ct/TYPE i/INDEX pd/PRODUCT_NAME`

* The TYPE specified should be one of these values: pw / ps.
* The PRODUCT_NAME must be identifiable and starts with alphanumeric character.
* The product with the PRODUCT_NAME should be in the INDEX-th supplier/warehouse in the displayed list.

Examples:

* `delete ct/pw i/1 pd/Panadol` Removes product with the name _Panadol_ from
the warehouse at index 1 of the list of warehouses.
* `delete ct/ps i/12 pd/Aspirin` Removes product with the name _Aspirin_
from the supplier at index 12 of the list of suppliers.

### Deletes a product in a certain warehouse or supplier : `delete`

Deletes a product entry no longer stored by a certain warehouse or sold by a specific supplier.

Format: `delete TYPE INDEX pd/PRODUCT_NAME`

* The PRODUCT_NAME must be identifiable and starts with alphanumeric character.
* The product with the PRODUCT_NAME should be in the INDEX-th supplier/warehouse in the displayed list.

Examples:

* `delete warehouse 1 pd/Panadol` Removes product with the name _Panadol_ from
the warehouse at index 1 of the list of warehouses.
* `delete supplier 12 pd/Aspirin` Removes product with the name _Aspirin_
from the supplier at index 12 of the list of suppliers.

### Edit a warehouse or supplier : `edit`

Edits a warehouse or supplier at specified index. Name, Phone, Remarks of specified supplier and warehouse can be edited. In addition, a warehouse's address and a supplier's email can be edited as well. Note that no two warehouses or suppliers can share the same name in CLI-nic.

Format: `edit ct/TYPE i/INDEX [n/NAME] [p/PHONE] [r/REMARK] [addr/ADDRESS] [e/EMAIL]`

* `addr/ADDRESS` prefix can only be used for Warehouses and `e/EMAIL` prefix can only be used for Suppliers.
* At least one field has to be specified in the edit command.
* Edited supplier or warehouse must be different from one started with.

Examples:

* `edit ct/s i/1 n/Alice p/85236417 r/Largest Supplier e/alicekoh@example.com` Edits the first supplier in the displayed supplier list to be named _Alice_ with contact number of _85236417_ and remark of _Largest Supplier_. In addition, email field of supplier is to be edited as _alicekoh@example.com_.
* `edit ct/w i/2 n/Bob p/67851234 r/Largest Warehouse addr/Jurong Street 11` Edits the second warehouse in the displayed warehouse list to be named _Bob_ with contact number of _67851234_ and remark of _Largest Warehouse_. In addition, address field of warehouse is to be edited as _Jurong Street 11_.

### Finding medical product associated with warehouses / suppliers: `find`

Finds all suppliers or warehouses managed by the manager that sells the relevant medical products.

Format: `find TYPE KEYWORD [KEYWORD]…​`

* KEYWORD specified is case-insensitive and must contain at least one keyword.
* The TYPE specified should be one of these values: warehouse / supplier.

Examples:

* `find warehouse PANADOL SUSP` Displays all the warehouses managed by the manager that has a product named PANADOL SUSP.
* `find supplier masks` Displays all the suppliers that have stock for the input product.

### List all suppliers and warehouses entries : `list`

List all entries (Suppliers and Warehouses) from the CLI-nic.

Format: `list`

### View a specific supplier / warehouse: `view`

Shows the supplier/warehouse at the specified index with their relevant information e.g. products associated with the supplier/warehouse, address etc.

Format: `view ct/TYPE i/INDEX`

* The TYPE specified should be one of these values: s or w representing supplier or warehouse.
* The supplier/warehouse INDEX specified should be within range of the supplier/warehouse list, corresponding to the number on the list.

Examples:

* `view ct/s 1` Displays all the information associated with supplier at index 1 in the supplier list e.g. name, contact, email, remarks, products sold by the supplier.
* `view ct/w 2` Displays all the information associated with at index 2 in the warehouse list e.g. name, contact, address, remarks, all the products stored in the warehouse.

### Update the stock for a product: `update`

* If the product does not exist for that warehouse, it will associate the new product with the warehouse and the input quantity. Otherwise, it will update the stock of the existing product with the new quantity.

Format:	`update w/WAREHOUSE_NAME pd/PRODUCT_NAME q/QUANTITY`

Example:

* `update w/WarehouseA pd/Panadol q/10` Updates the quantity of Panadol in WarehouseA to 10. The quantity of Panadol in WarehouseA can be more than 10 or lesser than 10 before the update is done.

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CLI-nic data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br />

--------------------------------------------------------------------------------------------------------------------

## To be implemented in the future

### Creating a purchase order : `create`
Creates a purchase order to track the purchase of medical products from a supplier to a warehouse.

Format: `create s/SUPPLIER_NAME w/WAREHOUSE_NAME pd/PRODUCT_NAME…​ qty/PRODUCT_QUANTITY…​ date/EXPECTED_DELIVERED_DATE`

* All the SUPPLIER_NAME/WAREHOUSE_NAME/PRODUCT_NAME must be identifiable.
* The number of arguments specified for PRODUCT_QUANTITY and PRODUCT_NAME must match.
* EXPECTED_DELIVERED_DATE must be after current time and of the form YYYY-MM-DD.

Examples:

* `create s/SupplierA w/WarehouseA pd/Panadol1 Panadol2 qty/100 200 date/2020-12-12` Creates a purchase order for the delivery of 100, 200 of products Panadol1 & Panadol2 respectively from SupplierA to WarehouseA by December 12, 2020.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous CLI-nic home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** Warehouse | `add w/WAREHOUSE_NAME p/PHONE addr/ADDRESS [r/WAREHOUSE_REMARK]`<br> e.g., `add w/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse`
**Add** Supplier | `add s/SUPPLIER_NAME p/PHONE [e/EMAIL_ADDRESS] [r/SUPPLIER_REMARK]`<br> e.g., `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor`
**Addp** Product | `addp s/SUPPLIER_NAME pd/PRODUCT_NAME [t/TAG…​]`<br> e.g., `addp s/SupplierA pd/PANADOL SUSP t/FEVER`
**Clear** | `clear`
**Delete** | `delete ct/TYPE i/INDEX`<br> e.g., `delete ct/w i/1`
**Delete** Product| `delete ct/TYPE i/INDEX pd/PRODUCT_NAME`<br> e.g., `delete ct/pw i/1 pd/Panadol`
**Edit** | `edit ct/TYPE i/INDEX [n/NAME] [p/PHONE] [r/REMARK] [addr/ADDRESS] [e/EMAIL]`<br> e.g., `edit ct/w i/1 n/Alice Warehouse p/98765432 r/second largest warehouse addr/21 Lower Kent Ridge Rd`
**Find** | `find TYPE KEYWORD [KEYWORD]…`<br> e.g. `find warehouse panadol`
**Help** | `help [COMMAND]`<br> e.g., `help add`
**List** | `list`
**Update** | `update w/WAREHOUSE_NAME pd/PRODUCT_NAME q/QUANTITY` <br> e.g., `update w/WarehouseA pd/Panadol q/10`
**View** | `view ct/TYPE i/INDEX`<br> e.g. `view ct/s i/1`

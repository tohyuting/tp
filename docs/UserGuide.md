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

   * **`add`** `ct/w n/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse` : Adds a
    warehouse with the name warehouseA located at John street, block 123, #01-01. This warehouse is the
    “First warehouse”.

   * **`add`** `ct/s n/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor` : Adds a
    supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is philipsPharm@gmail.com.
    This supplier is the “largest contractor”.
    
   * **`assignmacro`** `a/uwm cs/update ct/w n/main warehouse` : Assign a macro that pairs the alias "uwm" to the command string "update ct/w n/main warehouse".

   * **`clear`** : Deletes all contacts.

   * **`delete`** `ct/s i/12` : Removes supplier at index 12 from the list of suppliers.

   * **`exit`** : Exits the app.

   * **`find`** `ct/w pd/panadol` : Displays all the warehouses managed by the manager that has a product
    named PANADOL.

   * **`list`**: Displays all the warehouses and suppliers in CLI-nic.

   * **`removemacro`** `uwm` : Removes the macro with the alias 'uwm'.

   * **`update`** `ct/w n/WarehouseA pd/Panadol q/10 t/fever` : Updates the quantity of PANADOL in WarehouseA to 10, and assigns the tag 'fever' to the product.

   * **`view supplier supplierA`** : Displays all the information associated with supplierA e.g. address, contact, email, products sold by the supplier etc.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `delete TYPE INDEX`, `TYPE` is a parameter which can be used as `delete warehouse 1`.

* Items in square brackets are optional.<br>
  e.g `add ct/s n/SUPPLIER_NAME p/PHONE e/EMAIL_ADDRESS` can be used as `add ct/s n/Philips Pharmaceutical p
  /00000000 e/philipsPharm@gmail.com` or as `add ct/s n/Philips Pharmaceutical p/00000000`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[t/TAG]…​` can be used as `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE`, `p/PHONE n/NAME` is also acceptable.

* If multiple arguments with same prefixes are in the input and all the values are valid, the last value is chosen. <br>
  e.g. if a user's input specifies `ct/w ct/s` where both `w` and `s` are valid, there will be no error thrown. However, the type __s - supplier__ will be chosen instead.

</div>

### Viewing help : `help`

Displays a list of available commands and their utility description.
Narrows down to a specific command and its actual input format and samples if specified.

![help message](images/helpMessage.png)

Format: `help [COMMAND]`

Examples:

* `help`  Display entire list of `COMMAND` and their description.
* `help add` Displays the detailed description, input format and an input example of add command.

### Adding a warehouse : `add`

Adds warehouse to the CLI-nic application.

Format: `add ct/TYPE n/WAREHOUSE_NAME p/PHONE addr/ADDRESS [r/WAREHOUSE_REMARK]`
* The TYPE specified here should be w for warehouse.

Examples:

* `add ct/w n/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse` Adds a warehouse
 with the name warehouseA located at John street, block 123, #01-01. The warehouse's contact number is 00000000.
 This warehouse is the “First warehouse”.

### Adding a supplier : `add`

Adds a supplier to the CLI-nic application.

Format: `add ct/TYPE n/SUPPLIER_NAME p/PHONE e/EMAIL_ADDRESS [r/SUPPLIER_REMARK]`
* The TYPE specified here should be s for supplier.

Examples:

* `add ct/s n/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor`
Adds a supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is philipsPharm@gmail.com.
This supplier is the “largest contractor”.

### Clearing all entries : `clear`

Clears all entries (Suppliers and Warehouses) from the CLI-nic.

Format: `clear`

### Deletes a particular warehouse or supplier : `delete`

Deletes entries of warehouses or suppliers that are not needed anymore.

Format: `delete ct/TYPE i/INDEX`

* The TYPE specified should be one of these values: `w` / `s`.
* The INDEX must be a positive integer, not exceeding the total length of the displayed list.

Examples:

* `delete ct/w i/1` Removes the warehouse at index 1 of the list of warehouses.
* `delete ct/s i/12` Removes supplier at index 12 of the list of suppliers.

### Deletes a product in a certain warehouse or supplier : `delete`

Deletes a product entry no longer stored by a certain warehouse or sold by a specific supplier.

Format: `delete ct/TYPE i/INDEX pd/PRODUCT_NAME`

* The TYPE specified should be one of these values: `pw` / `ps`.
* The INDEX must be a positive integer, not exceeding the total length of the displayed list.
* The PRODUCT_NAME must be an identifiable full name of the product chosen, and must starts with an alphanumeric character.
* The product with the PRODUCT_NAME should be in the INDEX-th supplier/warehouse in the displayed list.

Examples:

* `delete ct/pw i/1 pd/Panadol` Removes product with the name _Panadol_ from
the warehouse at index 1 of the list of warehouses.
* `delete ct/ps i/12 pd/Aspirin` Removes product with the name _Aspirin_
from the supplier at index 12 of the list of suppliers.

<div markdown="span" class="alert alert-info">:information_source:
**Note:** If additional PRODUCT_NAME behind prefix `pd/` is added when the TYPE `ct/` given is "w" or "s", the PRODUCT_NAME will be ignored
as the entire warehouse/supplier entry is removed.
</div>

### Finding suppliers/warehouses: `find`

Finds all suppliers or warehouses whose name, remark and/or products sold/stored matches the provided argument keywords.

Format: `find ct/TYPE [n/NAME…​] [pd/PRODUCT_NAME…​] [r/REMARK…​]`

* Keywords specified are case-insensitive.
* The TYPE specified should be one of these values: s/w.
* Any combination of the name, product and remark prefix can be provided but at least one of the prefix for name,
product or remark must be specified.

Examples:

* `find ct/s pd/masks` Displays all the suppliers that sell masks.
* `find ct/w pd/PANADOL SUSP r/biggest` Displays the biggest warehouse that stores products matching either PANADOL or
SUSP.

### List all suppliers and warehouses entries : `list`

List all entries (Suppliers and Warehouses) from the CLI-nic.

Format: `list`

### View a specific supplier / warehouse: `view`

Shows a particular supplier/warehouse with their relevant information e.g. products associated with the supplier/warehouse, address etc.

Format: `view TYPE NAME`

* The TYPE specified should be one of these values: supplier or warehouse.
* The supplier/warehouse NAME specified is case-insensitive.

Examples:

* `view supplier supplierA` Displays all the information associated with supplierA e.g. address, contact, email, products sold by the supplier etc.
* `view warehouse warehouseB` Displays all the information associated with warehouseB e.g. address, all the products stored in the warehouse etc.

### Update the quantity/tags of a product for a supplier/warehouse: `update`

* If the product does not exist for that warehouse/supplier, it will associate the new product (optionally with quantity/tags) with the warehouse/supplier. Otherwise, it will update the existing product in the warehouse/supplier with the new quantity/tags. If the product already exists, at least one optional argument has to be supplied.

Format:	`update ct/TYPE n/ENTITY_NAME pd/PRODUCT_NAME [q/QUANTITY] [t/TAG]`

Example:

* `update ct/w n/WarehouseA pd/Panadol q/10 t/fever` Updates the quantity of Panadol in WarehouseA to 10, and gives the product the tag 'fever'.

### Assign Macro to selected command string: `assignmacro`

* The alias cannot be an existing command word or already used in an existing macro , and should only consist of alphanumeric characters and/or underscores. The saved command string can consist of any number of prefixes, but the first word has to be a pre-defined command word.  

Format:	`assignmacro a/ALIAS cs/COMMAND_STRING`

Example:

* `assignmacro a/uwm cs/update ct/w n/main warehouse` Assign a macro that pairs the alias "uwm" to the command string "update ct/w n/main warehouse".

### Remove Macro with specified alias: `removemacro`

* The alias specified has to exist.

Format:	`removemacro ALIAS`

Example:

* `removemacro uwm` Removes the macro with the alias 'uwm'.

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
**Add** Warehouse | `add ct/w n/WAREHOUSE_NAME p/PHONE addr/ADDRESS [r/WAREHOUSE_REMARK]`<br> e.g., `add ct/w n/warehouseA p/00000000 addr/John street, block 123, #01-01 r/First warehouse`
**Add** Supplier | `add ct/s n/SUPPLIER_NAME p/PHONE e/EMAIL_ADDRESS [r/SUPPLIER_REMARK]`<br> e.g., `add ct/s n/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com r/largest contractor`
**Assign Macro** | `assignmacro a/ALIAS cs/COMMAND_STRING`<br> e.g., `assignmacro a/uwm cs/update ct/w n/main warehouse`
**Clear** | `clear`
**Delete** | `delete ct/TYPE i/INDEX`<br> e.g., `delete ct/w i/1`
**Delete** Product| `delete ct/TYPE i/INDEX pd/PRODUCT_NAME`<br> e.g., `delete ct/pw i/1 pd/Panadol`
**Find** | `find ct/TYPE [n/NAME…​] [pd/PRODUCT_NAME…​] [r/REMARK…​]`<br> e.g. `find ct/w pd/panadol`
**Help** | `help [COMMAND]`<br> e.g., `help add`
**List** | `list`
**Remove Macro** | `removemacro ALIAS`<br> e.g.`removemacro uwm`
**Update** | `update ct/TYPE n/ENTITY_NAME pd/PRODUCT_NAME [q/QUANTITY] [t/TAG…​]` <br> e.g., `update ct/w n/WarehouseA pd/Panadol q/10 t/fever`
**View** | `view TYPE NAME`<br> e.g. `view supplier supplierA`

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

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will display instructions on various commands.<br>
   Some example commands you can try:

   * **`add`** `w/warehouseA addr/John street, block 123, #01-01 wn/First warehouse` : Adds a warehouse
    with the name warehouseA located at John street, block 123, #01-01. The warehouse is noted to be the “First warehouse”.
   
   * **`add`**` s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com sn/largest contractor` : Adds a
    supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is
    philipsPharm@gmail.com. The supplier is noted to be the “largest contractor”.
   
   * **`add`**` s/SupplierA p/PANADOL SUSP t/FEVER` : Adds the product PANADOL SUSP to list of products from
    supplierA. This indicates that supplierA is selling this product. PANADOL SUSP also has a tag of FEVER.
    
   * **`clear`** : Deletes all contacts.

   * **`delete`**` delete supplier 12` : Removes supplier at index 12 from the list of suppliers.

   * **`exit`** : Exits the app.

   * **`find`**` PANADOL warehouse`** : Displays all the warehouses managed by the manager that has a product
    named PANADOL.

   * **`update`**` w/WarehouseA p/Panadol q/10` : Updates the quantity of Panadol in WarehouseA to 10. The
    quantity of Panadol in WarehouseA can be more than 10 or lesser than 10 before the update is done.

   * **`view supplier supplierA`** : Displays all the information associated with supplierA e.g. address, contact, email, products sold by the supplier etc.


1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `list TYPE`, `TYPE` is a parameter which can be used as `list products`.

* Items in square brackets are optional.<br>
  e.g `[LIST_TYPE] INDEX` can be used as `delete store 12` or as `delete 1`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[t/TAG]…​` can be used as `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

### Viewing help : `help`

Displays a list of available commands and their utility description.
Narrows down to a specific command and its actual input format and samples if specified.

![help message](images/helpMessage.png)

Format: `help [COMMAND]`

Examples:
* `help`  Display entire list of commands and their description
* `help add` Displays the detailed description, input format and an input example of add command.

### Adding a warehouse : `add`

Adds warehouse to the CLI-nic application.

Format: `add w/WAREHOUSE_NAME addr/ADDRESS [wn/WAREHOUSE_NOTE]`

Examples:
* `add w/warehouseA addr/John street, block 123, #01-01 wn/First warehouse` : Adds a warehouse with the name
 warehouseA located at John street, block 123, #01-01. The warehouse is noted to be the “First warehouse”.

### Adding a supplier : `add`

Adds a supplier to the CLI-nic application.

Format: `add s/SUPPLIER_NAME p/CONTACT_NUMBER [e/EMAIL_ADDRESS] [sn/SUPPLIER_NOTE]`

Examples:
* `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com sn/largest contractor` : Adds a
 supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is philipsPharm@gmail.com. The supplier is noted to be the “largest contractor”.

### Adding a product to a supplier : `add`

Adds product information to a supplier; associates a particular product with the supplier in the CLI-nic application.

Format: `add s/SUPPLIER_NAME p/PRODUCT_NAME [t/TAG...]`

Examples:
* `add s/SupplierA p/PANADOL SUSP t/FEVER` : Adds the product PANADOL SUSP to list of products from supplierA.
* This indicates that supplierA is selling this product. PANADOL SUSP also has a tag of FEVER.

### Update the stock for a product: `update`

* If the product does not exist for that store, it will associate the new product with the store and the
* input quantity. Otherwise, it will update the stock of the existing product with the new quantity.

Format:	`update w/WAREHOUSE_NAME p/PRODUCT_NAME q/QUANTITY`

Example:
* `update w/WarehouseA p/Panadol q/10` : Updates the quantity of Panadol in WarehouseA to 10. The
* quantity of Panadol in WarehouseA can be more than 10 or lesser than 10 before the update is done.

### View a specific supplier / warehouse: `view`

Shows a particular supplier/warehouse with their relevant information e.g. products associated with the supplier/warehouse, address etc.

Format: `view TYPE NAME`
* The TYPE specified should be one of these values: supplier or warehouse
* NAME specified is case-insensitive

Examples:
* `view supplier supplierA` Displays all the information associated with supplierA e.g. address, contact, email, products sold by the supplier etc.
* `view warehouse warehouseB` Displays all the information associated with warehouseB e.g. address, all the products stored in the warehouse etc. 

### Finding medical product associated with warehouses / suppliers: `find`

Finds all suppliers or warehouses managed by the manager that sells the relevant medical products.

Format: `find PRODUCT TYPE`

* PRODUCT and KEYWORD specified is case-insensitive.
* The TYPE specified should be one of these values: warehouse / supplier

Examples:
* `find PANADOL warehouse` displays all the warehouses managed by the manager that has a product named PANADOL. 
* `find masks supplier` displays all the suppliers that have stock for the input product.	

### Deletes a particular warehouse or supplier : `delete`

Delete entries of warehouses or suppliers that are not needed anymore.

**Format**: `delete TYPE INDEX`

* The TYPE specified should be one of these values: warehouse / supplier.
* The INDEX must be a positive integer, not exceeding the total number of items.

**Examples**

* `delete warehouse 1` Removes the warehouse at index 1.
* `delete supplier 12` Removes supplier at index 12 from the list of suppliers.

### Clearing all entries : `clear`

Clears all entries (Suppliers and Warehouses) from the CLI-nic.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

CLI-nic data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br />

### To be implemented in the future

### **Creating a purchase order : `create`**
* Create a purchase order to track the purchase of medical products from a supplier to a store.
* **Format**: `create sid/SUPPLIER_ID s/STORE_ID id/PRODUCT_ID…​ qty/PRODUCT_QUANTITY…​ date/EXPECTED_DELIVERED_DATE`		
    * The number specified for PRODUCT_ID cannot exceed the total number of products. All the IDs must be identifiable
    * The number of arguments specified for PRODUCT_QUANTITY and PRODUCT_ID must match
    * EXPECTED_DELIVERED_DATE must be after current time and of the form YYYY-MM-DD

* **Examples**: 
    * `create sid/01 s/123 id/1 2 4 8 qty/100 200 400 800 date/2020-12-12` : Creates a purchase order for the delivery of 100, 200, 400 and 800 of products with ID 1, 2, 4 and 8 respectively from supplier ID of 01 to store ID of 123 by December 12, 2020.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous CLI-nic home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** Warehouse | `add w/WAREHOUSE_NAME addr/ADDRESS [wn/WAREHOUSE_NOTE]`<br> e.g., `add w/warehouseA addr/John street, block 123, #01-01 wn/First warehouse`
**Add** Supplier | `add s/SUPPLIER_NAME p/CONTACT_NUMBER [e/EMAIL_ADDRESS] [sn/SUPPLIER_NOTE]`<br> e.g., `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com sn/largest contractor`
**Add** Product | `add s/SUPPLIER_NAME p/PRODUCT_NAME [t/TAG...]`<br> e.g., `add s/SupplierA p/PANADOL SUSP t/FEVER`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Find** | `find TYPE KEYWORD`<br> e.g. `find product panadol`
**Help** | `help`
**Update** | `update w/WAREHOUSE_NAME p/PRODUCT_NAME q/QUANTITY` <br> e.g., `update w/WarehouseA p/Panadol q/10`
**View** | `view TYPE NAME`<br> e.g. `view supplier supplierA`

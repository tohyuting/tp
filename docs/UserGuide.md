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

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`** `w/warehouseA addr/John street, block 123, #01-01 wn/First warehouse` : Adds a warehouse
    with the name warehouseA located at John street, block 123, #01-01. The warehouse is noted to be the “First warehouse”.
   
   * **`add`** `s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com sn/largest contractor` : Adds a
    supplier named Philips Pharmaceutical. His contact number is 00000000 and his email is
    philipsPharm@gmail.com. The supplier is noted to be the “largest contractor”.
   
   * **`add`** `s/SupplierA p/PANADOL SUSP t/FEVER` : Adds the product PANADOL SUSP to list of products from
    supplierA. This indicates that supplierA is selling this product. PANADOL SUSP also has a tag of FEVER.
    
   * **`update`** `w/WarehouseA p/Panadol q/10` : Updates the quantity of Panadol in WarehouseA to 10. The
    quantity of Panadol in WarehouseA can be more than 10 or lesser than 10 before the update is done.

   * **`Create`** : Create a purchase order

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times.<br>
  e.g. `[t/TAG]…​` can be used as `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

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

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Finding medical products / suppliers: `find`

Finds medical products / suppliers whose information contains any of the given keywords.

Format: `find TYPE KEYWORD`

* `TYPE` takes in either `product` / `supplier`.
* `KEYWORD` is case-insensitive.
* The search is case-insensitive.
* Searches only the name and additional information of the products and suppliers.
* Only full words will be matched e.g. `Han` will not match `Hans`.

Examples:
* `find product panadol` returns all medical products containing `panadol` in its name or additional description.
* `find supplier Kent Ridge` returns all suppliers that are located in `Kent Ridge`.	

### Removing a purchase order/store/product/supplier : `delete` [Coming soon]

Remove entries that are not needed anymore.

**Format**: `delete [LIST_TYPE] INDEX`

* Deletes from the purchase order list by default at `INDEX`
* The `LIST_TYPE` specified should be one of these values: **order/store/product/supplier**
* The `INDEX` **must be a positive integer**, not exceeding the total number of items


**Examples**

* `delete 1`: Removes the 1st order from the list of orders as no `LIST_TYPE` is specified.
* `delete store 12`: Removes 12th store from the list of stores.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

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
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** Warehouse | `add w/WAREHOUSE_NAME addr/ADDRESS [wn/WAREHOUSE_NOTE]` <br> e.g., `add w/warehouseA addr/John street, block 123, #01-01 wn/First warehouse`
**Add** Supplier | `add s/SUPPLIER_NAME p/CONTACT_NUMBER [e/EMAIL_ADDRESS] [sn/SUPPLIER_NOTE]` <br> e.g., `add s/Philips Pharmaceutical p/00000000 e/philipsPharm@gmail.com sn/largest contractor`
**Add** Product | `add s/SUPPLIER_NAME p/PRODUCT_NAME [t/TAG...]` <br> e.g., `add s/SupplierA p/PANADOL SUSP t/FEVER`
**Update** | `update w/WAREHOUSE_NAME p/PRODUCT_NAME q/QUANTITY` <br> e.g., `update w/WarehouseA p/Panadol q/10`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Find** | `find TYPE KEYWORD`<br> e.g. `find product panadol`
**List** | `list`
**Help** | `help`

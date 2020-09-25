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

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will display instructions on various commands.<br>
   Some example commands you can try:

   * **`view supplier supplierA`** : Displays all the information associated with supplierA e.g. address, contact, email, products sold by the supplier etc.

   * **`add`**` p/PANADOL SUSP id/1 t/FEVER` : Adds product named `PANADOL SUSP` with product id of 1 with a `FEVER` tag to CLI-nic.

   * **`delete`**` delete supplier 12` : Removes supplier at index 12 from the list of suppliers.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

   * **`Create`** : Create a purchase order


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

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### **Creating a purchase order : `create`**
* Create a purchase order to track the purchase of medical products from a supplier to a store.
* **Format**: `create sid/SUPPLIER_ID s/STORE_ID id/PRODUCT_ID…​ qty/PRODUCT_QUANTITY…​ date/EXPECTED_DELIVERED_DATE`		
    * The number specified for PRODUCT_ID cannot exceed the total number of products. All the IDs must be identifiable
    * The number of arguments specified for PRODUCT_QUANTITY and PRODUCT_ID must match
    * EXPECTED_DELIVERED_DATE must be after current time and of the form YYYY-MM-DD

* **Examples**: 
    * `create sid/01 s/123 id/1 2 4 8 qty/100 200 400 800 date/2020-12-12` : Creates a purchase order for the delivery of 100, 200, 400 and 800 of products with ID 1, 2, 4 and 8 respectively from supplier ID of 01 to store ID of 123 by December 12, 2020.

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

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<br />

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Create** | `create sid/SUPPLIER_ID s/STORE_ID id/PRODUCT_ID…​ qty/PRODUCT_QUANTITY…​ date/EXPECTED_DELIVERED_DATE`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find TYPE KEYWORD`<br> e.g. `find product panadol`
**List** | `list`
**Help** | `help`

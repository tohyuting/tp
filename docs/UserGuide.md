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

   * **`list suppliers`** : Lists all suppliers.

   * **`add`**` p/PANADOL SUSP id/1 t/FEVER` : Adds product named `PANADOL SUSP` with product id of 1 with a `FEVER` tag to CLI-nic.

   * **`delete`**` store 12` : Deletes the 12th store from the list of stores.

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

### Listing all suppliers / stores / products: list`

Shows a list of all the suppliers/products/stores with their information.

Format: `list TYPE`
* The TYPE specified should be one of these values: suppliers, stores or products

Examples:
* `list suppliers` Displays a list of all suppliers.
* `list products` Displays a list of all products.
* `list stores` Displays a list of all stores.

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

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

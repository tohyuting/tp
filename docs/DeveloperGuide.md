---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/java/seedu/clinic/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/java/seedu/clinic/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the
 user issues the command `delete ct/s i/12`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/java/seedu/clinic/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `SupplierListPanel`, `WarehouseListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts is defined in matching `.fxml
` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow
`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/java/seedu/clinic/ui/MainWindow.java) is
 specified in [`MainWindow.fxml`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103-W14-4/tp/tree/master/src/main/java/seedu/clinic/logic/Logic.java)

1. `Logic` uses the `ClinicParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a supplier).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete ct/s i/12")` API call.

![Interactions Inside the Logic Component for the `delete ct/s i/12` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103-W14-4/tp/blob/master/src/main/java/seedu/clinic/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the clinic data.
* exposes an unmodifiable `ObservableList<Supplier>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a
 more OOP) model is given below.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103-W14-4/tp/blob/master/src/main/java/seedu/clinic/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the clinic data in json format and read it back.

### Common classes

Classes used by multiple components are in the `seedu.clinic.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Delete feature

#### What Delete Feature does
The delete feature allows user to delete a particular warehouse or supplier __(case 1)__.
The feature also allows user to delete a product from a specific warehouse or supplier __(case 2)__.
The deletion is limited to the items shown in the UI, i.e. the displayed results, and is done 1 item at a time.

#### How it is implemented
The `delete` feature is mainly facilitated via the `DeleteCommand` class.
It extends the abstract `Command` class, with the ability to handle __case 1__ and __case 2__ separately:

* `DeleteCommand#executeWarehouseRelatedDeletion()` — Delete the entire target warehouse or its specific product.
* `DeleteCommand#executeSupplierRelatedDeletion()` — Delete the entire target supplier or its specific product.

Given below is a description about the mechanism of deletion.

Step 1. CLI-nic's `parser` will parse the user input and if the `delete` command word is present, the parser will try to parse the
input into a valid `DeleteCommand` via **DeleteCommandParser**.
As usual, checks for compulsory prefixes and valid arguments (`ct/TYPE` and `i/INDEX` in this case) are done.
If two entries of `ct/TYPE` are found, the latter entry will be used as the argument.
If the deletion command asks for deletion of product (indicated by the `TYPE` keywords `ps` and `pw`), the additional check
for prefix `pd/` and valid product name will also be conducted.
The code will throw a **ParseException** if the check fails.
Afterwards, all the valid arguments will form a `DeleteCommand`, which will be executed.

Step 2. The `DeleteCommand` is executed via a call from `LogicManager`. The execution is first classified into Supplier-related deletion
and Warehouse-related deletions via the `targetType` attribute. Under each category, The execution splits into deletion of an entire supplier/warehouse
or its particular product.

Step 3. The method will retrieve the displayed list of warehouse/supplier via `model#getFilteredWarehouseList()`.
It locates the warehouse/supplier user wants to delete (or from whom the product to delete) via the `index` passed in.

<div markdown="span" class="alert alert-info">:information_source:
**Note:** If the index passed in is larger than the size of the list, an error will be raised and the deletion will terminate.
</div>

Step 4. If the user wants to delete an entire warehouse/supplier entry, `model#deleteWarehouse` will remove the entry
from the list in the `model`.

If the user wants to delete a product inside the entry, the set of `Product` for the warehouse/supplier entry will be retrieved first.
The `warehouse#getProductByName` or `supplier#getProductByName` will give the target product to delete from the name argument,
and the retrieved product will be removed from the current product set in the selected warehouse/supplier entry.
Afterwards, the updated product set will replace the existing set in this warehouse/supplier. The selected warehouse/supplier entry in the model is then 
replaced by the updated warehouse/supplier with the target product deleted.

Step 5. With the deletion completed, a `CommandResult` will be returned to the `LogicManager` with a success message, which will
be shown to the user in the UI.

The following activity diagram summarizes the execution procedure: (to be uploaded)

### Edit feature (to be implemented)
The edit feature will be elaborated in this section by its' functionality, path execution, class diagrams associated to edit feature (next update) and the interactions between the different objects when the feature is used by a user (next update).

#### What Edit Feature does
The edit feature allows user to edit supplier/warehouse name, phone number and remarks. In addition, the edit feature also allows user to edit a supplier's email and a warehouse's address. One thing to note is that the edit feature does not allow users to edit any products associated with a particular supplier or warehouse. To edit the quantity or tags of a product, the update feature should be invoked instead. This feature will be elaborated in the section below.

#### How it is implemented
After the `edit` command is called, the user input will be sent to **EditCommandParser** for parsing. The **EditCommandParser** will check if the compulsory prefixes are present (i.e. `ct/COMMAND_TYPE` and `i/INDEX`). The `edit` command only allows editing of a single warehouse or supplier for every single command. If two types of `ct/COMMAND_TYPE` is provided, the last type specified will be used to process user's input. This applies for other prefixes used as inputs as well. The code will throw a **ParseException** if no compulsory prefixes or only one of the compulsory prefixes are given. Afterwards, **EditCommandParser** will check to see if at least one field is provided for editing. The code will throw a **ParseException** if no field for editing of suppliers or warehouses is provided. Parsing of user input then begins. If there are any inappropriate fields supplied (e.g. input a string for index or phone prefix), **ParseException** will be thrown. Furthermore, **EditCommandParser** will also throw a **ParseException** if the fields input result in no changes to the existing supplier or warehouse entry. Finally, **EditCommandParser** will create a new **EditCommand** to be executed. **EditCommand** takes in an index for supplier or warehouse list and an **EditDescriptor** containing the fields to be edited when the command is executed. The edited supplier or warehouse will be updated in the model, allowing users to see the changes done for the respective supplier or warehouse.

#### Why it is implemented this way
The `edit` command is implemented this way to ensure consistency with the other commands in the application. This helps to minimise any potential confusion for the users by standardising the prefixes that `edit` command takes in with the other relevant commands. In addition, it was intended for **EditCommandParser** to throw out a **ParseException** when none of the field changes an existing entry. This is to remind users in case they made a minor mistake, resulting in a supplier or warehouse to not update the way they intended for it to. Lastly, a command type prefix, `ct/COMMAND_TYPE` is required in the implementation of `edit` command to indicate whether user wishes to edit a warehouse or supplier entry. Without a command type prefix, an alternative would be for a `TYPE` parameter, where user have to indicate `supplier` or `warehouse`. However, this may not be suitable for our target user, who wishes to update stocks quickly. Hence, our team decided to use a command type prefix in place of a parameter. Furthermore, another alternative considered would be to create separate commands for warehouses and suppliers respectively. For example, `editw` and `edits` to represent edit warehouse and edit supplier. However, this might increase duplicated codes, since minimal changes to the code would be found for each class of command. By using a prefix, this helps us to reduce any potential code duplication. Therefore, our team decided to implement edit command by taking in prefixes and throwing our relevant exceptions at appropriate points after considering code quality and end user experience.

### Find feature

#### What Find feature does
The find feature allows users to find all relevant suppliers or warehouses by their names, by their remarks and/or by
names of the products sold/stored. Users are able to search for relevant suppliers or warehouses using either only one
of these criterion or a combination of these criteria. Note that users are only able to search for either suppliers or
warehouses at any one time and not both at the same time.

#### How it is implemented
Step 1. After the `find` command is called, the user input will be sent to **FindCommandParser** for parsing.

Step 2. **FindCommandParser** will then check if the compulsory prefix `ct/COMMAND_TYPE` is present. If the user enters
`ct/COMMAND_TYPE` prefix more than once, only the last prefix specified will be used to process user's input. If the
prefix `ct/COMMAND_TYPE` is not present, a **ParseException** will be thrown. 

Step 3. **FindCommandParser** will then proceed to check for the existence of at least one of the following prefixes
 `n/NAME`, `r/REMARK` and `pd/PRODUCT`. If none is found, a **ParseException** will be thrown. Again, if the user
 specifies the same prefix more than once, only the last prefix specified will be used to process the user's input.
 
Step 4. Once the user has entered the correct format for the command, their input will then be parsed.

Step 5. **FindCommandParser** will create a new **FindCommand** to be executed and the relevant suppliers or warehouses
will be filtered out.

Step 6. The model will then display the relevant suppliers or warehouses to the users via the method
`model#getFilteredSupplierList()` or `model#getFilteredWarehouseList()`.

#### Why it is implemented this way
The `find` command is implemented this way to ensure consistency with the other commands in the application regarding
the prefixes. This helps to minimise any potential confusion for the users by standardising the prefixes that `find`
command takes in with the other relevant commands. In addition, a command type prefix, `ct/COMMAND_TYPE` is required in the
implementation of `find` command to indicate whether the user wishes to search for suppliers or warehouses. Without this
prefix, the application will not be able to know if the user wishes to search for suppliers or warehouse.

#### Alternatives considered
In our previous implementation, we did not require the user to enter the command type prefix. Instead, we only required
the user to enter the `TYPE` parameter in the form of either `supplier` or `warehouse`. However, typing the whole word
out may not have been suitable for our target user, who wishes to find suppliers or warehouses quickly. Hence, our team
decided to use a command type prefix in place of a `TYPE` parameter, which is shorter and easier to type.

In our current implementation, users are required to enter at least one or a combination of the following prefixes: 
`n/NAME`, `r/REMARK`, `pd/PRODUCT`. This allows the application to determine which criterion/criteria to filter by.
Another alternative that was previously implemented was to split the find command into three separate commands: `findn`
which allows users to search by name, `findr` which allows users to search by remarks and `findp` which allows users to
search by products. However, this implementation was deemed unsuitable as it increased code duplication with minimal
changes between the different classes. In addition, by splitting into three separate commands, users are unable to
search for suppliers or warehouses using multiple criteria. They could only search by name, by remark or by product in
any single command. By using prefixes, users are able to search for suppliers or warehouses using any combination of
name, remark and product. Taking the aforementioned points into consideration, our team has therefore decided to
implement the `find` command by taking in prefixes and throwing our relevant exceptions at appropriate points after
considering code quality and end user experience.

### Update product feature

The update product mechanism is facilitated by the `UpdateCommandParser`, `UpdateCommand`,  and the `UpdateProductDescriptor`.
The `UpdateCommandParser` implements `Parser` to parse the user input, the `UpdateCommand` extends `Command` to execute the main logic, 
and the `UpdateProductDescriptor` allows the parser to pass a specification of the updated product to the `UpdateCommand`.

Given below is an example usage scenario and how the update product mechanism behaves at each step.

Step 1. The user decides to update the stock for a product called 'Panadol' with a new quantity of 50 units 
in the warehouse named 'Jurong Warehouse'. The user also decides that he wants to give 'Panadol' a tag 'fever'. 
The user does this by executing the `update ct/w n/Jurong Warehouse pd/Panadol q/50 t/fever` command.
The `ClinicParser#parseCommand` will then call the `UpdateCommandParser#parse` method with all the arguments 
passed by the user.
 
Step 2. `UpdateCommandParser#parse` then attempts to create new instances of `name` for the supplier/warehouse 
and the product, and a new `UpdateProductDescriptor` with the provided quantity and tags, if any. An exception will be thrown if any of the arguments are invalid, which will be presented on the GUI. 
After which, it will call the `UpdateCommand` with the `Type`, warehouse/supplier's `Name` and `UpdateProductDescriptor` created, 
and return it to `ClinicParser#Parse` which will in turn return the `UpdateCommand` to `LogicManager#execute`.

The following sequence diagram shows how the update product operation works: (TODO: Insert diagram)

Step 3. `LogicManager#execute` calls `UpdateCommand#execute` with the `Model` instance. In `LogicManager#execute`, 
the `Model#getWarehouseByName` or `Model#getSupplierByName` is called (to be implemented), which 
iterates through the warehouse/supplier list to find a warehouse/supplier with a `Name` that matches the one provided 
in the `UpdateCommand`. If it is not found, `NoSuchElementException` is thrown, otherwise, the `UpdateCommand#execute` 
method copies the existing product set for that warehouse/supplier to a new `Set<Product>`. 

Step 4. `UpdateCommand#execute` then checks if a `Product` of the same `Name` as the `Product` to be updated exists in the `Set<Product>`. 
If the `Product` exists, the method does an additional check to ensure that either the tag(s) or quantity (or both)
is supplied for the `Product` to be updated, failing which, an exception is thrown. If the check passes, the original 
`Product` is removed from the set. 

Step 5. `UpdateCommand#execute` adds the updated `Product` to the `Set<Product>`, and creates an updated 
warehouse/supplier with the updated product. The method then calls `Model#setWarehouse` or `Model#setSupplier` to update the model, 
and calls `Model#updateFilteredWarehouseList` to update the list to be displayed to the user.
The method then passes a `CommandResult` with a success message back to `LogicManager#execute`. Finally, the model 
is saved and the GUI is updated with the success message.

The following activity diagram summarizes what happens when a user updates a product: (TODO: Insert Diagram)

### Add feature
In this section, the functionality of the add feature, the expected execution path, the structure of
the AddCommand class, the interactions between objects with the AddCommand object will be discussed.

#### What is the Add supplier/warehouse feature
The add supplier/warehouse feature is facilitated by the `AddCommandParser` and the `AddCommand`.
The `AddCommandParser` implements `Parser` and the `AddCommand` extends `Command`, allowing the user to 
add a supplier/warehouse to the app using the command line.

The supplier consists of : Type, Name, Phone, Email
The warehouse consists of : Type, Name, Phone, Address

The supplier/warehouse also consists of one optional field that can be added:
* Remark

#### Path Execution of Add Command
The overview of the AddCommand Activity Diagram is shown below:

![Add Command Activity Diagram](images/AddCommandActivityDiagram.png)

After the user calls the Add command, the code will check for the presence of all the compulsory prefixes
in the command. The code will throw a ParseException if there are any missing/invalid prefixes. After that is
checked, it will check if the new supplier/warehouse added is a duplicate (The supplier/warehouse already
exist in the application). It will throw a CommandException when the user tries to insert a duplicate
order. Otherwise, it will insert the supplier/warehouse and prints a success message to the user.

#### Structure of Add feature
The following diagram shows the overview of the AddCommand Class Diagram:

![Add Command Class Diagram](images/AddCommandClassDiagram.png)

The above class diagram shows the structure of the AddCommand and its associated classes and interfaces. Some
methods and fields are not included because they are not extensively utilised in AddCommand; such as public
static fields and getter/setter methods.

#### Interaction between objects when the Add Command is executed
The sequence for adding supplier and warehouse is similar, here is the sequence diagram for the Add Command
for supplier as shown below:

![Add Command Sequence Diagram](images/AddCommandSequenceDiagram.png)

The arguments of the add command will be parsed using the parse method of the AddCommandParser class.
The AddCommandParser will tokenize the arguments parsed in using the tokenize method of ArgumentTokenizer
class which returns the tokenized arguments. Using the tokenized arguments, the Parser will check if the
arguments parsed in matches with the tokenized arguments using the arePrefixesPresent method.

There are two scenarios :

Some compulsory prefixes are not present :

AddCommandParser will throw a new ParseException object to the LogicManager.

All compulsory prefixes are present in the arguments :

It will then proceed to use the getValue method of the ArgumentMultimap class to get the value of the
prefixes. For example, if the argument parsed in is ct/s, the getValue method will get the value 's'.
Subsequently, it will use the ParseUtil methods to get the corresponding object values and put it into
the parameters of the new Supplier/Warehouse object. The Supplier/Warehouse object will be put into the
parameter of the AddCommand object and this will be returned to the LogicManager class for execution.

LogicManager will call the execute() method of this AddCommand object. In the execute() method, it will
use the Model class to call hasSupplier/hasWarehouse method to check for duplicates, if it is a duplicate, the
order will throw a CommandException which indicates that there is a duplicate supplier/warehouse in the CLI-nic
application already. Else, it will successfully add the new supplier/warehouse using addSupplier/addWarehouse
method. Finally, it return a new CommandResult object, containing a String that indicates a successful
addition.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* manager of a medical supplies company that manages warehouses across the country
* tech-savvy manager who prefers typing to clicking
* keeps track of supplies in each warehouse
* needs to quickly contact suppliers to restock medical supplies when the stock runs low at various warehouses

**Value proposition**:

* manages suppliers/warehouses information conveniently
* helps to retrieve and identify key suppliers/warehouses information quickly


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                 | I want to …​                | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ------------------------------------------------------------------------- |
| `* * *`  | standard user  | add my suppliers' information and products offered | easily refer to the contacts and give them a call for updates on their supply availability     |
| `* * *`  | standard user  | add details of warehouses and stocks for each product | easily keep track of the stocks in each of my warehouse |
| `* * *`  | standard user  | add remarks to a supplier entry         | note down details that are specific to the supplier          |
| `* * *`  | standard user  | access the command list/user guide  | easily refer to instructions for commands and guidance for usage    |
| `* * *`  | standard user  | delete a supplier/warehouse entry   | remove suppliers/warehouses no longer operating |
| `* * *`  | standard user  | find medical products associated with warehouses or suppliers     | locate relevant items without having to go through all the lists                |
| `* * *`  | standard user  | view the information of a specific warehouse or supplier          | so that I can retrieve details about suppliers/warehouses I can't remember and contact them       |
| `* * * ` | intermediate user | update the stock of a specific product in warehouses           | so that I can keep track of the changes in the stocks of the warehouses |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `CLI-nic` and the **Actor** is the `user`, unless specified
 otherwise)

**Use case: UC01 Add a warehouse**

**MSS**

1. User keys in command to add a warehouse.
2. CLI-nic adds the warehouse into the list and shows a success message.

    Use case ends.

**Extensions**

* 1a. CLI-nic detects invalid syntax in the entered warehouse info.

  * 1a1. CLI-nic shows an error message.
  * 1a2. CLI-nic requests for the correct syntax of warehouse info.
  * 1a3. User enters new data.

    Steps 1a1-1a3 are repeated until the data entered are correct. <br>
    Use case resumes from step 2.

* 1b. CLI-nic detects addition of duplicated warehouse.

  * 1b1. CLI-nic shows duplicated warehouse message.
  * 1b2. CLI-nic requests for the correct warehouse info.
  * 1b3. User enters new data.

    Steps 1b1-1b3 are repeated until the data entered are correct. <br>
    Use case resumes from step 2.
    
**Use case: UC02 Add a supplier**

**MSS**

1. User keys in command to add a supplier.
2. CLI-nic adds the supplier into the list and shows a success message.

    Use case ends.

**Extensions**

* 1a. CLI-nic detects an error in the entered supplier info.

  * 1a1. CLI-nic shows an error message .
  * 1a2. CLI-nic requests for the correct data.
  * 1a3. User enters new data.

    Steps 1a1-1a3 are repeated until the data entered are correct. <br>
    Use case resumes from step 2.

**Use case: UC03 Add a new product to supplier**

**MSS**

1. User keys in command to add a product.
2. CLI-nic adds the product into the list and shows a success message.

    Use case ends.

**Extensions**

* 1a. CLI-nic detects an overlap in the entered product and the existing products in the supplier's supply list.

  * 1a1. CLI-nic shows an error message.
  * 1a2. CLI-nic requests for a new product name.
  * 1a3. User enters a product name.

    Steps 1a1-1a3 are repeated until the name entered is correct. <br>
    Use case resumes from step 2.

* 1b. CLI-nic cannot find the supplier from the supplier list.

  * 1b1. CLI-nic shows an error message.
  * 1b2. CLI-nic requests for a new supplier name.
  * 1b3. User enters a new supplier name.

    Steps 1b1-1b3 are repeated until the supplier is found. <br>
    Use case resumes from step 2.

**Use case: UC04 Clear all supplier and warehouse entries**

**MSS**

1. User requests to clear the data in the application.
2. CLI-nic clears all supplier and warehouse entries, shows empty lists of suppliers and warehouses and shows a success message.

    Use case ends.

**Use case: UC05 Delete a supplier**

**MSS**

1. User requests to view a specific supplier by keyword.
2. CLI-nic shows the specific supplier and its index.
3. User deletes the supplier via its index in the list.
4. CLI-nic deletes the supplier in the list and shows a success message.

    Use case ends.

**Extensions**

* 2a. The supplier list is empty.

  * 2a1. CLI-nic informs the user there is no supplier in the list currently.

    Use case ends.

* 3a. The given index is invalid.

  * 3a1. CLI-nic shows an error message and gives command suggestions.
  * 3a2. User enters the new supplier index.

    Steps 3a1-3a2 are repeated until the data entered are correct. <br>
    Use case resumes at step 4.

**Use case: UC06 Delete a warehouse**

**MSS**

1. User requests to view a specific warehouse by keyword.
2. CLI-nic shows the specific warehouse and its index.
3. User deletes the warehouse via its index in the list.
4. CLI-nic deletes the warehouse in the list and shows a success message.

    Use case ends.

**Extensions**

* 2a. The warehouse list is empty.

  * 2a1. CLI-nic informs the user there is no warehouse in the list currently.

    Use case ends.

* 3a. The given index is invalid.

  * 3a1. CLI-nic shows an error message and gives command suggestions.
  * 3a2. User enters the new warehouse index.

    Steps 3a1-3a2 are repeated until the data entered are correct.
    Use case resumes at step 4.


**Use case: UC07 Find Suppliers of a product**

**MSS**

1. User enters the command to find the suppliers of a specific product.
2. CLI-nic displays all suppliers that sells the product if any.
3. User scrolls through all the relevant results and looks for the information that they desire.

    Use case ends.

**Extensions**

* 1a. User enters invalid command for finding.

  * 1a1. CLI-nic requests for the correct command.
  * 1a2. User enter a new command for finding.

    Steps 1a1 and 1a2 are repeated until a valid find command is entered. <br>
    Use case resumes at step 2.

**Use case: UC08 Find Warehouses containing a product**

**MSS**

1. User enters the command to view a particular product stored in all warehouses.
2. CLI-nic displays all the relevant products that are stored in the warehouses if any.
3. User scrolls through all the relevant results and looks for the information that they desire.

    Use case ends.

**Extensions**

* 1a. User enters invalid command for finding.

  * 1a1. CLI-nic requests for the correct command.
  * 1a2. User enter a new command for finding.

    Steps 1a1 and 1a2 are repeated until a valid find command is entered. <br>
    Use case resumes from step 2.

**Use case: UC09 List all supplier and warehouse entries**

**MSS**

1. User requests to list the data in the application.
2. CLI-nic retrieves all supplier and warehouse entries, shows lists of suppliers and warehouses and shows a success message.

    Use case ends.

**Use case: UC09 View Help**

**MSS**
1. User asks for the list of command.
2. CLI-nic displays information about all the commands and contains sample commands that the user can try out.
3. User try out commands listed under help to familiarise themselves with CLI-nic.

    Use case ends.

**Extensions**

* 1a. User asks for a specific command via the command keyword.
  * 1a1. CLI-nic displays instruction on how the command they ask can be used. Sample command call will also be given.
  * 1a2. User follows the instruction to try out command they asked to familiarise themselves with this command.

    Use case ends.

* 1b. User enters invalid help command.
  * 1b1. CLI-nic shows an error message.
  * 1b2. CLI-nic requests for the correct command.
  * 1b3. User enters a new help command.

    Steps 1b1-1b3 are repeated until a valid help command is entered. <br>
    Use case resumes from step 2.

**Use case: UC10 Update quantity of a Product in a Warehouse**

**MSS**

1. User keys in command to update a product’s quantity with a specific product, warehouse and quantity.
2. If the product exists, CLI-nic overwrites the product’s quantity. Else if the product does not exist, CLI-nic adds the product and its quantity to the warehouse. CLI-nic shows a success message.

    Use case ends.

**Extensions**

* 1a. CLI-nic cannot find the warehouse.
  * 1a1. CLI-nic shows an error message.
  * 1a2. CLI-nic requests for a new warehouse name.
  * 1a3. User enters a new name.

    Steps 1a1-1a3 are repeated until the data entered are correct. <br>
    Use case resumes from step 2.

**Use case: UC11 View Supplier**

**MSS**

1. User enters command to view supplierA information.
2. CLI-nic displays all of supplierA information, including contact number and any notes such as “fast supplier”.
3. User looks through the information of supplierA displayed and might call up the supplier if necessary.

    Use case ends.

**Extensions**

* 1a User enters invalid supplier name.
  * 1a1. CLI-nic requests for new supplier name.
  * 1a2. User enters a new supplier name.

    Steps 1a1 and 1a2 are repeated until a valid supplier name is entered. <br>
    Use case resumes from step 2.

**Use case: UC12 View Warehouse**

**MSS**

1. User enters command to view WarehouseA information.
2. CLI-nic displays all of WarehouseA information, including contact number and any notes such as “first warehouse”.
3. User looks through the information of WarehouseA displayed and might call up the warehouse if necessary.

    Use case ends.

**Extensions**

* 1a User enters invalid warehouse name.
  * 1a1. CLI-nic requests for new warehouse name.
  * 1a2. User enters a new warehouse name.

    Steps 1a1 and 1a2 are repeated until a valid warehouse name is entered. <br>
    Use case resumes from step 2.

--------------------------------------------------------------------------------------------------------------------
### Use cases [Coming Soon]

**Use case: UC00 Create a purchase order**

**MSS**

1. User requests to create a purchase order with a specific supplier, store, and a list of products and their quantities
2. CLI-nic creates the purchase order

    Use case ends.

**Extensions**

* 1a. Any of the given names are invalid.
  * 1a1. CLI-nic shows an error message.
  * 1a2. User enters new names for the command.

    Steps 1a1 and 1a2 are repeated until all the given names are valid. <br>
    Use case resumes from step 2.

* 1b. The number of products names supplied and the number of product quantities given do not match.
  * 1b1. CLI-nic shows an error message.
  * 1b2. User enters the command again with the new products and quantities.

    Steps 1b1 and 1b2 are repeated until the products and their quantities match. <br>
    Use case resumes from step 2.

* 1c. Any one of the arguments is not supplied.
  * 1c1. CLI-nic shows an error message.
  * 1c2. User enters the command again.

    Steps 1c1 and 1c2 are repeated until all required arguments are supplied. <br>
    Use case resumes from step 2.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
1.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
1.  The files used to store information about supplier/warehouse/product should be independent from each other and follow a similar format.
1.  The system should recognize common French/German letters as they may appear in the name of medical products.
1.  The system should be able to track > 1k suppliers, > 1k warehouses, > 1k medical supplies without a noticeable sluggishness in performance for typical usage.
1.  The size of the application excluding the data files should be minimal (< 30MB).
1.  The system should work off-line.
1.  The system stores data locally.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Medical products/supplies**: The items / tools / medicine consumed by patients
* **Supplier**: The companies / entities providing the sources of medical products
* **Warehouse**: The places where the medical supplies are channeled to and kept. The storage condition of these warehouses are managed by the manager, which is our app user

Command Prefix
|Prefix |Meaning |Used in the following Command(s)|
| ------- |-------- | ------------ |
|ct/ |Command Type |Add, Delete, Edit, Find, Update |
|n/ |Supplier/Warehouse Name |Add, Edit, Find, Update |
|p/ |Phone Number |Add, Edit |
|e/ |Email Address |Add, Edit |
|addr/ |Address |Add, Edit |
|r/ |Remark |Add, Find, Edit |
|pd/ |Product Name |Addp, Edit, Delete, Find, Update |
|i/ |Index |Delete |
|t/ |Tag |Addp, Update |
|q/ |Quantity of product |Update |

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Adding a supplier

1. Add command format: `add ct/TYPE n/SUPPLIER_NAME p/PHONE e/EMAIL_ADDRESS [r/SUPPLIER_REMARK]`

   1. Test case: Minimal information e.g. `add ct/s n/John Doe p/98766789 e/johndoe@example.com`<br>
      Expected: Adds a supplier with the above details to the list and displayed on the GUI
   1. Test case: With remarks e.g. `add ct/s n/John Doe p/98766789 e/johndoe@example.com r/Fast
      delivery`<br>
      Expected: Adds the supplier to the list, including the remark
   1. Test case: Invalid Prefix or missing compulsory Prefixes e.g. `add ct/s n/John Doe p/98766789` 
      or `add ct/s n/John Doe p/98766789 e/johndoe@example.com z/friend`<br>
      Expected: No supplier is added. Error details shown in the response message. A help message displayed
      to guide user accordingly. SupplierList on GUI remain unchanged.
   1. Test case: Add order with existing SUPPLIER_NAME in list e.g. `add ct/s n/John Doe p/98766789 e
      /johndoe@example.com` followed by `add ct/s n/John Doe p/91234567 e/johndot@example.com`<br>
      Expected: An error will occur and a message will be displayed, stating that a supplier with duplicate
      SUPPLIER NAME cannot be added into the list. SupplierList on GUI remain unchanged.

### Adding a warehouse

1. Add command format: `add ct/TYPE n/WAREHOUSE_NAME p/PHONE addr/ADDRESS [r/WAREHOUSE_REMARK]`

   1. Test case: Minimal information e.g. `add ct/w n/John Ptd Ltd p/98766789 addr/John street, block 123
      , #01-01`<br>
      Expected: Adds a warehouse with the above details to the list and displayed on the GUI.
   1. Test case: With remarks e.g. `add ct/w n/John Ptd Ltd p/98766789 addr/John street, block 123, #01-01
      r/Largest warehouse`<br>
      Expected: Adds the warehouse to the list, including the remark
   1. Test case: Invalid Prefix or missing compulsory Prefixes e.g. `add ct/w n/John Ptd Ltd p/98766789` 
      or `add ct/w n/John Ptd Ltd p/98766789 addr/John street, block 123, #01-01 z/large`<br>
      Expected: No warehouse is added. Error details shown in the response message. A help message displayed
      to guide user accordingly. WarehouseList on GUI remain unchanged.
   1. Test case: Add order with existing WAREHOUSE_NAME in list e.g. `add ct/w n/John Ptd Ltd p/98766789
      addr/John street, block 123, #01-01` followed by `add ct/w n/John Ptd Ltd p/91234567 addr/Ang Mo Kio
      street 12, block 123, #01-01`<br>
      Expected: An error will occur and a message will be displayed, stating that a warehouse with duplicate
      WAREHOUSE NAME cannot be added into the list. WarehouseList on GUI remain unchanged.
      
### Deleting a supplier

1. Deleting a supplier while all suppliers are being shown

   1. Prerequisites: List all suppliers using the `list` command. Multiple suppliers in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No supplier is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

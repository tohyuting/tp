---
layout: page
title: Yu Ting's Project Portfolio Page
---

## Project: CLI-nic

 CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**. It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**. It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tohyuting&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **New/Enhancement to AB3 features**:

  * List all suppliers and warehouses at once.
    * What it does: Allow users to know all suppliers and warehouses present in CLI-nic with one command.
    * Why is it complete: Functionality is attained as GUI will be updated to reflect all suppliers and warehouses present in CLI-nic.
    * How hard it was to implement: It requires updating of `FilteredSupplierList` and `FilteredWarehouseList` with a `Predicate` such that all suppliers and warehouses will be displayed.

  * Display help for various commands within the application
    * What it does: Allow users to have a quick look up of what they can do with CLI-nic without having to open the User Guide in another separate browser. They can have an overview of the commands available in **CLI-nic** or choose to read in depth about a particular command.
    * Why is it complete: Users are now able to read about all the commands available in **CLI-nic** within the application itself using `help` command. If they wish to view the `help` message for a specific command, they are able to input an argument associated with `help`. e.g. `help add`.
    * How hard it was to implement: Users will

  * View a particular supplier and warehouse via index on the displayed supplier/warehouse list.
    * What it does
      * Allow users to view all information (e.g. Name, Phone) related to a supplier or warehouse at a particular index of displayed supplier/warehouse list.

  * Edit a particular supplier and warehouse entry via index on the displayed supplier/warehouse list.
    * What it does
      * Allow users to edit a supplier's Name, Phone, Remark and Email.
      * Allow users to edit a warehouse's Name, Phone, Remark and Address.

* **New Class**:
  * Added Product class to be used by Supplier and Warehouse classes.
    * What it does
      * Stores information related to a Product object which will be stored in a Supplier and Warehouse.
      * Each product has attributes such as its' associated quantity and tags associated with the product.

* **Documentation**:
  * User Guide
    * Added documentations for list, help, view and edit commands in the user guide

* **Contributions to Developer Guide**:
    * Added implementation details of the `list`, `help`, `view` and `edit` features.
      * These details includes UML diagrams (Sequence Diagram and Activity Diagram).

* **Review/mentoring contributions**:

* **Contributions to team-based tasks**:
  * Managed release of CLI-nic jar `v1.3.2` for PE Dry Run.
  * Enable assertions on Gradle

* **Contributions beyond the project team**:


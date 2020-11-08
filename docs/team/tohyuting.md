---
layout: page
title: Yu Ting's Project Portfolio Page
---

## Project: CLI-nic

 CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**. It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**. It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tohyuting&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **New Feature**: List all suppliers and warehouses at once.

  * What it does: Allow users to know all suppliers and warehouses present in **CLI-nic** with one command.
  * Justification: This feature is crucial to **CLI-nic** as it allows all suppliers and warehouses stored in **CLI-nic** to be displayed again after `find` or `view` command was executed.
  * Highlights:
  * Credits: The feature was originally implemented in AB3. It was adjusted to fit **CLI-nic** by displaying warehouses and suppliers in 2 different lists as compared to only person objects stored in one list for AB3.

* **New Feature**: Display help for various commands within the application

  * What it does: Allow users to have a quick look up of what they can do with CLI-nic without having to open the User Guide in a separate browser. They can see an overview of the commands available in **CLI-nic** or choose to read in depth about a particular command.
  * Justification: This improves the product as it provides great convenience for users This is especially so for users who wish to learn how to use **CLI-nic** quickly. Users can copy the sample commands and try them out on the sample data directly. In addition, it allows users to have a quick reference to a specific commands if they forgotten what each command does. Instead of loading the entire user guide and find what they need, they just have to type `help COMMAND` in **CLI-nic** now.
  * Highlights: Messages displayed has to be constantly updated as the project grows with new features so that it will be the same as the User Guide. Sample commands provided was chosen to ensure that they could work with sample data loaded when **CLI-nic** is first installed.
  * Credits: The feature was adapted from AB3, where users was asked to copy the URL for the user guide to learn about the application.

* **New Feature**: View a particular supplier and warehouse via index on the displayed supplier/warehouse list.

  * What it does: Allow users to view a supplier or warehouse information in the GUI at a particular index of displayed supplier/warehouse list. In particular, users can view the products associated with the supplier or warehouse without clicking on the products pane. This feature is extremely useful when used with `find` command. In such a situation, users will be able to filter out the relevant suppliers and warehouses before making a choice of which supplier or warehouse products they wish to look at in details.
  * Justification: This feature improves the product as it helps **CLI-nic** to be more CLI-friendly. Using `view` (together with `find` for optimised impact) allows users to see products associated with a warehouse or supplier without using the mouse.
  * Highlights: Initial implementation of `view` requires user to spell out the type in full (i.e. supplier/warehouse) instead of using `ct/s` or `ct/w` in current implementation. In addition, if user wish to `view` a supplier or warehouse, they would have to view by `name` instead of `index`. This was changed as to reduce the amount of typing required by end users. However, this involves changes to codes as the project progresses, which poses a challenge. Other than implementation codes, codes for parsing of arguments and test codes as to be re-written as well. This process is rather heavy and time-consuming.

* **New Feature**: Edit a particular supplier and warehouse entry via index on the displayed supplier/warehouse list.
    * What it does: Allow users to edit a supplier or warehouse information (excluding products associated) at a particular index of displayed supplier/warehouse list.
    * Justification: As a manager who manages many warehouses and suppliers, the user has to be able to update any outdated supplier/warehouse information quickly. Hence, this feature is crucial to **CLI-nic**.
    * Highlights:
    * Credits:

* **New Class**:
  * Created Product class to be used by Supplier and Warehouse classes.
    * What it does: Stores information related to a Product object which will be stored in a Supplier and Warehouse. Each product has attributes such as its' associated quantity and tags associated with the product.
    * Justification:
    * Highlights:
    * Credits:


* **Minor enhancement to AB3**:
  * Standardisation of Error Messages thrown out by all commands, to ensure consistency among all features. This includes error messages for

* **Contributions to Developer Guide**:
    * Added documentations for list, help, view and edit commands in the user guide

* **Contributions to Developer Guide**:
    * Added implementation details of the `list`, `help`, `view` and `edit` features.
      * These details includes UML diagrams (Sequence Diagram and Activity Diagram).

* **Review/mentoring contributions**:

* **Contributions to team-based tasks**:
  * Managed release of CLI-nic jar `v1.3.2` for PE Dry Run.
  * Enable assertions on Gradle

* **Contributions beyond the project team**:


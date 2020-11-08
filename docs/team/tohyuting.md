---
layout: page
title: Toh Yu Ting's Project Portfolio Page
---

## Project: CLI-nic

 CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**. It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**. It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tohyuting&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **New Feature**: List all suppliers and warehouses at once.

  * What it does: Allow users to know all suppliers and warehouses present in **CLI-nic** with one command.
  * Justification: This feature is crucial to **CLI-nic** as it allows all suppliers and warehouses stored in **CLI-nic** to be displayed again after `find` or `view` command was executed.
  * Credits: The feature was originally implemented in **AB3**. It was adjusted to fit **CLI-nic**.

* **New Feature**: Display help for various commands within the application

  * What it does: Allow users to have a quick look up of what they can do with CLI-nic without having to open the User Guide in a separate browser. They can see an overview of the commands available in **CLI-nic** or choose to read in depth about a particular command.
  * Justification: This improves the product as this provides great convenience for users, especially so for users who wish to learn about **CLI-nic** quickly. Users can try sample commands on the sample data directly. In addition, users can have a quick reference to a specific command if they have forgotten what each command does. Instead of loading the entire user guide and find what they need, they just have to type `help COMMAND` in **CLI-nic**.
  * Highlights: Messages displayed has to be constantly updated as the project grows with new features so that it will be the same as the User Guide. Sample commands provided were crafted to ensure they could work with sample data loaded.
  * Credits: The feature was adapted from **AB3**, where users was asked to copy the URL for the user guide.

* **New Feature**: View a particular supplier and warehouse via index on the displayed supplier/warehouse list.

  * What it does: Allow users to view a supplier or warehouse information at a particular index of displayed supplier/warehouse list in the GUI. In particular, users can view the products associated with the supplier or warehouse without clicking on the products pane. This feature is extremely useful when used with `find` command. In such a situation, users will be able to filter out the relevant suppliers and warehouses using `find` before making a choice of which supplier or warehouse products they wish to look at in details using `view`.
  * Justification: This feature improves the product as it helps **CLI-nic** to be more CLI-friendly. Using `view` (together with `find` for optimised effect) allows users to see products associated with a warehouse or supplier without using the mouse.
  * Highlights: Initial implementation of `view` requires user to spell out the type in full (i.e. supplier/warehouse) instead of using prefixes. If a user wish to `view` a supplier or warehouse, they would also have to view by `name` instead of `index`. This was changed to reduce the amount of typing required by users. However, this involves changes to codes as the project progresses which poses a challenge. Other than implementation codes, codes for parsing of inputs and test codes have to be re-written as well. This process can be tedious and time-consuming.

* **New Feature**: Edit a particular supplier and warehouse entry via index on the displayed supplier/warehouse list.
    * What it does: Allow users to edit a supplier or warehouse information (excluding products associated) at a particular index of displayed supplier/warehouse list.
    * Justification: As a manager who manages many warehouses and suppliers, the user has to be able to update any outdated supplier/warehouse information quickly. Hence, this feature is crucial to **CLI-nic**.
    * Highlights: This feature demands parsing of numerous prefixes followed by checking of presence and validity of values. In addition, checks for invalid prefixes, e.g. `addr` for supplier and `e/` for warehouse has to be done. Initial implementation uses a different set of prefix (such as `s/` and `w/` for supplier and warehouse name respectively). Hence, changes to implementations, parsing of inputs and test codes has to be done along the way. This process can be tedious and time-consuming.
    * Credits: This feature was adapted from **AB3**.

* **New Class**:
  * Created Product class to be used by Supplier and Warehouse classes.
    * What it does: Stores information related to a Product which will be associated to a `Supplier` or `Warehouse`. Each product has attributes such as its' associated quantity and tags associated with the product.
    * Justification: Suppliers and warehouses for medical supplies will have varying quantities for each entity. There is a need for user to know which warehouse is running out of stock. This allows the user to contact the relevant supplier and place an order. Without the Product class, the user has to keep track of products and its associated quantities, tags and entities in a separate document, which will be inefficient.
    * Highlights: In early stages, product associated to `Supplier` contained only tags, while product associated to `Warehouse` contained only quantity. This was made as stocks for a `Supplier` may not be readily available. However, it was decided in later stages of the project that products associated to both `Supplier` and `Warehouse` should contain tags and quantity. This gives an option to users who might be able to obtain `Supplier` stocks readily.
    * Credits: This class was adapted slightly from `Person` class in **AB3**.

* **Minor enhancement**:
  * Fix a minor yet crucial bug involving minor wrong naming of vairable, resuling in jar file to not be able to function as expected before PE dry run. [#161](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/161)
  * Error messages of **AB3** are not representative of certain commands entered by users. For instance, a command such as `view ct/s i/1 z/testing` will throw an error indicating that index is not a non-zero unsigned integer. In this case, an error of invalid prefix used should be thrown instead. To develop this, an intensive logic handling of inputs from users was done, with try-catch blocks used to customise error messages to fit every unique commands in **CLI-nic**. This is due to the numerous possibilities allowed, especially since users are allowed to reorder their prefixes in their command input [#216](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/216), [#242](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/242).
  * Fix bug related to `AutoComplete` feature, where pop-up box interfere with `CommandHistory` feature if the previous command was a single word command. [#227](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/227)

* **Contributions to User Guide**:
    * Added documentations for `list`, `help`, `view` and `edit` commands in the user guide. [#153](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/153/files)
    * Updated user stories relevant to each commands above [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73).

* **Contributions to Developer Guide**:
    * Added implementation details of the `list`, `help`, `view` and `edit` features [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73), [#111](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/111/), [#153](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/153). These details includes UML diagrams (Class, Sequence and Activity Diagrams).
    * Added manual testing for `list`, `help`, `view`, `edit`, `clear`, `exit` and saving data [#232](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/232).
    * Updated Model Class diagram with my team mates, Jeffrey and Qin Liang to represent the current CLI-nic [#245](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/245).

* **Contributions to team-based tasks**:
  * Managed release of CLI-nic jar `v1.3.2` for PE Dry Run.
  * Enabled assertions on Gradle.
  * Updated target user profile [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73).

* **Contributions beyond the project team**:
  * PRs reviewed (with non-trivial review comments): [#103](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/103#discussion_r502200542), [#110](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/110#discussion_r502924200), [#133](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/133#discussion_r508483009), [#211](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/211#discussion_r515597848)
  * Contribution to forum discussions (examples: [#78](https://github.com/nus-cs2103-AY2021S1/forum/issues/78), [#109](https://github.com/nus-cs2103-AY2021S1/forum/issues/109), [#118](https://github.com/nus-cs2103-AY2021S1/forum/issues/118))

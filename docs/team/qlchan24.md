---
layout: page
title: Chan Qin Liang's Project Portfolio Page
---

## Project: CLI-nic

CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**.
It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**.
It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to create new purchase order
  * What it does: Allows the user to create a purchase order to track the purchase of medical products from a supplier to a store.
  * Justification: This is a key functionality of CLI-nic to as keeping track of orders is a key aspect of inventory management.
  * Highlights: This command will allow for future features with regard to inventory management and sales metrics
  * Credits: *{mention here if you reused any code/ideas from elsewhere or if a third-party library is heavily used in the feature so that a reader can make a more accurate judgement of how much effort went into the feature}*

* **New Feature**: Update product Feature
  * What it does: Allows the user to update the quantity and/or tags of the product in the selected supplier or warehouse.
  If the product does not already exist for that supplier/warehouse, the feature allows the user to add new products with their associated tags/quantity to the supplier/warehouse.
  * Justification: This is a key feature of CLI-nic to as keeping track of products under each supplier/warehouse is one of the fundamental functionalities that our application is based on.
  * Highlights: This feature had underwent several changes in the fields that it accepts, which meant re-implementations of various parts of the code. Another challenge presented was that the prefixes required by the command differs whether the product exists in the warehouse/supplier, hence it required analysis of design alternatives so as not to create dependency between the parser and the model.
  * Credits: The feature was originally split into separate commands for supplier and warehouse, as they originally had different fields for the products listed.
  My teammate Zhenlin did the supplier-side command, while I did the warehouse-side. I eventually integrated the 2 commands together into 1 command and implemented access to all fields of products for both suppliers and warehouses as part of a revamp of our commands.

* **New Feature**: Assigning, deleting and listing of macros
  * What it does: Allows users to save commands into their custom aliases.
  * Justification: This feature further builds on the efficiency of the CLI-based commands for quick-typists by allowing users to create shorthand commands or ones that they are more comfortable with.
  * Highlights: Implementing the underlying infrastructure for macro support required new classes and structures across the storage, model, and logic components, with changes in more than 50 files. It also required in-depth analysis of several design alternatives of how to integrate the new classes and structures with minimal interference with the existing code.
  This was followed by the implementation of the 3 new commands (AssignMacroCommand, RemoveMacroCommand, ListMacroCommand) and their associated parsers on top of the new infrastructure to support the macro features.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=qlchan24)

* **Project management**: 
  * Created the new product name
  * Managed v1.2 release on GitHub

* **Enhancements to existing features**: 
  * Refactored the class name and fields of the Person class into the Supplier class together with new test cases and removed traces of the original Person class in the documentation.
  * Added common test utility constants and classes.
  * Fixed bugs in code outside implemented commands (examples: [#117](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/117), [#211](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/211))

* **Documentation**:
  * User Guide:
    * Added documentation for the update product, assign macro, delete macro, and list macro, and create purchase order (removed feature) features.
  * Developer Guide:
    * Created new use cases and user stories for update product, assign macro, delete macro, list macro, and create purchase order (removed feature).
    * Added implementation details and diagrams of the update product, assign macro, delete macro and list macro features.
    * Added manual testing instructions for update product, assign macro, delete macro and list macro features.
    * Updated description and class diagrams for Logic and Storage components.

* **Community**:
  * PRs reviewed (with non-trivial review comments): (examples: [TP comments dashboard](https://nus-cs2103-ay2021s1.github.io/dashboards/contents/tp-comments.html#71-chan-qin-liang-qlchan24-34-comments))
  * Contributed to forum discussions (examples: [130](https://github.com/nus-cs2103-AY2021S1/forum/issues/130), [159](https://github.com/nus-cs2103-AY2021S1/forum/issues/159))
  * Reported bugs and suggestions for other teams in the class (examples: )

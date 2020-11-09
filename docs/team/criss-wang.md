---
layout: page
title: Wang Zhenlin's Project Portfolio Page
---

## Project: CLI-nic

CLI-nic is **a desktop application to help medical supply managers keep track of medical products and storage**.
It is optimized for these managers to **update product supply & storage conditions and access critical product information quickly via fast typing**.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=criss-wang)

* **New class**: Implement the warehouse class (Pull requests [\#98](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/98/), [\#103](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/103)).
  * What it represents: the warehouses the user manages. Each warehouse can store medical products of various quantity.
  * Justification: The warehouse is a major class whose attributes are displayed in the UI. It contains all the stored products' information including their stocks and tags.
  * Difficulty: The difficult part is to come up with design of warehouse-related classes that handle the storage and retrieval of warehouse attributes, especially its products' information.
  * Credits: {The implementation is similar to the Person class in [AddressBook-3](https://se-education.org/addressbook-level3/)}

* **New feature**: Added the ability to delete a supplier/warehouse/product (Pull requests [\#123](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/123/), [\#110](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/110)).
  * What it does: Removes unwanted entries in the displayed supplier list or warehouse list.
  * Justification: The user should be able to quickly delete an entire supplier/warehouse as well as a small product within the supplier/warehouse.
  * Difficulties:
    * The `delete` command needs to accept 4 different types of deletions: `supplier`/`warehouse`/`product in a supplier`/`product in a warehouse`. This means 4 different parsing and execution paths.
    * In addition, to standardize the prefix-attached argument format, we need to use keyword identifiers for each type. This prompted the creation of `Type` enumeration class.
  * Highlights: The deletion can be achieved by a single command word. A `Type` enum is implemented. These practices help to reduce code duplication and simplify commands.
  Parsing of the delete command is also carefully contemplated so that users won't accidentally delete some important entries.

* **New feature**: Added the ability to add a product to a supplier (Pull requests [\#115](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/115/)).
  * What it does: Adding a new product with several tags to an existing supplier.
  * Highlights: This feature allows multiple tagging of a product for a supplier. Users can also create different tags for the same product for different suppliers based on each supplier's role.
  * Credits: {The AddProduct command is later adopted and modified to be part of the Update command by my teammate Qin Liang}

* **New feature**: Allow undo/redo of data editing for CLI-nic app (Pull requests [\#210](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/210/)).
  * What it does: Undo/redo the earlier commands which modify the data stored.
  * Justification: Even the most careful user can make small mistakes in typing. The feature enables users to recover their earlier/modified data.
  * Difficulties:
    * To simulate the common practices of undo/redo in text editing, we must enable multiple undo/redo and remove the saved redoable versions if new editing occurs.
  * Highlight: The feature uses stacks to store the pre-editing/post-editing versions of data to facilitate undo and redo.
  * Credits: {The implementation is inspired by the proposed undo/redo implementation in [AddressBook-3 Developer Guide](https://se-education.org/addressbook-level3/DeveloperGuide.html)}

* **Project management**:
  * Setup all the User Stories and Issue Labels on GitHub
  * Managed milestone `v1.2` and release `v1.2.1` on GitHub

* **Enhancements to existing features**:
  * Updated the GUI (Pull requests [\#175](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/175), [\#210](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/210))
  * Conducted Add-on checks for code style and standard violation (Pull request [\#175](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/175))
  * Identified some potential bugs in original AddressBook-3 which leads to major implementation decisions (Issue [\#122](https://github.com/AY2021S1-CS2103-W14-4/tp/issues/122)))
  * Wrote additional tests for features to increase coverage from 60.75% to 66.75% (Pull requests [\#103](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/103), [\#224](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/224))

* **Documentation**:
  * Site-wide setting, Index page & README (Pull requests [\#156](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/156)):
    * Updated _config.yml. Edited styling for the sites.
    * Modified content to fit CLI-nic project description, added relevant links.
  * User Guide (Pull requests [\#83](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/83), [\#210](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/210)):
    * Reformatted the content so that it is standardized across different sections.
    * Added documentations for the `delete` and `undo/redo` feature.
    * Checked for markdown violations. Filled in missing constraints for commands' input.
  * Developer Guide:
    * Added implementation details of the `delete` and `undo/redo` feature.
    * Added use cases for `add product` and `delete`.
    * Fix typos in DG.  


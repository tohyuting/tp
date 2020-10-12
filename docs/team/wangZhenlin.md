---
layout: page
title: Wang Zhenlin's Project Portfolio Page
---

## Project: CLI-nic

CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**.
It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**.
It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link]()

* **New class**: Implement the warehouse class.
  * What it represents: the warehouses the user manages. Each warehouse can store medical products of various quantity.
  * Justification: This is one of the main classes that user needs to store information about and make contact with in real life.
  * Credits: {The implementation is similar to the Person class in AddressBook-3, with Tag class getting replaced by a more complicated Product class}

* **New feature**: Added the ability to delete a supplier/warehouse.
  * What it does: Removes unwanted entries in the supplier list or warehouse list.
  * Justification: This feature allows a user to get rid of suppliers or warehouse it no longer in contact with.
  * Highlights: When a supplier/warehouse gets deleted, all the product information relate to it will be removed as well.

* **New feature**: Added the ability to add a product to a supplier.
  * What it does: Adding a new product with tags to an existing supplier.
  * Highlights: This allows multiple tagging of a product for a supplier and different tags of same product for different supplier.
  * Credits: {}

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Updated the GUI (Pull requests [\#33](), [\#34]())
  * Wrote additional tests for existing features to increase coverage from __% to __% (Pull requests [\#36](), [\#38]())

* **Documentation**:
  * Site-wide setting:
    * Updated _config.yml.
  * Index page:
    * Modified content to fit CLI-nic project description, added relevant links.
  * README:
    * Modified content to fit CLI-nic project description.
  * User Guide:
    * Added documentation for the `delete` feature.
  * Developer Guide:
    * Added implementation details of the `delete` feature.
    * Added use cases.

* **Community**:
  * PRs reviewed (with non-trivial review comments): nil
  * Contributed to forum discussions (examples: nil)
  * Reported bugs and suggestions for other teams in the class (examples: nil)
  * Some parts of the history feature I added was adopted by several other class mates (nil)

* **Tools**:
  * nil

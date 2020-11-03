---
layout: page
title: Yu Ting's Project Portfolio Page
---

## Project: CLI-nic

 CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**. It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **Enhancements to existing features**

  * Added feature for users to list all suppliers and warehouses at once.
    * What it does
      * Allow users to know all suppliers and warehouses present in CLI-nic with one command.
  
  * Added feature for users to view help for various commands within the application 
    * What it does
      * Allow users to have a quick look up of what they can do with CLI-nic without having to open the User Guide in another separate browser.

  * Added feature for users to view a particular supplier and warehouse via index on the displayed supplier/warehouse list.
    * What it does
      * Allow users to view all information (e.g. Name, Phone) related to a supplier or warehouse at a particular index of displayed supplier/warehouse list.
  
  * Added feature for users to edit a particular supplier and warehouse entry via index on the displayed supplier/warehouse list. 
    * What it does
      * Allow users to edit a supplier's Name, Phone, Remark and Email.
      * Allow users to edit a warehouse's Name, Phone, Remark and Address.

* **New Feature**
  * Added Product class to be used by bother Supplier and Warehouse classes.
    * What it does: Stores information related to a Product object which will be stored in a Supplier and Warehouse Object.
    Each product has attributes such as its' associated quantity and tags associated with the product.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tohyuting&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Documentation**:
  * User Guide:
    * Added documentations for list, help, view and edit commands in the user guide
  * Developer Guide:
    * Added implementation details of the `list`, `help`, `view` and `edit` features.
    * Added implementation details of Product class.

---
layout: page
title: Jeffrey Tan's Project Portfolio Page
---

## Project: CLI-nic

CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**.
It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**.
It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **Major Enhancement**: Enhancement to add command (Pull requests: [#112](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/112), [#113](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/113))
  * What it does: Allows the user to insert new Suppliers/Warehouses into the CLI-nic Supplier/Warehouse List
   respectively.<br>
   The user can add:
    * Name, Phone, and Remark (Optional) attributes to both Suppliers and Warehouses
    * In addition, an Email attribute to Suppliers and an Address attribute to Warehouses
  * Justification: This is an important feature, and it is a must-have. It is the first touch for most users
   and allows the user to effectively keep track of medical products and storage.
  * Highlights: This feature is now able to add different entities such as Suppliers and Warehouses
   as opposed to the initial implementation where only Persons are allowed. This allowed for more
   flexibility in the development of features and usage of the application. As such, the codes for parser
   and test codes were mostly rewritten.
   <br> Credits: This feature was adapted from [AddressBook-Level3](https://github.com/se-edu/addressbook-level3/blob/master/src/main/java/seedu/address/logic/commands/AddCommand.java)

* **Major Enhancement**: Refactoring of AddressBook-Level3 to accommodate CLI-nic use case (Pull request: [#100](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/100))
   * What it does: Acts as the initial codebase for team to allow for the easier building of features into CLI-nic.
   * Justification: This is an essential update, the process can be tedious and time-consuming but it helped
    minimised the refactoring that each member has to do which could require more time to resolve and
     synchronise.
    <br> Credits: The refactoring is done on the codebase provided by [AddressBook-Level3](https://github.com/se-edu/addressbook-level3)

* **New Feature**: Adaptation and integration of autocomplete (Pull requests: [#223](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/223), [#228](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/228))
   * What it does: Suggests updated autocompleted commands for user in real time as they key in the command
    they would like to execute.
   * Justification: This is a good-to-have feature which can help improve user experience as they would be
    able to achieve the intended outcome more quickly.
   * Highlights: To implement auto-complete, a new UI class had to be created and integrated with
    the existing UI classes. The integration with commandHistory feature was the most time-consuming yet
    fulfilling component as they have largely overlapping codes especially in the UI classes. There were a
    number of bugs while dealing with JavaFX but they were resolved in the final implementation where the
    various features were successfully integrated with the codebase for production.
    <br> Credits: This feature was adapted from past student [ShaunNgTX](https://github.com/AY1920S1-CS2103-F10-3/main/blob/master/src/main/java/seedu/revision/ui/AutoComplete.java)

* **Minor Enhancement**: Fix bugs for the CLI-nic (Pull requests: [#130](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/130), [#131](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/131), [#141](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/141), [#158](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/158), [#160](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/160), [#165](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/165), [#172](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/172), [#212](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/212))
   * What it does: Restricts user from performing invalid actions and improve user experience
   * Justification: This is an essential update, as invalid actions would affect the functionality of
    CLI-nic. Unresolved UI bugs may reduce usability of CLI-nic.
   * Challenges: There were many UI related bugs which it took quite some time to learn about the various
    components followed by stepwise trial-and-error to fix.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=jeffreytjs)

* **Project management**:
  * In charge of Documentation and UI Design
  * Tracked GitHub issues
  * Contributed to releases v1.0 - v1.4 (6 releases) on GitHub

* **Enhancements to existing features**:
  * Did the first mock-up for CLI-nic's UI using Adobe Photoshop (Pull request: [#83](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/83))
  * Designed and replaced App Logo (Pull request: [#114](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/114))
  * Made major changes to the UI to include drop-down for products (Pull request: [#131](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/131))
  * Updated the GUI color scheme and added new themes (Pull request: [#131](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/131))
  * Housekeeping to ensure codebase and documentations' format and language are up-to-date (Pull requests: [#144](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/144), [#231](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/231), [#247](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/247))

* **Documentation**:
  * User Guide:
    * Added documentation for the `add` command (Pull requests: [#75](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/75), [#154](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/154))
    * Added documentation for `autoComplete` feature (Pull request: [#228](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/228))
  * Developer Guide:
    * Added `add` feature implementation details (Pull requests: [#139](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/139), [#154](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/154))
    * Added `autoComplete` implementation details and user stories (Pull request: [#228](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/228))
    * Added Activity, Class and Sequence UML Diagrams (Pull request: [#154](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/154))
    * Added Manual Testing Instructions for Developer Guide (Pull request: [#154](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/154))
    * Added Command Prefix table (Pull request: [#154](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/154))

* **Community**:
  * PRs reviewed (with non-trivial comments): (Pull requests: [#133](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/133), [#138](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/138), [#142](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/142), [#156](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/156), [#175](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/175))
  * Reported bugs for other teams in the class (Pull request: [AY2021S1-CS2103-F09-1](https://github.com/AY2021S1-CS2103-F09-1/tp/pull/178))
  * Written Test Cases for tests broken by refactoring (Pull request: [#100](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/100))

* **Tools**:
  * Integrated a third party library ([PlantUML Github Action](https://github.com/cloudbees/plantuml-github-action)) (Pull request: [#145](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/145))
  <br> Credits for workflow to: [qwoprocks](https://github.com/qwoprocks)

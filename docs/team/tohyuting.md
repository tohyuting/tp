---
layout: page
title: Toh Yu Ting's Project Portfolio Page
---

## Project: CLI-nic

 CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**. It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**. It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=tohyuting&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **New Feature**: List all suppliers and warehouses at once.
  * Justification: It is crucial for all suppliers and warehouses to be displayed again after `find` or `view` command was executed.
  * Credits: The feature was originally implemented in **AB3** and adjusted to fit **CLI-nic**.

* **New Feature**: Display help for various commands within the application
  * What it does: Allow users to have a quick look up for a particular command without having to open the User Guide in a separate browser.
  * Justification: This improves the product as it serves as a quick reference for users if they have forgotten what some command does without having to load the User Guide. This provides greater convenience.
  * Highlights: Messages displayed has to be constantly updated as the project grows with new feature. Sample commands were crafted to work with sample data loaded.
  * Credits: The feature was adapted from **AB3**, where User Guide URL was given.

* **New Feature**: View a particular supplier and warehouse entry
  * What it does: Allow users to view a supplier or warehouse information at a particular index of displayed supplier/warehouse list by updating the supplier/warehouse list in the GUI.
  * Justification: This feature helps **CLI-nic** to be more CLI-friendly. Using `view` (together with `find` to filter relevant entities first) allows users to see products associated with a warehouse or supplier without clicking.
  * Highlights: Initial implementation requires user to use name instead of index. This was changed to reduce the amount of typing required by users. Codes for parsing of inputs and test codes have to be re-written as well which was slightly tedious and time-consuming.
  <div style="page-break-after: always;"></div>

* **New Feature**: Edit a particular supplier and warehouse entry
    * What it does: Allow users to edit a supplier or warehouse information at a particular index of displayed supplier/warehouse list.
    * Justification: The user has to update any outdated supplier/warehouse information quickly. Hence, this feature is crucial.
    * Highlights: Parse many prefixes and check for their presence and validity. Uses a different set of prefix (e.g. `s/` and `w/`) initially and this change was slightly challenging.
    * Credits: This feature was adapted from **AB3**.

* **New Class**: Product class to be used by `Supplier` and `Warehouse` classes.
    * What it does: Stores information (quantity and tags) related to a Product associated with a `Supplier` or `Warehouse`.
    * Justification: By keeping track of products related to their entities in **CLI-nic** instead of a separate document, this will bring about convenience to user.
    * Highlights: Users can update stocks of products in `Supplier` and `Warehouse` quickly when they have the information by using `update` command.
    * Credits: This class was adapted slightly from `Person` class in **AB3**.

* **Minor enhancement**:
  * Fix a minor yet crucial bug for jar file to function as expected. [#161](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/161)
  * Error messages of **AB3** are not representative of some commands entered by users. Invalid prefix used errors were not considered and displayed in error messages. Extensive logic handling of inputs from users was done to customise error messages in **CLI-nic**. This is slightly challenging as users are allowed to reorder prefixes in their inputs [#216](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/216), [#242](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/242).
  * Fix bug related to `AutoComplete` feature, where `CommandHistory` feature does not work smoothly if the previous command was a single word command [#227](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/227).

* **Contributions to Documentations**:
  * User Guide:
    * Added documentations and user stories for `list`, `help`, `view` and `edit` commands in the user guide [#153](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/153/files), [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73).
  * Developer Guide:
    * Implementation details of the `list`, `help`, `view` and `edit` features [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73), [#111](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/111/), [#153](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/153). Includes UML diagrams (Class, Sequence and Activity Diagrams).
    * Manual testing: `list`, `help`, `view`, `edit`, `clear`, `exit`, saving data [#232](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/232).
    * Updated Model Class Diagram with teammates, Jeffrey and Qin Liang to represent current **CLI-nic** [#245](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/245).

* **Contributions to team-based tasks**:
  * Managed release of CLI-nic jar `v1.3.2` and enabled assertions on Gradle.
  * Updated initial target user profile [#73](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/73).

* **Contributions beyond the project team**:
  * PRs reviewed (with non-trivial review comments): [#103](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/103#discussion_r502200542), [#110](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/110#discussion_r502924200), [#133](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/133#discussion_r508483009), [#211](https://github.com/AY2021S1-CS2103-W14-4/tp/pull/211#discussion_r515597848)
  * Contribution to forum discussions (examples: [#78](https://github.com/nus-cs2103-AY2021S1/forum/issues/78), [#109](https://github.com/nus-cs2103-AY2021S1/forum/issues/109), [#118](https://github.com/nus-cs2103-AY2021S1/forum/issues/118))

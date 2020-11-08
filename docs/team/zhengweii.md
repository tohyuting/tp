---
layout: page
title: Zheng Wei's Project Portfolio Page
---

## Project: CLI-nic

CLI-nic is **a desktop application to help medical product sales managers keep track of medical products and storage**.
It is optimized for these managers to **update product conditions and access critical product information quickly via fast typing**.
It is written in Java, and has about 21 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added functionality to find relevant supplier(s) or warehouse(s) by names, remarks and/or products
  sold/stored.
  * What it does: Allows users to find relevant supplier(s) or warehouse(s) in addition to their related information.
  * Justification: This feature improves the product significantly because users can effortlessly search for relevant
    information without having to look through every entry manually.
  * Highlights: This feature originally did not take in prefixes for its parameters. To achieve consistency with the
    rest of the commands which were taking in prefixes, this feature had to be re-implemented to accept prefixes. With
    the command now accepting prefixes, users are able to find supplier(s) or warehouse(s) using a combination of
    names, remarks and products as opposed to the previous implementation where users could only find supplier(s) or
    warehouse(s) using either names, remarks or products in a single command.
    
    
* **New Feature**: Added functionality to access command history from the GUI.
  * What it does: The command history feature helps users to recall, edit and to reuse long or complicated commands with
    ease without having to retype them.
  * Justification: This feature improves the user experience significantly as users no longer have to retype
    complicated commands from scratch.
  * Highlights: To implement command history, new classes had to be created and integrated with the
    existing model, storage, logic and UI classes. Multiple implementations for command history were therefore
    considered in order to achieve a seamless integration with the existing infrastructure. 
    The initial implementation integrated seamlessly with the existing codebase but violated multiple abstraction
    barriers and coding design principles. The final implementation, which is built on the initial implementation,
    resolved these abstraction and design issues while succeeding in integrating seamlessly with the codebase.
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=zhengweii&tabRepo=AY2021S1-CS2103-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code)

* **Project management**:
  * Added user stories and issue labels.
  * Helped to release JAR file.

* **Enhancements to existing features**:
  * Helped to update initial GUI.
  * Refactored code to improve maintainability and readability.

* **Documentation**:
  * User Guide:
    * Added documentation for find and command history.
    * Standardise UG for consistency.
    * Added example screenshots for entire UG.
  * Developer Guide:
    * Added implementation details for find and command history. These details include UML diagrams
    (Activity Diagram).
    * Added use cases for `find`.
    * Added manual testing instructions for `find`.
        
* **Contributions to team-based tasks**:
  * Setup team org and repo
  
* **Community**:
  * [PRs reviewed](https://nus-cs2103-ay2021s1.github.io/dashboards/contents/tp-comments.html#32-lim-zheng-wei-zhengweii-67-comments)
  * Contributed to forum discussion (issue [#130](https://github.com/nus-cs2103-AY2021S1/forum/issues/130)) 

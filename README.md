# Dota Underlords Build Creator

**1. What will the application do?**

Dota Underlords is a strategic, auto battler game, where players build teams with Alliances to defeat opponents. These
Alliances give a boost to the team depending on how many units have those Alliances. With this project, I will try to 
implement all the different heroes and Alliances, which will allow the user to create teams that can be used during
their games.

**2. Who will use it?**

This application is aimed towards giving Dota Underlords enthusiasts and novices a chance to practice and test out 
different builds, which can be applied to their normal and ranked games.

**3. Why is this project of interest to you?**

Dota Underlords was first introduced to the Dota community as an arcade game under the title *Dota Auto Chess*. 
After its success as an arcade game, Valve decided to create a standalone game *Dota Underlords*. As an avid Dota 2
and Dota Auto Chess player, I have been constantly playing Dota Underlords ever since its release. This project will
allow me to test out different team combinations and its viability in ranked games. In the long run, this project will
enable me to practice using the Object-Oriented Programming (OOP) techniques and creating a Graphical User Interface
(GUI).

### User Stories

 - As a user, I want to be able to add multiple heroes to a board
 - As a user, I want to be able to remove a hero from the board
 - As a user, I want to be able to move the heroes 
 - As a user, I want to be able to add multiple items to a board
 - As a user, I want to be able to remove an item from the board
 - As a user, I want to be able to move the items  
 - As a user, I want to be able to view the heroes and items on the board
 - As a user, I want to see the Alliances of my board
 - As a user, I want to be able to save my board to file
 - As a user, I want to be able to be reminded to save my board when quitting
 - As a user, I want to be able to load my board from file
 - As a user, I want to be able to get the option to load my board from file

## Phase 4: Task 2
I made the Board class robust by adding exceptions to the methods that had a REQUIRES in the specification and also
test for those exceptions in the BoardTest. The methods that has checked exceptions are the getUnit, addHero, addItem,
removeHero, removeItem, and moveUnit. The main exception that is being checked by these methods is whether the given
row and column are in the acceptable range (i.e. row is between 0 and 3 (inclusive) and column is between 0 and 7 
(inclusive)). The addHero and addItem methods have an extra checked exception to see if the unit is already on the board
or if the unit is being added to an occupied tile. The moveUnit method also has another checked exception to see if the
tile that the unit will move to is already occupied.

## Phase 4: Task 3
If I had more time to work on this project, I would refactor the Board class to remove the duplicated code for Heroes
and Items. For example, there are methods for adding and removing heroes and items that essentially do similar tasks but
are split into seperate methods. If I had more time, this would be the first thing I would change. Furthermore, I would 
refactor the code such that there is less coupling between the UI classes and the Board class. 
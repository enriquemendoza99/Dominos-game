# Domino Game 

Inside your project you should have the following structure:
## At the top level:
### README.md
This project implements a two-player Domino game in Java. One player is 
controlled by the user, while the other is controlled by the computer. 
The game follows standard Domino rules and provides both a console-based 
interface (Version 1) and a graphical user interface (Version 2).

Features
    Two-player game: Human vs Computer
    Standard 28-piece domino set
    Console-based interface (Version 1)
    Graphical user interface (Version 2)
    Simple AI for computer player
    Score tracking

How to Play

    The game starts with each player drawing 7 dominos from the boneyard.
    Players take turns placing dominos on the board.
    A domino can be placed if its value matches the value of an open end on the 
    board.
    If a player cannot place a domino, they must draw from the boneyard until
    they can play or until the boneyard is empty.
    The game ends when a player has no dominos left or when no more moves can 
    be made.
    The player with the lowest total pip count in their hand wins.
### .gitignore
This file tells git which files to you should not track with version control
### Jar file(s)
Executable jar file(s) with all resources needed to run.

## src/ folder
This contains your source code, organized into one or more packages.

## doc/ folder
Includes all documentation other than this README

### Object design diagram

The object design document should be in PDF format.
First page/slide is object diagram, with description of objects on the next page(s).

On more complicated projects, you may need additional diagrams to
clearly describe subcomponents.

### Other documentation

If you found it useful to document your projects in other ways (class
diagrams, algorithm description, tables of events, etc.) put the
documents here.

## resources/ folder

This is an optional folder that you'll include if you are using any
resource files (sounds, images, etc.)


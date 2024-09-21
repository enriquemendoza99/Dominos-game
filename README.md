# Domino Game 

Inside your project you should have the following structure:
## At the top level:
### README.md
This project implements a two-player Domino game in Java. One player is 
controlled by the user, while the other is controlled by the computer. 
The game follows standard Domino rules and provides both a console-based 
interface (Version 1) and a graphical user interface (Version 2).

Key Features

- 28 domino pieces
- Players start with 7 dominos each
- Turn-based gameplay
- Matching adjacent domino values
- Drawing from boneyard when unable to play
- Win condition: lowest total dots on remaining dominos

Game Rules
- The game uses 28 domino pieces.
- Players start with 7 dominos each, drawn from a face-down "boneyard".
- Players take turns placing dominos to form two parallel rows.
- Adjacent dominos must have matching values on their overlapping halves.
- Blanks are wild and can match any value.
- If a player can't play, they draw from the boneyard until they can or 
  it's empty.
- The game ends when the boneyard is empty and either:
  A player places their last domino.
  Both players pass in consecutive turns.
- The player with the lowest total of dots on their remaining dominos wins.

Version Details
Version 1: Console-based

- Uses standard input/output for user interaction
- Displays game state, player's dominos, and available actions in text format

Version 2: GUI-based

- Graphical interface for game state display
- Allows direct user interaction with the game display

### .gitignore
This file tells git which files to you should not track with version control
### Jar file(s)
Executable jar file(s) with all resources needed to run.

## src/ folder
This contains your source code, organized into one or more packages.

## doc/ folder
In the doc folder are the proposed designs of each domino version.

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


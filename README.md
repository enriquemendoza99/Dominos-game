# Domino Game — Java

A two-player Domino game implemented in Java where a human plays against 
a computer opponent. Developed in two versions: a terminal-based version 
and a graphical interface version built with Java Swing.

## Project Structure
src/
DominoGameVer1/    — Terminal-based version
Boneyard.java
Domino.java
DominoGame.java
GameBoard.java
Player.java
DominoGameVer2/    — GUI version
Boneyard.java
Domino.java
DominoGame.java
DominoGameGUI.java
GameBoard.java
Player.java

## How to Run

**Version 1 — Terminal:**
1. Create a virtual environment or compile directly
2. Compile: `javac src/DominoGameVer1/*.java`
3. Run: `java -cp src DominoGameVer1.DominoGame`

**Version 2 — GUI:**
1. Compile: `javac src/DominoGameVer2/*.java`
2. Run: `java -cp src DominoGameVer2.DominoGameGUI`

Or run the executable JAR file:
java -jar domino-game.jar

## How to Play

**Version 1 — Terminal:**
1. On your turn select a domino number from your hand
2. Choose left (L) or right (R) placement
3. Choose whether to rotate the domino (Y/N)
4. If no playable domino exists you will draw from the boneyard automatically

**Version 2 — GUI:**
1. Click a domino button from your hand
2. Choose left or right placement from the dialog
3. Choose whether to rotate from the dialog
4. Click Draw if you have no playable domino

## Game Rules
- Each player starts with 7 dominos drawn from a shuffled boneyard
- A domino can only be placed if one of its values matches the open board end
- The connecting face must exactly equal the board end value
- If no playable domino exists draw from the boneyard until one is found
- If the boneyard is empty and no moves are possible the turn is skipped
- The game ends when a player empties their hand or no moves remain
- Winner has the lowest remaining pip count
- Ties are broken by the last player to successfully place a domino

## File Manifest

**Version 1**
1. `DominoGame.java` — Main executable with game loop and player turn logic
2. `Domino.java` — Domino piece with pip values and rotation
3. `Boneyard.java` — Shuffled pool of 28 dominos
4. `GameBoard.java` — Board state with left and right end tracking
5. `Player.java` — Player hand management and scoring

**Version 2**
1. `DominoGameGUI.java` — Main executable with Swing GUI and board rendering
2. `DominoGame.java` — Core game logic driven by the GUI
3. `Domino.java` — Domino piece with pip values and rotation
4. `Boneyard.java` — Shuffled pool of 28 dominos
5. `GameBoard.java` — Board state with left and right end tracking
6. `Player.java` — Player hand management

### .gitignore
This file tells git which files to you should not track with version control
### Jar file(s)
Executable jar file(s) with all resources needed to run.
The jar files are located in the .idea folder.

## src/ folder
This contains your source code, organized into one or more packages.

## doc/ folder
In the doc folder are the proposed designs of each domino version.

### Object design diagram
The object design document should be in PDF format.
First page/slide is object diagram, with description of objects on the next page(s).
On more complicated projects, you may need additional diagrams to
clearly describe subcomponents.


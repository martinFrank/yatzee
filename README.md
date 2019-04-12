# Yatzee
a yatzee game based on the public game rules

basically it was developed to train my skills on Game AI. Later i had to
move the clumpy Keyboard Input to the CLI-Library. This way i solved the
Input Handling in generall and is now used on many other apps.

# About
Yatzee is a dice game where you have to roll the dice, up to three times
and later write your rolled score into a score board. You can keep some
dice over the next dice roll. The game is over, when all fields in the
score board are filled up.

# Screen shots
well - it's a CLI (Command Line Interface) Application, so you don't
have a Screenshot, but rather the console output:

```
+--------+-----+-----+-----+
| Player | CP1 | CP2 | YOU |
+--------+-----+-----+-----+
|   1    |     |     |     |
|   2    |     |     |     |
|   3    |     |     |     |
|   4    |     |     |     |
|   5    |     |     |     |
|   6    |     | 24  |     |
+--------+-----+-----+-----+
| bonus  |  0  |  0  |  0  |
| t sum  |  0  | 24  |  0  |
+--------+-----+-----+-----+
| 1pair  | 19  |     |     |
| 2pair  |     |     |     |
|  3oak  |     |     |     |
|  4oak  |     |     |     |
|   fh   |     |     |     |
| mistr  |     |     |     |
| mastr  |     |     |     |
| yatzee |     |     |     |
| chance |     |     |     |
+--------+-----+-----+-----+
| b sum  | 19  |  0  |  0  |
| total  | 19  | 24  |  0  |
+--------+-----+-----+-----+
$>
```
As you can see the computer player already filled in their first rolls.
Now it's up to you to do your roll. Rolling uses a different conssole output:

```
+-----------+---+---+---+---+---+
|  dice #   | 1 | 2 | 3 | 4 | 5 |
+-----------+---+---+---+---+---+
|  roll #1  |   |   |   |   |   |<-- you are here
+-----------+---+---+---+---+---+
|   keep    |   |   |   |   |   |
+-----------+---+---+---+---+---+
|  roll #2  |   |   |   |   |   |
+-----------+---+---+---+---+---+
|   keep    |   |   |   |   |   |
+-----------+---+---+---+---+---+
|  roll #3  |   |   |   |   |   |
+-----------+---+---+---+---+---+
$>
```

after your first roll you can keep some of the dice over to the next
roll, i'll picked dice #3 and # 4

```
$>keep 34
+-----------+---+---+---+---+---+
|  dice #   | 1 | 2 | 3 | 4 | 5 |
+-----------+---+---+---+---+---+
|  roll #1  | 5 | 3 | 6 | 6 | 2 |
+-----------+---+---+---+---+---+
|   keep    |   |   | k | k |   |<-- you can keep these
+-----------+---+---+---+---+---+
|  roll #2  |   |   |   |   |   |<-- you are here
+-----------+---+---+---+---+---+
|   keep    |   |   |   |   |   |
+-----------+---+---+---+---+---+
|  roll #3  |   |   |   |   |   |
+-----------+---+---+---+---+---+
$>
```
NOTE: it doesn't matter if you write `$>keep 34` or `$>keep 3 4` thanks
to the CLI-Lib it's easy to handle such input without much worries.

but Let's go on and roll a second time:
```
$>roll
+-----------+---+---+---+---+---+
|  dice #   | 1 | 2 | 3 | 4 | 5 |
+-----------+---+---+---+---+---+
|  roll #1  | 5 | 3 | 6 | 6 | 2 |
+-----------+---+---+---+---+---+
|   keep    |   |   | k | k |   |
+-----------+---+---+---+---+---+
|  roll #2  | 6 | 4 | 6 | 6 | 6 |
+-----------+---+---+---+---+---+
|   keep    |   |   |   |   |   |<-- you can keep these
+-----------+---+---+---+---+---+
|  roll #3  |   |   |   |   |   |<-- you are here
+-----------+---+---+---+---+---+
```
well that went very smooth for me an i'll keep the rest as well:
```
$>keep 1 345
$>roll
+-----------+---+---+---+---+---+
|  dice #   | 1 | 2 | 3 | 4 | 5 |
+-----------+---+---+---+---+---+
|  roll #1  | 5 | 3 | 6 | 6 | 2 |
+-----------+---+---+---+---+---+
|   keep    |   |   | k | k |   |
+-----------+---+---+---+---+---+
|  roll #2  | 6 | 4 | 6 | 6 | 6 |
+-----------+---+---+---+---+---+
|   keep    | k |   | k | k | k |
+-----------+---+---+---+---+---+
|  roll #3  | 6 | 3 | 6 | 6 | 6 |
+-----------+---+---+---+---+---+
```
Too bad i didn't make it into a Yatzee but 24 is a nice result! I'll
write in the the field for 6.
```
$>write 6
+--------+-----+-----+-----+
| Player | CP1 | CP2 | YOU |
+--------+-----+-----+-----+
|   1    |     |     |     |
|   2    |     |     |     |
|   3    |     |     |     |
|   4    |     |     |     |
|   5    |     |     |     |
|   6    |     | 24  | 24  |
+--------+-----+-----+-----+
| bonus  |  0  |  0  |  0  |
| t sum  |  0  | 24  | 30  |
+--------+-----+-----+-----+
| 1pair  | 19  |     |     |
| 2pair  |     |     |     |
|  3oak  | 19  | 28  |     |
|  4oak  |     |     |     |
|   fh   |     |     |     |
| mistr  |     |     |     |
| mastr  |     |     |     |
| yatzee |     |     |     |
| chance |     |     |     |
+--------+-----+-----+-----+
| b sum  | 38  | 28  |  0  |
| total  | 38  | 52  | 30  |
+--------+-----+-----+-----+
```
In the mean time the computer player already made their rolls, so
don't mind

# Background
this App
 - uses the [Board-Game Lib](https://github.com/martinFrank/boardgamelib)
 so it makes it easier to handle
round-based Board games and easier handle Player settings
 - uses the [CLI Lib](https://github.com/martinFrank/commandLineInterface)
 so the whole input handling is resolved


The AI is (yet) rule-based, but i want to make the AI smarter so it
tries to reach certain goals (like getting the bonus for the upper
fields)

The settings for Scores right now hard coded, it's intended to **make
settings** so you can adjust the game experience.
 - what is a minor staright what is a major straight (I had some
 discussions about that, is a minor start a straight of 4 in row?)
 - how much points do you get for a FullHouse?
 - how to handle a second yatzee?

All these questions must be answered be your own, you should use a
setting file for that... Coming soon...

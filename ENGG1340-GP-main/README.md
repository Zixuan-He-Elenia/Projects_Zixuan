#ENGG1340 Group Project
Group 97
Liu Tianjia (3035729370) 
He Zixuan (3035827861)

Description and rule of game:
ENGG1340 Adventure is a turn-based, text-based game where multiple players can choose their roles (fighter/mage/support) and use various skills to fight against boss --- ENGG1340Fail.

Different roles have different skills. The following is a brief introduction of each role:
1.	fighter
feature: Making a lot of damage
defect: No skill to recover mp (magic point) or hp (health point)

2.	mage
feature: Recover mp
defect: No skill to recover hp and damage to boss is low

3.	support
feature: Recover hp and provide shield for players
defect: No skill to recover mp and damage to boss is very low
*shield could prevent players from boss’s next hit

Hence, teamwork is important in OC dmx, different roles could make the most of themselves only with their team members.


Procedures:
The user can first choose a language (English is preferable because the Chinese version still contains some English). Then the user can choose whether to load the saved game or start a new game. If a saved game is loaded, animation will appear to generate the battle board. If a new game is started, the user needs to input the number of players, and their respective names and roles. (WARNING: the name cannot exceed 10 characters)

After entering the battle board, there will be a 50% probability to have a random event. The damage or healing value of some skills is generated randomly within a range, and there are two buff and two debuff that player have chance to get each round, the following is buff & debuff included.
Buff including: 	1. ALL players recover 500hp. 	2. ALL players' mp become double
Debuff including:	3. ALL players lose 50% mp	4. ALL players fall asleep, players' rounds end.

Then, the players take turns to use their skills. When a player does not have enough mp to use some skill, using that skill will automatically skip that round. 
Then, the boss uses his skill.
At the end of one round, please press Enter once or more until the game continues.
Every 5 rounds, there will be a chance for the user to save and leave the game.
The final target of players is defeating boss, 1340Fail. When the HP of the boss becomes 0, players win; when the HPs of all the players become 0, players lose.

(When users input, they just need to type an integer according to the instructions on the screen and press Enter. It is so easy!)

Features Included:
1. Single or multiple players are allowed to play.
2. Each players ca input their own name.
3. Players can choose different roles with multiply skills each.
4. The damage values of some skills for each round are randomly set within a range. This is to make the gameplay more uncertain and challenging.
5. At the beginning of each round, there are chance for players to get random event, which will bring them buff or debuff.
6. Dynamic class and dynamic class array are used to store status of players and boss.
7. Players could choose to save the current game status and exit after every 5 rounds. The status information will be stored in their computer as a file ("data.txt"). For the next play, they could choose to load the last saved game from that file. When playing the game, at the beginning of each round, there will be a notice to inform you how much rounds are there until the next chance to save.
8. Simple animations through pausing the program for certain seconds to make the game more interesting.


Code Requirement:
1.	Generation of random game sets or events
Support features 4 and 5. Random number is generated through the random functions written in function.cpp. Also, a function to control the probability of random event is written.
2.	Data structures for storing game status
Support feature 6
3.	Dynamic memory management
Support feature 6 and the player array size is based on the number of players
4.	File input/output (e.g., for loading/saving game status)
Support feature 7. 
5.	Program codes in multiple files
Different sections with different functions will be stored in different .cpp and .h files based on their functionalities.
6.	Proper indentation and naming styles
Can be seen in the code
7.	In-code documentation
Can be seen in the code


Non-standard c++ library used:
#include <unistd.h>
Function used in this library: sleep() and usleep()
To support feature 8


Compilation and execution instructions:

$ make Adventure
$ ./Adventure

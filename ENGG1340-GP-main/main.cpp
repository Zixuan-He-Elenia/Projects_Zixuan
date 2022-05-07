#include "player.h"
#include "boss.h"
#include "function.h"
#include "init.h"
#include "global.h"
#include "playerfight.h"
#include "bossfight.h"
#include <iostream>
#include <iomanip>
#include <ctime>
#include <cstdlib>
#include <unistd.h>

using namespace std;

bool MAINTAIN(int &save_and_leave)   // returns whether it is a win or loss. 		save_and_leave refers to whether the player chooses to save and leave the game
{
	int count = 0; // count the number of rounds
	int saveIndicator; // save when saveIndicator = 1
	system("clear");
	while (1)
	{
		count += 1; 
		if (count % 5 == 0)  // only give the option to save every 5 rounds
		{
			cout << word[45][lang] << endl; // ask whether need to load or not
			cin >> saveIndicator;
			if (saveIndicator == 1)
			{
				save(boss, p, NUM);
				save_and_leave = 1;
				return 1;	
			}
		}
		else 
		{
			cout << word[46][lang] << 5 - (count % 5) << word[47][lang] << endl;  // there are still x rounds till the next chance to save
			cout << endl;
		}

		int randomEvent = random_events();
		if (randomEvent == 0) // the round is not skipped due to random event
		{
			for (int i = 1; i <= NUM; i++)
				if (p[i].hp > 0)
				{
					Show_Status();
					cout << "Player " << i << " " << p[i].name << word[41][lang] << endl; // player i xxx's round
					switch (p[i].type)
					{
					case 1:
						Fighter_turn(i);
						break;
					case 2:
						Magic_turn(i);
						break;
					case 3:
						Support_turn(i);
						break;
					}
					cout << endl;
					if (boss->hp <= 0) 
					{  //boss is dead and players win
						return 1;
					}
				}
			Show_Status();
			cout << "Boss " << boss->name << word[41][lang] << endl;  //boss'round
			Boss_turn();
			bool mark = 0;	//
			for (int i = 1; i <= NUM; i++)
				if (p[i].hp > 0)
					mark = 1;
			if (mark == 0)	//all players are dead and players loss
				return 0;    
			cout << endl;
			sleep(2);
			cout << "Please press Enter to continue" << endl;
			cin.get();
			cin.get();	//wait for the user to input Enter
		}
		else //player's round is skipped due to random event
		{
			Show_Status();
			cout << "Boss " << boss->name << word[41][lang] << endl;
			Boss_turn();
			bool mark = 0;
			for (int i = 1; i <= NUM; i++)
				if (p[i].hp > 0)
					mark = 1;
			if (mark == 0)
				return 0;
			cout << endl;
			sleep(2);
			cout << "Please press Enter to continue." << endl;
			cin.get();
			cin.get();
		}
	}
}

int main()
{
	srand(time(NULL));
	INIT();
	int save_and_leave = 0;
	if (MAINTAIN(save_and_leave) && save_and_leave != 1)  //returns 1 
	{
		cout << word[42][lang] << endl; // win
	}
	else if (save_and_leave != 1)  //returns 0
	{
		cout << "Game Over!" << endl; //lose
	}
	else	//save_and_leave = 1
	{ 
		delete[] p;
		delete boss;
		return 0;
	}

	delete[] p;
	delete boss;
	sleep(4);
	return 0;
}

#include "playerfight.h"
#include "global.h"
#include "function.h"
#include <iostream>
#include <iomanip>
using namespace std;

//Show the status of players and boss
//no input
//output: a table having 4 columns (Hero, Name, Hp, Mp)shows the status
void Show_Status()
{
	cout << setw(38) << word[22][lang] << endl;
	cout << "  -----------------------------------------------------------------------------" << endl;
	cout << word[23][lang] << endl;
	cout << "  -----------------------------------------------------------------------------" << endl;
	for (int i = 1; i <= NUM; i++)
		p[i].show_status();
	cout << "  -----------------------------------------------------------------------------" << endl;
	boss->show_status();
	cout << "  -----------------------------------------------------------------------------" << endl;
	cout << endl;
}

//Judge whether a random event is met at the beginning of each round, randomly generate an integer a between 1-10. When a is between 1 and 4, a random event is met. There will be change to players' status or skipping players' round
//no input
//output: when a is between 1 and 4, print the specific detail of random event met to the screen. When a = 1, return 0 (A judge for whether to skip the round for players), else return 1.
int random_events()
{
	int randomValue;
	randomValue = Rand(1, 8);
	if (randomValue == 1)
	{
		cout << "  ---------------------------------------------------------------" << endl;
		cout << "  |                 WARNING:  Random event!!!                   |" << endl;
		cout << "  |    DEBUFF: ALL players fall asleep, players' rounds end.    |" << endl;
		cout << "  ---------------------------------------------------------------" << endl;
		return 1;
	}
	else if (randomValue == 2)
	{
		cout << "  ---------------------------------------------------------------" << endl;
		cout << "  |                 WARNING:  Random event!!!                   |" << endl;
		cout << "  |                BUFF: ALL players recover 500hp.             |" << endl;
		cout << "  ---------------------------------------------------------------" << endl;
		for (int i = 1; i <= NUM; i++)
		{
			p[i].hp += 500;
		}
		return 0;
	}
	else if (randomValue == 3)
	{
		cout << "  ---------------------------------------------------------------" << endl;
		cout << "  |                 WARNING:  Random event!!!                   |" << endl;
		cout << "  |                DEBUFF: ALL players lose 50% mp              |" << endl;
		cout << "  ---------------------------------------------------------------" << endl;
		for (int i = 1; i <= NUM; i++)
		{
			p[i].mp = (p[i].mp) / 2;
		}
		return 0;
	}
	else if (randomValue == 4)
	{
		cout << "  ---------------------------------------------------------------" << endl;
		cout << "  |                 WARNING:  Random event!!!                   |" << endl;
		cout << "  |               BUFF: ALL players' mp become double           |" << endl;
		cout << "  ---------------------------------------------------------------" << endl;
		for (int i = 1; i <= NUM; i++)
		{
			p[i].mp = 2 * (p[i].mp);
		}
		return 0;
	}
	else
	{
		return 0;
	}
}

//Players input a number to indicate which skill is chosen for this round
//input: an integer a to show which skill is chosen
//output: return the integer a inputed.
int Skill_Choose(int x)
{
	int choice;
	p[x].show_skill();
	cout << word[24][lang]; //Please input the skill you choose:
	cin >> choice;
	return choice;
}

//fight round for fighter, according to different skill chosen by player, different changes are generated to the status of players and boss
//input: an integer x which is the player number (ex: 2 means the second player)
//output: skill used, mp used, demage made to boss are printed on the screen, if mp is not enough, "mp is not enough. This round is skipped" is printed, if no that skill,"This skill does not exist. This round is skipped." is printed.
void Fighter_turn(int x)
{
	int damage;
	int choice = Skill_Choose(x);
	switch (choice)
	{
	case 1:
		cout << word[2][lang] << p[x].name << word[25][lang] << word[10][lang] << endl; //cout << "Hero" << p[x].name << "used skill --- " << p[x].name << "mad dance" << endl;
		damage = Rand(200, 1000);
		boss->hp -= damage;
		cout << word[26][lang] << damage << word[27][lang] << endl; //cout << "Made" << damage << "damage to the boss" << endl;
		break;
	case 2:
		if (p[x].mp >= 100)
		{
			p[x].mp -= 100;
			cout << word[2][lang] << p[x].name << word[25][lang] << word[12][lang] << endl; //bone skewer, using 100 mp
			int half_chance = Rand(1, 2);
			if (half_chance == 1)
			{
				damage = (boss->hp) / 4;
				boss->hp -= damage;
				cout << word[26][lang] << damage << word[27][lang] << endl;
			}
			else
			{
				cout << "UnLucky that you missed the strike"; // has chance to miss the strike
			}
		}
		else
		{
			cout << word[29][lang] << endl; //	cout << "mp is not enough. This round is skipped" << endl;
		}
		break;
	case 3:
		if (p[x].mp >= 50)
		{
			p[x].mp -= 50;
			cout << word[2][lang] << p[x].name << word[25][lang] << "  bola strike" << endl; //bola strike, using 50 mp
			damage = Rand(300, 2000);
			boss->hp -= damage;
			cout << word[26][lang] << damage << word[27][lang] << endl;
		}
		else
		{
			cout << word[29][lang] << endl; //	cout << "mp is not enough. This round is skipped" << endl;
		}
		break;
	case 4:
		cout << word[2][lang] << p[x].name << word[25][lang] << "  nomral hit" << endl; //cout << "Hero" << p[x].name << "used skill --- " << p[x].name << "normal hit" << endl;
		damage = 500;
		boss->hp -= damage;
		cout << word[26][lang] << damage << word[27][lang] << endl; //cout << "made" << damage << "damage to the boss" << endl;
		break;
	default:
		cout << word[28][lang] << endl; //"This skill does not exist. This round is skipped."
	}
}
//fight round for mage, according to different skill chosen by player, different changes are generated to the status of players and boss
//input: an integer x which is the player number (ex: 2 means the second player)
//output: skill used, mp used, demage made to boss, mp recovered are printed on the screen, if mp is not enough, "mp is not enough. This round is skipped" is printed, if no that skill,"This skill does not exist. This round is skipped." is printed.
void Magic_turn(int x)
{
	int damage, mp_recover;
	int choice = Skill_Choose(x);
	switch (choice)
	{
	case 1:                 // baleful strike, using 50 mp
		if (p[x].mp >= 50)
		{
			p[x].mp -= 50;
			cout << word[2][lang] << p[x].name << word[25][lang] << word[14][lang] << endl; 
			damage = Rand(100, 1500);
			boss->hp -= damage;
			cout << word[26][lang] << damage << word[27][lang] << endl; 
		}
		else
		{
			cout << word[29][lang] << endl; 
		}
		break;
	case 2:                 // normal hit
		cout << word[2][lang] << p[x].name << word[25][lang] << word[16][lang] << endl; 
		damage = Rand(200, 350);
		boss->hp -= damage;
		cout << word[26][lang] << damage << word[27][lang] << endl;
		break;
	case 3:                 // meditation
		cout << word[2][lang] << p[x].name << word[25][lang] << word[18][lang] << endl; 
		mp_recover = Rand(20, 50);
		p[x].mp += mp_recover;
		cout << p[x].name << word[30][lang] << mp_recover << word[31][lang] << endl; 
		break;
	case 4:                 // illumination , using 50 mp
		if (p[x].mp >= 60)
		{
			p[x].mp -= 60;
			cout << word[2][lang] << p[x].name << word[25][lang] << word[20][lang] << endl; 
			mp_recover = Rand(30, 70);
			for (int i = 1; i <= NUM; i++)
			{
				p[i].mp += mp_recover;
			}
			cout << "Every hero" << word[30][lang] << mp_recover << word[31][lang] << endl; 
		}
		else
		{
			cout << word[29][lang] << endl; 
		}
		break;
	default:              // wrong number is inputed 
		cout << word[28][lang] << endl; 
	}
}

//fight round for support, according to different skill chosen by player, different changes are generated to the status of players and boss
//input: an integer x which is the player number (ex: 2 means the second player)
//output: skill used, mp used, demage made to boss, hp recovered are printed on the screen, if mp is not enough, "mp is not enough. This round is skipped" is printed, if no that skill,"This skill does not exist. This round is skipped." is printed.
void Support_turn(int x)
{
	int choice = Skill_Choose(x);
	int hp_recover, damage;
	switch (choice)
	{
	case 1:
		if (p[x].mp >= 25)
		{
			p[x].mp -= 25;
			cout << word[2][lang] << p[x].name << word[25][lang] << "drunken butterfly, using 25 mp" << endl;
			p[x].hp += 500;
			cout << "recover 500hp" << endl;
		}
		else
		{
			cout << word[29][lang] << endl;
		}
		break;
	case 2:
		if (p[x].mp >= 50)
		{
			p[x].mp -= 50;
			cout << word[2][lang] << p[x].name << word[25][lang] << "double flowers' word, using 50 mp" << endl;
			hp_recover = Rand(200, 500);
			for (int i = 1; i <= NUM; i++)
			{
				p[i].hp += hp_recover;
			}
			cout << "recover " << hp_recover << "hp for all players";
		}
		else
		{
			cout << word[29][lang] << endl;
		}
		break;
	case 3:
		if (p[x].mp >= 40)
		{
			p[x].mp -= 40;
			cout << word[2][lang] << p[x].name << word[25][lang] << "two tigers love dancing, using 40 mp" << endl;
			p[x].shield = 1;
			cout << p[x].name << " could fend off boss's next hit" << endl;
		}
		else
		{
			cout << word[29][lang] << endl;
		}
		break;
	case 4:
		if (p[x].mp >= 150)
		{
			p[x].mp -= 150;
			cout << word[2][lang] << p[x].name << word[25][lang] << "little rabit pulls carrots, using 150 mp" << endl;
			for (int i = 1; i <= NUM; i++)
			{
				p[i].shield = 1;
			}
			cout << "ALL players could fend off boss's next hit" << endl;
		}
		else
		{
			cout << word[29][lang] << endl;
		}
		break;
	case 5:
		cout << word[2][lang] << p[x].name << word[25][lang] << "normal hit" << endl; 
		damage = Rand(200, 400);
		boss->hp -= damage;
		cout << word[26][lang] << damage << word[27][lang] << endl;
		break;
	default:
		cout << word[28][lang] << endl; 
	}
}

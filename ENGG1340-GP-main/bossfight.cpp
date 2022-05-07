#include "global.h"
#include "function.h"
#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;

//function to randomly decide which skill boss will use for this round
//no input
//ouput: return the skill chosen, different skills have differnt probability
int boss_choose() // return 1-4 with uneuqal probability
{
	static int range[11] = {0, 25, 25, 20, 15, 15};
	int randomValue = Rand(1, 100);
	for (int i = 1; i <= 10; i++)
	{
		if (randomValue <= range[i])
			return i;
		randomValue -= range[i];
	}
	return -1;
}

//fight round for boss, according to different skill used, different changes are generated to the status of players and boss
//no input
//output: skill used, demage made to players, whether this demage is fended off by players, hp recovered are printed on the screen.
void Boss_turn()
{
	for (int i = 1; i <= NUM; i++) // every round the player naturally recovers 20mp, mage only recovers 10mp
	{
		if (p[i].type == 2)
		{
			p[i].mp += 10;
		}
		else
		{
			p[i].mp += 20;
		}
	}

	int choice = boss_choose();
	if (choice == -1)	// to prevent unexpected error in boss_choose() causing the game to stop. Normally co wont be -1
	{
		cout << word[32][lang] << endl; //"Something wrong with boss. The round is skipped."
		return;
	}
	int damage, recover, tar;
	switch (choice)
	{
	case 1:	//group damage
		damage = Rand(250, 750);
		for (int i = 1; i <= boss->upgrade; i++ ) {
			damage = damage * 11 / 10;
		}									
		cout << word[33][lang] << damage << word[34][lang] << endl; //cout << "Boss hit everybody, making " << damage << " damage to all heros" << endl;
		for (int i = 1; i <= NUM; i++)
		{
			if (p[i].hp > 0 && p[i].shield == 0)
			{
				p[i].hp -= damage;
			}
			else if (p[i].hp > 0 && p[i].shield == 1)
			{
				p[i].shield = 0; //if player has shield
				cout << "But " << p[i].name << " fended off this hit" << endl;
			}
		}
		break;
	case 2:	//single target damage
		do	//choosing an alive target
		{
			tar = Rand(1, NUM);
		} while (p[tar].hp <= 0);
		damage = Rand(450, 750) * (int)sqrt(NUM);	 // damage based on sqrt num
		for (int i = 1; i <= boss->upgrade; i++ ) {
			damage = damage * 11 / 10;
		}													
		cout << word[35][lang] << p[tar].name << word[36][lang] << damage << word[43][lang] << endl; //	cout << "Boss was attracted by the beauty of " << p[tar].name << " touched him/her with a dirty hand, making " << damage <<  " damage to that hero" << endl;
		if (p[tar].shield == 0)
		{
			p[tar].hp -= damage;
		}
		else if (p[tar].shield == 1)
		{
			cout << "But " << p[tar].name << " fended off this hit" << endl;
			p[tar].shield = 0;
		}
		break;
	case 3:	//healing
		recover = Rand(50, 1000) * NUM;								 // heal based on NUM
		for (int i = 1; i <= boss->upgrade; i++ ) {
			recover = recover * 11 / 10;
		}	
		cout << word[37][lang] << recover << word[38][lang] << endl; //"Boss healed itself and recovered XXX hp"
		boss->hp += recover;
		break;
	case 4:	//rest one round
		cout << word[39][lang] << endl; //"After writing too many codes, it decided to rest for 1 round."
		break;
	case 5:	//upgrade
		cout << "The tutor gave students one more assignment before the final exam. Boss upgraded!!! Damage and healing ability increased by 10% permanently." << endl; 
		boss->upgrade += 1;
		break;
	default:
		cout << word[40][lang] << endl; //"Boss fell asleep and ended its round."
		break;
	}
}

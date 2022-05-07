#include "player.h"
#include "global.h"
#include <iostream>
#include <iomanip>
using namespace std;

//initial assignment for differnt types of players.
//input: name of player (string), type of player (int), index of player, ex: 2 is the seond player (int)
//no output
Player::Player(string Name, int Type, int Ind)
{
	name = Name;
	type = Type;
	ind = Ind;
	if (type == 1)
	{ // fighter
		hp = 4000;
		mp = 100;
		shield = 0;
	}
	if (type == 2)
	{ // mage
		hp = 4000;
		mp = 100;
		shield = 0;
	}
	if (type == 3)
	{ // support
		hp = 3000;
		mp = 100;
		shield = 0;
	}
}

//show the status of players if hp > 0;
// no input
// output: the status showed, including name, hp, mp
void Player::show_status()
{
	if (hp <= 0)
		return;

	cout << " |" << setw(7) << ind << setw(8) << "|";
	cout << setw(10) << name << setw(7) << "|";

	if (hp > 0)
		cout << setw(10) << hp << setw(7) << "|";
	else
		cout << setw(10) << "dead" << setw(7) << "|";

	if (hp > 0)
	{
		cout << setw(8) << mp << setw(7) << "|";
		cout << setw(8) << shield << setw(7) << "|";
	}
	cout << endl;
}

//show the skills of players for different types
//no input
//output: the order number of skills, the name of skills and details of skill(mp used and effect of skills) will be showed on screen.
void Player::show_skill()
{
	switch (type)
	{
	case 1:
		cout << word[9][lang] << "1"
			 << "  " << name << " " << word[10][lang] << endl; //mad dance
		cout << word[11][lang] << endl;
		cout << word[9][lang] << "2" << word[12][lang] << endl; // bone skewer
		cout << word[13][lang] << endl;
		cout << word[9][lang] << "3"
			 << "  bola strike, using 50 mp" << endl;
		cout << "         Making 300~2000 damage" << endl;
		cout << word[9][lang] << "4"
			 << "  nomral hit" << endl;
		cout << "         Making 500 damage" << endl;
		cout << endl;
		break;
	case 2:
		cout << word[9][lang] << "1" << word[14][lang] << endl;// baleful strike, using 50 mp
		cout << word[15][lang] << endl;
		cout << word[9][lang] << "2" << word[16][lang] << endl;// normal hit
		cout << word[17][lang] << endl;
		cout << word[9][lang] << "3" << word[18][lang] << endl;// meditation
		cout << word[19][lang] << endl;
		cout << word[9][lang] << "4" << word[20][lang] << endl;// illumination , using 50 mp
		cout << word[21][lang] << endl;
		cout << endl;
		break;
	case 3:
		cout << word[9][lang] << "1"
			 << "  drunken butterfly, using 25mp" << endl;
		cout << "         Recover 500 hp" << endl;
		cout << word[9][lang] << "2"
			 << "  double flowers' world, using 50mp" << endl;
		cout << "         Recover 200-500 hp for all players" << endl;
		cout << word[9][lang] << "3"
			 << "  two tigers love dancing, using 40mp" << endl;
		cout << "         Fend off boss's next hit" << endl;
		cout << word[9][lang] << "4"
			 << "  little rabit pulls carrots, using 150mp" << endl;
		cout << "         All players could fend off boss's next hit" << endl;
		cout << word[9][lang] << "5"
			 << "  nomarl hit" << endl;
		cout << "         Make 200~400 damage" << endl;
		cout << endl;
		break;
	}
}

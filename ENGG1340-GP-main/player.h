#ifndef __PLAYER_H__
#define __PLAYER_H__

#include <string>
using namespace std; 

//declare a class Player
//used for storing the status data of different players and show the skills and status of players
class Player {
public:
	string name;
	int hp, mp, type, ind, shield;
	Player() { }
	Player(string , int , int );
	void show_status();
	void show_skill();
};
#endif


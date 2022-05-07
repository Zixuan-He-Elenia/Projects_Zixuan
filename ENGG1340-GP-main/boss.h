#ifndef __BOSS_H__
#define __BOSS_H__

#include <string>
using namespace std;

//declare a class Boss
//used for storing status data of boss and show the status of boss
class Boss {
public:
	string name;
	int hp, upgrade;
	Boss() {}
	Boss(int );
	void show_status();
};

#endif


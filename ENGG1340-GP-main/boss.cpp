#include "boss.h"
#include <iostream>
#include <iomanip>
using namespace std;

//the initial assignment for boss
//input: number of players(int)
//no output
Boss::Boss(int num)
{
	name = "1340Fail";
	hp = 10000 * num; // the more people, the stronger the boss is
	upgrade = 0;
}

//show the status data of boss, including name, hp
//no input
//output: the status of boss showed
void Boss::show_status()
{
	cout << " |" << setw(9) << "Boss" << setw(6) << "|";
	cout << setw(10) << name << setw(7) << "|";
	cout << setw(10) << hp << setw(7) << "|";
	cout << setw(8) << '-' << setw(7) << "|";
	cout << setw(8) << '-' << setw(7) << "|";
	cout << endl;
}

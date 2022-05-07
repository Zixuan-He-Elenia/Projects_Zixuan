#include "init.h"
#include "function.h"
#include "global.h"
#include <unistd.h>
#include <iostream>
#include <iomanip>
using namespace std;

void INIT() // a function for the initiation of the game
{
	int saved_or_new;
	int language;
	string name;
	cout << "Language: 1. English; 0. Chinese. " << endl
		<< "(The Chinese version still requires basic English, where some of the contents, especially the support hero, are still mainly in English)" << endl
		<< "Please input (preferably 1): " << endl;
	cin >> language;
	while (language < 0 || language > 1)
	{
		cout << "Wrong input!!! Input must be 1 or 0, please input again:" << endl;
		cin >> language;
	}
	lang = language;
	cout << setw(45) << word[0][lang] << endl;	//Welcome to ENGG1340 Adventure
	cout << setw(45) << word[44][lang] << endl; //Whether do you want to load the saved game?(1.yes 2.no, create a new game)
	cin >> saved_or_new;
	while (saved_or_new < 1 || saved_or_new > 2)
	{
		cout << "Wrong input!!! Input must be 1 or 2, please input again:" << endl;
		cin >> saved_or_new;
	}
	if (saved_or_new == 2) // new game
	{
		cout << word[1][lang] << endl; //Please input the num of heros
		cin >> NUM;					   //number of players
		grow_array(p, NUM);
		for (int i = 1; i <= NUM; i++)
			new_player(i);

		sleep(1);
		animation(word[3][lang]); //Coding adventure valley is generating!
		animation(word[4][lang]); //Coding adventure valley has been generated!
		sleep(2);

		*boss = Boss(NUM);
	}
	else // load the saved game
	{
		name = "data.txt";
		NUM = get_NUM(name);
		grow_array(p, NUM);
		load(name, boss, p);

		sleep(1);
		animation(word[3][lang]);
		animation(word[4][lang]);
		sleep(2);
	}
}

void new_player(int num) //generate a new player
{ // num is the hero index
	string NAME;
	int TYPE;
	cout << word[5][lang] << num << word[6][lang] << " (The length of the name cannot exceed 10)" << endl; //please input the name
	cin >> NAME;
	cout << word[5][lang] << num << word[7][lang] << endl; //please input the type
	cin >> TYPE;
	while (TYPE < 1 || TYPE > 3)
	{
		cout << "Wrong type!!! Type of player must be 1 or 2 or 3, please input again:" << endl;
		cin >> TYPE;
	}
	p[num] = Player(NAME, TYPE, num);	// create a new p[num]
	animation(word[8][lang]); // Hero has been generated!
}

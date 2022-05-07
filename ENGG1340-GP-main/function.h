#ifndef __FUNCTION_H__
#define __FUNCTION_H__

#include <string>
#include "global.h"
using namespace std;	
int RandMax();
int Rand(int , int );
void animation(string );
void grow_array(Player *&array, int size);
void save(Boss *&boss, Player player[], int num);
int get_NUM(string a);
void load(string , Boss *, Player *& player);
#endif

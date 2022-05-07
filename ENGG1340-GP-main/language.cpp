#include <string>
using namespace std;
string word[1000][2] = {
    {"欢迎来到new OC世界大冒险", "Welcome to ENGG1340 Adventure"} ,					//0
    {"请输入本次闯关的英雄个数", "Please input the num of heros"} ,		//1
    {"英雄", "Hero "} ,													//2
    {"OC峡谷 生成中!", "Coding adventure valley is generating!"} 	,					//3
    {"OC峡谷生成完毕!", "Coding adventure valley has been generated!"} 	,			//4
    {"请输入第", "Please input No. "} 		,				//5
    {"个英雄的姓名", "'s name"} ,						//6
    {"个英雄的类型(1.战士  2.法师	3.辅助)", "'s type (1. fighter 2. mage 3. support)"} ,						//7
    {"英雄生成完毕!", "Hero has been generated!	"} 	,		//8  0-8 are in init.cpp
    {"技能", "Skill "} , 						//9
    {"乱舞", " mad dance"} 	,					//10
    {"       随机造成1~1000点伤害.", "         Make 200~1000 damage"} 	,					//11
    {"  开山斧  消耗100蓝", "  bone skewer, using 100 mp"} ,						//12
    {"       有50%概率对boss造成当前生命值25%的伤害.", "         50% chance to make 25% damage of the current HP of boss"} 	,					//13
    {"  强力击  消耗50蓝", "  baleful strike, using 50 mp"} ,						//14
    {"       随机造成1~1250点伤害.", "         Make 100~1500 damage"} 	,					//15
    {"  普通击", "  normal hit"} ,						//16
    {"       随机造成200~350点伤害.", "         Make 200~350 damage"} ,						//17
    {"  冥想", "  meditation"} ,						//18
    {"       自身随机回复15~45蓝.", "         Recover 20~50 mp"} 	,					//19
    {"	群体冥想  消耗50蓝", "  illumination , using 60 mp"} ,						//20
    {"       所有友方英雄随机回复30~70蓝.", "         Recover 30~70 mp for all heros"} ,						//21  9-21 are in player.cpp
    {"战斗面板", "Battle Panel"} ,						//22
    {"    英雄      |      Name      |       Hp       |      Mp      |    Shield    | ", " |    Hero      |      Name      |       Hp       |      Mp      |    Shield    | "} ,						//23
    {"请输入你选择的技能 : ", "Please input the skill you choose: "} ,						//24
    {"使用技能  ---  ", " used skill ---"} ,						//25
    {"对boss造成了", "Made "} ,						//26
    {"点伤害", " damage to the boss"} ,						//27
    {"没有这个技能，回合跳过", "This skill does not exist. This round is skipped."} ,						//28
    {"不够蓝，回合结束", "mp is not enough. This round is skipped"} ,						//29
    {"回复了", " recovered "} ,						//30
    {"点mp", " mp"} ,						//31   22-31 are in playerfight.h
    {"boss出现了故障,回合结束!", "Something wrong with boss. The round is skipped."} ,						// 32 
    {"Boss释放了群攻，对所有英雄造成", "Boss hit everybody, making "} ,						//33
    {"伤害", " damage to all heros"} ,						//34
    {"Boss盯上了英雄 ", "Boss was attracted by the beauty of "} ,						// 35
    {" 使用单体技能攻击，造成了", " touched him/her with a dirty hand, making "} ,									// 36
    {"Boss释放了回血术，自身回复", "Boss healed itself and recovered "} ,						//37
    {"血量", "hp"} ,						// 38
    {"Boss写了太多代码后，长出黑眼圈，它决定休息一回合", "After writing too many codes, it decided to rest for 1 round."} ,						//39
    {"Boss本回合睡着了,回合结束!", "Boss fell asleep and ended its round."} ,						// 40
    {"的回合", "'s round"} ,						//41
    {"玩家胜利!", "Player victory!"} ,						//42
    {"伤害", " damage to that hero"} ,						//43
    {"是否读取存档？(1.是 2.否，创建新游戏)","Whether do you want to load the saved game?(1.yes 2.no, create a new game)"},       //44
    {"是否需要存档？(1.是 2.否)","Whether do you want to be save the game and leave?(1.yes 2.no)"},         //45
    {"每五回合可选择是否要存档，距离下次存档还有","You can choose to save the game and leave every 5 rounds, there is(are) "},        //46
    {"回合"," round(s) till the next chance to save"},            //47
    {"已死亡", " is dead"} ,						//48
};
Adventure: main.o player.o language.o init.o global.o function.o playerfight.o bossfight.o boss.o
	g++ -no-pie -o Adventure main.o player.o language.o init.o global.o function.o playerfight.o bossfight.o boss.o

main.o: main.cpp player.h boss.h function.h init.h global.h playerfight.h bossfight.h
	g++ -c main.cpp

player.o: player.cpp player.h global.h
	g++ -c player.cpp

language.o: language.cpp
	g++ -c language.cpp

init.o: init.cpp init.h function.h global.h
	g++ -c init.cpp

global.o: global.cpp global.h player.h boss.h init.h
	g++ -c global.cpp

function.o: function.cpp function.h global.h
	g++ -c function.cpp

playerfight.o: playerfight.cpp playerfight.h global.h function.h
	g++ -c playerfight.cpp
 
bossfight.o: bossfight.cpp bossfight.h global.h function.h
	g++ -c bossfight.cpp

boss.o: boss.cpp boss.h
	g++ -c boss.cpp
	
clean: 
	rm *.o && rm Adventure


package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    TETile RED_FLOOR = new TETile('·', new Color(255, 0, 0), Color.black,"floor");
    int[][] b, dir;
    int[] fa;
    String str = null;
    int ax, ay, dx, dy, fx, fy, rand_cnt = 0, index = 0;
    long seed;
    int step = 1000, lv = 10;
    boolean got = false, hint = false;
    TETile[][] tiles;
    Random rand;
    private char nextKey() {
        if (str != null)
            return index == str.length() ? ' ' : Character.toUpperCase(str.charAt(index++));
        while (!StdDraw.hasNextKeyTyped());
        return Character.toUpperCase(StdDraw.nextKeyTyped());
    }
    public int nextInt(int x) {
        rand_cnt++;
        return rand.nextInt(x);
    }
    public long nextLong() {
        rand_cnt += 2;
        return ((long)rand.nextInt()) << 32 + rand.nextInt();
    }
    public void newGame() {
        if (str==null) StdDraw.setPenColor(255,170,170);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        if (str==null) StdDraw.setFont(fontBig);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.45,"Remainder: Please end with S");
        if (str==null) StdDraw.text(0.5,0.55,"Please enter the seed for generating game: ");
        char c = 'a';
        String s = "";
        while (c != 'S') {
            if (true) {
                c = nextKey();
                if (c == ' ')
                    return;
                if (c != 'S') {
                    s += c;
                }
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.text(0.5,0.5,s);
                if (str==null) StdDraw.show();
            }
        }
        long l = Long.parseLong(s);
        generateWorld(l);
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"Game is generating...");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(1000);
        Font fontBig2 = new Font("Monaco", Font.BOLD, 30);
        if (str==null) StdDraw.setFont(fontBig2);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"3");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"2");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"1");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.setFont(fontBig);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"A new world is generated for you! Enjoy!");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(1000);
        int x = nextInt(WIDTH), y = nextInt(HEIGHT);
        while (tiles[x][y] != Tileset.FLOOR) {
            x = nextInt(WIDTH);
            y = nextInt(HEIGHT);
        }
        ax = x;
        ay = y;
        tiles[x][y] = Tileset.AVATAR;
        x = nextInt(WIDTH - 2) + 1;
        y = nextInt(HEIGHT - 2) + 1;
        while (true) {
            boolean flag = (tiles[x][y] == Tileset.WALL);
            if (tiles[x][y+1]!=Tileset.FLOOR&&tiles[x][y-1]!=Tileset.FLOOR&&tiles[x+1][y]!=Tileset.FLOOR&&tiles[x-1][y]!=Tileset.FLOOR)
                flag = false;
            if (tiles[x][y+1]!=Tileset.NOTHING&&tiles[x][y-1]!=Tileset.NOTHING&&tiles[x+1][y]!=Tileset.NOTHING&&tiles[x-1][y]!=Tileset.NOTHING)
                flag = false;
            if(flag)
                break;
            x = nextInt(WIDTH - 2) + 1;
            y = nextInt(HEIGHT - 2) + 1;
        }
        dx = x;
        dy = y;
        tiles[x][y] = Tileset.LOCKED_DOOR;
        x = nextInt(WIDTH);
        y = nextInt(HEIGHT);
        while (tiles[x][y] != Tileset.FLOOR) {
            x = nextInt(WIDTH);
            y = nextInt(HEIGHT);
        }
        fx = x;
        fy = y;
        tiles[x][y] = Tileset.FLOWER;
    }

    public void loadGame(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("savefile.txt"));
            ax = Integer.parseInt(in.readLine());
            ay = Integer.parseInt(in.readLine());
            dx = Integer.parseInt(in.readLine());
            dy = Integer.parseInt(in.readLine());
            fx = Integer.parseInt(in.readLine());
            fy = Integer.parseInt(in.readLine());
            step = Integer.parseInt(in.readLine());
            lv = Integer.parseInt(in.readLine());
            seed = Long.parseLong(in.readLine());
            rand_cnt = Integer.parseInt(in.readLine());
            rand = new Random(seed);
            for (int i = 0; i < rand_cnt; i++)
                rand.nextInt();
            got = Boolean.parseBoolean(in.readLine());
            hint = Boolean.parseBoolean(in.readLine());
            tiles = new TETile[WIDTH][HEIGHT];
            for (int i = 0; i < WIDTH; i++) {
                String s = in.readLine();
                for (int j = 0; j < HEIGHT; j++) {
                    if (s.charAt(j) == '.')
                        tiles[i][j] = Tileset.FLOOR;
                    else if (s.charAt(j) == 'R')
                        tiles[i][j] = RED_FLOOR;
                    else if (s.charAt(j) == '#')
                        tiles[i][j] = Tileset.WALL;
                    else if (s.charAt(j) == 'F')
                        tiles[i][j] = Tileset.FLOWER;
                    else if (s.charAt(j) == 'L')
                        tiles[i][j] = Tileset.LOCKED_DOOR;
                    else if (s.charAt(j) == 'U')
                        tiles[i][j] = Tileset.UNLOCKED_DOOR;
                    else if (s.charAt(j) == 'A')
                        tiles[i][j] = Tileset.AVATAR;
                    else
                        tiles[i][j] = Tileset.NOTHING;
                }
            }
        } catch (IOException e) {
        }
        if (str==null) StdDraw.setPenColor(255,170,170);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        if (str==null) StdDraw.setFont(fontBig);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"Welcome back!");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"Your game is loading...");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(1000);
        Font fontBig2 = new Font("Monaco", Font.BOLD, 30);
        if (str==null) StdDraw.setFont(fontBig2);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"3");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"2");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.text(0.5,0.5,"1");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.pause(500);
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.setFont(fontBig);
        if (str==null) StdDraw.text(0.5,0.5,"The saved world is loaded for you! Enjoy!");
        if (str==null) StdDraw.show();
    }
    public char start(){
        if (str==null) StdDraw.clear(Color.BLACK);
        if (str==null) StdDraw.setPenColor(255,170,170);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        if (str==null) StdDraw.setFont(fontBig);
        String s = "WELCOME TO CS61BL FINAL GAME";
        if (str==null) StdDraw.text(0.5,1,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if (str==null) StdDraw.text(0.5,0,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        if (str==null) StdDraw.text(0.5, 0.70, s);
        if (str==null) StdDraw.text( 0.35,0.60,"❀");
        if (str==null) StdDraw.text( 0.35,0.50,"❀");
        if (str==null) StdDraw.text( 0.35,0.40,"❀");
        Font fontBig1 = new Font("Monaco", Font.BOLD, 15);
        if (str==null) StdDraw.setFont(fontBig1);
        if (str==null) StdDraw.text(0.5, 0.60, "New Game (N)");
        if (str==null) StdDraw.text(0.5,0.50,"Load Game (L)");
        if (str==null) StdDraw.text(0.5,0.40,"Quit Game (Q)");
        if (str==null) StdDraw.show();
        if (str==null) StdDraw.setFont(fontBig);
        while (true) {
            if (true) {
                char c = nextKey();
                if (c == ' ')
                    return ' ';
                if (c == 'Q'){
                    if (str==null) StdDraw.clear(Color.BLACK);
                    if (str==null) StdDraw.text(0.5,0.5,"See you next time ~");
                    if (str==null) StdDraw.show();
                    if (str==null) StdDraw.pause(2000);
                    return c;
                }
                if (str==null) StdDraw.setPenColor(255,170,170);
                if (c == 'N'){
                    newGame();
                    return c;
                }
                if (c == 'L'){
                    loadGame();
                    return c;
                }
            }
        }
    }

    private int calc() {
        LinkedList<Integer> q = new LinkedList<>();
        int[] d = new int[WIDTH * HEIGHT];
        d[ax * HEIGHT + ay] = 1;
        q.add(ax * HEIGHT + ay);
        while (!q.isEmpty()) {
            int x = q.getFirst() / HEIGHT, y = q.getFirst() % HEIGHT;
            q.removeFirst();
            if (x == fx && y == fy)
                break;
            for (int i = 0; i < 4; i++) {
                int nx = x + dir[i][0], ny = y + dir[i][1];
                if ((tiles[nx][ny] == Tileset.FLOOR || tiles[nx][ny] == Tileset.FLOWER) && d[nx * HEIGHT + ny] == 0) {
                    d[nx * HEIGHT + ny] = d[x * HEIGHT + y] + 1;
                    q.add(nx * HEIGHT + ny);
                }
            }
        }
        int ans = d[fx * HEIGHT + fy] - 1;
        q.clear();
        for (int i = 0; i < HEIGHT * WIDTH; i++)
            d[i] = 0;
        d[fx * HEIGHT + fy] = 1;
        q.add(fx * HEIGHT + fy);
        while (!q.isEmpty()) {
            int x = q.getFirst() / HEIGHT, y = q.getFirst() % HEIGHT;
            q.removeFirst();
            for (int i = 0; i < 4; i++) {
                int nx = x + dir[i][0], ny = y + dir[i][1];
                if (tiles[nx][ny] == Tileset.LOCKED_DOOR) {
                    return ans + d[x*HEIGHT + y];
                }
                if ((tiles[nx][ny] == Tileset.FLOOR || tiles[nx][ny] == Tileset.FLOWER) && d[nx * HEIGHT + ny] == 0) {
                    d[nx * HEIGHT + ny] = d[x * HEIGHT + y] + 1;
                    q.add(nx * HEIGHT + ny);
                }
            }
        }
        return -1;
    }

    public void interactWithKeyboard() {
        char a = start();
        if (a == 'Q'){
            if (str == null)
                System.exit(0);
            return;
        }
        if (a == 'N')
            step = calc() + lv * 10;
        if (str==null) ter.initialize(WIDTH, HEIGHT+4);
        dir = new int[4][2];
        dir[0][0] = dir[2][1] = 1;
        dir[1][0] = dir[3][1] = -1;
        boolean leave = false, judge = false;
        while(judge == false) {
            for (int i = 0; i < WIDTH; i++)
                for (int j = 0; j < HEIGHT; j++)
                    if (tiles[i][j] == RED_FLOOR)
                        tiles[i][j] = Tileset.FLOOR;
            if (hint) {
                LinkedList<Integer> q = new LinkedList<>();
                int[] pre = new int[WIDTH * HEIGHT];
                int end = -1;
                pre[ax * HEIGHT + ay] = ax * HEIGHT + ay;
                q.add(ax * HEIGHT + ay);
                while (!q.isEmpty()) {
                    int x = q.getFirst() / HEIGHT, y = q.getFirst() % HEIGHT;
                    q.removeFirst();
                    if (!got && x == fx && y == fy)
                        break;
                    for (int i = 0; i < 4; i++) {
                        int nx = x + dir[i][0], ny = y + dir[i][1];
                        if ((tiles[nx][ny] == Tileset.FLOOR || tiles[nx][ny] == Tileset.FLOWER) && pre[nx * HEIGHT + ny] == 0) {
                            pre[nx * HEIGHT + ny] = x * HEIGHT + y;
                            q.add(nx * HEIGHT + ny);
                        }
                        if (got && tiles[nx][ny] == Tileset.UNLOCKED_DOOR) {
                            end = nx * HEIGHT + ny;
                            pre[end] = x * HEIGHT + y;
                        }
                    }
                    if (end != -1)
                        break;
                }
                if (end == -1)
                    end = fx * HEIGHT + fy;
                int curr = pre[end];
                while (curr != ax * HEIGHT + ay) {
                    tiles[curr / HEIGHT][curr % HEIGHT] = RED_FLOOR;
                    curr = pre[curr];
                }
            }
            if (str==null) ter.renderFrame(tiles);
            if (str==null) StdDraw.setPenColor(Color.PINK);
            Font HUD = new Font("Monaco", Font.BOLD, 15);
            if (str==null) StdDraw.setFont(HUD);
            if (str==null) {
                if (WIDTH > StdDraw.mouseX() && HEIGHT > StdDraw.mouseY()) {
                    String s = "This is " + tiles[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()].description();
                    StdDraw.text(WIDTH - 10, HEIGHT + 2, s);
                } else if (WIDTH <= StdDraw.mouseX() || HEIGHT <= StdDraw.mouseY()) {
                    StdDraw.text(WIDTH - 10, HEIGHT + 2, "This is nothing");
                }
            }
            String hello = "Hello " + (got?"! Try to leave from the door now!":"! Try to find flower first!");
            if (str==null) StdDraw.text(10,HEIGHT+2,hello);
            String steps = "You can take "+step +" more step(s)!";
            if (str==null) StdDraw.text(WIDTH/2,HEIGHT+2,steps);
            Font font = new Font("Monaco", Font.BOLD, 16 - 2);
            if (str==null) StdDraw.setFont(font);
            if (str==null)  StdDraw.show();
            if (str==null) StdDraw.pause(10);

            if (true){
                char c = nextKey();
                if (c == ' ')
                    return;
                if (leave == true && c!= 'Q'){
                    leave = false;
                }
                if (c == 'S' || c == 'W' || c == 'D' || c == 'A'){
                    int b = move(c);
                    if (b == 1){
                        judge = true;
                    }
                    else {
                        if (b == -1)
                            got = true;
                        if (str==null) StdDraw.show();
                    }
                    if (--step == 0) {
                        if (str==null) StdDraw.clear(Color.BLACK);
                        if (str==null) StdDraw.setPenColor(Color.PINK);
                        Font font1 = new Font("Monaco", Font.BOLD, 30);
                        if (str==null) StdDraw.setFont(font1);
                        if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2,"You have run out of steps.");
                        if (str==null) StdDraw.show();
                        if (str==null) StdDraw.pause(2000);
                        lv = 11;
                        judge = true;
                    }
                }
                else if (c == ':'){
                    leave = true;
                }
                else if (c == 'Q'){
                    if (leave == true) {
                        try {
                            BufferedWriter out = new BufferedWriter(new FileWriter("savefile.txt"));
                            out.write(ax + "\n");
                            out.write(ay + "\n");
                            out.write(dx + "\n");
                            out.write(dy + "\n");
                            out.write(fx + "\n");
                            out.write(fy + "\n");
                            out.write(step + "\n");
                            out.write(lv + "\n");
                            out.write(seed + "\n");
                            out.write(rand_cnt + "\n");
                            out.write(got + "\n");
                            out.write(hint + "\n");
                            for (int i = 0; i < WIDTH; i++,out.write("\n"))
                                for (int j = 0; j < HEIGHT; j++) {
                                    if(tiles[i][j] == Tileset.FLOOR)
                                        out.write(".");
                                    else if(tiles[i][j] == Tileset.FLOWER)
                                        out.write("F");
                                    else if(tiles[i][j] == RED_FLOOR)
                                        out.write("R");
                                    else if(tiles[i][j] == Tileset.WALL)
                                        out.write("#");
                                    else if(tiles[i][j] == Tileset.LOCKED_DOOR)
                                        out.write("L");
                                    else if(tiles[i][j] == Tileset.UNLOCKED_DOOR)
                                        out.write("U");
                                    else if(tiles[i][j] == Tileset.AVATAR)
                                        out.write("A");
                                    else
                                        out.write("N");
                                }
                            out.close();
                        } catch (IOException e) {
                        }
                        judge = true;
                        if (str==null) StdDraw.clear(Color.BLACK);
                        if (str==null) StdDraw.setPenColor(Color.PINK);
                        Font font1 = new Font("Monaco", Font.BOLD, 30);
                        if (str==null) StdDraw.setFont(font1);
                        if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2,"See you next time ~");
                        if (str==null) StdDraw.show();
                        if (str==null) StdDraw.pause(2000);
                    }
                }
                else if (c == 'H') {
                    if (hint)
                        hint = false;
                    else
                        hint = true;
                }
            }
            if (judge && !leave) {
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2 + 2,"New Game (N)");
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2,"Quit Game (Q)");
                if (str==null) StdDraw.show();
                while (true) {
                    if (true) {
                        char c = nextKey();
                        if (c == ' ')
                            return;
                        if (c == 'Q'){
                            if (str==null) StdDraw.clear(Color.BLACK);
                            if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2,"See you next time ~");
                            if (str==null) StdDraw.show();
                            if (str==null) StdDraw.pause(2000);
                            break;
                        }
                        if (c == 'N'){
                            long sd = nextLong();
                            while (sd < 0)
                                sd = nextLong();;
                            generateWorld(sd);
                            int x = nextInt(WIDTH), y = nextInt(HEIGHT);
                            while (tiles[x][y] != Tileset.FLOOR) {
                                x = nextInt(WIDTH);
                                y = nextInt(HEIGHT);
                            }
                            ax = x;
                            ay = y;
                            tiles[x][y] = Tileset.AVATAR;
                            x = nextInt(WIDTH - 2) + 1;
                            y = nextInt(HEIGHT - 2) + 1;
                            while (true) {
                                boolean flag = (tiles[x][y]==Tileset.WALL);
                                if (tiles[x][y+1]!=Tileset.FLOOR&&tiles[x][y-1]!=Tileset.FLOOR&&tiles[x+1][y]!=Tileset.FLOOR&&tiles[x-1][y]!=Tileset.FLOOR)
                                    flag = false;
                                if (tiles[x][y+1]!=Tileset.NOTHING&&tiles[x][y-1]!=Tileset.NOTHING&&tiles[x+1][y]!=Tileset.NOTHING&&tiles[x-1][y]!=Tileset.NOTHING)
                                    flag = false;
                                if(flag)
                                    break;
                                x = nextInt(WIDTH - 2) + 1;
                                y = nextInt(HEIGHT - 2) + 1;
                            }
                            dx = x;
                            dy = y;
                            tiles[x][y] = Tileset.LOCKED_DOOR;
                            x = nextInt(WIDTH);
                            y = nextInt(HEIGHT);
                            while (tiles[x][y] != Tileset.FLOOR) {
                                x = nextInt(WIDTH);
                                y = nextInt(HEIGHT);
                            }
                            fx = x;
                            fy = y;
                            tiles[x][y] = Tileset.FLOWER;
                            judge = false;
                            got = false;
                            hint = false;
                            if (--lv < 0)
                                lv = 0;
                            step = calc() + lv * 10;
                            ter.initialize(WIDTH, HEIGHT + 4);
                            break;
                        }
                    }
                }
            }
        }
        if(str == null) System.exit(0);
    }
    public int move(char dir){
        boolean judge = false;
        if (dir == 'W'){
            if (tiles[ax][ay+1] == Tileset.FLOOR || tiles[ax][ay+1] == RED_FLOOR){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax][ay+1] = Tileset.AVATAR;
                ay = ay+1;
            }
            else if (tiles[ax][ay+1] == Tileset.UNLOCKED_DOOR){
                judge = true;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"YOU WIN!!!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(2000);
            }
            else if (tiles[ax][ay+1] == Tileset.FLOWER){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax][ay+1] = Tileset.AVATAR;
                tiles[dx][dy]= Tileset.UNLOCKED_DOOR;
                ay++;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"You got the flower, door is open now!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(1000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
                return -1;
            }
            else if (tiles[ax][ay+1] == Tileset.LOCKED_DOOR){
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"The door is locked, try to find ❀ first!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(3000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
            }
        }
        else if (dir == 'S'){
            if (tiles[ax][ay-1] == Tileset.FLOOR || tiles[ax][ay-1] == RED_FLOOR){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax][ay-1] = Tileset.AVATAR;
                ay = ay-1;
            }
            else if (tiles[ax][ay-1] == Tileset.UNLOCKED_DOOR){
                judge = true;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"YOU WIN!!!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(2000);
            }
            else if (tiles[ax][ay-1] == Tileset.FLOWER){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax][ay-1] = Tileset.AVATAR;
                tiles[dx][dy]= Tileset.UNLOCKED_DOOR;
                ay--;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"You got the flower, door is open now!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(1000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
                return -1;
            }
            else if (tiles[ax][ay-1] == Tileset.LOCKED_DOOR){
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"The door is locked, try to find ❀ first!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(3000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
            }
        }
        else if (dir == 'A'){
            if (tiles[ax-1][ay] == Tileset.FLOOR || tiles[ax-1][ay] == RED_FLOOR){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax-1][ay] = Tileset.AVATAR;
                ax = ax-1;
            }
            else if (tiles[ax-1][ay] == Tileset.UNLOCKED_DOOR){
                judge = true;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"YOU WIN!!!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(2000);
            }
            else if (tiles[ax-1][ay] == Tileset.FLOWER){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax-1][ay] = Tileset.AVATAR;
                tiles[dx][dy]= Tileset.UNLOCKED_DOOR;
                ax--;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"You got the flower, door is open now!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(1000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
                return -1;
            }
            else if (tiles[ax-1][ay] == Tileset.LOCKED_DOOR){
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"The door is locked, try to find ❀ first!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(3000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
            }
        }
        else if (dir == 'D'){
            if (tiles[ax+1][ay] == Tileset.FLOOR || tiles[ax+1][ay] == RED_FLOOR){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax+1][ay] = Tileset.AVATAR;
                ax = ax+1;
            }
            else if (tiles[ax+1][ay] == Tileset.UNLOCKED_DOOR){
                judge = true;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"YOU WIN!!!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(2000);
            }
            else if (tiles[ax+1][ay] == Tileset.FLOWER){
                tiles[ax][ay] = Tileset.FLOOR;
                tiles[ax+1][ay] = Tileset.AVATAR;
                tiles[dx][dy]= Tileset.UNLOCKED_DOOR;
                ax++;
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"You got the flower, door is open now!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(1000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
                return -1;
            }
            else if (tiles[ax+1][ay] == Tileset.LOCKED_DOOR){
                if (str==null) StdDraw.clear(Color.BLACK);
                if (str==null) StdDraw.setPenColor(Color.PINK);
                Font font1 = new Font("Monaco", Font.BOLD, 30);
                if (str==null) StdDraw.setFont(font1);
                if (str==null) StdDraw.text(WIDTH/2,HEIGHT/2+2,"The door is locked, try to find ❀ first!");
                if (str==null) StdDraw.show();
                if (str==null) StdDraw.pause(3000);
                ter.initialize(WIDTH, HEIGHT + 4);
                ter.renderFrame(tiles);
            }
        }
        return (judge ? 1 : 0);
    }
    public TETile[][] load(){
        return null;
    }
    private int find(int x) { return fa[x] < 0 ? x : (fa[x] = find(fa[x])); }

    private TETile[][] generateWorld(long sd) {
        seed = sd;
        rand = new Random(seed);
        int sum = 0 , t = 0;
        b = new int[WIDTH][HEIGHT];
        tiles = new TETile[WIDTH][HEIGHT];
        while ((double)sum / WIDTH / HEIGHT < 0.2) {
            int w = nextInt(6) + 2, h = nextInt(6) + 2;
            int x = nextInt(WIDTH - 2) + 1, y = nextInt(HEIGHT - 2) + 1;
            if (x + w >= WIDTH || y + h >= HEIGHT)
                continue;
            boolean flag = true;
            for (int i = x - 1; i <= x + w; i++)
                for (int j = y - 1; j <= y + h; j++)
                    if (tiles[i][j] != null)
                        flag = false;
            if (!flag)
                continue;
            sum += w * h;
            for (int i = x; i < x + w; i++)
                for (int j = y; j < y + h; j++) {
                    tiles[i][j] = Tileset.FLOOR;
                    b[i][j] = t;
                }
            t++;
        }
        fa = new int[t];
        for (int i = 0; i < t; i++)
            fa[i] = -1;
        dir = new int[8][2];
        dir[0][0] = dir[1][1] = dir[4][0] = dir[5][0] = dir[4][1] = dir[6][1] = 1;
        dir[2][0] = dir[3][1] = dir[6][0] = dir[7][0] = dir[5][1] = dir[7][1] = -1;
        while (fa[find(0)] != -t) {
            int x = nextInt(WIDTH - 2) + 1, y = nextInt(HEIGHT - 2) + 1;
            boolean emp = true;
            if (tiles[x][y] != null)
                continue;
            for (int i = 0; i < 4; i++) {
                int nx = x + dir[i][0], ny = y + dir[i][1];
                if (tiles[x][y] != null)
                    emp = false;
            }
            if (!emp)
                continue;
            HashSet<Integer> s = new HashSet<Integer>();
            for (int i = 0; i < 4; i++) {
                int nx = x + dir[i][0], ny = y + dir[i][1];
                while (1 < nx && nx < WIDTH - 1 && 1 < ny && ny < HEIGHT - 1) {
                    boolean flag = false;
                    for (int j = 0; j < 4; j++) {
                        int nnx = nx + dir[j][0], nny = ny + dir[j][1];
                        if (tiles[nnx][nny] != null) {
                            s.add(find(b[nnx][nny]));
                            flag = true;
                        }
                    }
                    if (flag)
                        break;
                    nx += dir[i][0];
                    ny += dir[i][1];
                }
            }
            if (s.size() > 1) {
                Iterator<Integer> it = s.iterator();
                int rt = it.next();
                while (it.hasNext()) {
                    int ch = it.next();
                    fa[rt] += fa[ch];
                    fa[ch] = rt;
                }
                for (int i = 0; i < 4; i++) {
                    int nx = x + dir[i][0], ny = y + dir[i][1];
                    while (1 < nx && nx < WIDTH - 1 && 1 < ny && ny < HEIGHT - 1) {
                        boolean flag = false;
                        for (int j = 0; j < 4; j++) {
                            int nnx = nx + dir[j][0], nny = ny + dir[j][1];
                            if (tiles[nnx][nny] != null) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            while (nx != x || ny != y) {
                                tiles[nx][ny] = Tileset.FLOOR;
                                b[nx][ny] = rt;
                                nx -= dir[i][0];
                                ny -= dir[i][1];
                            }
                            break;
                        }
                        nx += dir[i][0];
                        ny += dir[i][1];
                    }
                }
                tiles[x][y] = Tileset.FLOOR;
                b[x][y] = rt;
            }
        }
        for (int i = 1; i < WIDTH - 1; i++)
            for (int j = 1; j < HEIGHT - 1; j++)
                if (tiles[i][j] != null) {
                    for (int k = 0; k < 8; k++) {
                        int x = i + dir[k][0], y = j + dir[k][1];
                        if (tiles[x][y] == null)
                            b[x][y] = 1;
                    }
                }
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                if (tiles[i][j] == null) {
                    if (b[i][j] == 1)
                        tiles[i][j] = Tileset.WALL;
                    else
                        tiles[i][j] = Tileset.NOTHING;
                }
        return tiles;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        str = input;
        interactWithKeyboard();
        return tiles;
    }
}
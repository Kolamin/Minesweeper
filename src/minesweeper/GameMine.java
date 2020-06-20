import java.util.Arrays;
import java.util.Random;

public class GameMine {
    int[][] mines;
    boolean[][] flags;
    boolean[][] revealed;
    public String[][] draw;



    int gridW = 9;
    int gridH = 9;

    int numMines;

    Random random = new Random();

    public void setup() {
        mines = new int[gridW][gridH];
        flags = new boolean[gridW][gridH];
        revealed = new boolean[gridW][gridH];
        draw = new String[gridW][gridH];


        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                mines[x][y] = 0;
                flags[x][y] = false;
                revealed[x][y] = false;
                draw[x][y] = ".";
            }
        }
    }

    public void placeMines() {
        int i = 0;
        while (i < numMines) {
            int x = random.nextInt(gridW);
            int y = random.nextInt(gridH);
            if (mines[x][y] == 1) continue;
            mines[x][y] = 1;
            i++;
        }
    }

    void clearMines() {
        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                mines[x][y] = 0;
            }
        }
    }


    boolean outBounds(int x, int y) {
        return x < 0 || y < 0 || x >= gridW || y >= gridH;
    }

    int calcNear(int x, int y) {
        if (outBounds(x, y)) return 0;
        int i = 0;
        for (int oX = -1; oX <= 1; oX++) {
            for (int oY = -1; oY <= 1; oY++) {
                if (outBounds(oX + x, oY + y)) continue;
                i += mines[oX + x][oY + y];
            }
        }
        return i;
    }

    void reveal(int x, int y) {
        if (outBounds(x, y)) return;
        if (revealed[x][y]) return;
        revealed[x][y] = true;
        draw[x][y] = "/";
        if (calcNear(x, y) > 0) {
            int near = calcNear(x, y);
            draw[x] [y] = String.valueOf(near);
            return;
        }

        reveal(x - 1, y - 1);
        reveal(x - 1, y + 1);
        reveal(x + 1, y - 1);
        reveal(x + 1, y + 1);

        reveal(x - 1, y);
        reveal(x + 1, y);
        reveal(x, y - 1);
        reveal(x, y + 1);
    }

    void drawField(String[][] array) {
        System.out.println(" │123456789│");
        System.out.println("—│—————————│");
        int index = 1;

        for (int i = 0; i < 9; i++) {
            System.out.println(index++ + "│" + Arrays.toString(array[i])
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "") + "|");
        }

        System.out.println("—│—————————│");
    }
}

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        int[][] mines;
        boolean[][] flags;
        boolean[][] revealed;
        String[][] draw;
        int gridW = 9;
        int gridH = 9;

        Random random = new Random();


        mines = new int[gridW][gridH];
        flags = new boolean[gridW][gridH];
        revealed = new boolean[gridW][gridH];
        draw = new String[gridW][gridH];

        setup(mines, flags, revealed, draw, gridW, gridH);

        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? > ");
        int numMines = scanner.nextInt();
        scanner.nextLine();
        drawField(draw);
        placeMines(mines, gridW, gridH, random, numMines);

        boolean firstMove = true;
        boolean checkOut = true;

        while (checkOut) {
            System.out.print("Set/unset mines marks or claim a cell as free: > ");
            String[] input = scanner.nextLine().split(" ");
            int x = Integer.parseInt(input[0]) - 1;
            int y = Integer.parseInt(input[1]) - 1;
            String marks = input[2];
            if (marks.equals("mine")) {
                flags[x][y] = !flags[x][y];
                draw[x][y] = "*";
                drawField(draw);
                continue;
            } else if (firstMove) {
                firstMove = false;
                do {
                    clearMines(mines, gridW, gridH);
                    placeMines(mines, gridW, gridH, random, numMines);
                } while (mines[x][y] != 0);
            }
        }

    }

    private static void clearMines(int[][] mines, int gridW, int gridH) {
        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                mines[x][y] = 0;
            }
        }
    }

    private static void placeMines(int[][] mines, int gridW, int gridH, Random random, int numMines) {
        int i = 0;
        while (i < numMines) {
            int x = random.nextInt(gridW);
            int y = random.nextInt(gridH);
            if (mines[x][y] == 1) continue;
            mines[x][y] = 1;
            i++;
        }
    }

    private static void setup(int[][] mines, boolean[][] flags, boolean[][] revealed, String[][] draw, int gridW, int gridH) {
        for (int x = 0; x < gridW; x++) {
            for (int y = 0; y < gridH; y++) {
                draw[x][y] = ".";
                mines[x][y] = 0;
                flags[x][y] = false;
                revealed[x][y] = false;
            }
        }
    }

    private static void drawField(String[][] array) {
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

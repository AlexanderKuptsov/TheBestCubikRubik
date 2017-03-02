package Cube;

import java.util.Scanner;

/**
 * Created by shurik on 15.02.2017.
 */
public class allSides {
    private Color[][][] memory = new Color[6][3][3]; //хранит состояние кубика Рубика
    private String[] difSides = new String[6];

    //конструктор
    private allSides() {
        int rndColor = 0;
        int countWhite = 0;
        int countPink = 0;
        int countGreen = 0;
        int countYellow = 0;
        int countBlue = 0;
        int countOrange = 0;
        Color[] colors = new Color[6];
        colors[0] = Color.ORANGE;
        colors[1] = Color.BLUE;
        colors[2] = Color.WHITE;
        colors[3] = Color.YELLOW;
        colors[4] = Color.Pink;
        colors[5] = Color.GREEN;

        difSides[0] = "Front";
        difSides[1] = "Right";
        difSides[2] = "Back";
        difSides[3] = "Left";
        difSides[4] = "Upside";
        difSides[5] = "Downside";
        boolean check = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    check = false;
                    while (check != true) {
                        rndColor = (int) (Math.random() * 6);
                        switch (rndColor) { //проверка цветов( не должно быть больше 9 одного цвета)
                            case 0:
                                countOrange++;
                                check = (countOrange <= 9);
                                break;
                            case 1:
                                countBlue++;
                                check = (countBlue <= 9);
                                break;
                            case 2:
                                countWhite++;
                                check = (countWhite <= 9);
                                break;
                            case 3:
                                countYellow++;
                                check = (countYellow <= 9);
                                break;
                            case 4:
                                countPink++;
                                check = (countPink <= 9);
                                break;
                            case 5:
                                countGreen++;
                                check = (countGreen <= 9);
                                break;
                        }
                    }
                    memory[i][j][k] = colors[rndColor];
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        difSides[0] = "Front";
        difSides[1] = "Right";
        difSides[2] = "Back";
        difSides[3] = "Left";
        difSides[4] = "Upside";
        difSides[5] = "Downside";
        for (int i = 0; i < 6; i++) {
            str.append("\nSide ").append(i + 1).append(" (").append(difSides[i]).append(")\n");
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    str.append(memory[i][j][k]).append("\t ");
                }
                str.append("\n");
            }
        }
        return str.toString();
    }

    private void rotate(int side, int direction) { // метод поворота любой стороны кубика напрво или налево
        Color[][][] help = new Color[6][3][3];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                System.arraycopy(memory[i][j], 0, help[i][j], 0, 3);
            }
        }

        if (direction == 1) { //поворот направо
            for (int i = 0; i <= 2; i++) {
                for (int k = 2; k >= 0; k--) {
                    memory[side][i][2 - k] = help[side][k][i];
                }
            }

            if (side == 4) { //поворот верхней грани
                for (int i = 0; i <= 3; i++) {
                    int k;
                    if (i == 0) k = 3;
                    else k = i - 1;
                    memory[i][0][0] = help[k][0][0];
                    memory[i][0][1] = help[k][0][1];
                    memory[i][0][2] = help[k][0][2];
                }
            }
            if (side == 5) { //поворот нижней грани
                for (int i = 0; i <= 3; i++) {
                    int k;
                    if (i == 0) k = 3;
                    else k = i - 1;
                    memory[i][2][0] = help[k][2][0];
                    memory[i][2][1] = help[k][2][1];
                    memory[i][2][2] = help[k][2][2];
                }
            }
            if (side == 1) { //поворот боковой грани (правая)
                for (int i = 0; i <= 2; i += 2) {
                    int k, j = 0;
                    if (i == 0) k = 5;
                    else {
                        k = 4;
                        j = 2;
                    }
                    memory[i][0][2] = help[k][j][2];
                    memory[i][1][2] = help[k][1][2];
                    memory[i][2][2] = help[k][2 - j][2];
                    memory[k][0][2] = help[2 - i][2 - j][2];
                    memory[k][1][2] = help[2 - i][1][2];
                    memory[k][2][2] = help[2 - i][j][2];
                }
            }
            if (side == 2) { //поворот боковой грани (задняя)
                for (int i = 1; i <= 3; i += 2) {
                    int k, j = 0;
                    if (i == 1) k = 5;
                    else {
                        k = 4;
                        j = 2;
                    }
                    memory[i][0][2 - j] = help[k][2 - j][2];
                    memory[i][1][2 - j] = help[k][2 - j][1];
                    memory[i][2][2 - j] = help[k][2 - j][0];
                    memory[k][2 - j][0] = help[4 - i][0][j];
                    memory[k][2 - j][1] = help[4 - i][1][j];
                    memory[k][2 - j][2] = help[4 - i][2][j];
                }
            }
            if (side == 3) { //поворот боковой грани (левая)
                for (int i = 0; i <= 2; i += 2) {
                    int k, j = 0;
                    if (i == 0) k = 4;
                    else {
                        k = 5;
                        j = 2;
                    }
                    memory[i][0][j] = help[k][j][0];
                    memory[i][1][j] = help[k][1][0];
                    memory[i][2][j] = help[k][2 - j][0];
                    memory[k][0][0] = help[2 - i][2 - j][2 - j];
                    memory[k][1][0] = help[2 - i][1][2 - j];
                    memory[k][2][0] = help[2 - i][j][2 - j];
                }
            }
            if (side == 0) { //поворот боковой грани (фронтальная)
                memory[4][2][0] = help[3][2][2];
                memory[4][2][1] = help[3][1][2];
                memory[4][2][2] = help[3][0][2];
                memory[1][0][0] = help[4][2][0];
                memory[1][1][0] = help[4][2][1];
                memory[1][2][0] = help[4][2][2];
                memory[5][0][0] = help[1][2][0];
                memory[5][0][1] = help[1][1][0];
                memory[5][0][2] = help[1][0][0];
                memory[3][0][2] = help[5][0][0];
                memory[3][1][2] = help[5][0][1];
                memory[3][2][2] = help[5][0][2];
            }
        }

        if (direction == 2) { //поворот налево
            for (int i = 0; i <= 2; i++) {
                for (int k = 2; k >= 0; k--) {
                    memory[side][2 - k][i] = help[side][i][k];
                }
            }
            if (side == 4) { //поворот верхней грани
                for (int i = 0; i <= 3; i++) {
                    int k;
                    if (i == 3) k = 0;
                    else k = i + 1;
                    memory[i][0][0] = help[k][0][0];
                    memory[i][0][1] = help[k][0][1];
                    memory[i][0][2] = help[k][0][2];
                }
            }
            if (side == 5) { //поворот нижней грани грани
                for (int i = 0; i <= 4; i++) {
                    int k;
                    if (i == 3) k = 0;
                    else k = i + 1;
                    memory[i][2][0] = help[k][2][0];
                    memory[i][2][1] = help[k][2][1];
                    memory[i][2][2] = help[k][2][2];
                }
            }
            if (side == 0) { //поворот боковой грани (фронтальная)
                memory[4][2][0] = help[1][0][0];
                memory[4][2][1] = help[1][1][0];
                memory[4][2][2] = help[1][2][0];
                memory[1][0][0] = help[5][0][2];
                memory[1][1][0] = help[5][0][1];
                memory[1][2][0] = help[5][0][0];
                memory[5][0][0] = help[3][0][2];
                memory[5][0][1] = help[3][1][2];
                memory[5][0][2] = help[3][2][2];
                memory[3][0][2] = help[4][2][2];
                memory[3][1][2] = help[4][2][1];
                memory[3][2][2] = help[4][2][0];
            }
            if (side == 1) { //поворот боковой грани (правая)
                memory[0][0][2] = help[4][0][2];
                memory[0][1][2] = help[4][1][2];
                memory[0][2][2] = help[4][2][2];
                memory[4][0][2] = help[2][2][0];
                memory[4][1][2] = help[2][1][0];
                memory[4][2][2] = help[2][0][0];
                memory[2][0][0] = help[5][2][2];
                memory[2][1][0] = help[5][1][2];
                memory[2][2][0] = help[5][0][2];
                memory[5][0][2] = help[0][0][2];
                memory[5][1][2] = help[0][1][2];
                memory[5][2][2] = help[0][2][2];
            }
            if (side == 2) { //поворот боковой грани (задняя)
                memory[1][0][2] = help[4][0][0];
                memory[1][1][2] = help[4][0][1];
                memory[1][2][2] = help[4][0][2];
                memory[4][0][0] = help[3][2][0];
                memory[4][0][1] = help[3][1][0];
                memory[4][0][2] = help[3][0][0];
                memory[3][0][0] = help[5][2][0];
                memory[3][1][0] = help[5][2][1];
                memory[3][2][0] = help[5][2][2];
                memory[5][2][2] = help[1][0][2];
                memory[5][2][1] = help[1][1][2];
                memory[5][2][0] = help[1][2][2];
            }
            if (side == 3) { //поворот боковой грани (левая) ++
                memory[0][0][0] = help[5][0][0];
                memory[0][1][0] = help[5][1][0];
                memory[0][2][0] = help[5][2][0];
                memory[4][0][0] = help[0][0][0];
                memory[4][1][0] = help[0][1][0];
                memory[4][2][0] = help[0][2][0];
                memory[2][0][2] = help[4][2][0];
                memory[2][1][2] = help[4][1][0];
                memory[2][2][2] = help[4][0][0];
                memory[5][0][0] = help[2][2][2];
                memory[5][1][0] = help[2][1][2];
                memory[5][2][0] = help[2][0][2];
            }
        }
    }

    public static void main(String[] args) {
        allSides result = new allSides();
        int side, direction;
        System.out.println("Choose difficulty (number of movements):\n" +
                "(1)Beginner - 250   (2)Average skilled - 100   (3)Pro - 56   " +
                "(4)Computer - 20   (5)Lucky - 1   default-100");
        Scanner lvl = new Scanner((System.in));
        int difficulty = 100;
        switch (lvl.nextInt()) {
            case 1:
                difficulty = 500;
                break;
            case 2:
                difficulty = 200;
                break;
            case 3:
                difficulty = 100;
                break;
            case 4:
                difficulty = 50;
                break;
            case 5:
                difficulty = 1;
                break;
        }
        System.out.println(result.toString());
        for (int movements = difficulty; movements > 0; movements--) {
            System.out.print("\n" + movements + " movements left");
            System.out.println("\nChoose the side (1-6)");
            Scanner in = new Scanner((System.in));
            side = in.nextInt();
            System.out.println("Choose direction right(1)/left(2)");
            Scanner input = new Scanner((System.in));
            direction = input.nextInt();
            if ((side >= 1 && side <= 6) && (direction == 1 || direction == 2)) {
                result.rotate(side - 1, direction);
                System.out.println(result.toString());
            } else {
                System.out.print("Write correct information!");
                movements++;
            }
        }
        System.out.print("End of the game!");
        System.exit(0);
    }
}
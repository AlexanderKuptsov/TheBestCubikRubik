package Cube;

import java.util.Scanner;

/**
 * Created by shurik on 15.02.2017.
 */
public class AllSides {
    private static Color[][][] memory = new Color[6][3][3]; //хранит состояние кубика Рубика

    //конструктор
    private AllSides() { // собранный кубик
        Color[] colors = new Color[6];
        colors[0] = Color.PINK;
        colors[1] = Color.YELLOW;
        colors[2] = Color.ORANGE;
        colors[3] = Color.WHITE;
        colors[4] = Color.BLUE;
        colors[5] = Color.GREEN;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    memory[i][j][k] = colors[i];
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Side[] difSides = new Side[6];
        difSides[0] = Side.FRONT;
        difSides[1] = Side.RIGHT;
        difSides[2] = Side.BACK;
        difSides[3] = Side.LEFT;
        difSides[4] = Side.UPSIDE;
        difSides[5] = Side.DOWNSIDE;
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

    public static void helpMemory(Color[][][] help) { // вспомогательный массив
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                System.arraycopy(memory[i][j], 0, help[i][j], 0, 3);
            }
        }
    }

    private void rotateRight(int side) { // метод поворота любой стороны кубика напрво
        Color[][][] help = new Color[6][3][3];
        helpMemory(help);

        for (int i = 0; i <= 2; i++) { //поворот внутри выбранной грани
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

    private void rotateLeft(int side) { // метод поворота любой стороны кубика налево
        Color[][][] help = new Color[6][3][3];
        helpMemory(help);
        for (int i = 0; i <= 2; i++) { //поворот внутри выбранной грани
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
        if (side == 3) { //поворот боковой грани (левая)
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

    public void shuffle() { // перемешевание кубика
        int numberOfRotations = (int) (Math.random() * 500);
        AllSides randomCube = new AllSides();
        for (int i = 0; i < numberOfRotations; i++) {
            int randomSide = (int) (Math.random() * 6);
            int randomDirection = (int) (Math.random() * 2);
            if (randomDirection == 0) randomCube.rotateRight(randomSide);
            else randomCube.rotateLeft(randomSide);
        }
    }

    public static void main(String[] args) {
        AllSides result = new AllSides();
        System.out.println(result.toString());
        System.out.println("Choose difficulty (number of movements):\n" +
                "(1)Beginner - 250   (2)Average skilled - 100   (3)Pro - 56   " +
                "(4)Computer - 20   (5)Lucky - 1   default-100");
        Scanner lvl = new Scanner((System.in));
        int difficulty = 100;
        switch (lvl.nextInt()) {
            case 1:
                difficulty = 250;
                break;
            case 2:
                difficulty = 100;
                break;
            case 3:
                difficulty = 56;
                break;
            case 4:
                difficulty = 20;
                break;
            case 5:
                difficulty = 1;
                break;
        }
        result.shuffle();
        System.out.println(result.toString());
        Side side;
        Direction direction;
        for (int movements = difficulty; movements > 0; movements--) {
            System.out.print("\n" + movements + " movements left");
            System.out.println("\nChoose the side (1-6)");
            Scanner in = new Scanner((System.in));
            side = Side.valueOf(in.next().toUpperCase());
            System.out.println("Choose direction right/left");
            Scanner input = new Scanner((System.in));
            direction = Direction.valueOf(input.next().toUpperCase());
            int sideNumber = -1;
            switch (side) {
                case FRONT:
                    sideNumber = 0;
                    break;
                case RIGHT:
                    sideNumber = 1;
                    break;
                case BACK:
                    sideNumber = 2;
                    break;
                case LEFT:
                    sideNumber = 3;
                    break;
                case UPSIDE:
                    sideNumber = 4;
                    break;
                case DOWNSIDE:
                    sideNumber = 5;
                    break;
            }
            //проверка введенных данных
            if ((sideNumber != -1) && (direction == Direction.RIGHT || direction == Direction.LEFT)) {
                if (direction == Direction.RIGHT) result.rotateRight(sideNumber);
                else result.rotateLeft(sideNumber);
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
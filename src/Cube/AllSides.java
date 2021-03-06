package Cube;


import jdk.management.resource.internal.inst.SimpleAsynchronousFileChannelImplRMHooks;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by shurik on 15.02.2017.
 */
public class AllSides {
    private Color[][][] memory = new Color[6][3][3]; //хранит состояние кубика Рубика

    //конструктор
    public AllSides() { // собранный кубик
        Color[] colors = new Color[6];
        colors[0] = Color.PINK;
        colors[1] = Color.YELLOW;
        colors[2] = Color.ORANGE;
        colors[3] = Color.WHITE;
        colors[4] = Color.BLUE;
        colors[5] = Color.GREEN;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < memory[0][0].length; j++) {
                for (int k = 0; k < memory[0][0].length; k++) {
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
            for (int j = 0; j < memory[0][0].length; j++) {
                for (int k = 0; k < memory[0][0].length; k++) {
                    str.append(memory[i][j][k]).append("\t ");
                }
                str.append("\n");
            }
        }
        return str.toString();
    }

    public Color getCellColor(Side side, int numberLine, int numberColumn) {
        Color cellColor = null;
        switch (side) {
            case FRONT:
                cellColor = memory[0][numberLine][numberColumn];
                break;
            case RIGHT:
                cellColor = memory[1][numberLine][numberColumn];
                break;
            case BACK:
                cellColor = memory[2][numberLine][numberColumn];
                break;
            case LEFT:
                cellColor = memory[3][numberLine][numberColumn];
                break;
            case UPSIDE:
                cellColor = memory[4][numberLine][numberColumn];
                break;
            case DOWNSIDE:
                cellColor = memory[5][numberLine][numberColumn];
                break;
        }
        return cellColor;
    }

    private void helpMemory(Color[][][] help) { // вспомогательный массив
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < memory[0][0].length; j++) {
                System.arraycopy(memory[i][j], 0, help[i][j], 0, memory[0][0].length);
            }
        }
    }

    private void swipeAnySideRight(int side) { //поворот внутри выбранной грани направо
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        helpMemory(help);
        for (int i = 0; i < memory[0][0].length; i++) {
            for (int k = memory[0][0].length - 1; k >= 0; k--) {
                memory[side][i][memory[0][0].length - 1 - k] = help[side][k][i];
            }
        }
    }

    private void swipeAnySideLeft(int side) { //поворот внутри выбранной грани налево
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        helpMemory(help);
        for (int i = 0; i < memory[0][0].length; i++) {
            for (int k = memory[0][0].length - 1; k >= 0; k--) {
                memory[side][memory[0][0].length - 1 - k][i] = help[side][i][k];
            }
        }
    }

    /*private void positionRotation() { // поворот всего кубика (вертикальная ось становится горизонтальной)
        //////////////

    } */
    public void moveToFront(Side side) { // делает выбранную грань фронтальной
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        helpMemory(help);
        int sideOld;
        if (side == Side.UPSIDE || side == Side.DOWNSIDE) {
            sideOld = side == Side.UPSIDE ? 2 : 0;
            int sideNew = side == Side.UPSIDE ? 0 : 2;
            for (int j = 0; j < memory[0][0].length; j++) {
                System.arraycopy(help[4][j], 0, memory[sideNew][j], 0, memory[0][0].length);
            }
            for (int j = 0; j < memory[0][0].length; j++) {
                System.arraycopy(help[sideOld][j], 0, memory[4][j], 0, memory[0][0].length);
            }
            for (int j = 0; j < memory[0][0].length; j++) {
                System.arraycopy(help[sideNew][j], 0, memory[5][j], 0, memory[0][0].length);
            }
            for (int j = 0; j < memory[0][0].length; j++) {
                System.arraycopy(help[5][j], 0, memory[sideOld][j], 0, memory[0][0].length);
            }
            if (side == Side.UPSIDE) {
                swipeAnySideRight(3);
                swipeAnySideLeft(1);
            } else {
                swipeAnySideRight(1);
                swipeAnySideLeft(3);
            }
        } else {
            if (side != Side.FRONT) {
                int numberOfRotations = side == Side.BACK ? 1 : 2;
                if (side == Side.RIGHT) numberOfRotations = 0;
                for (int swipe = 0; swipe <= numberOfRotations; swipe++) {
                    helpMemory(help);
                    for (int sideNew = 0; sideNew < 4; sideNew++) {
                        sideOld = sideNew != 3 ? sideNew + 1 : 0; // направо/налево
                        for (int j = 0; j < memory[0][0].length; j++) {
                            System.arraycopy(help[sideOld][j], 0, memory[sideNew][j], 0, memory[0][0].length);
                        }
                    }
                    swipeAnySideRight(4);
                    swipeAnySideLeft(5);
                }
            }
        }
    }

    //поворот всего кубика направо. Приведение правой, задней или левой стороны к фронтальной
    private void rightSwipe(int numberOfRotations, Direction direction, int[] numberOfSides) {
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        int sideOld;
        for (int swipe = 0; swipe <= numberOfRotations; swipe++) {
            helpMemory(help);
            for (int sideNew = 0; sideNew < 4; sideNew++) {
                sideOld = sideNew != 3 ? sideNew + 1 : 0; // направо/налево
                for (int j = 0; j < memory[0][0].length; j++) {
                    System.arraycopy(help[sideOld][j], 0, memory[sideNew][j], 0, memory[0][0].length);
                }
            }
            swipeAnySideRight(4);
            swipeAnySideLeft(5);
        }
        rotationMain(direction, numberOfSides);
        for (int swipe = 0; swipe <= numberOfRotations; swipe++) {
            helpMemory(help);
            for (int sideNew = 3; sideNew >= 0; sideNew--) {
                sideOld = sideNew != 0 ? sideNew - 1 : 3; // направо/налево
                for (int j = 0; j < memory[0][0].length; j++) {
                    System.arraycopy(help[sideOld][j], 0, memory[sideNew][j], 0, memory[0][0].length);
                }
            }
            swipeAnySideLeft(4);
            swipeAnySideRight(5);
        }
    }

    //поворот всего кубика вверх/вниз. Приведение верхней или нижней грани к фронтальной
    private void upDownSwipe(Side side, Direction direction, int[] numberOfSides) {
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        helpMemory(help);
        int sideOld = side == Side.UPSIDE ? 2 : 0;
        int sideNew = side == Side.UPSIDE ? 0 : 2;
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[4][j], 0, memory[sideNew][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[sideOld][j], 0, memory[4][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[sideNew][j], 0, memory[5][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[5][j], 0, memory[sideOld][j], 0, memory[0][0].length);
        }
        if (side == Side.UPSIDE) {
            swipeAnySideRight(3);
            swipeAnySideLeft(1);
        } else {
            swipeAnySideRight(1);
            swipeAnySideLeft(3);
        }

        rotationMain(direction, numberOfSides);
        helpMemory(help);

        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[sideNew][j], 0, memory[4][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[4][j], 0, memory[sideOld][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[5][j], 0, memory[sideNew][j], 0, memory[0][0].length);
        }
        for (int j = 0; j < memory[0][0].length; j++) {
            System.arraycopy(help[sideOld][j], 0, memory[5][j], 0, memory[0][0].length);
        }
        if (side == Side.UPSIDE) {
            swipeAnySideRight(1);
            swipeAnySideLeft(3);
        } else {
            swipeAnySideRight(3);
            swipeAnySideLeft(1);
        }
    }

    // поворот фронтальной стороны( с возможным выбором колличества слоев для поворота) главный метод поворота
    private void rotationMain(Direction direction, int[] difSides) {
        Color[][][] help = new Color[6][memory[0][0].length][memory[0][0].length];
        helpMemory(help);
        boolean checkUp = false;
        boolean checkDown = false;
        for (int difSide1 : difSides) {
            if (difSide1 == 1) checkUp = true;
            if (difSide1 == 3) checkDown = true;
        }
        if (direction == Direction.COUNTERCLOCKWISE) {
            if (checkUp) {
                swipeAnySideLeft(4);
            }
            if (checkDown) {
                swipeAnySideRight(5);
            }
        } else {
            if (checkUp) {
                swipeAnySideRight(4);
            }
            if (checkDown) {
                swipeAnySideLeft(5);
            }
        }
        int sideOld;
        for (int sideNew = 0; sideNew < 4; sideNew++) {
            if (direction == Direction.COUNTERCLOCKWISE) {
                sideOld = sideNew != 0 ? sideNew - 1 : 3; // направо/налево
            } else {
                sideOld = sideNew != 3 ? sideNew + 1 : 0;// направо/налево
            }
            for (int difSide1 : difSides) {
                int difSide = difSide1 - 1;
                System.arraycopy(help[sideOld][difSide], 0,
                        memory[sideNew][difSide], 0, memory[0][0].length);
            }
        }
    }

    public void rotate(Side side, Direction direction, int[] numberOfSides) {
        switch (side) {
            case FRONT:
                rotationMain(direction, numberOfSides);
                break;
            case RIGHT:
                rightSwipe(0, direction, numberOfSides);
                break;
            case BACK:
                rightSwipe(1, direction, numberOfSides);
                break;
            case LEFT:
                rightSwipe(2, direction, numberOfSides);
                break;
            case UPSIDE:
                upDownSwipe(side, direction, numberOfSides);
                break;
            case DOWNSIDE:
                upDownSwipe(side, direction, numberOfSides);
                break;
        }
    }

    public void shuffle() { // перемешевание кубика
        int numberOfRotations = (int) (Math.random() * 500);
        for (int i = 0; i < numberOfRotations; i++) {
            int randomSide = (int) (Math.random() * 6);
            int randomDirectionNum = (int) (Math.random() * 2);
            Direction randomDirection = randomDirectionNum == 0 ? Direction.COUNTERCLOCKWISE : Direction.CLOCKWISE;
            int randomLinesNum = (int) (Math.random() * 2);
            int[] randomLines = new int[1];
            randomLines[0] = randomLinesNum == 0 ? 1 : 3;
            switch (randomSide) {
                case 0:
                    rotate(Side.FRONT, randomDirection, randomLines);
                    break;
                case 1:
                    rotate(Side.RIGHT, randomDirection, randomLines);
                    break;
                case 2:
                    rotate(Side.BACK, randomDirection, randomLines);
                    break;
                case 3:
                    rotate(Side.LEFT, randomDirection, randomLines);
                    break;
                case 4:
                    rotate(Side.UPSIDE, randomDirection, randomLines);
                    break;
                case 5:
                    rotate(Side.DOWNSIDE, randomDirection, randomLines);
                    break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllSides allSides = (AllSides) o;

        return Arrays.deepEquals(memory, allSides.memory);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(memory);
    }

    public static void main(String[] args) {
        AllSides result = new AllSides();
        System.out.println(result.toString());
        System.out.println("Choose difficulty (number of movements):\n" +
                "(1)Beginner - 250   (2)Average skilled - 100   (3)Pro - 56   " +
                "(4)Computer - 20   (5)Lucky - 1   default-100");
        Scanner lvl = new Scanner((System.in));
        int difficulty = 100;
        int level;
        try {
            level = lvl.nextInt();
        } catch (InputMismatchException e) { //проверка введенных данных
            level = 2;
            System.out.println("Incorrect information! Number of movements was set to 100");
        }
        switch (level) {
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
        Direction direction, movementVertHor;
        for (int movements = difficulty; movements > 0; movements--) {
            System.out.print("\n" + movements + " movements left");
            System.out.println("\nChoose the side: front / right / back / left / upside / downside  \n" +
                    "Also you can write \"show\" to see the cube or \"tofront\" to make any side front");
            Scanner in = new Scanner((System.in));
            try {
                side = Side.valueOf(in.next().toUpperCase());
                if (side == Side.TOFRONT) {
                    System.out.println("ToFRONT");
                    System.out.println("Choose the side you want to make front: front / right / back / left / upside / downside");
                    Scanner information = new Scanner((System.in));
                    try {
                        side = Side.valueOf(information.next().toUpperCase());
                        result.moveToFront(side);
                        System.out.println(result.toString());
                        continue;
                    } catch (IllegalArgumentException e) { //проверка введенных данных
                        System.out.print("Write correct information!");
                        movements++;
                        continue;
                    }
                }
                System.out.println(side + " side");
            } catch (IllegalArgumentException e) { //проверка введенных данных
                System.out.print("Write correct information!");
                movements++;
                continue;
            }
            if (side == Side.SHOW) { // показывает состояние всех граней
                System.out.println(result.toString());
                movements++;
                continue;
            }

            System.out.println("Choose direction clockwise / counterclockwise");
            Scanner input = new Scanner((System.in));
            try {
                direction = Direction.valueOf(input.next().toUpperCase());
            } catch (IllegalArgumentException e) { //проверка введенных данных
                System.out.print("Write correct information!");
                movements++;
                continue;
            }
           /* System.out.println("Choose direction horizontal / vertical");
            Scanner read = new Scanner((System.in));
            try {
                movementVertHor = Direction.valueOf(read.next().toUpperCase());
            } catch (IllegalArgumentException e) { //проверка введенных данных
                System.out.print("Write correct information!");
                movements++;
                continue;
            }
            if (movementVertHor == Direction.VERTICAL) result.positionRotation(); // поворот всего кубика */
            System.out.println("Choose sides you want to rotate (For example: 1 or 2,3 or 3,1)");
            Scanner info = new Scanner((System.in));
            String numberOfSides;
            try {
                numberOfSides = info.next();
            } catch (InputMismatchException e) { //проверка введенных данных
                System.out.println("Write correct information!");
                movements++;
                continue;
            }
            int[] difSides = Arrays.stream(numberOfSides.split(",")).mapToInt(s -> Integer.parseInt(s)).toArray();
            result.rotate(side, direction, difSides);
            // if (movementVertHor == Direction.VERTICAL) result.positionRotation(); // поворот всего кубика
            System.out.println(result.toString());
        }
        System.out.print("End of the game!");
        System.exit(0);
    }
}

import java.util.Scanner;

public class Battleship {
    public static Scanner sc = new Scanner(System.in);
    public static int count = 0;
    public static final int rows = 10;
    public static final int cols = 10;


    public static void main(String[] args) {

        char[][] grid = new char[rows][cols];
        char[][] grid2 = new char[rows][cols];
        char[][] grid3 = new char[rows][cols];
        char[][] grid4 = new char[rows][cols];


        createGrid(grid);
        createGrid(grid2);
        createGrid(grid3);
        createGrid(grid4);

        System.out.println("\n");
        printGrid(grid);
        getShip(grid, 5, "Aircraft Carrier");
        printGrid(grid);
        getShip(grid, 4, "Battleship");
        printGrid(grid);
        getShip(grid, 3, "Submarine");
        printGrid(grid);
        getShip(grid, 3, "Cruiser");
        printGrid(grid);
        getShip(grid, 2, "Destroyer");
        printGrid(grid);


        System.out.println();
        System.out.println("Press Enter and pass the move to another player");

        String enter = sc.nextLine();

        System.out.println("Player 2, place your ships on the game field");
        System.out.println();

        printGrid(grid2);
        getShip(grid2, 5, "Aircraft Carrier");
        printGrid(grid2);
        getShip(grid2, 4, "Battleship");
        printGrid(grid2);
        getShip(grid2, 3, "Submarine");
        printGrid(grid2);
        getShip(grid2, 3, "Cruiser");
        printGrid(grid2);
        getShip(grid2, 2, "Destroyer");
        printGrid(grid2);

        System.out.println();
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");

        enter = sc.nextLine();


        while (true) {
            printGrid(grid3);
            System.out.println("---------------------");
            printGrid(grid);
            System.out.println();
            System.out.println("Player 1, it's your turn:");
            System.out.println();
            shootShip(grid2, grid3);
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");

            enter = sc.nextLine();

            printGrid(grid4);
            System.out.println("---------------------");
            printGrid(grid2);
            System.out.println();
            System.out.println("Player 2, it's your turn:");
            System.out.println();
            shootShip(grid, grid4);
            System.out.println("Press Enter and pass the move to another player");
            System.out.println();

            enter = sc.nextLine();
        }


    }


    public static void shootShip(char battleField[][], char battlefield2[][]) {

        char[][] grid2 = battlefield2;


        String input = sc.nextLine();
        int rowShoot = input.charAt(0) - 65;
        int colShoot = Integer.parseInt(input.substring(1)) - 1;


        if (rowShoot < 10 && colShoot < 10) {
            if (battleField[rowShoot][colShoot] == '~') {
                battleField[rowShoot][colShoot] = 'M';
                grid2[rowShoot][colShoot] = 'M';

                System.out.println();
                System.out.println("You missed!");


            } else if (battleField[rowShoot][colShoot] == 'O') {


                if (count == 16) {
                    battleField[rowShoot][colShoot] = 'X';
                    grid2[rowShoot][colShoot] = 'X';

                    System.out.println("You sank the last ship. You won. Congratulations!");


                } else {
                    battleField[rowShoot][colShoot] = 'X';
                    grid2[rowShoot][colShoot] = 'X';


                    if (rowShoot < 9 && battleField[rowShoot + 1][colShoot] == 'O' || rowShoot >= 1 && battleField[rowShoot - 1][colShoot] == 'O' || colShoot >= 1 && battleField[rowShoot][colShoot - 1] == 'O' || colShoot < 9 && battleField[rowShoot][colShoot + 1] == 'O') {
                        System.out.println();
                        System.out.println("You hit a ship!");

                    } else {
                        System.out.println("You sank a ship! Specify a new target");

                    }
                    count++;
                }

            }
        } else {
            System.out.println("Error! Wrong ship location! Try again:");

        }
    }


    public static void getShip(char battleField[][], int turn, String ship) {


        while (true) {
            System.out.println();
            System.out.println("Enter the coordinates of the " + ship + " (" + turn + " cells): ");
            System.out.println();


            String input = sc.nextLine();
            System.out.println();
            String[] split = input.split(" ");

            int rowOne = (int) split[0].charAt(0) - 65;
            int columOne = Integer.parseInt(split[0].substring(1)) - 1;
            int rowTwo = (int) split[1].charAt(0) - 65;
            int columTwo = Integer.parseInt(split[1].substring(1)) - 1;

            int digRow = Math.abs(rowOne - rowTwo) + 1;
            int digCol = Math.abs(columOne - columTwo) + 1;


            boolean rowEquals = rowOne == rowTwo;
            boolean colmEquals = columOne == columTwo;

            boolean checkError = false;

            int startPosition = 0;

            if (!((rowOne == rowTwo || columOne == columTwo) && (digRow == turn || digCol == turn))) {
                System.out.println("Error! Wrong length of the " + ship + "! Try again:");
                continue;

            } else {
                if (rowEquals) {
                    startPosition = Math.min(columOne, columTwo);
                    int count = 0;
                    for (int i = startPosition; i < startPosition + turn; i++) {
                        count++;
                        if (checkShip(battleField, rowOne, i, turn, rowEquals, count, startPosition)) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            checkError = true;
                            break;
                        } else {
                            battleField[rowOne][i] = 'O';

                        }

                    }


                } else if (colmEquals) {

                    startPosition = Math.min(rowOne, rowTwo);
                    int count = 0;
                    for (int i = startPosition; i < startPosition + turn; i++) {
                        count++;

                        if (checkShip(battleField, i, columOne, turn, rowEquals, count, startPosition)) {
                            System.out.println("Error! You placed it too close to another one. Try again:\n");
                            checkError = true;
                            break;
                        } else {
                            battleField[i][columOne] = 'O';

                        }

                    }
                }

                if (checkError) {

                    continue;
                }

                break;
            }
        }
    }


    public static boolean checkShip(char[][] battleField, int row, int col, int turn, boolean rowEquals, int count, int start) {


        if (row - 1 >= 0) {
            if (col - 1 > 0) {
                if (battleField[row - 1][col - 1] == 'O') {
                    return true;
                }
                if (rowEquals) {
                    if (battleField[row - 1][col] == 'O') {
                        return true;
                    }
                }
                if (!rowEquals && count == 1) {
                    if (battleField[row - 1][col] == 'O') {
                        return true;
                    }
                }
                if (col < battleField.length - 1) {
                    if (battleField[row - 1][col + 1] == 'O') {
                        return true;
                    }
                }
            }
        }

        if (rowEquals && count == 1) {
            if (col - 1 >= 0) {
                if (battleField[row][col - 1] == 'O') {
                    return true;
                }
            }
        }

        if (!rowEquals) {
            if (col - 1 >= 0) {
                if (battleField[row][col - 1] == 'O') {
                    return true;
                }
            }
        }

        if (rowEquals && count == start + turn) {
            if (col < battleField[row].length - 1) {
                if (battleField[row][col + 1] == 'O') {
                    return true;
                }


            }
        }

        if (!rowEquals) {
            if (col < battleField.length - 1) {
                if (battleField[row][col + 1] == '0') {
                    return true;
                }
            }
        }


        if (row < battleField.length - 1) {
            if (col - 1 > 0) {
                if (battleField[row + 1][col - 1] == 'O') {
                    return true;
                }
            }

            if (rowEquals) {
                if (battleField[row + 1][col] == 'O') {
                    return true;
                }
            }
            if (!rowEquals && count == start + turn) {
                if (battleField[row + 1][col] == 'O') {
                    return true;
                }
            }

            if (col < battleField[row].length - 1) {
                if (battleField[row + 1][col + 1] == 'O') {
                    return true;
                }
            }
        }
        return false;
    }


    public static void printGrid(char[][] grid) {

        char[] alphabet = "ABCDEFGHIJ".toCharArray();

        for (int i = 0; i < rows + 1; i++) {

            for (int j = 0; j < cols + 1; j++) {
                if (i == 0) {
                    if (j == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(j + " ");
                    }
                } else if (j == 0) {
                    System.out.print(alphabet[i - 1] + " ");

                } else {
                    System.out.print(grid[i - 1][j - 1] + " ");
                }
            }

            System.out.println();
        }


    }


    public static void createGrid(char[][] grid) {
        for (int i = 0; i < rows; i++) {
            for (int y = 0; y < cols; y++) {
                grid[i][y] = '~';
            }
        }
    }


}
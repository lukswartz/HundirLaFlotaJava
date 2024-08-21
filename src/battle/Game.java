package battle;
import java.util.Scanner;

public class Game {

    final String[] flota = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer" };
    final int[] longBarcos = {5, 4, 3, 3, 2};
    final String SEPARATOR_BOARD = "-".repeat(22);
    Scanner sc = new Scanner(System.in);
    Board b1, b2;
    boolean playerOne;
    String playerString;
    boolean gamefinished = false;

    public Game() {
        b1 = new Board();
        b2 = new Board();
        playerOne = true;
        playerString = playerOne ? "Player 1" : "Player 2";
    }


    public void initGame() {

        setBoards();

        gameLoop();

    }

    public void setBoards() {
        System.out.println( playerString + ", place your ships on the game field");
        b1.printBoard();
        placeShips(b1);
        finishTurn();
        System.out.println( playerString + ", place your ships on the game field");
        b2.printBoard();
        placeShips(b2);
        finishTurn();
    }

    public void finishTurn() {

        if (!gamefinished) {
            playerOne = !playerOne;
            playerString = playerOne ? "Player 1" : "Player 2";
            System.out.println("Press Enter and pass the move to another player");
            String input = sc.nextLine();
        }


    }
    private void gameLoop() {

        while(!gamefinished) {

            makeShot();
            checkWin();
            finishTurn();

        }
    }

    private void checkWin() {
        Board current = playerOne ? b2 : b1;

        if (current.getBarcosHundidos() == 5) {
            System.out.println("You sank the last ship. You won. Congratulations!");
            gamefinished = true;
        }

    }

    private void makeShot() {

        Board currentPlayerBoard = playerOne ? b1 : b2;
        Board oponentBoard = playerOne ? b2 : b1;

        while(true) {

            oponentBoard.printBoardWithFog();
            System.out.println(SEPARATOR_BOARD);
            currentPlayerBoard.printBoard();
            System.out.println(playerString + ", it's your turn:");



            String input = sc.nextLine().toUpperCase();
            if (input.length() < 2 || input.length() > 3) {
                System.out.println("Error: wrong input");
            } else if (input.charAt(0) < 'A' || input.charAt(0) > 'J') {
                System.out.println("Error: wrong row");
            } else if (!Character.isDigit(input.charAt(1)) ||
                        input.length() == 3 && Character.isDigit(input.charAt(1)) && !Character.isDigit(input.charAt(2))) {
                System.out.println("Error: column must be a number");
            } else if (input.length() == 3 && input.charAt(2) != '0') {
                System.out.println("Error: column must be in range 1-10");
            }else {
                oponentBoard.makeShot(input.charAt(0), Integer.parseInt(input.substring(1)));
                break;
            }
        }

    }
    private void placeShips(Board b) {

        for (int i = 0; i < flota.length; i++) {

            while(true) {

                System.out.println("Enter the coordinates of the " + flota[i] + " (" + longBarcos[i] + " cells): ");
                String input = sc.nextLine();
                String[] cInput = new String[2];

                if (!validInput(input)) {
                    System.out.println("Error! : wrong format input");

                } else {

                    cInput = input.toUpperCase().split(" ");

                    int beginCol = Integer.parseInt(cInput[0].substring(1));
                    int endCol = Integer.parseInt(cInput[1].substring(1));

                    Cell begin = Cell.createCell(cInput[0].charAt(0), beginCol);
                    Cell end = Cell.createCell(cInput[1].charAt(0), endCol);

                    if (begin != null && end != null) {

                        Ship s = Ship.createShip(flota[i], longBarcos[i], begin, end);

                        if (s != null) {
                            if (b.addShip(s)) {
                                b.printBoard();
                                break;
                            } else {
                                System.out.println("Error: can't place the ship adjacent other ships");
                                System.out.println();
                            }
                        }

                    }
                }



            }
        }
    }


    private static boolean validInput(String input) {

        boolean result = true;
        if (!(input.length() > 4 && input.length() < 8) ) {

            result = false;
        } else if(input.charAt(2) != ' ' && input.charAt(3) != ' ') {
            result = false;
        } else if(!Character.isDigit(input.charAt(1))) {
            result = false;
        } else if (!Character.isDigit(input.charAt(4))  && !Character.isDigit(input.charAt(5))) {
            result = false;
        }

        return result;

    }


}
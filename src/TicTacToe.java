import java.util.*;

public class TicTacToe {
    private static final ArrayList<Integer> player1Positions = new ArrayList<>();
    private static final ArrayList<Integer> player2Positions = new ArrayList<>();
    private static final ArrayList<Integer> cpuPositions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static String player1Name = "Player";
    private static String player2Name;

    private static int playerPosition;
    private static int cpuPosition;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("WELCOME TO TIC/TAC/TOE in console :D");
        System.out.print("1 Player or 2 Players? (1/2): ");
        int gameMode = scanner.nextInt();
        char[][] gameBoard =
                        {{' ', '|', ' ', '|', ' '},
                        {'-', '+', '-', '+', '-'},
                        {' ', '|', ' ', '|', ' '},
                        {'-', '+', '-', '+', '-'},
                        {' ', '|', ' ', '|', ' '}};
        if (gameMode == 2) {
            playersNamesInput();
            printGameBoardWithNumbers();
            while (true) {
                playerTurn(player1Name, player1Positions, player2Positions, gameBoard);
                if (checkedCross())
                    break;

                playerTurn(player2Name, player2Positions, player1Positions, gameBoard);
                if (checkedCross())
                    break;
            }
        }else { //CPU Mode
            printGameBoardWithNumbers();
            while (true) {
                playerTurn(player1Name, player1Positions, cpuPositions, gameBoard);
                if (checkedCross())
                    break;

                cpuTurn(player1Positions, cpuPositions, gameBoard);
                if (checkedCross())
                    break;
            }
        }
    }

    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row: gameBoard) {
            for (char c: row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void printGameBoardWithNumbers() {
        char[][] gameBoard =
                        {{'1', '|', '2', '|', '3'},
                        {'-', '+', '-', '+', '-'},
                        {'4', '|', '5', '|', '6'},
                        {'-', '+', '-', '+', '-'},
                        {'7', '|', '8', '|', '9'}};

        for (char[] row: gameBoard) {
            for (char c: row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void providedPosition(int pos, char[][] gameBoard, String player) {
        char sign;
        if (player.equals(player1Name))
            sign = 'X';
        else
            sign = '0';

        switch (pos) {
            case 1 -> gameBoard[0][0] = sign;
            case 2 -> gameBoard[0][2] = sign;
            case 3 -> gameBoard[0][4] = sign;
            case 4 -> gameBoard[2][0] = sign;
            case 5 -> gameBoard[2][2] = sign;
            case 6 -> gameBoard[2][4] = sign;
            case 7 -> gameBoard[4][0] = sign;
            case 8 -> gameBoard[4][2] = sign;
            case 9 -> gameBoard[4][4] = sign;
        }
    }

    public static boolean checkedCross() {
        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> bottomRow = Arrays.asList(7, 8, 9);
        List<Integer> leftColumn = Arrays.asList(1, 4, 7);
        List<Integer> rightColumn = Arrays.asList(3, 6, 9);
        List<Integer> downCross = Arrays.asList(1, 5, 9);
        List<Integer> upCross = Arrays.asList(7, 5, 3);

        List<List> winning = new ArrayList<>();
        winning.add(topRow);
        winning.add(bottomRow);
        winning.add(leftColumn);
        winning.add(rightColumn);
        winning.add(downCross);
        winning.add(upCross);

        for (List l: winning) {
            if (player1Positions.containsAll(l)) {
                System.out.println(player1Name +" you won!!");
                return true;
            } else if (player2Positions.containsAll(l)) {
                System.out.println(player2Name +" you won!!");
                return true;
            } else if (cpuPositions.containsAll(l)) {
                System.out.println("CPU won!!");
                return true;
            } else if ((player1Positions.size() + player2Positions.size() == 9 || player1Positions.size() + cpuPositions.size() == 9) &&
                    !player1Positions.containsAll(l) && !player2Positions.containsAll(l) && !cpuPositions.containsAll(l)) {
                System.out.println("It's a TIE!!");
                return true;
            }
        }
        return false;
    }



    public static void playerTurn(String playerName, ArrayList<Integer> playerPositions,
                                  ArrayList<Integer> playerOrCpuPositions, char[][] gameBoard) {
        System.out.println(playerName + " your turn.");
        System.out.print("Enter your placement (1-9): ");
        playerPosition = scanner.nextInt();
        positionTaken(playerPositions, playerOrCpuPositions);
        playerPositions.add(playerPosition);
        providedPosition(playerPosition, gameBoard, playerName);
        printGameBoard(gameBoard);
    }

    public static void positionTaken(ArrayList<Integer> playerPositions,
                                     ArrayList<Integer> playerOrCpuPositions) {
        while (playerPositions.contains(playerPosition) || playerOrCpuPositions.contains(playerPosition)) {
            System.out.println("This position is taken!!");
            System.out.print("Enter correct placement: ");
            playerPosition = scanner.nextInt();
        }
    }

    public static void cpuTurn(ArrayList<Integer> playerPositions,
                               ArrayList<Integer> playerOrCpuPositions, char[][] gameBoard) throws InterruptedException {
        System.out.println("CPU's turn. CPU's thinking...");
        Thread.sleep(2500);
        Random random = new Random();
        cpuPosition = random.nextInt(9) + 1;
        System.out.print("CPU's placement is: " + cpuPosition + "\n");
        positionGeneration(playerPositions, playerOrCpuPositions);
        cpuPositions.add(cpuPosition);
        providedPosition(cpuPosition, gameBoard, "CPU");
        printGameBoard(gameBoard);
    }

    public static void positionGeneration(ArrayList<Integer> playerPositions,
                                     ArrayList<Integer> playerOrCpuPositions) {
        Random random = new Random();
        while (playerPositions.contains(cpuPosition) || playerOrCpuPositions.contains(cpuPosition)) {
            System.out.println("Generated position is taken.");
            cpuPosition = random.nextInt(9) + 1;
            System.out.print("Generating new placement: " + cpuPosition + "\n");
        }
    }

    public static void playersNamesInput() {
        System.out.print("Type player1 name: ");
        player1Name = scanner.next();
        System.out.print("Type player2 name: ");
        player2Name = scanner.next();
    }
}
package tictactoe;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int drawMessage = 0;
        int turn = 1;
        char a[] = new char[9];
        for (int i = 0; i < 9; i++) {
            a[i] = ' ';
        }
        int counterOfEnd = 0;
        boolean canPlay = true;
        Scanner scanner = new Scanner(System.in);
        Grid(a);
        System.out.print("Enter the coordinates: ");


        while (canPlay) {
            counterOfEnd = 0;


            char point;

            if (turn % 2 == 1) {
                point = 'X';
            } else point = 'O';


            String nextTurn = scanner.nextLine();
            String[] nt1 = nextTurn.split(" ");

            String nt2 = nt1[0] + nt1[1];


            int playerChoose;

            try {
                playerChoose = Integer.parseInt(nt2);
                if (ChooseValue(playerChoose) == 999) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    turn--;

                } else if (a[ChooseValue(playerChoose)] == ' ') {
                    a[ChooseValue(playerChoose)] = point;// ------------------------------------


                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                    turn--;
                }


            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                turn--;
            }


            Grid(a);
            String textResult;
            if ((a[0] == a[1] && a[0] == a[2] && a[0] != ' ')) {
                textResult = a[0] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[3] == a[4] && a[3] == a[5] && a[3] != ' ')) {
                textResult = a[3] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[6] == a[7] && a[6] == a[8] && a[6] != ' ')) {
                textResult = a[6] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[0] == a[3] && a[0] == a[6] && a[0] != ' ')) {
                textResult = a[0] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[1] == a[4] && a[1] == a[7] && a[1] != ' ')) {
                textResult = a[1] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[2] == a[5] && a[2] == a[8] && a[2] != ' ')) {
                textResult = a[2] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[0] == a[4] && a[0] == a[8] && a[0] != ' ')) {
                textResult = a[0] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            } else if ((a[2] == a[4] && a[2] == a[6] && a[2] != ' ')) {
                textResult = a[2] + " wins";
                System.out.println(textResult);
                drawMessage = 1;
                canPlay = false;
            }
            turn++;

            for (int i = 0; i < a.length; i++) {
                if (a[i] != ' ') {

                    counterOfEnd++;
                    if (counterOfEnd == 9) {
                        canPlay = false;
                        if (drawMessage != 1){
                            System.out.println("Draw");}
                        break;

                    }
                }
            }
            if (canPlay) {
                System.out.print("Enter the coordinates: ");
            }


        }


    }

    public static void Grid(char[] a) {
        System.out.println("---------");
        System.out.println("| " + a[0] + " " + a[1] + " " + a[2] + " |");
        System.out.println("| " + a[3] + " " + a[4] + " " + a[5] + " |");
        System.out.println("| " + a[6] + " " + a[7] + " " + a[8] + " |");
        System.out.println("---------");
    }

    public static int ChooseValue(int value) {
        int a = 0;

        if (value == 11) {
            a = 0;
        } else if (value == 12) {
            a = 1;
        } else if (value == 13) {
            a = 2;
        } else if (value == 21) {
            a = 3;
        } else if (value == 22) {
            a = 4;
        } else if (value == 23) {
            a = 5;
        } else if (value == 31) {
            a = 6;
        } else if (value == 32) {
            a = 7;
        } else if (value == 33) {
            a = 8;
        } else {
            a = 999;
        }
        return a;
    }
}

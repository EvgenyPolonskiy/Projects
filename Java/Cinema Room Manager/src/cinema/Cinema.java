package cinema;

import java.util.ArrayList;
import java.util.Scanner;

public class Cinema {

    public static ArrayList<ArrayList> cinema = new ArrayList<>();
    public static boolean canWork = true;
    public static int soldTickets = 0;
    public static int soldTicketsCash = 0;
    public static int totalSeats = 0;
    public static int totalCanGetPayOff = 0;
    public static int rows;
    public static int seatsInRow;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInRow = scanner.nextInt();
        totalSeats = rows * seatsInRow;
        buildCinema(rows, seatsInRow);

        while (canWork) {
            System.out.println();
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int inNumber = scanner.nextInt();
            switch (inNumber) {
                case 1:
                    printCinema();
                    System.out.println();
                    break;
                case 2:
                    buyTicket(scanner);

                    break;
                case 3:
                    statistics();
                    break;

                case 0:
                    exit();
                    break;
            }
        }
    }

    public static void statistics() {
        System.out.println();
        double percent = (double) soldTickets / totalSeats;
        String resPercent = String.format("%.2f", percent * 100);
        System.out.println("Number of purchased tickets: " + soldTickets);
        System.out.println("Percentage: " + resPercent + "%");
        System.out.println("Current income: $" + soldTicketsCash);
        System.out.println("Total income: $" + totalCanGetPayOff);
    }

    public static void buildCinema(int rows, int seatsInRow) {
        for (int i = 0; i < rows + 1; i++) {
            cinema.add(new ArrayList<String>());
            if (i == 0) {
                for (int j = 0; j < seatsInRow + 1; j++) {
                    if (j == 0) {
                        cinema.get(i).add(" ");
                    } else cinema.get(i).add(j);
                }
            } else {
                for (int j = 0; j < seatsInRow; j++) {
                    if (j == 0) {
                        cinema.get(i).add(i);
                    }
                    cinema.get(i).add("S");
                }
            }
        }

        int totalSeats = rows * seatsInRow;
        int ticketPriseFront = 10;
        int ticketPriseBack = 8;
        int totalPayOff;

        if (totalSeats < 60) {
            totalPayOff = ticketPriseFront * totalSeats;
        } else {
            int frontPart;
            int backPart;
            if (rows % 2 == 1) {
                frontPart = rows / 2;
                backPart = rows / 2 + 1;
                totalPayOff = frontPart * seatsInRow * ticketPriseFront + backPart * seatsInRow * ticketPriseBack;
            } else {
                frontPart = rows / 2;
                backPart = rows / 2;
                totalPayOff = frontPart * seatsInRow * ticketPriseFront + backPart * seatsInRow * ticketPriseBack;
            }
        }

        totalCanGetPayOff = totalPayOff;


    }

    public static void printCinema() {
        System.out.println("Cinema:");
        for (int i = 0; i < cinema.size(); i++) {
            for (int j = 0; j < cinema.get(i).size(); j++) {
                System.out.print(cinema.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public static int checkSeat(int chosenRow, int chosenSeat) {
        int a;

        if (chosenRow > rows || chosenSeat > seatsInRow) {
            a = 1;
        } else if (cinema.get(chosenRow).get(chosenSeat).equals("B")) {
            a = 2;
        } else
            a = 0;
        return a;
    }

    public static void buyTicket(Scanner scanner) {
        boolean realSeat = true;

        while (realSeat) {
            System.out.println("Enter a row number:");
            int chosenRow = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int chosenSeat = scanner.nextInt();

            switch (checkSeat(chosenRow, chosenSeat)) {
                case 1:
                    System.out.println("Wrong input!");
                    break;
                case 2:
                    System.out.println("That ticket has already been purchased!");
                    break;
                case 0:
                    ticketPrise(rows, seatsInRow, chosenRow, chosenSeat);
                    realSeat = false;
                    break;
            }

        }
    }


    public static void ticketPrise(int rows, int seatsInRow, int chosenRow, int chosenSeat) {

        int totalSeats;
        int ticketPrise;
        int frontPart = rows / 2;
        totalSeats = rows * seatsInRow;


        if (chosenRow > frontPart) {
            ticketPrise = 8;
        } else {
            ticketPrise = 10;
        }
        if (totalSeats < 60) {
            ticketPrise = 10;
        }


        soldTickets++;
        cinema.get(chosenRow).set(chosenSeat, "B");
        soldTicketsCash = soldTicketsCash + ticketPrise;
        System.out.println();
        System.out.println("Ticket price: $" + ticketPrise);

    }

    public static void exit() {
        canWork = false;
    }


}
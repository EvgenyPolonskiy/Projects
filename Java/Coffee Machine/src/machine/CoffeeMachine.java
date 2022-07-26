package machine;

import java.util.Scanner;

public class CoffeeMachine {
    static int cash = 550;
    static int water = 400;
    static int milk = 540;
    static int coffee = 120;
    static int disposableCups = 9;
    static boolean canWork = true;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        remaining();


        while (canWork) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            String action = scanner.nextLine();

            switch (action) {
                case "buy":
                    System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                    String a = scanner.nextLine();
                    buy(a);
                    break;
                case "fill":
                    System.out.println("Write how many ml of water you want to add: ");
                    int addWater = scanner.nextInt();
                    System.out.println("Write how many ml of milk you want to add: ");
                    int addMilk = scanner.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add: ");
                    int addCoffee = scanner.nextInt();
                    System.out.println("Write how many disposable cups of coffee you want to add: ");
                    int addDisposableCups = scanner.nextInt();
                    fill(addWater, addMilk, addCoffee, addDisposableCups);
                    break;
                case "take":
                    System.out.println("I gave you $" + cash);
                    take();
                    break;
                case "remaining":
                    remaining();
                    break;
                case "exit":
                    exit();
                    break;
            }

        }
    }

    public static void remaining() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffee + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + cash + " of money");
    }

    public static void exit() {
        canWork = false;
    }

    public static void fill(int a, int b, int c, int d) {
        water = water + a;
        milk = milk + b;
        coffee = coffee + c;
        disposableCups = disposableCups + d;
    }

    public static void take() {
        cash = 0;
        System.out.println();
    }


    public static void buy(String a) {
        switch (a) {
            case "1":
                water = water - 250;
                coffee = coffee - 16;
                cash = cash + 4;
                disposableCups--;
                if (water < 0) {
                    System.out.println("Sorry, not enough water!");
                    water = water + 250;
                    coffee = coffee + 16;
                    cash = cash - 4;
                    disposableCups++;
                } else if (milk < 0) {
                    System.out.println("Sorry, not enough milk!");
                    water = water + 250;
                    coffee = coffee + 16;
                    cash = cash - 4;
                    disposableCups++;
                } else if (coffee < 0) {
                    System.out.println("Sorry, not enough coffee!");
                    water = water + 250;
                    coffee = coffee + 16;
                    cash = cash - 4;
                    disposableCups++;
                } else if (disposableCups < 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                    water = water + 250;
                    coffee = coffee + 16;
                    cash = cash - 4;
                    disposableCups++;
                } else
                    System.out.println("I have enough resources, making you a coffee!");
                break;
            case "2":
                water = water - 350;
                milk = milk - 75;
                coffee = coffee - 20;
                cash = cash + 7;
                disposableCups--;
                if (water < 0) {
                    System.out.println("Sorry, not enough water!");
                    water = water + 350;
                    milk = milk + 75;
                    coffee = coffee + 20;
                    cash = cash - 7;
                    disposableCups++;
                } else if (milk < 0) {
                    System.out.println("Sorry, not enough milk!");
                    water = water + 350;
                    milk = milk + 75;
                    coffee = coffee + 20;
                    cash = cash - 7;
                    disposableCups++;
                } else if (coffee < 0) {
                    System.out.println("Sorry, not enough coffee!");
                    water = water + 350;
                    milk = milk + 75;
                    coffee = coffee + 20;
                    cash = cash - 7;
                    disposableCups++;
                } else if (disposableCups < 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                    water = water + 350;
                    milk = milk + 75;
                    coffee = coffee + 20;
                    cash = cash - 7;
                    disposableCups++;
                } else
                    System.out.println("I have enough resources, making you a coffee!");
                break;
            case "3":
                water = water - 200;
                milk = milk - 100;
                coffee = coffee - 12;
                cash = cash + 6;
                disposableCups--;
                if (water < 0) {
                    System.out.println("Sorry, not enough water!");
                    water = water + 200;
                    milk = milk + 100;
                    coffee = coffee + 12;
                    cash = cash - 6;
                    disposableCups++;
                } else if (milk < 0) {
                    System.out.println("Sorry, not enough milk!");
                    water = water + 200;
                    milk = milk + 100;
                    coffee = coffee + 12;
                    cash = cash - 6;
                    disposableCups++;
                } else if (coffee < 0) {
                    System.out.println("Sorry, not enough coffee!");
                    water = water + 200;
                    milk = milk + 100;
                    coffee = coffee + 12;
                    cash = cash - 6;
                    disposableCups++;
                } else if (disposableCups < 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                    water = water + 200;
                    milk = milk + 100;
                    coffee = coffee + 12;
                    cash = cash - 6;
                    disposableCups++;
                } else
                    System.out.println("I have enough resources, making you a coffee!");

                break;
            case "back":
                break;
        }

    }


}

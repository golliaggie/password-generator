package passwordGenerator;

import java.util.Scanner;

public class App {


    public static void main(String[] args) {
        System.out.println("--------------------------------------------");
        System.out.println("Welcome to Password Generator by Golliaggie!");
        System.out.println("--------------------------------------------");

        Scanner scan = new Scanner(System.in);

        UserInterface ui = new UserInterface(scan);

        ui.initiateSession();
    }
}

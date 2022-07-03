package passwordGenerator;

import java.util.Scanner;

public class UserInterface {

    public Scanner scan;
    private boolean isActiveSession;

    public UserInterface(Scanner scan) {
        this.scan = scan;
        this.isActiveSession = true;
    }

    public void openMainMenu() {
        System.out.println("Please select from menu below then hit ENTER:");
        System.out.println("[1] Generate a password");
        System.out.println("[2] Check password strength");
        System.out.println("[3] Exit");

        String chosenMenu = scan.nextLine();

        switch (chosenMenu) {
            case "3":
                isActiveSession = false;
                printClosingMessage();
                break;
            case "1":
                generatePassword();
                break;
            case "2":
                checkPasswordStrength();
                break;
            default:
                printErrorInvalidInput();
        }
    }

    public void printErrorInvalidInput() {
        System.out.println("Invalid input!");
    }

    public void printClosingMessage() {
        System.out.println("Closing program.. Thank you for using our service! See you :)");
    }

    public void generatePassword() {
        System.out.println("Password length:");
        String passwordLenInput = scan.nextLine();
        try{
            int passwordLen = Integer.parseInt(passwordLenInput);

            System.out.println("Contains number (1-9)? [y/n]");
            String containNumberInput = scan.nextLine();
            boolean containNumber = false;
            switch(containNumberInput) {
                case "y":
                    containNumber = true;
                    break;
                case "n":
                    break;
                default:
                    throw new InvalidInputException("Invalid input!");
            }

            System.out.println("Contains lowercase letter (a-z)? [y/n]");
            String containLowercaseInput = scan.nextLine();
            boolean containLowercase = false;
            switch (containLowercaseInput) {
                case "y":
                    containLowercase = true;
                    break;
                case "n":
                    break;
                default:
                    throw new InvalidInputException("Invalid input!");
            }

            System.out.println("Contains uppercase letter (A-Z)? [y/n]");
            String containUppercaseInput = scan.nextLine();
            boolean containUppercase = false;
            switch (containUppercaseInput) {
                case "y":
                    containUppercase = true;
                    break;
                case "n":
                    break;
                default:
                    throw new InvalidInputException("Invalid input!");
            }

            System.out.println("Contains special character (~!?@$..)? [y/n]");
            String containSpecCharInput = scan.nextLine();
            boolean containSpecChar = false;
            switch (containSpecCharInput) {
                case "y":
                    containSpecChar = true;
                    break;
                case "n":
                    break;
                default:
                    throw new InvalidInputException("Invalid input!");
            }

            System.out.println("We got everything! Generating your password..");
            Password generatedPassword = new Password(containNumber,containLowercase,containUppercase,containSpecChar,passwordLen);
            System.out.printf("Generated password: %s %n %n",generatedPassword.getValue());

        }

        catch (Exception e) {
            printErrorInvalidInput();
            openMainMenu();
        }

    }

    public void checkPasswordStrength() {
        System.out.println("Please input password that you want to check: ");
        String passwordToCheck = scan.nextLine();
        Password toCheck =  new Password(passwordToCheck);
        System.out.printf("Entropy of your password is %f %n",toCheck.getStrength());
        toCheck.passwordStrengthReport();
        System.out.println();
    }

    public void initiateSession() {
        while(isActiveSession) {
            openMainMenu();
        }
    }
}

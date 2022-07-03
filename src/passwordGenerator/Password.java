package passwordGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class Password {
    public static List<Integer> numberList = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
    public static List<String> lowercaseList = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
    public static List<String> uppercaseList = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
    public static List<String> specCharList = Arrays.asList("!","#","$","%","&","'","(",")","*","+",",","-",".","/",":",";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~");

    private final List<Integer> combination;
    private final int length;
    private final String value;
    private final double strength;

    public Password(String password) {
        boolean containNumber = Pattern.compile("[0-9]+").matcher(password).find();
        boolean containLowercase = Pattern.compile("[a-z]+").matcher(password).find();
        boolean containUppercase = Pattern.compile("[A-Z]+").matcher(password).find();
        boolean containSpecChar = Pattern.compile("[!\"#$%&'()*+,-\\./:;<=>?@\\[\\]^_`{|}~\\\\]+").matcher(password).find();

        this.combination = passwordCombinationList(containNumber,containLowercase,containUppercase,containSpecChar);
        this.length = password.length();
        this.value = password;
        this.strength = calculateStrength();
    }

    public Password(boolean containNumber, boolean containLowercase, boolean containUppercase, boolean containSpecChar, int length) {
        this.combination = passwordCombinationList(containNumber,containLowercase,containUppercase,containSpecChar);
        this.length = length;
        this.value = generatePasswordValue();
        this.strength = calculateStrength();

    }

    public List<Integer> passwordCombinationList(boolean containNumber, boolean containLowercase, boolean containUppercase, boolean containSpecChar) {
        ArrayList<Integer> combination = new ArrayList<>();
        if(containNumber) {
            combination.add(0);
        }
        if(containLowercase) {
            combination.add(1);
        }
        if(containUppercase) {
            combination.add(2);
        }
        if(containSpecChar) {
            combination.add(3);
        }
        return combination;
    }

    public String generatePasswordValue() {
        StringBuilder passwordBuilder = new StringBuilder();
        Random randomizer = new Random();

        for(int i = 1; i <= length; i++) {

            int componentCode = randomizer.nextInt(4);
            while(!this.combination.contains(componentCode)) {
                componentCode = randomizer.nextInt(4);
            }
            switch (componentCode) {
                case 0:
                    int chosenNumber = randomizer.nextInt(numberList.size());
                    passwordBuilder.append(numberList.get(chosenNumber));
                    break;
                case 1:
                    int chosenLowercase = randomizer.nextInt(lowercaseList.size());
                    passwordBuilder.append(lowercaseList.get(chosenLowercase));
                    break;
                case 2:
                    int chosenUppercase = randomizer.nextInt(uppercaseList.size());
                    passwordBuilder.append(uppercaseList.get(chosenUppercase));
                    break;
                case 3:
                    int chosenSpecChar = randomizer.nextInt(specCharList.size());
                    passwordBuilder.append(specCharList.get(chosenSpecChar));
            }
        }

        return passwordBuilder.toString();
    }

    public double calculateStrength() {
        // Password strength is calculated based on it's entropy, formula: E = L * log2(R)
        // R is pool size. If contain lower case then pool size = 26, also contain uppercase then pool size 26+26 = 52
        // L is length of the password
        int poolSize = 0;
        if(this.combination.contains(0)) {
            poolSize += 10;
        }
        if(this.combination.contains(1)) {
            poolSize += 26;
        }
        if(this.combination.contains(2)) {
            poolSize += 26;
        }
        if(this.combination.contains(3)) {
            poolSize +=31;
        }

        double entropy = length * (Math.log(poolSize)/Math.log(2));

        return entropy;
    }

    public void passwordStrengthReport() {
        double entropyOfCheckedPassword = getStrength();
        if(entropyOfCheckedPassword > 60) {
            System.out.println("Your password is very strong! Good one :) ");
        } else if (entropyOfCheckedPassword >= 50) {
            System.out.println("Your password is strong! Keep it up :) ");
        } else if (entropyOfCheckedPassword >= 30) {
            System.out.println("Your password is reasonable. There's still room for improvement! ");
        } else if (entropyOfCheckedPassword >= 20){
            System.out.println("Your password is weak :( Maybe you can consider to change it! ");
        } else {
            System.out.println("Your password is extremely weak! :( Change it as soon as possible!");
        }
    }

    public String getValue() {
        return this.value;
    }

    public double getStrength() {
        return this.strength;
    }
}

package uz.pdp.appcommunicationcompany.entity;


import java.util.Random;

public class GenUsername {
    public static void main(String[] args) {

        // Create random generator
        Random generator = new Random();
        int randomNumber = generator.nextInt(9000) + 10;

        // Generate username
        String first = "Khamza";
        String last = "Kuranbayev";
        String username = (first.charAt(new Random().nextInt(first.length())) + "$").toUpperCase() + last.substring(0, last.length() - 2) + randomNumber;
        System.out.println(username);
    }
}

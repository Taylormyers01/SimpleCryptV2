package rocks.zipcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Character.*;

public class ROT13  {
    private static final List<Character> alphabet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l','m', 'n','o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    static int difference;
    boolean encrypted = false;

    public ROT13(Character cs, Character cf) {
        difference = alphabet.indexOf(Character.toLowerCase(cf)) - alphabet.indexOf(Character.toLowerCase(cs));

    }

    public ROT13() {
        difference = 13;
    }


    public String crypt(String text) throws UnsupportedOperationException {
        if(encrypted){
            return decrypt(text);
        }
        return encrypt(text);

    }

    public String encrypt(String text) {
        encrypted = true;
        return crypt(text, difference);

    }

    public String decrypt(String text) {
        encrypted = false;
       return crypt(text, 26-difference);
    }

    public static String rotate(String s, Character c) {
        return "";
    }

    public String crypt(String text, int diff){
        StringBuilder sb = new StringBuilder();
        char[] letters = text.toCharArray();
        int position = 0;
        for(char let: letters){
            if(alphabet.contains(Character.toLowerCase(let))){
                if (Character.isUpperCase(let)) {
                    position = (alphabet.indexOf(Character.toLowerCase(let)) + diff) % 26;
                    sb.append(Character.toUpperCase(alphabet.get(position))); //
                } else {
                    position = (alphabet.indexOf(let) + diff) % 26;
                    sb.append(alphabet.get(position)); //+ " " + position + "\n"
                }
            }else{
                sb.append(let);
            }

        }
        //System.out.println(sb);
        return sb.toString();

    }

    private String encryptFile(String fileName) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File(fileName));
        PrintWriter fileOut = new PrintWriter("sonnet" + difference + ".enc");
        while(fileIn.hasNext()){
            fileOut.println(encrypt(fileIn.nextLine())); //gets next line, encrypts it
        }
        fileOut.close();
        fileIn.close();
        return "sonnet" + difference + ".enc";
    }
    private void decryptFile(String fileName) throws FileNotFoundException {
        Scanner fileIn = new Scanner(new File(fileName));
        PrintWriter fileOut = new PrintWriter("sonnet" + difference + ".txt");
        while(fileIn.hasNext()){
            fileOut.println((decrypt(fileIn.nextLine()))); //gets next line, encrypts it
        }
        fileOut.close();
        fileIn.close();
    }
    public static void main(String[] args) throws FileNotFoundException {
        ROT13 rot13 = new ROT13('a', 'i');
        String fileName = rot13.encryptFile("sonnet.txt");
        rot13.decryptFile(fileName);
    }
}

package encryptdecrypt;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.valueOf;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = null;
        {
            file = new File("output.txt");
            if (file.exists()) {
                file.delete();
            }
        }

        int offset = 0;
        String typeOfOperation = "";
        String shiftTypeIncome = "";
        String textToChange = "";
        String output = "";
        String result = "";
        int resultTrigger = 0;

// mode
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-mode")) {
                if (args[i + 1].equals("enc")) {
                    typeOfOperation = "enc";
                } else {
                    typeOfOperation = "dec";
                }
                break;
            } else if (!args[i].equals("-mode")) {
                typeOfOperation = "enc";
            }
        }

// alg
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-alg")) {
                if (args[i + 1].equals("shift")) {
                    shiftTypeIncome = "shift";
                } else {
                    shiftTypeIncome = "unicode";
                }
                break;

            } else {
                shiftTypeIncome = "shift";
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-key")) {
                offset = Integer.parseInt(args[i + 1]);
                break;
            } else {
                offset = 0;
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-data")) {
                textToChange = args[i + 1];
                break;
            } else if (args[i].equals("-in")) {
                textToChange = new String(Files.readAllBytes(Paths.get(args[i + 1])));

                //textToChange = args[i + 1];
                break;
            } else {
                textToChange = "";
            }
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-out")) {
                output = args[i + 1];
                resultTrigger = 1;
                break;
            } else {
                resultTrigger = 0;
            }
        }

        switch (shiftTypeIncome) {
            case "shift": {
                switch (typeOfOperation) {
                    case "enc": {
                        result = encryptionShift(textToChange, offset);
                        break;
                    }
                    case "dec": {
                        result = decryptionShift(textToChange, offset);
                        break;
                    }
                }
                break;
            }
            case "unicode": {
                switch (typeOfOperation) {
                    case "enc": {
                        result = encryptionUnicode(textToChange, offset);
                        break;
                    }
                    case "dec": {
                        result = decryptionUnicode(textToChange, offset);
                        break;
                    }
                }
            }
            break;
        }

        if (resultTrigger == 0) {
            System.out.println(result);
        } else {

            file = new File(output);
            FileWriter writer = new FileWriter(file, true);
            writer.write(result);
            writer.close();
        }
    }
    public static String encryptionShift(String in, int key) {

        char[] text = in.toCharArray();
        char[] newText = new char[in.length()];
        key = key % 26;
        for (int i = 0; i < newText.length; i++) {
            if (64 < text[i] && text[i] < 91) {
                if (text[i] + key > 90) {
                    newText[i] = (char) (text[i] + key - 26);
                } else if (text[i] + key < 65) {
                    newText[i] = (char) (text[i] - key + 26);
                } else {
                    newText[i] = (char) (text[i] + key);
                }
            } else if (96 < text[i] && text[i] < 123) {
                if (text[i] + key > 122) {
                    newText[i] = (char) (text[i] + key - 26);
                } else if (text[i] + key < 97) {
                    newText[i] = (char) (text[i] - key + 26);
                } else {
                    newText[i] = (char) (text[i] + key);
                }
            } else {
                newText[i] = text[i];
            }
        }
        return valueOf(newText);
    }

    public static String decryptionShift(String in, int key) {
        char[] text = in.toCharArray();
        char[] newText = new char[in.length()];
        int offset = 26 - key % 26;
        key = key % 26;
        for (int i = 0; i < newText.length; i++) {
            if (65 < text[i] && text[i] < 91) {
                if (text[i] + offset > 90) {
                    newText[i] = (char) (text[i] + offset - 26);
                } else if (text[i] + key < 65) {
                    newText[i] = (char) (text[i] - offset + 26);
                } else {
                    newText[i] = (char) (text[i] + offset);
                }
            } else if (97 < text[i] && text[i] < 123) {
                if (text[i] + offset > 122) {
                    newText[i] = (char) (text[i] + offset - 26);
                } else if (text[i] + offset < 97) {
                    newText[i] = (char) (text[i] - offset + 26);
                } else {
                    newText[i] = (char) (text[i] + offset);
                }
            } else {
                newText[i] = text[i];
            }
        }

        return valueOf(newText);
    }

    public static String decryptionUnicode(String in, int offset) {
        char[] text = in.toCharArray();
        char[] newText = new char[in.length()];

        for (int i = 0; i < in.length(); i++) {
            newText[i] = (char) (text[i] - offset);
        }
        return valueOf(newText);
    }

    public static String encryptionUnicode(String in, int offset) {
        char[] text = in.toCharArray();
        char[] newText = new char[in.length()];
        //int shift = offset % 26;

        for (int i = 0; i < in.length(); i++) {
            newText[i] = (char) (text[i] + offset);
        }
        return valueOf(newText);
    }
}

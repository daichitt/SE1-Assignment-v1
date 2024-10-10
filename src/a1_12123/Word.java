package engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import java.util.Set;
//import java.util.HashSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Word {
    private String prefix = "";
    private String suffix = "";
    private String text = "";
    public static Set<String> stopWords = new HashSet<>();

    private Word(String prefix, String text, String suffix) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
    }

    public static Word createWord(String rawText) {
        String prefix = "", suffix = "", text = rawText;

        for (int i = 0; i < rawText.length(); i++) {
            char c = rawText.charAt(i);
            if (Character.isLetter(c) || c == '-' || c == '\'') break;
            prefix += c;
        }
        text = rawText.substring(prefix.length());

        for (int i = text.length() - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (Character.isLetter(c) || c == '-' || c == '\'') break;
            suffix = c + suffix;
        }
        text = text.substring(0, text.length() - suffix.length());

        if (text.endsWith("'s") || text.endsWith("'")) {
            suffix = text.substring(text.length() - 2) + suffix;
            text = text.substring(0, text.length() - 2);
        }

        return new Word(prefix, text, suffix);
    }

    public boolean isKeyword() {
        if (text.isEmpty()) return false;
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != '\'') return false;
        }
        return !stopWords.contains(text.toLowerCase());
    }

    public String getPrefix() { return prefix; }
    public String getSuffix() { return suffix; }
    public String getText() { return text; }

    public boolean equals(Object o) {
        if (o instanceof Word) {
            Word w = (Word) o;
            return text.equalsIgnoreCase(w.text);
        }
        return false;
    }

    public String toString() {
        return prefix + text + suffix;
    }

    public static boolean loadStopWords(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) stopWords.add(sc.next().toLowerCase());
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
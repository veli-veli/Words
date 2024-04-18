package main.java.com.geowealth.codingtask.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

//Да се намерят всички валидни 9 буквени думи, които могат да бъдат доведени до
//еднобуквени, чрез поетапно премахване на една буква, като след всяко премахване ще получим
//наново валидна дума.
//Задачата трябва да е оптимизирана за скорост. Ако една дума е валидна при една комбинация
//от премахване на букви, не е нужно да се проверява дали би била валидна, ако премахнем
//други букви.
public class Words {
    public static void main(String[] args) throws IOException {
        Set<String> allWords = loadAllWords();

        Set<String> wordsWith9Letters = new HashSet<>();

        for (String word : allWords) {
            if (word.length() == 9) {
                wordsWith9Letters.add(word);
            }
        }

        Set<String> result = new HashSet<>();

        for (String word : wordsWith9Letters) {
            if (canReduceToSingleLetter(word, allWords)) {
                result.add(word);
            }
        }

        //Print the result
        int count = 0;
        System.out.println("Valid 9-letter words reducible to single-letter words:");
        for (String word : result) {
            System.out.println(word);
            count++;
        }
        System.out.println("Count: "+count);

    }

    private static Set<String> loadAllWords() throws IOException {
        URL scrabbleWords = new URL("https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt");

        Set<String> wordsSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(scrabbleWords.openConnection().getInputStream()))) {
            br.lines().skip(2).forEach(wordsSet::add);
            return wordsSet;
        }
    }

        private static boolean canReduceToSingleLetter(String word, Set<String> wordsList) {

            // Early termination if the word is "I" or "A"
            if (word.equals("I") || word.equals("A")) {
                return true;
            }

            Set<String> visited = new HashSet<>();
            Deque<String> stack = new ArrayDeque<>();
            stack.push(word);

            while (!stack.isEmpty()) {
                String currentWord = stack.pop();
                visited.add(currentWord);

                StringBuilder sb = new StringBuilder(currentWord);

                for (int i = 0; i < sb.length(); i++) {
                    char removedChar = sb.charAt(i);
                    sb.deleteCharAt(i);
                    String reducedWord = sb.toString();
                    sb.insert(i, removedChar);

                    if (reducedWord.equals("I") || reducedWord.equals("A")) {
                        return true;
                    }
                    if (!visited.contains(reducedWord) && wordsList.contains(reducedWord)) {
                        stack.push(reducedWord);
                    }
                }
            }
            return false;
        }
    }

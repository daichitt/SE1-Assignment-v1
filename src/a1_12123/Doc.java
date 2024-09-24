package engine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
//        String[] lines = content.split("\n");
        String[] parts = content.split("\n", 2);
//        title = extractWords(lines[0]);
//        body = extractWords(lines[1]);

        this.title = parseWords(parts[0]);
        this.body = parseWords(parts[1]);
    }

    private List<Word> parseWords(String text) {
        List<Word> words = new ArrayList<>();
        for (String rawWord : text.split("\\s+")) {
            if (!rawWord.isEmpty()) {
                words.add(Word.createWord(rawWord));
            }
        }
        return words;
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Doc other = (Doc) o;
        return title.equals(other.title) && body.equals(other.body);
    }

    private List<Word> extractWords(String line) {
        String[] words = line.split(" ");
        List<Word> wordList = new ArrayList<>();
        for (String word : words) {
//            wordList.add(new Word(word));
        }
        return wordList;
    }
}

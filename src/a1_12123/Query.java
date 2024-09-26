package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Query {
    private List<Word> keywords = new ArrayList<>();

    public Query(String searchPhrase) {
        for (String w : searchPhrase.split("\\s+")) {
            Word word = Word.createWord(w);
            if (word.isKeyword()) keywords.add(word);
        }
    }

    public List<Word> getKeywords() { return keywords; }

    public List<Match> matchAgainst(Doc d) {
        List<Word> words = new ArrayList<>(d.getTitle());
        words.addAll(d.getBody());

        List<Match> matches = new ArrayList<>();
        for (Word kw : keywords) {
            int freq = 0, firstIndex = -1;
            for (int i = 0; i < words.size(); i++) {
                if (kw.equals(words.get(i))) {
                    freq++;
                    if (firstIndex == -1) firstIndex = i;
                }
            }
            if (freq > 0) matches.add(new Match(d, kw, freq, firstIndex));
        }
        matches.sort(null);
        return matches;
    }
}


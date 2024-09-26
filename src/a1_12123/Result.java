package engine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;
public class Result implements Comparable<Result> {
    private Doc doc;
    private List<Match> matches;

    public Result(Doc d, List<Match> matches) {
        this.doc = d;
        this.matches = matches;
    }

    public Doc getDoc() { return doc; }
    public List<Match> getMatches() { return matches; }

    public int getTotalFrequency() {
        int total = 0;
        for (Match m : matches) total += m.getFreq();
        return total;
    }

    public double getAverageFirstIndex() {
        int total = 0;
        for (Match m : matches) total += m.getFirstIndex();
        return (double) total / matches.size();
    }

    public String htmlHighlight() {
        List<Word> words = new ArrayList<>(doc.getTitle());
        words.addAll(doc.getBody());

        StringBuilder sb = new StringBuilder();
        for (Word w : words) {
            String text = w.getText();
            if (matches.stream().anyMatch(m -> m.getWord().equals(w))) {
                if (doc.getTitle().contains(w)) {
                    text = "<u>" + text + "</u>";
                } else {
                    text = "<b>" + text + "</b>";
                }
            }
            sb.append(w.getPrefix()).append(text).append(w.getSuffix());
        }
        return sb.toString();
    }

    public int compareTo(Result r) {
        int cmp = Integer.compare(r.matches.size(), matches.size());
        if (cmp != 0) return cmp;
        cmp = Integer.compare(r.getTotalFrequency(), getTotalFrequency());
        if (cmp != 0) return cmp;
        return Double.compare(getAverageFirstIndex(), r.getAverageFirstIndex());
    }
}

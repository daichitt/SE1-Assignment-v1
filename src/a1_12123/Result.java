package engine;

import java.util.List;
import java.util.stream.Collectors;
public class Result implements Comparable<Result> {
    private Doc document;
    private List<Match> matches;

    public Result(Doc document, List<Match> matches) {
        this.document = document;
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public int getTotalFrequency() {
//        int totalFrequency = 0;
//        for (Match match : matches) {
//            totalFrequency += match.getFreq();
//        }
//        return totalFrequency;
        return matches.stream().mapToInt(Match::getFreq).sum();
    }

    public double getAverageFirstIndex() {
//        int sumFirstIndex = 0;
//        for (Match match : matches) {
//            sumFirstIndex += match.getFirstIndex();
//        }
//        return (double) sumFirstIndex / matches.size();

        return matches.stream().mapToInt(Match::getFirstIndex).average().orElse(0);
    }

    public String htmlHighlight() {

        StringBuilder sb = new StringBuilder();
        highlightWords(sb, document.getTitle(), "u");
        sb.append("\n");
        highlightWords(sb, document.getBody(), "b");
        return sb.toString();

//        String title = document.getTitle();
//        String body = document.getBody();
//
//        for (Match match : matches) {
//            String word = match.getWord();
//            int firstIndex = match.getFirstIndex();
//            int lastIndex = firstIndex + word.length();
//
//            if (match.isTitleMatch()) {
//                title = title.substring(0, firstIndex) + "<u>" + word + "</u>" + title.substring(lastIndex);
//            } else {
//                body = body.substring(0, firstIndex) + "<b>" + word + "</b>" + body.substring(lastIndex);
//            }
//        }
//
//        return "<html><body><h1>" + title + "</h1><p>" + body + "</p></body></html>";
    }

    private void highlightWords(StringBuilder sb, List<Word> words, String tag) {
        for (Word word : words) {
            if (matches.stream().anyMatch(matches -> matches.getWord().equals(word))) {
                sb.append(word.getPrefix()).append("<").append(tag).append(">")
                        .append(word.getText()).append("</").append(tag).append(">")
                        .append(word.getSuffix()).append(" ");
            } else {
                sb.append(word.toString()).append(" ");
            }
        }
    }

    public Doc getDoc() {
        return document;
    }

    @Override
    public int compareTo(Result other) {

        int matchCountDiff = Integer.compare(other.matches.size(), this.matches.size());
        if (matchCountDiff != 0) return matchCountDiff;

        int totalFreqDiff = Integer.compare(other.getTotalFrequency(), this.getTotalFrequency());
        if (totalFreqDiff != 0) return totalFreqDiff;

        return Double.compare(this.getAverageFirstIndex(), other.getAverageFirstIndex());


//        if (this.matches.size() > other.matches.size()) {
//            return -1;
//        } else if (this.matches.size() < other.matches.size()) {
//            return 1;
//        } else {
//            if (this.getTotalFrequency() > other.getTotalFrequency()) {
//                return -1;
//            } else if (this.getTotalFrequency() < other.getTotalFrequency()) {
//                return 1;
//            } else {
//                if (this.getAverageFirstIndex() < other.getAverageFirstIndex()) {
//                    return -1;
//                } else if (this.getAverageFirstIndex() > other.getAverageFirstIndex()) {
//                    return 1;
//                } else {
//                    return 0;
//                }
//            }
//        }
    }
}

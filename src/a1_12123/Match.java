package engine;

public class Match implements Comparable<Match> {
    private Doc document;
    private Word word;
    private int frequency;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.document = d;
        this.word = w;
        this.frequency = freq;
        this.firstIndex = firstIndex;
    }

    public int getFreq() {
        return frequency;
    }

    public int getFirstIndex() {
        return firstIndex;
    }
    public Word getWord() {
        return word;
    }

    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.firstIndex, o.firstIndex);
    }
}

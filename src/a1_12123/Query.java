package engine;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Query {
    private List<Word> keywords;
    public Query(String searchPhrase) {
        List<Word> words = new ArrayList<>();
        for (String rawWord : searchPhrase.split("\\s+")) {
            if (!rawWord.isEmpty()) {
                Word word = Word.createWord(rawWord);
                if (word.isKeyword()) {
                    words.add(word);
                }
            }
        }
        this.keywords = words;
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
//        List<Word> docWords = new ArrayList<>(d.getTitle());
//        docWords.addAll(d.getBody());
//
//        return keywords.stream()
//                .flatMap(keyword -> docWords.stream()
//                        .filter(docWord -> docWord.equals(keyword))
//                        .map(docWord -> {
//                            int freq = (int) docWords.stream().filter(w -> w.equals(keyword)).count();
//                            int firstIndex = docWords.indexOf(docWord);
//                            return new Match(d, keyword, freq, firstIndex);
//                        })
//                        .findFirst()
//                        .stream())
//                .sorted()
//                .collect(Collectors.toList());


        List<Word> docWords = new ArrayList<>(d.getTitle());
        docWords.addAll(d.getBody());

        return keywords.stream()
                .map(keyword -> {
                    List<Word> matchingWords = docWords.stream()
                            .filter(docWord -> docWord.equals(keyword))
                            .collect(Collectors.toList());
                    if (!matchingWords.isEmpty()) {
                        int freq = matchingWords.size();
                        int firstIndex = docWords.indexOf(matchingWords.get(0));
                        return new Match(d, keyword, freq, firstIndex);
                    }
                    return null;
                })
                .filter(match -> match != null)
                .sorted()
                .collect(Collectors.toList());
    }

//    public Query(String searchPhrase) {
//        keywords = extractKeywords(searchPhrase);
//    }
//
//    public List<Word> getKeywords() {
//        return keywords;
//    }
//
//    public List<Match> matchAgainst(Doc d) {
//        List<Match> matches = new ArrayList<>();
//        for (Word keyword : keywords) {
//            List<Integer> positions = d.getPositions(keyword);
//            if (!positions.isEmpty()) {
//                Match match = new Match(keyword, positions);
//                matches.add(match);
//            }
//        }
//        matches.sort((m1, m2) -> m1.getPositions().get(0) - m2.getPositions().get(0));
//        return matches;
//    }
//
//    private List<Word> extractKeywords(String searchPhrase) {
//        // Implement keyword extraction logic here
//        // You can use regular expressions or any other method to extract keywords from the search phrase
//        // Return a list of Word objects representing the extracted keywords
//    }
}



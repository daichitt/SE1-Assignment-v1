package engine;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine {
    private List<Doc> docs;

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null) return 0;

        for (File file : files) {
            try {
                Scanner scanner = new Scanner(file);
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                docs.add(new Doc(content.toString().trim()));
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return docs.size();
    }

    public Doc[] getDocs() {
        return docs.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        return docs.stream()
                .map(doc -> new Result(doc, q.matchAgainst(doc)))
                .filter(result -> !result.getMatches().isEmpty())
                .sorted()
                .collect(Collectors.toList());

    }

    public String htmlResult(List<Result> results) {
        return results.stream()
                .map(Result::htmlHighlight)
                .collect(Collectors.joining());
    }





    // public int loadDocs(String dirname) {



    // }


    // public Doc[] getDocs() {
    //     // Returns an array of documents in the original order

    // }

    // public List<Result> search(Query q) {

    // }

    // public String htmlResult(List<Result> results) {

    // }
}

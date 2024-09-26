package engine;
import java.util.List;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine {
    private List<Doc> docs = new ArrayList<>();

    public int loadDocs(String dirname) {
        File dir = new File(dirname);
        for (File f : dir.listFiles()) {
            try {
                Scanner sc = new Scanner(f);
                String title = sc.nextLine();
                String body = sc.nextLine();
                docs.add(new Doc(title + "\n" + body));
            } catch (FileNotFoundException e) {
                // ignore missing files
            }
        }
        return docs.size();
    }

    public Doc[] getDocs() {
        return docs.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        for (Doc d : docs) {
            List<Match> matches = q.matchAgainst(d);
            if (!matches.isEmpty()) {
                results.add(new Result(d, matches));
            }
        }
        results.sort(null);
        return results;
    }

    public String htmlResult(List<Result> results) {
        StringBuilder sb = new StringBuilder();
        for (Result r : results) sb.append(r.htmlHighlight());
        return sb.toString();
    }
}
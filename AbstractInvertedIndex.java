import java.io.File;
import java.util.*;

/**
 * Inverted index of a document cluster for queries response
 */
public abstract class AbstractInvertedIndex {
    protected HashMap<String, TreeSet<String>> invertedIndexMap = new HashMap<>();
    protected HashMap<String, Integer> fileNamesByIndex = new HashMap<>();
    protected HashMap<Integer, String> invertedFileNamesByIndex = new HashMap<>();

    public abstract void buildInvertedIndex(File[] files);

    protected HashMap<String, ArrayList<String>> buildIndex(File[] files) {
        HashMap<String, ArrayList<String>> docMap = new HashMap<>();
        int i = 0;
        for (File file : files) {
            int index = 0;
            ArrayList<String> words = new ArrayList<>();
            List<String> lines = Utils.readLines(file);
            String allText = String.join("", lines);
            String docName = Utils.substringBetween(allText, "<DOCNO> ",
                    " </DOCNO>");
            fileNamesByIndex.put(docName, i);
            invertedFileNamesByIndex.put(i, docName);
            i++;
            index = allText.indexOf("<TEXT>");

            while (index >= 0) {
                String text = Utils.substringBetween(allText.substring(index),
                        "<TEXT>", "</TEXT>");
                Collections.addAll(words, Utils.splitBySpace((text)));
                index = allText.indexOf("<TEXT>", index + 1);
            }
            if (docMap.containsKey(docName)) {
                docMap.get(docName).addAll(words);
            } else {
                docMap.put(docName, words);
            }
        }
        return docMap;
    }

    /**
     * Retrieves the appropriate documents for the query
     *
     * @return names of documents matching the query, or empty collection if
     * no documents found
     */
    public TreeSet<String> runQuery(String query) {
        String[] queryArray = Utils.splitBySpace(query);
        Stack<Boolean[]> queryStack = new Stack<>();
        ArrayList<String> operands = new ArrayList<>(Arrays.asList("AND"
                , "OR", "NOT"));
        for (String s : queryArray) {
            if (!operands.contains(s)) {
                queryStack.push(createWordVector(s));
            } else {
                Boolean[] a = queryStack.pop();
                Boolean[] b = queryStack.pop();
                Boolean[] c = new Boolean[a.length];
                switch (s) {
                    case "AND":
                        for (int i = 0; i < a.length; i++) {
                            c[i] = a[i] & b[i];
                        }
                        queryStack.push(c);
                        break;
                    case "OR":
                        for (int i = 0; i < a.length; i++) {
                            c[i] = a[i] | b[i];
                        }
                        queryStack.push(c);
                        break;
                    case "NOT":
                        for (int i = 0; i < a.length; i++) {
                            c[i] = (!a[i]) & b[i];
                        }
                        queryStack.push(c);
                        break;
                }
            }
        }
        TreeSet<String> docs = new TreeSet<>();
        Boolean[] resultVector = queryStack.pop();
        for (int i = 0; i < resultVector.length; i++) {
            if (resultVector[i].equals(Boolean.TRUE)) {
                docs.add(invertedFileNamesByIndex.get(i));
            }
        }
        return docs;
    }

    /**
     * Returns vector of TRUE/FALSE, as the presence of the word in the document
     * corresponding to the entry in the vector
     */
    protected abstract Boolean[] createWordVector(String word);
}

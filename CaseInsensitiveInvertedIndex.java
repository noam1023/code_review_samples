import java.io.File;
import java.util.*;

public class CaseInsensitiveInvertedIndex extends AbstractInvertedIndex {
    public static CaseInsensitiveInvertedIndex caseInsensitiveInvertedIndex;

    private CaseInsensitiveInvertedIndex() {
    }

    /**
     * Creates singleton CaseInsensitiveInvertedIndex object
     *
     * If creates prints that new object is created, else prints that an object
     * is already created
     *
     * @return reference of the new object if created, else return reference
     * of the object already created
     */
    public static CaseInsensitiveInvertedIndex getInstance() {
        if (caseInsensitiveInvertedIndex == null) {
            caseInsensitiveInvertedIndex = new CaseInsensitiveInvertedIndex();
            System.out.println("New CaseInsensitive index is created");
        } else
            System.out.println("You already have a CaseInsensitive index");
        return caseInsensitiveInvertedIndex;
    }

    public void buildInvertedIndex(File[] files) {
        HashMap<String, ArrayList<String>> docMap = buildIndex(files);
        for (String doc : docMap.keySet()) {
            for (String element : docMap.get(doc)) {
                if (invertedIndexMap.containsKey(element.toLowerCase())) {
                    invertedIndexMap.get(element.toLowerCase()).add(doc);
                } else {
                    TreeSet<String> document = new TreeSet<>();
                    document.add(doc);
                    invertedIndexMap.put(element.toLowerCase(), document);
                }
            }
        }
    }

    protected Boolean[] createWordVector(String word) {
        Boolean[] vector = new Boolean[fileNamesByIndex.size()];
        Arrays.fill(vector, Boolean.FALSE);
        for (String file : invertedIndexMap.get(word.toLowerCase())) {
            vector[fileNamesByIndex.get(file)] = Boolean.TRUE;
        }
        return vector;
    }
}

import java.io.File;
import java.util.*;

public class CaseSensitiveInvertedIndex extends AbstractInvertedIndex {
    public static CaseSensitiveInvertedIndex caseSensitiveInvertedIndex;

    private CaseSensitiveInvertedIndex() {
    }

    /**
     * Creates singleton CaseSensitiveInvertedIndex object
     *
     * If creates prints that new object is created, else prints that an object
     * is already created
     *
     * @return reference of the new object if created, else return reference
     * of the object already created
     */
    public static CaseSensitiveInvertedIndex getInstance() {
        if (caseSensitiveInvertedIndex == null) {
            caseSensitiveInvertedIndex = new CaseSensitiveInvertedIndex();
            System.out.println("New CaseSensitive index is created");
        } else
            System.out.println("You already have a CaseSensitive index");
        return caseSensitiveInvertedIndex;
    }

    public void buildInvertedIndex(File[] files) {
        HashMap<String, ArrayList<String>> docMap = buildIndex(files);
        for (String doc : docMap.keySet()) {
            for (String element : docMap.get(doc)) {
                if (!element.equals("")) {
                    if (invertedIndexMap.containsKey(element)) {
                        invertedIndexMap.get(element).add(doc);
                    } else {
                        TreeSet<String> document = new TreeSet<>();
                        document.add(doc);
                        invertedIndexMap.put(element, document);
                    }
                }
            }
        }
    }

    protected Boolean[] createWordVector(String word) {
        Boolean[] vector = new Boolean[fileNamesByIndex.size()];
        Arrays.fill(vector, Boolean.FALSE);
        for (String file : invertedIndexMap.get(word)) {
            vector[fileNamesByIndex.get(file)] = Boolean.TRUE;
        }
        return vector;
    }
}

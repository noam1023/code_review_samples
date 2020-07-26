import java.io.File;
import java.util.*;

/**
 * The class AbstractInvertedIndex is an abstract class with
 information retrieval functions.
 */
public abstract class AbstractInvertedIndex {
    protected HashMap<String, TreeSet<String>> map;

    // constructor
    protected AbstractInvertedIndex() {
        map = new HashMap<>();
    }


    /**
     * The method builds arrays.
     The name of each array is a word that appears in files,
     and the array contains the file names in which the word appears
     *
     * @param files is text file.
     */
    void buildInvertedIndex(File[] files) {
        for (File file : files) {
            List<String> lines = Utils.readLines(file);
            String nameFile = null;
            boolean Text = false;

            for (String line : lines) {
                if (nameFile == null) {
                    nameFile=Utils.substringBetween(line,"<DOCNO>","</DOCNO>");
                    if (nameFile != null) {
                        nameFile = nameFile.trim();
                    }
                }
                if (line.equals("<TEXT>")) {
                    Text = true;
                    continue;}

                if (line.equals("</TEXT>")) {
                    Text = false;
                    continue;}

                if (!Text) {
                    continue;}

                String[] words = Utils.splitBySpace(line);

                for (String word : words) {
                    word = this.getKey(word);

                    if (!map.containsKey(word)) {
                        map.put(word, new TreeSet<String>());
                    }
                    TreeSet<String> documentsOfWord = map.get(word);
                    documentsOfWord.add(nameFile);
                }
            }
        }
    }

    /**
     The method receives a word.
     if necessary the method changes words that start with a capital letter
     to a small letter and return the word.
     *
     * @param wordFromFile is word.
     * @return the word.
     */
    abstract protected String getKey(String wordFromFile);


    /**
     *The method returns a list of files that answer the query.
     *
     * @param query is boolean query.
     * @return TreeSet Is a sorted set.
     */
    public TreeSet<String> runQuery(String query) {
        Stack<TreeSet<String>> stack = new Stack<>();
        ArrayList<String> operators = new ArrayList<>();
        operators.add("OR");
        operators.add("AND");
        operators.add("NOT");

        String[] tokens = Utils.splitBySpace(query);
        for (String token : tokens) {
            if (!operators.contains(token)) {
                TreeSet<String> set = map.get(this.getKey(token));
                if (set != null) {
                    stack.push(set);
                }
                continue;
            }

            TreeSet<String> right = stack.pop();
            TreeSet<String> left = stack.pop();
            TreeSet<String> operationSet = new TreeSet<>();
            operationSet.addAll(left);

            switch (token) {
                case "OR": {
                    operationSet.addAll(right);
                    break;
                }
                case "AND": {
                    operationSet.retainAll(right);
                    break;
                }
                case "NOT": {
                    operationSet.removeAll(right);
                    break;
                }
            }
            stack.push(operationSet);
        }

        return stack.pop();
    }
}

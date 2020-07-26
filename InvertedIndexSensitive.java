import java.io.File;
import java.util.TreeSet;
import java.util.*;

public class InvertedIndexSensitive implements AbstractInvertedIndex {


    private static InvertedIndexSensitive invertedIndexSensitive;
    private InvertedIndexSensitive(){}

    public static InvertedIndexSensitive getInstance(){
        if (invertedIndexSensitive==null){
            invertedIndexSensitive = new InvertedIndexSensitive();
            System.out.println("New CaseSensitive index is created");
        }
        else{
            System.out.println("You already have a CaseSensitive index");
        }
        return invertedIndexSensitive;
    }

    protected Map<Integer, String> myMap = new HashMap();

    protected HashMap<String, HashSet<Integer>> myHashMap = new HashMap();


    public TreeSet<String> runQuery(String query){

        String[] split_query = Utils.splitBySpace(query);

        Stack<HashSet<Integer>> myStack = new Stack<>();

        for (String word : split_query)
        {
            if(word.equals("OR"))
            {
                HashSet<Integer> word_Or1 = myStack.pop();
                HashSet<Integer> word_Or2 = myStack.pop();

                word_Or2.addAll(word_Or1);
                myStack.push(word_Or2);
            }

            else if (word.equals("AND"))
            {

                HashSet<Integer> word_And1 = myStack.pop();
                HashSet<Integer> word_And2 = myStack.pop();

                HashSet<Integer> fuze = new HashSet<>();


                if (word_And1.size() >= word_And2.size()) {
                    for (Integer caract : word_And1) {
                        if (word_And2.contains(caract)) {
                            fuze.add(caract);
                        }
                    }
                    myStack.push(fuze);
                    break;
                } else {
                    for (Integer caract : word_And2) {
                        if (word_And2.contains(caract)) {
                            fuze.add(caract);
                        }

                    }
                    myStack.push(fuze);
                }
            }

            else if (word.equals("NOT"))
            {

                HashSet<Integer> word_Not1 = myStack.pop();
                HashSet<Integer> word_not2 = myStack.pop();

                for (Integer caract : word_Not1)
                {
                    word_not2.remove(caract);
                }
                myStack.push(word_not2);

            }

            else {
                HashSet<Integer> word1 = myHashMap.get(word);
                    myStack.push(word1);
            }
        }


        TreeSet<String> output = new TreeSet<>();
        HashSet<Integer> idx = myStack.pop();
        for (Integer i: idx)
        {
            output.add(myMap.get(i));
        }

        return output;
    }

    public void buildInvertedIndex(File[] files) {

        int file_length = files.length;

        for (int i=0;i<file_length-1;i++)
        {
            String s = null;
            File path = new File(files[i].getPath());
            String title = files[i].getName();
            myMap.put(i, title);
            List<String> line_Path = Utils.readLines(path);

            for (String n : line_Path)
            {
                s = s + n + "\t";
            }
            String substring = Utils.substringBetween(s,"<TEXT>", "</DOC>");
            assert substring != null;
            String[] line = Utils.splitBySpace(substring);

            for (String word : line) {
                if (myHashMap.containsKey(word))
                {
                    myHashMap.get(word).add(i);
                    continue;
                }
                else
                {
                    myHashMap.put(word, new HashSet<>());
                }
                myHashMap.get(word).add(i);
            }

        }
    }
}



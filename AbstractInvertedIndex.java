import java.io.File;
import java.util.TreeSet;

public interface AbstractInvertedIndex {
    public abstract void buildInvertedIndex(File[] listFiles);

    public abstract TreeSet<String> runQuery(String query);

}

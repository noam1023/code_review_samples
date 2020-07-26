/**
 * The class checks whether the case sensitive index exist.
 */
public class CaseSensitiveIndex extends AbstractInvertedIndex {
    private static CaseSensitiveIndex index;

    /**
     * The method prints a notice whether the index exists or not.
     And if it does not exist it creates it
     *
     * @return the index
     */
    public static CaseSensitiveIndex getInstance() {
        if (index != null) {
            System.out.println("You already have a CaseSensitive index");
        } else {
            index = new CaseSensitiveIndex();
            System.out.println("New CaseSensitive index is created");
        }
        return index;
    }

    @Override
    protected String getKey(String wordFromFile) {
        return wordFromFile;
    }
}

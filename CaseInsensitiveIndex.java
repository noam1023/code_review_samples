/**
 * The class checks whether the case insensitive index exist.
 */
public class CaseInsensitiveIndex extends AbstractInvertedIndex {
    private static CaseInsensitiveIndex index;

    /**
     * The method prints a notice whether the index exists or not.
     And if it does not exist it creates it
     *
     * @return the index
     */
    public static CaseInsensitiveIndex getInstance() {
        if (index != null) {
            System.out.println("You already have a CaseInsensitive index");
        } else {
            index = new CaseInsensitiveIndex();
            System.out.println("New CaseInsensitive index is created");
        }
        return index;
    }

    @Override
    protected String getKey(String wordFromFile) {
        return wordFromFile.toLowerCase();
    }
}

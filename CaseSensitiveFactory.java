public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {

    public AbstractInvertedIndex createInvertedIndex(){
        return CaseSensitiveInvertedIndex.getInstance();
    }
}

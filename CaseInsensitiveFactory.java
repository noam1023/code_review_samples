public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {

    public AbstractInvertedIndex createInvertedIndex(){
        return CaseInsensitiveInvertedIndex.getInstance();
    }
}

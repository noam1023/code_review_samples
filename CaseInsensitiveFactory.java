public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    public AbstractInvertedIndex createInvertedIndex() {
        return InvertedIndexInsensitive.getInstance();
    }
}

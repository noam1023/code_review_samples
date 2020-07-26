public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    public AbstractInvertedIndex createInvertedIndex() {
        return InvertedIndexSensitive.getInstance();
    }
}

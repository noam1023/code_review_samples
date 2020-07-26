/**
 * The class creates CaseInsensitive object,
 according to the factory design pattern.
 */
public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    @Override
    AbstractInvertedIndex createInvertedIndex() {
        return CaseInsensitiveIndex.getInstance();
    }
}

/**
 * The class creates CaseSensitive object,
 according to the factory design pattern.
 */
public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    @Override
    AbstractInvertedIndex createInvertedIndex() {
        return CaseSensitiveIndex.getInstance();
    }
}

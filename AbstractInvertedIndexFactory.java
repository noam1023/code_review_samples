/**
 * The class AbstractInvertedIndexFactory is an abstract class which
 creates objects from InvertedIndex types,
 according to the factory design pattern.
 *
 */
public abstract class AbstractInvertedIndexFactory {
    /**
     * The method that realizes the function getInstance.
     *
     */
    abstract AbstractInvertedIndex createInvertedIndex();

}

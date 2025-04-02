package seedu.address.logic.backend;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Compares two sets by their number of common elements with the provided {@code ref} set
 */
public class SetSimilarityComparator<T> implements Comparator<Set<T>> {

    private final Set<T> ref;

    public SetSimilarityComparator(Set<T> ref) {
        this.ref = ref;
    }

    @Override
    public int compare(Set<T> o1, Set<T> o2) {
        Set<T> r1 = new HashSet<>(ref);
        Set<T> r2 = new HashSet<>(ref);

        r1.retainAll(o1);
        r2.retainAll(o2);

        return Integer.compare(r2.size(), r1.size());
    }

}

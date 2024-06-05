package Set;

public interface Set<T> extends Iterable<T> {
    void add(T value);
    boolean contains(T value);
    int size();

    static <T> boolean equals(Set<T> s1, Set<T> s2) {
        if (s1 == null && s2 == null) return true;
        else if (s1 == null || s2 == null) return false;
        if (s1.size() != s2.size()) return false;
        for (T entry : s1)
            if (!s2.contains(entry)) return false;
        return true;
    }
}

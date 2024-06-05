package Other;

public interface Copy<E extends Copy<E>> {
    E copy();
}

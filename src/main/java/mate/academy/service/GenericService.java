package mate.academy.service;

public interface GenericService<T, I> {
    T add(T movie);

    T get(I id);
}

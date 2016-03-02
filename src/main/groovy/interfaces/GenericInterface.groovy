package interfaces

/**
 * Created by Rafa on 23.02.2016.
 */
interface GenericInterface<E,S> {
    E e
    def o
    public <T> T getT(T t)
    public <K, V> K getT(K k, V v)
    public E getE(int i)
    public <K, V> Map<K, V> getMap(E e, def d)
}
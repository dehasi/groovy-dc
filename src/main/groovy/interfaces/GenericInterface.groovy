package interfaces

/**
 * Created by Rafa on 23.02.2016.
 */
interface GenericInterface<E,S extends String> {
    E e
    def o
    public <T> S getT(T t)
    public <K, V> K getT(K k, V v)
    public E getE(int i)
    public <K, V> Map<K, V> getMap(E e, def d)
}
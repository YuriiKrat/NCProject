package dao.collection;

import dao.AbstractDAO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Denys Vodotiiets.
 */
public abstract class AbstractDAOImpl<T, K> implements AbstractDAO<T, K> {

    protected Map<K, T> entities;

    public AbstractDAOImpl() {
        entities = new ConcurrentHashMap<K, T>();
    }

    @Override public abstract void insert(T obj);
    @Override public abstract void update(T obj);
    @Override public abstract T delete(T obj);

    @Override
    public List<T> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public T get(K key) {
        return entities.get(key);
    }
}

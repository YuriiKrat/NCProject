package dao.collection;

import dao.AbstractDAO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 20.03.17.
 */
public abstract class AbstractDAOImpl<T, K> implements AbstractDAO<T, K> {

    protected Map<K, T> entities;

    public AbstractDAOImpl() {
        entities = new ConcurrentHashMap<>();
    }

    @Override
    public List<T> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public T get(K key) {
        return entities.get(key);
    }
}

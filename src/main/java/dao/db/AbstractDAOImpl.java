package dao.db;

import dao.AbstractDAO;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.03.17.
 */
public abstract class AbstractDAOImpl<T, K> implements AbstractDAO<T, K>{

    protected abstract Class<T> getEntityClass();

}

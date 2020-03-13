package sve2.jee.fhbay.dao.cdi;


import sve2.jee.fhbay.util.TypeUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDaoBean<T, ID extends Serializable> implements Dao<T, ID> {
  @PersistenceContext
  private EntityManager entityManager;

  private Class<T> entityType;

  public AbstractDaoBean() {
    ParameterizedType type = TypeUtil.getTypeInfoOfGenericBaseclass(this.getClass(), AbstractDaoBean.class);
    this.entityType = (Class<T>)(type.getActualTypeArguments()[0]);
  }

  protected EntityManager getEntityManager() {
    if (this.entityManager == null) {
      throw new IllegalStateException("EntityManager has not been set on DAO before usage");
    }
    return this.entityManager;
  }

  public Class<T> getEntityBeanType() {
    return this.entityType;
  }

  @Override
  public T findById(ID id) {
    T entity = this.getEntityManager().find(this.getEntityBeanType(), id);
    return entity;
  }

  @Override
  public boolean entityExists(ID id) {
    return this.getEntityManager().find(this.getEntityBeanType(), id) != null;
  }

  @Override
  public List<T> findAll() {
    TypedQuery<T> selectAllQuery = this.entityManager.createQuery(
            String.format("select entity from %s entity", this.getEntityBeanType().getName()),
            this.getEntityBeanType()
    );
    return selectAllQuery.getResultList();
  }

  @Override
  public T merge(T entity) {
    return this.getEntityManager().merge(entity);
  }

  @Override
  public void persist(T entity) {
    this.getEntityManager().persist(entity);
  }

  @Override
  public void remove(T entity) {
    this.getEntityManager().remove(entity);
  }
}

package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.BaseModel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

// TODO For real world applications, activate container-managed transactions with
// @TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class BaseService<T extends BaseModel> {
    @PersistenceContext
    EntityManager em;
    
    public abstract T create();
    
    public T findById(Long id) {
        return em.find(getModelClass(), id);
    }
    
    public List<T> findAll() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(getModelClass());
        query.select(query.from(getModelClass()));
        return (List<T>) em.createQuery(query).getResultList();
    }
    
    public long countAll() {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        query.select(em.getCriteriaBuilder().count(query.from(getModelClass())));
        return em.createQuery(query).getSingleResult();
    }
    
    public T save(T entity) {        
        if (entity.getId() == null) {
            em.persist(entity);
        }
        else {
            entity = em.merge(entity);
        }
        em.flush();
        return entity;
    }
    
    public void delete(Long id) {
        T entity = em.getReference(getModelClass(), id);
        em.remove(entity);
    }
    
    protected abstract Class<T> getModelClass();
}

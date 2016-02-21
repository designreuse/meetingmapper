package n.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

//http://www.luckyryan.com/2013/02/20/spring-mvc-with-basic-persistence-spring-data-jpa-hibernate/
public class AbstractJpaDao<T extends Serializable> {
    private Class<T> clazz;
    @PersistenceContext
    EntityManager entityManager;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T find(Long id) {
        return this.entityManager.find(this.clazz, id);
    }

    public List<T> findAll() {
        return this.entityManager.createQuery("from " + this.clazz.getName()).getResultList();
    }

    public void save(T entity) {
        this.entityManager.persist(entity);
    }

    public void update(T entity) {
        this.entityManager.merge(entity);
    }

    public void delete(T entity) {
        this.entityManager.remove(entity);
    }

    public void deleteById(Long entityId) {
        T entity = this.find(entityId);
        this.delete(entity);
    }

    public void truncate(final String table) {
        this.entityManager.createNativeQuery("truncate "+table);
    }
}

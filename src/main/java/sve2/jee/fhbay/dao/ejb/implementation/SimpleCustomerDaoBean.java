package sve2.jee.fhbay.dao.ejb.implementation;

import sve2.jee.fhbay.dao.ejb.SimpleCustomerDao;
import sve2.jee.fhbay.domain.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SimpleCustomerDaoBean implements SimpleCustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean entityExists(Long id) {
        return this.entityManager.find(Customer.class, id) != null;
    }

    @Override
    public Customer findById(Long id) {
        return this.entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return this.entityManager
                .createQuery("SELECT C FROM Customer AS C ORDER BY C.lastName DESC", Customer.class)
                .getResultList();
    }

    @Override
    public Customer merge(Customer entity) {
        return this.entityManager.merge(entity);
    }

    @Override
    public void persist(Customer entity) {
        this.entityManager.persist(entity);
    }

    @Override
    public void remove(Customer entity) {
        this.entityManager.remove(entity);
    }
}

package sve2.jee.fhbay.dao.cdi;

import sve2.jee.fhbay.domain.Customer;

import java.util.List;

public interface SimpleCustomerDao {
    boolean entityExists(Long id);
    Customer findById(Long id);
    List<Customer> findAll();

    Customer merge(Customer entity);
    void persist(Customer entity);
    void remove(Customer entity);
}

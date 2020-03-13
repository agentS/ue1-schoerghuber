package sve2.jee.fhbay.dao.ejb;

import sve2.jee.fhbay.domain.Customer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SimpleCustomerDao {
    boolean entityExists(Long id);
    Customer findById(Long id);
    List<Customer> findAll();

    Customer merge(Customer entity);
    void persist(Customer entity);
    void remove(Customer entity);
}

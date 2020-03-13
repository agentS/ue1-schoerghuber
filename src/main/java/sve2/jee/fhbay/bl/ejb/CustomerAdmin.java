package sve2.jee.fhbay.bl.ejb;

import sve2.jee.fhbay.domain.Customer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CustomerAdmin {
    boolean existsCustomer(Long id);
    Customer findCustomerById(Long id) throws IdNotFoundException;
    List<Customer> findAllCustomers();
    Long saveCustomer(Customer customer);
}

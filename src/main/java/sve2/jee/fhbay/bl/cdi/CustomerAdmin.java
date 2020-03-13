package sve2.jee.fhbay.bl.cdi;

import sve2.jee.fhbay.bl.cdi.IdNotFoundException;
import sve2.jee.fhbay.domain.Customer;

import java.util.List;

public interface CustomerAdmin {
    boolean existsCustomer(Long id);
    Customer findCustomerById(Long id) throws IdNotFoundException; // a checked exception is part of the contract --> in case of an exception the transaction is still committed
    List<Customer> findAllCustomers();
    Long saveCustomer(Customer customer);
}

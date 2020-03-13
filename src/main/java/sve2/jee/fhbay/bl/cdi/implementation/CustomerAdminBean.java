package sve2.jee.fhbay.bl.cdi.implementation;

import org.slf4j.Logger;
import sve2.jee.fhbay.bl.cdi.CustomerAdmin;
import sve2.jee.fhbay.bl.cdi.IdNotFoundException;
import sve2.jee.fhbay.dao.cdi.CustomerDao;
import sve2.jee.fhbay.dao.cdi.SimpleCustomerDao;
import sve2.jee.fhbay.domain.Customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class CustomerAdminBean implements CustomerAdmin {
    @Inject
    private Logger logger;

    //@Inject
    //private SimpleCustomerDao customerDao;

    @Inject
    private CustomerDao customerDao;

    @Override
    public boolean existsCustomer(Long id) {
        this.logger.info("existsCustomer(id = " + id + ") called");
        return this.customerDao.entityExists(id);
    }

    @Override
    public Customer findCustomerById(Long id) throws IdNotFoundException {
        this.logger.info("findCustomerById(id = " + id + ") called");
        if (this.existsCustomer(id)) {
            return this.customerDao.findById(id);
        } else {
            throw new IdNotFoundException("Customer with id " + id + " does not exist!");
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        this.logger.info("findAllCustomers() called");
        return this.customerDao.findAll();
    }

    @Override
    public Long saveCustomer(Customer customer) {
        this.logger.info("saveCustomer(customer = '" + customer + "') called");
        return this.customerDao.merge(customer).getId();
    }
}

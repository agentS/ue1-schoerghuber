package sve2.jee.fhbay.bl.ejb.implementation;

import org.slf4j.Logger;
import sve2.jee.fhbay.bl.ejb.CustomerAdmin;
import sve2.jee.fhbay.bl.ejb.IdNotFoundException;
import sve2.jee.fhbay.dao.ejb.SimpleCustomerDao;
import sve2.jee.fhbay.domain.Customer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CustomerAdminBean implements CustomerAdmin {
    @Inject
    private Logger logger;

    @EJB
    private SimpleCustomerDao customerDao;

    @Override
    public boolean existsCustomer(Long id) {
        this.logger.info("existsCustomer(id = " + id + ") called");
        return this.customerDao.entityExists(id);
    }

    @Override
    public Customer findCustomerById(Long id) throws IdNotFoundException {
        this.logger.info("findCustomerById(id = " + id + ") called");
        return this.customerDao.findById(id);
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

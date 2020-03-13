package sve2.jee.fhbay.dao.cdi.implementation;

import sve2.jee.fhbay.dao.cdi.AbstractDaoBean;
import sve2.jee.fhbay.dao.cdi.CustomerDao;
import sve2.jee.fhbay.domain.Customer;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class CustomerDaoBean extends AbstractDaoBean<Customer, Long> implements CustomerDao {
}

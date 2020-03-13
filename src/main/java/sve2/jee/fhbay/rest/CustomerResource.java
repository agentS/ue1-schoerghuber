package sve2.jee.fhbay.rest;

import sve2.jee.fhbay.bl.cdi.CustomerAdmin;
import sve2.jee.fhbay.bl.cdi.IdNotFoundException;
//import sve2.jee.fhbay.bl.ejb.CustomerAdmin;
//import sve2.jee.fhbay.bl.ejb.IdNotFoundException;
import sve2.jee.fhbay.domain.Customer;

//import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@Path("/customers")
public class CustomerResource {
    @Inject
    private CustomerAdmin customerAdmin;

    //@EJB
    //private CustomerAdmin customerAdmin;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> findAllCustomers() {
        return this.customerAdmin.findAllCustomers();
    }

    @GET
    @Path("/{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer findCustomerById(@PathParam("customerId") Long customerId) {
        try {
            return this.customerAdmin.findCustomerById(customerId);
        } catch (IdNotFoundException customerNotFoundException) {
            throw new NotFoundException("Customer with ID " + customerId + " does not exist!");
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer createCustomer(Customer customer) {
        if (customer != null) {
            Long customerId = this.customerAdmin.saveCustomer(customer);
            customer.setId(customerId);
            return customer;
        } else {
            throw new BadRequestException("The request body must contain a customer representation!");
        }
    }

    @PUT
    @Path("/update/{customerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Customer updateCustomer(Customer customer) {
        if (customer != null) {
            Long customerId = this.customerAdmin.saveCustomer(customer);
            if (!Objects.equals(customerId, customer.getId())) {
                customer.setId(customerId);
            }
            return customer;
        } else {
            throw new BadRequestException("The request body must contain a customer representation!");
        }
    }

    @POST
    @Path("/init")
    public void initCustomerDatabase() {
        this.customerAdmin.saveCustomer(new Customer(
                "Homer", "Simpson", "homer.simpson", "Donuts", "homer.simpson@burnspowerplant.com"
        ));
        this.customerAdmin.saveCustomer(new Customer(
                "Lenford", "Leonard", "lenford.leonard", "Carl", "lenford.leonard@burnspowerplant.com"
        ));
        this.customerAdmin.saveCustomer(new Customer(
                "Carl", "Carlson", "carl.carlson", "Lenny", "carl.carlson@burnspowerplant.com"
        ));
        this.customerAdmin.saveCustomer(new Customer(
                "Charles Montgomery", "Burns", "burns", "Money", "burns@burnspowerplant.com"
        ));
        this.customerAdmin.saveCustomer(new Customer(
                "Waylon Jr", "Smithers", "waylon.jr.smithers", "MrBurns", "waylon.jr.smithers@burnspowerplant.com"
        ));
    }
}

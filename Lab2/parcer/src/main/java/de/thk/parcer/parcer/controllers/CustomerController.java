package de.thk.parcer.parcer.controllers;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.thk.parcer.parcer.domain.Customer;
import de.thk.parcer.parcer.resources.CustomerResource;
import de.thk.parcer.parcer.services.CustomerService;

/**
 * Class that handles incoming restful calls, which concern the resource {@code customer}
 */
@RestController
@RequestMapping(produces = "application/hal+json")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Returns a list of all customers.
     * 
     * Example call with {@code curl}:
     * 
     * <pre class="code">
     *  curl -s http://localhost:8080/customers
     * </pre>
     * 
     * @return all customers
     */
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public @ResponseBody Resources<CustomerResource> getCustomers() {
        return new Resources<>(StreamSupport.stream(customerService.getAllCustomers().spliterator(), false)
                .map(CustomerResource::new).collect(Collectors.toList()));
    }

    /**
     * Returns a single customer of the given id.
     * 
     * Example call with {@code curl}:
     *
     * <pre class="code">
     *  curl -s http://localhost:8080/customer/1
     * </pre>
     * 
     * @param id the unique customer id
     * @return the customer if existent
     */
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<CustomerResource> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok(new CustomerResource(customerService.getCustomer(id)));
    }

    /**
     * Creates a new customer and assigns a unique id to him.
     * 
     * Example call with {@code curl}:
     * 
     * <pre class="code">
     *  curl -sSL -D - -H "Content-Type: application/json" -X POST -d '{"name":"TH Koeln"}' http://localhost:8080/customers
     * </pre>
     * 
     * @param customer the data of the new customer
     * @return a URI that identifies the new customer
     */
    @RequestMapping(value = "/customers", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        final Customer persistedCustomer = customerService.createCustomer(customer);
        final CustomerResource customerResource = new CustomerResource(persistedCustomer);
        return ResponseEntity.created(URI.create(customerResource.getLink("self").getHref())).body(customerResource);
    }
}
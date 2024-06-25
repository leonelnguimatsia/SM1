package de.thk.parcer.parcer.resources;

import de.thk.parcer.parcer.controllers.CustomerController;
import de.thk.parcer.parcer.controllers.ShipmentController;
import de.thk.parcer.parcer.domain.Customer;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Wrapper that can be serialized to a json+hal-representation
 * for {@code Customer}s.
 */
public class CustomerResource extends ResourceSupport {
    private final Customer customer;

    public CustomerResource(final Customer customer) {
        this.customer = customer;
        final long customerId = customer.getId();
        add(linkTo(methodOn(ShipmentController.class).getAllShipmentsOfCustomer(customerId)).withRel("shipments"));
        add(linkTo(methodOn(CustomerController.class).getCustomer(customerId)).withSelfRel());
    }

    public Customer getCustomer() {
        return customer;
    }
}
package de.thk.parcer.parcer.resources;

import org.springframework.hateoas.ResourceSupport;

import de.thk.parcer.parcer.controllers.CustomerController;
import de.thk.parcer.parcer.controllers.LabelController;
import de.thk.parcer.parcer.controllers.ShipmentController;
import de.thk.parcer.parcer.domain.Shipment;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Wrapper that can be serialized to a json+hal-representation
 * for {@code Shipment}s.
 */
public class ShipmentResource extends ResourceSupport {
    private final Shipment shipment;

    public ShipmentResource(final Shipment shipment) {
        this.shipment = shipment;
        final long labelId = shipment.getLabel().getId();
        final long customerId = shipment.getCustomer().getId();
        final String shipmentNumber = shipment.getShipmentNumber();
        add(linkTo(methodOn(LabelController.class).getLabel(labelId)).withRel("label"));
        add(linkTo(methodOn(CustomerController.class).getCustomer(customerId)).withRel("customer"));
        add(linkTo(methodOn(ShipmentController.class).getShipment(shipmentNumber)).withSelfRel());
    }

    public Shipment getShipment() {
        return shipment;
    }
}
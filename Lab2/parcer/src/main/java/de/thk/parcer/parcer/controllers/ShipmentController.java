package de.thk.parcer.parcer.controllers;

import de.thk.parcer.parcer.domain.Shipment;
import de.thk.parcer.parcer.resources.ShipmentResource;
import de.thk.parcer.parcer.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Collectors;

/**
 * Class that handles incoming restful calls, which concern the resource {@code shipment}
 */
@RestController
@RequestMapping(produces = "application/hal+json")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(final ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Returns list of all shipments that belong to a certain customer
     * <p>
     * Example call with {@code curl}:
     *
     * <pre class="code">
     *  curl -s http://localhost:8080/customers/1/shipments
     * </pre>
     *
     * @param id the customer's id
     * @return all shipments of the customer
     */
    @RequestMapping(value = "/customers/{id}/shipments", method = RequestMethod.GET)
    public ResponseEntity<Resources<ShipmentResource>> getAllShipmentsOfCustomer(@PathVariable long id) {
        final Resources<ShipmentResource> shipmentResources = new Resources<>(shipmentService
                .getAllShipmentsOfCustomer(id).stream().map(ShipmentResource::new).collect(Collectors.toList()));
        return ResponseEntity.ok(shipmentResources);
    }

    /**
     * Returns a single shipment with the given shipment number.
     * <p>
     * Example call with {@code curl}:
     *
     * <pre class="code">
     *  curl -s http://localhost:8080/shipments/{shipmentNumber}
     * </pre>
     *
     * @param shipmentNumber the unique shipment number of the shipment
     * @return the shipment if existent
     */
    @RequestMapping(value = "/shipments/{shipmentNumber}", method = RequestMethod.GET)
    public ResponseEntity<ShipmentResource> getShipment(@PathVariable String shipmentNumber) {
        Shipment shipment = shipmentService.getShipmentByShipmentNumber(shipmentNumber);
        return ResponseEntity.ok(new ShipmentResource(shipment));
    }

    /**
     * Adds a shipment to the collection of shipments of a certain customer.
     * <p>
     * Example call with {@code curl}:
     *
     * <pre class="code">
     *  curl -sSL -D - -H "Content-Type: application/json" -X POST -d '{"recipientAddress": {"line1": "Rene Woerzberger", "line2": "Claudiusstrasse 16", "line3": "51503 Roesrath"}}' http://localhost:8080/customers/1/shipments
     * </pre>
     *
     * @param shipment the new shipment to add
     * @param id       the customer's id
     * @return the URI to the newly created shipment
     */
    @RequestMapping(value = "/customers/{id}/shipments", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createShipment(@RequestBody Shipment shipment, @PathVariable long id) {
        final Shipment persistedShipment = shipmentService.createShipment(shipment, id);
        final ShipmentResource shipmentResource = new ShipmentResource(persistedShipment);
        return ResponseEntity.created(URI.create(shipmentResource.getLink("self").getHref())).body(shipmentResource);
    }
}
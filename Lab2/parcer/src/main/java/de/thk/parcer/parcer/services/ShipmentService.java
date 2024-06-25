package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Shipment;

import java.util.Set;

/**
 * Interface of services for the creation and retrival of shipments.
 */
public interface ShipmentService {
    /**
     * Creates and stores a shipment
     *
     * @param shipment   the shipment that is to be stored
     * @param customerId the id of the {@link de.thk.parcer.parcer.domain.Customer} which is the owner
     *                   of the shipment
     * @return the passed shipment with a generated id
     */
    Shipment createShipment(Shipment shipment, long customerId);

    /**
     * Returns a set of shipments belonging to the customer with the passed id
     *
     * @param id the id of the customer
     * @return the set of shipments that are owned by the customer
     */
    Set<Shipment> getAllShipmentsOfCustomer(long id);

    /**
     * Returns a shipment with the given shipment number
     *
     * @param shipmentNumber the unique shipment number that identifies the shipments
     * @return the shipment with the given shipment number if existent
     * @throws de.thk.parcer.parcer.exceptions.ResourceNotFoundException raised if the shipment with the
     *                                                                   given id could not be found
     */
    Shipment getShipmentByShipmentNumber(String shipmentNumber);
}
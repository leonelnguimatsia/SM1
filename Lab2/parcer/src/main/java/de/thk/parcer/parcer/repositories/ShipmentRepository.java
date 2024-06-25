package de.thk.parcer.parcer.repositories;

import de.thk.parcer.parcer.domain.Shipment;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access to for persisted shipments.
 */
public interface ShipmentRepository extends CrudRepository<Shipment, Long> {
    Shipment findByShipmentNumber(String shipmentNumber);
}
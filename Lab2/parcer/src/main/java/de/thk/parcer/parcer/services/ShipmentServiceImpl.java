package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Customer;
import de.thk.parcer.parcer.domain.Label;
import de.thk.parcer.parcer.domain.Shipment;
import de.thk.parcer.parcer.exceptions.InvalidAddressException;
import de.thk.parcer.parcer.repositories.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CustomerService customerService;
    private final AddressValidationService addressValidationService;
    private final LabelService labelService;

    @Autowired
    public ShipmentServiceImpl(final ShipmentRepository shipmentRepository,
                               final CustomerService customerService,
                               final AddressValidationService addressValidationService,
                               final LabelService labelService) {
        this.shipmentRepository = shipmentRepository;
        this.customerService = customerService;
        this.addressValidationService = addressValidationService;
        this.labelService = labelService;
    }

    public Shipment createShipment(Shipment shipment, long customerId) {
        Set<String> validationErrors = addressValidationService.getValidationErrors(shipment.getRecipientAddress());
        if (validationErrors.size() > 0) {
            throw new InvalidAddressException(validationErrors.toString());
        }
        final String shipmentNumber = getNextShipmentNumber();
        shipment.setShipmentNumber(shipmentNumber);
        final Customer customer = customerService.getCustomer(customerId);
        shipment.setCustomer(customer);
        final Label label = labelService.createLabel(shipment.getRecipientAddress(), shipment.getShipmentNumber());
        shipment.setLabel(label);
        return shipmentRepository.save(shipment);
    }

    private String getNextShipmentNumber() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Set<Shipment> getAllShipmentsOfCustomer(long id) {
        return customerService.getCustomer(id).getShipments();
    }

    @Override
    public Shipment getShipmentByShipmentNumber(String shipmentNumber) {
        return shipmentRepository.findByShipmentNumber(shipmentNumber);
    }

}
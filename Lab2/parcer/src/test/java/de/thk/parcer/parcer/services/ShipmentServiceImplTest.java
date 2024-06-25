package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.domain.Customer;
import de.thk.parcer.parcer.domain.Shipment;
import de.thk.parcer.parcer.exceptions.InvalidAddressException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
// Let each test method run in a transaction.
// Test methods in a transaction are rolled back by default.
@Transactional
public class ShipmentServiceImplTest {

    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private CustomerService customerService;

    private Customer testCustomer;


    @BeforeTransaction
    public void setUp() {
        Customer customer = new Customer();
        customer.setName("TH Koeln");
        testCustomer = customerService.createCustomer(customer);
    }

    @Test
    public void testCreateShipmentWithValidAddress() {
        //given
        Shipment shipment = new Shipment();
        shipment.setRecipientAddress(new Address());
        shipment.getRecipientAddress().setLine1("René Wörzberger");
        shipment.getRecipientAddress().setLine2("Claudiusstraße 16");
        shipment.getRecipientAddress().setLine3("51503 Rösrath");
        
        //when
        Shipment persistedShipment = shipmentService.createShipment(shipment, testCustomer.getId());

        //then
        assertNotNull("shipmentNumber has not been generated", persistedShipment.getShipmentNumber());
    }

    @Test(expected = InvalidAddressException.class)
    public void testCreateShipmentWithInvalidAddress() {
        //given
        Shipment shipment = new Shipment();
        shipment.setRecipientAddress(new Address());
        shipment.getRecipientAddress().setLine1("René Wörzberger");
        shipment.getRecipientAddress().setLine2("Claudiusstraße 16");
        shipment.getRecipientAddress().setLine3("5064 Rösrath 1");

        //when
        shipmentService.createShipment(shipment, testCustomer.getId());


        //then expect a InvalidAddressException to be thrown (s.a.)
    }
}
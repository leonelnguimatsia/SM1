
package de.thk.parcer.parcer.services;

import de.thk.parcer.parcer.domain.Address;
import de.thk.parcer.parcer.domain.Customer;
import de.thk.parcer.parcer.domain.Label;
import de.thk.parcer.parcer.domain.Shipment;
import de.thk.parcer.parcer.exceptions.InvalidAddressException;
import de.thk.parcer.parcer.exceptions.ResourceNotFoundException;
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
public class LabelServiceImplTest {

    @Autowired
    private LabelService labelService;


    private Customer testCustomer;


    @BeforeTransaction
    public void setUp() {
    }

    @Test
    public void testCreateLabelWithValidAddress() {
        //given

        Address address = new Address();
        address.setLine1("Moritz Jäger");
        address.setLine2("Eselspfad 99A");
        address.setLine3("41464 Neuss");

        Label label  = labelService.createLabel(address, "1111");

        //then
        assertNotNull("Label has not been generated", label.getId());
    }

    @Test
    public void testGetLabelById() {
        //given

        Address address = new Address();
        address.setLine1("Moritz Jäger");
        address.setLine2("Eselspfad 99A");
        address.setLine3("41464 Neuss");

        Label l1 = labelService.createLabel(address, "1111");

        Label l2  = labelService.getLabelById(l1.getId());

        //then
        assertNotNull("Label has not been generated", l2.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetLabelByIdInvalidId() {
        //given

        Label l2  = labelService.getLabelById(323222222);
    }


}

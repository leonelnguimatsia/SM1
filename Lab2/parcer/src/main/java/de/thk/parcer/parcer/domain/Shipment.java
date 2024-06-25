package de.thk.parcer.parcer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Class for a single shipment. I.e., this resource represents an order to
 * ship a parcel to the given recipient address.
 */
@Entity
public class Shipment {

    @GeneratedValue
    @Id
    private Long id;
    private String shipmentNumber;
    @Embedded
    private Address recipientAddress;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    @JsonIgnore
    @OneToOne
    private Label label;

    /**
     * @return the shipmentNumber
     */
    public String getShipmentNumber() {
        return shipmentNumber;
    }

    /**
     * @param shipmentNumber the shipmentNumber to set
     */
    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    /**
     * @return the receiver address
     */
    public Address getRecipientAddress() {
        return recipientAddress;
    }

    /**
     * @param address the receiver address to set
     */
    public void setRecipientAddress(Address address) {
        this.recipientAddress = address;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(Label label) {
        this.label = label;
    }


}
package de.thk.parcer.parcer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Class for customers. Note, that a customer is not necessarily a single user but
 * typically the an employer of the user of some client of Parcer.
 */
@Entity
public class Customer {
    @GeneratedValue
    @Id
    private long id;
    private String name;
    @OneToMany(mappedBy = "customer")
    @JsonIgnore // avoid infinite JSON recursion 
    private Set<Shipment> shipments;

    /**
     * @return the id, which is generated and therefore there is no setId(long)
     */
    public long getId() {
        return id;
    }

    /**
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the customer's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return all shipments that belong to this customer
     */
    public Set<Shipment> getShipments() {
        return shipments;
    }
}
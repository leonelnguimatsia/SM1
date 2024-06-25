package de.thk.parcer.parcer.repositories;

import de.thk.parcer.parcer.domain.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Data access to for persisted customers.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
package de.thk.parcer.parcer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.thk.parcer.parcer.domain.Customer;
import de.thk.parcer.parcer.exceptions.ResourceNotFoundException;
import de.thk.parcer.parcer.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Iterable<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getCustomer(long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()){
			return customer.get();		
		} else {
			throw new ResourceNotFoundException("customer with id '" + id + "' not found");
		}
	}
}
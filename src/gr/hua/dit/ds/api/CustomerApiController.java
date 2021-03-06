package gr.hua.dit.ds.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.dit.ds.entity.Customer;
import gr.hua.dit.ds.entity.CustomerList;
import gr.hua.dit.ds.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerList customerList;

	// get list of customers
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public CustomerList getListCustomers() {
		System.out.println("Before making list");
		List<Customer> customers = customerService.getCustomers();
		System.out.println("List of customers: " + customers);
		this.customerList.setCustomerList(customers);
		return this.customerList;
	}	

	// save customer
	@CrossOrigin
	@RequestMapping(value = "/jsonaddcustomer", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public Customer createCustomerFromJson(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return customer;
	}

	// update customer
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
	public Customer updateCustomer(@RequestParam("afm") int afm, @RequestParam("worker_id") int worker_id) {
		Customer customer = new Customer();
		customer.setAfm(afm);
		customer.setWorkerID(worker_id);
		Customer temp = customerService.getCustomer(afm);
		customer.setFirstName(temp.getFirstName());
		customer.setLastName(temp.getLastName());
		customer.setUsername(temp.getUsername());
		customer.setPassword(temp.getPassword());
		customerService.updateCustomer(customer);
		System.out.println(customer.toString());
		return customer;
	}

	// get customer
	@CrossOrigin
	@RequestMapping(value = "/{afm}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public Customer getCustomer(@PathVariable("afm") int afm) {

		Customer customer = customerService.getCustomer(afm);
		System.out.println("customer: " + customer);
		return customer;

	}

	// delete customer
	@RequestMapping(value = "/delete/{afm}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	public ResponseEntity deleteCustomer(@PathVariable("afm") int afm) {
		customerService.deleteCustomer(afm);
		return new ResponseEntity(HttpStatus.OK);
	}

}

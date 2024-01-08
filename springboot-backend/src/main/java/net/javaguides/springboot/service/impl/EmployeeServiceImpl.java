package net.javaguides.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	 private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class.getName());
	
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}



	@Override
	@Transactional
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}



	@Override
	@Transactional
	public List<Employee> getAllEmployee() {
		System.out.println("In List");
		logger.info("Successfull");
		return employeeRepository.findAll();
	
	}



	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new ResourceNotFoundException("Employee", "ID", id);
		}
	}



	@Override
	public Employee updateEmployee(Employee employee, long id) {
		// we need to check whether the given Employee exists in DB or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
				()->  new ResourceNotFoundException("Employee", "ID", id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		
		//save existing employee to database
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	}



	



	@Override
	public void deleteEmployee(long id) {
		// we need to check whether id exists in DB or not
		employeeRepository.findById(id).orElseThrow(
				()->  new ResourceNotFoundException("Employee", "ID", id));
		employeeRepository.deleteById(id);
		
	}
	
	

}

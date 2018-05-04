package com.example.employee.restfulapi.controller;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    //在此处完成Employee API
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(value ="/employees")
    public List<Employee> findAllEmployees(){
     return  employeeRepository.findAll();
    }

    @GetMapping(value = "/employees/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long id) {
        return employeeRepository.findOne(id);
    }

    @GetMapping(value = "/employees/page/{page}/pageSize/{pageSize}")
    public Page<Employee> findEmployeesOnPage(@PathVariable("page") int page,@PathVariable("pageSize") int pageSize) {
        return employeeRepository.findAll(new PageRequest(page,pageSize));
    }

    @GetMapping(value = "/employees/male")
    public List<Employee> findMaleEmployee() {
     return employeeRepository.findByGenderEquals("male");
    }

    @PostMapping(value="/employees")
    public Employee addOneEmployee(@RequestParam String name,@RequestParam Integer age,@RequestParam String gender,
                                   @RequestParam Integer salary ,@RequestParam Long companyId)
    {
        Employee addEmployee=new Employee(name,age,gender,salary,companyId);
        employeeRepository.save(addEmployee);
        return addEmployee;
    }
    @PutMapping(value="/employees/{id}")
    public String updateEmployeeById(@PathVariable("id") Long id,@RequestParam String name,
                                     @RequestParam int age,@RequestParam String gender,
                                     @RequestParam Long companyId,@RequestParam int salary){
        Employee updateEmployee= employeeRepository.findOne(id);
        updateEmployee.setName(name);
        updateEmployee.setAge(age);
        updateEmployee.setGender(gender);
        updateEmployee.setCompanyId(companyId);
        updateEmployee.setSalary(salary);
        employeeRepository.save(updateEmployee);
        return "update Employee success";
    }

    @DeleteMapping(value="/employees/{id}")
    public String deleteEmployeeById(@PathVariable("id") Long id){
        employeeRepository.delete(id);
        return "delete Employee success";
    }
}

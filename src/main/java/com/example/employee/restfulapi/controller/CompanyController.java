package com.example.employee.restfulapi.controller;

import com.example.employee.restfulapi.entity.Company;
import com.example.employee.restfulapi.entity.Employee;
import com.example.employee.restfulapi.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CompanyController {
    //在此处完成Company API
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping(value ="/companies")
    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    @GetMapping(value = "/companies/{id}")
    public Company getCompanyById(@PathVariable("id") Long id) {
        return companyRepository.findOne(id);
    }

    @GetMapping(value = "/companies/{id}/employees")
    public Set<Employee> getEmployeeOfOneCompany(@PathVariable("id") Long id) {
        return companyRepository.findOne(id).getEmployees();
    }

    @GetMapping(value = "/companies/page/{page}/pageSize/{pageSize}")
    public Page<Company> findAllOnPage(@PathVariable("page") int page,@PathVariable("pageSize") int pageSize) {
        return companyRepository.findAll(new PageRequest(page,pageSize));
    }

    @PostMapping(value = "/companies")
    public Company addOneCompany(@RequestParam String companyName,@RequestParam int employeesNumber) {
        Company addCompany=new Company(companyName,employeesNumber);
        companyRepository.save(addCompany);
        return addCompany;
    }
    @PutMapping(value ="/companies/{id}")
    public String updateCompanyById(@PathVariable("id") Long id,@RequestParam String companyName,@RequestParam int employeesNumber) {
        Company updateCompany= companyRepository.findOne(id);
        updateCompany.setCompanyName(companyName);
        updateCompany.setEmployeesNumber(employeesNumber);
        companyRepository.save(updateCompany);
        return "update success";
    }

    @DeleteMapping(value = "/companies/{id}")
    public String deleteCompanyAndEmployeesById(@PathVariable("id") Long id) {
        companyRepository.delete(id);
        return "delete success";
    }

}

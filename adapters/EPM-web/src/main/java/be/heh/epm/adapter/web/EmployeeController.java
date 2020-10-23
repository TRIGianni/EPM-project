package be.heh.epm.adapter.web;

import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.application.port.out.InMemoryEmployeeGateway;
import be.heh.epm.application.service.AddSalariedEmployee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmployeeController {

    @PostMapping("/employees/salaried")
    ResponseEntity<EmployeeSalariedValidating> newEmployee(@Valid @RequestBody EmployeeSalariedValidating newEmployee) {
        EmployeeGateway employeeGateway = new InMemoryEmployeeGateway();
        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(employeeGateway);
        addSalariedEmployee.execute(newEmployee);
        //return repository.save(newBook);
        return new ResponseEntity<EmployeeSalariedValidating>(newEmployee,HttpStatus.CREATED);

    }
}

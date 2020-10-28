package be.heh.epm.adapter.web;

import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.application.port.out.InMemoryEmployeeGateway;
import be.heh.epm.application.service.AddSalariedEmployee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class EmployeeController {

    @CrossOrigin
    @PostMapping("/employees/salaried")
    ResponseEntity<Void> newEmployee(@Valid @RequestBody EmployeeSalariedValidating newEmployee) {
        EmployeeGateway employeeGateway = new InMemoryEmployeeGateway();
        AddSalariedEmployee addSalariedEmployee = new AddSalariedEmployee(employeeGateway);
        addSalariedEmployee.execute(newEmployee);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getEmpId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

package be.heh.epm.adapter.web;

import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.in.AddSalariedEmployeeUseCase;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.application.service.AddSalariedEmployeeService;
import be.heh.epm.common.WebAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@WebAdapter
@RestController
public class EmployeeController {

    private AddSalariedEmployeeUseCase addSalariedEmployee;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    public EmployeeController(AddSalariedEmployeeUseCase addSalariedEmployee){
        this.addSalariedEmployee = addSalariedEmployee;
    }

    @CrossOrigin
    @PostMapping("/employees/salaried")
    ResponseEntity<Void> newEmployee(@Valid @RequestBody EmployeeSalariedValidating newEmployee) {

        logger.info("Creating new employee with name: {}, address: {}\", newEmployee.getName()");

        addSalariedEmployee.execute(newEmployee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getEmpId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

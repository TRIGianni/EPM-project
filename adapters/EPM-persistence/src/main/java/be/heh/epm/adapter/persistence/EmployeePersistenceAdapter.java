package be.heh.epm.adapter.persistence;

import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.PersistenceAdapter;
import be.heh.epm.domain.Employee;
import be.heh.epm.domain.SalariedClassification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@PersistenceAdapter
public class EmployeePersistenceAdapter implements EmployeePort {

    private static final Logger logger = LoggerFactory.getLogger(EmployeePersistenceAdapter.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertEmployee;
    private final SimpleJdbcInsert simpleJdbcInsertSalariedClassification;
    private final DataSource dataSource;

    public EmployeePersistenceAdapter(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        this.simpleJdbcInsertEmployee = new SimpleJdbcInsert(dataSource).withTableName("employee")
                .usingGeneratedKeyColumns("empid");
        this.simpleJdbcInsertSalariedClassification = new SimpleJdbcInsert(dataSource).withTableName("salariedclassification");
    }

    @Override
    public Employee getEmployee(int empID) {
        try {
            Employee employee = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE WHERE EMPID = ?",
                    new Object[]{empID},
                    (rs, rowNum) -> {
                        Employee e = new Employee();
                        e.setEmpID(rs.getInt("EMPID"));
                        e.setName(rs.getString("NAME"));
                        e.setAddress(rs.getString("ADDRESS"));
                        e.setMail(rs.getString("MAIL"));
                        return e;
                    });
            logger.info("Recovery of the employee by id {} in the database",empID);
            return employee;
        } catch (EmptyResultDataAccessException e) {
            logger.error("Employee with id {} was not found",empID);
            return null;
        }
    }

    @Override
    public Employee save(Employee employee) {

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", employee.getName());
        parameters.put("address", employee.getAddress());
        parameters.put("mail", employee.getMail());
        parameters.put("paymentclassificationtype",employee.getPayClassification().toString());
        parameters.put("paymentmethodtype",employee.getPayMethod().toString());
        parameters.put("paymentscheduletype",employee.getPaySchedule().toString());

        // Execute the query and get the generated key
        Number newId = simpleJdbcInsertEmployee.executeAndReturnKey(parameters);

        logger.info("Inserting salaried employee into database, generated key is: {}", newId);

        employee.setEmpID((Integer) newId);

        if(employee.getPayClassification().toString()=="salaried"){
            Map<String, Object> parametersSalariedClassification = new HashMap<>(1);
            parametersSalariedClassification.put("EMPID", employee.getEmpID());
            SalariedClassification salariedClassification = (SalariedClassification)employee.getPayClassification();
            parametersSalariedClassification.put("MOUNT", salariedClassification.getSalary());
            simpleJdbcInsertSalariedClassification.execute(parametersSalariedClassification);

        }



        return employee;
    }

    @Override
    public void deleteEmployee(int empID) {

    }

    @Override
    public ArrayList<Employee> getAllEmployee() {
        return null;
    }
}

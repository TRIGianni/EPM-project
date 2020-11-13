package be.heh.epm.adapter.persistence;

import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.PersistenceAdapter;
import be.heh.epm.domain.Employee;
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
    private final SimpleJdbcInsert simpleJdbcInsert;

    public EmployeePersistenceAdapter(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;

        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("EMPLOYEE")
                .usingGeneratedKeyColumns("EMPID");
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
        parameters.put("NAME", employee.getName());
        parameters.put("ADDRESS", employee.getAddress());
        parameters.put("MAIL", employee.getMail());

        // Execute the query and get the generated key
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);

        logger.info("Inserting salaried employee into database, generated key is: {}", newId);

        employee.setEmpID((Integer) newId);

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

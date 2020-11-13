package be.heh.epm.adapter.persistence;

import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.domain.Employee;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@SpringBootTest
public class EmployeePersistenceAdapterTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmployeePersistenceAdapter employeePersistenceAdapter;

    @Test
    void testSave() {
        //JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //employeePersistenceAdapter = new EmployeePersistenceAdapter(jdbcTemplate,dataSource);
        Employee employee = new Employee("toto", "rue de Mons", "toto@heh.be");

        Employee SavedEmployee = employeePersistenceAdapter.save(employee);

        Assertions.assertEquals("toto", SavedEmployee.getName());
        Assertions.assertEquals("rue de Mons", SavedEmployee.getAddress());

        Employee loadedEmployee = employeePersistenceAdapter.getEmployee(SavedEmployee.getEmpID());
        Assertions.assertEquals("toto", loadedEmployee.getName(), "Employee name does not match");
        Assertions.assertEquals("toto@heh.be", loadedEmployee.getMail(), "Employee mail does not match");
    }

}

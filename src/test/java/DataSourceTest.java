import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.inject.Inject;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring-config/application-context.xml"})
public class DataSourceTest {



    @Inject
    private DataSource dataSource;

    @Test
    public void testConnection() throws Exception {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}
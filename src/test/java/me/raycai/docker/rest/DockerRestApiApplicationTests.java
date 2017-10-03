package me.raycai.docker.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import me.raycai.docker.rest.model.DatabaseCreation;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DockerRestApiApplicationTests {
  
  RestTemplate restTemplate;
  final String baseUrl="http://127.0.0.1:8080/";
  
  @Before
  public void setUp() {
    restTemplate = new RestTemplate();
  }

  @Test
  public void testOracleDatabaseCreateAndDestroy() throws Exception {
    final DatabaseCreation entity = restTemplate
        .postForObject(baseUrl+"database/oracle/create", StringUtils.EMPTY, DatabaseCreation.class);
    

    restTemplate.delete(baseUrl+"database/oracle/{containerId}", entity.getId());
  }

}

package me.raycai.docker.rest;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.PortBinding;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.raycai.docker.rest.model.DatabaseCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("database/oracle")
public class OracleDatabaseController {

  @Autowired
  DockerClient dockerClient;

  @RequestMapping(path = "create", method = RequestMethod.POST, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public DatabaseCreation create()
      throws DockerException, InterruptedException, UnknownHostException {
    final String containerPort = "1521";
    final PortBinding hostPortBinding = PortBinding.randomPort("0.0.0.0");
    

    final Map<String, List<PortBinding>> portBindings = new HashMap<>();
    portBindings.put(containerPort, Arrays.asList(hostPortBinding));

    final HostConfig hostConfig = HostConfig.builder().portBindings(portBindings).build();
    // Create container with exposed ports
    final ContainerConfig containerConfig = ContainerConfig.builder()
        .env("WEB_CONSOLE=false")
        .hostConfig(hostConfig)
        .image("sath89/oracle-12c").exposedPorts(containerPort)
        .build();

    final ContainerCreation creation = dockerClient.createContainer(containerConfig);
    final String id = creation.id();

    

    // Start container
    dockerClient.startContainer(id);

    // Inspect container
    final ContainerInfo info = dockerClient.inspectContainer(id);
    final String hostPort = info.networkSettings().ports().get("1521/tcp").get(0).hostPort();

    final String url = String
        .format("jdbc:oracle:thin:@%s:%s:%s", InetAddress.getLocalHost().getHostName(), hostPort,
            "xe");
    final String username = "system";
    final String password = "oracle";

    return new DatabaseCreation(id, url, username, password);
  }

  @RequestMapping(path = "{containerId}", method = RequestMethod.DELETE)
  public void destroy(@PathVariable("containerId") String containerId)
      throws DockerException, InterruptedException {
    dockerClient.killContainer(containerId);
    dockerClient.removeContainer(containerId);
  }
}

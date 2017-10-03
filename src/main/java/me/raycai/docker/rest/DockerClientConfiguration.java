package me.raycai.docker.rest;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfiguration {

  @Bean
  DockerClient dockerClient() {
    try {
      final DockerClient docker = DefaultDockerClient.fromEnv().build();
      return docker;
    } catch (DockerCertificateException ex) {
      throw new BeanCreationException(ex.getMessage(), ex);
    }
  }
}

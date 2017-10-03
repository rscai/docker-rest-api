package me.raycai.docker.rest.model;

public class DatabaseCreation {
  private  String id;
  private  String url;
  private  String username;
  private  String password;
  
  public DatabaseCreation(){
    
  }

  public DatabaseCreation(String id, String url, String username, String password) {
    this.id = id;
    this.url = url;
    this.username = username;
    this.password = password;
  }

  public String getId() {
    return id;
  }

  public DatabaseCreation setId(String id) {
    this.id = id;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public DatabaseCreation setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public DatabaseCreation setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public DatabaseCreation setPassword(String password) {
    this.password = password;
    return this;
  }
}

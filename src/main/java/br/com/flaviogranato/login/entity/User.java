package br.com.flaviogranato.login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


public class User {
  private Long userId;
  private String userName;
  private String encrytedPassword;


  @JsonCreator
  public User(@JsonProperty("userId") Long userId,
              @JsonProperty("userName") String userName,
              @JsonProperty("encryptedPassword") String encrytedPassword) {
    this.userId = userId;
    this.userName = userName;
    this.encrytedPassword = encrytedPassword;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(getUserName(),
                          user.getUserName()) && Objects.equals(getEncrytedPassword(),
                                                                user.getEncrytedPassword());
  }


  @Override
  public int hashCode() {
    return Objects.hash(getUserName(),
                        getEncrytedPassword());
  }


  @Override
  public String toString() {
    return "User{" + "userId=" + userId + ", userName='" + userName + '\'' + ", encrytedPassword='" + encrytedPassword + '\'' + '}';
  }


  public Long getUserId() {
    return userId;
  }


  public String getUserName() {
    return userName;
  }


  public String getEncrytedPassword() {
    return encrytedPassword;
  }
}

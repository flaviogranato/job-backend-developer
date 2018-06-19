package br.com.flaviogranato.login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Token {
  private String application;
  private String apiToken;


  @JsonCreator
  public Token(@JsonProperty("application") String application,
               @JsonProperty("apiToken") String apiToken) {
    this.application = application;
    this.apiToken = apiToken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Token)) {
      return false;
    }
    Token token = (Token) o;
    return Objects.equals(getApplication(),
                          token.getApplication()) && Objects.equals(getApiToken(),
                                                                    token.getApiToken());
  }


  @Override
  public int hashCode() {
    return Objects.hash(getApplication(),
                        getApiToken());
  }


  @Override
  public String toString() {
    return "Token{" + "application='" + application + '\'' + ", apiToken='" + apiToken + '\'' + '}';
  }


  public String getApplication() {
    return application;
  }


  public String getApiToken() {
    return apiToken;
  }
}

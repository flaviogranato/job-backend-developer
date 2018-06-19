package br.com.flaviogranato.login.infra;

import br.com.flaviogranato.login.entity.Token;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class TokenMapper implements RowMapper<Token> {
  @Override
  public Token mapRow(ResultSet rs,
                      int rowNum) throws SQLException {
    String application = rs.getString("application");
    String apiToken = rs.getString("api_token");

    return new Token(application,
                     apiToken);
  }
}

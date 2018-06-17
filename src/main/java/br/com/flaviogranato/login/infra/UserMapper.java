package br.com.flaviogranato.login.infra;

import br.com.flaviogranato.login.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    Long userId = rs.getLong("id");
    String userName = rs.getString("username");
    String encrytedPassword = rs.getString("password");

    return new User(userId, userName, encrytedPassword);
  }
}

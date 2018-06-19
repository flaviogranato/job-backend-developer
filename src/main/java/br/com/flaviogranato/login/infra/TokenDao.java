package br.com.flaviogranato.login.infra;

import br.com.flaviogranato.login.entity.Token;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TokenDao extends JdbcDaoSupport {
  @Qualifier("token")
  @Autowired
  private RedisTemplate<String,Token> redisTemplate;


  @Autowired
  public TokenDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }

  public Token get(String application,
                   String apiToken) {
    final String REDIS_PREFIX = "token:";
    Token token;
    String sql = "SELECT t.application,\n" +
                 "       t.api_token\n" +
                 "FROM tokens t\n" +
                 "WHERE t.application = ?\n" +
                 "  AND t.api_token = ?";

    Object[] params = new Object[]{application, apiToken};
    TokenMapper mapper = new TokenMapper();
    try {
      Token tokenFromRedis = redisTemplate.opsForValue()
                                        .get(REDIS_PREFIX + apiToken);

      if (tokenFromRedis != null) {
        token = tokenFromRedis;
      } else {
        token = this.getJdbcTemplate()
                       .queryForObject(sql,
                                       params,
                                       mapper);
        redisTemplate.opsForValue()
                     .set(REDIS_PREFIX + apiToken,
                          token);
      }
      return token;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}

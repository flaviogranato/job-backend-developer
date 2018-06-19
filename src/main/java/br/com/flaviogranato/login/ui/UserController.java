package br.com.flaviogranato.login.ui;

import br.com.flaviogranato.login.entity.Token;
import br.com.flaviogranato.login.entity.User;
import br.com.flaviogranato.login.infra.TokenDao;
import br.com.flaviogranato.login.infra.UserDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired
  private UserDao userDao;
  @Autowired
  private TokenDao tokenDao;


  @RequestMapping(value = "/users",
                  method = RequestMethod.GET)
  public ResponseEntity<List<User>> listAll(@RequestHeader(value = "application") String application,
                                            @RequestHeader(value = "api-token") String apiToken) {
    ResponseEntity<List<User>> result;
    List<User> users = userDao.listAll();
    Token token = tokenDao.get(application,
                               apiToken);

    if (token == null) {
      result = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } else {
      if (users == null) {
        result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        result = new ResponseEntity<>(users,
                                      HttpStatus.OK);
      }
    }
    return result;
  }


  @RequestMapping(value = "/user/{id}",
                  method = RequestMethod.GET)
  public ResponseEntity<User> get(@RequestHeader(value = "application") String application,
                                  @RequestHeader(value = "api-token") String apiToken,
                                  @PathVariable("id") Integer id) {
    ResponseEntity<User> result;
    User user = userDao.get(id);
    Token token = tokenDao.get(application,
                               apiToken);

    if (token == null) {
      result = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } else {
      if (user == null) {
        result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else {
        result = new ResponseEntity<>(user,
                                      HttpStatus.OK);
      }
    }
    return result;
  }
}

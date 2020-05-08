package com.springmvc.app.controller;

import com.springmvc.app.DAO.UserDao;
import com.springmvc.app.beans.User;
import com.springmvc.app.exceptions.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
 // http://localhost:8080/users command to run :- mvn spring-boot:run
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;

    @GetMapping(path = "/users/{id}")
    public EntityModel<User> getUser(@PathVariable String id) throws Exception{
        logger.info("UserController getUser called for user");
        User user = userDao.getUser(id);
        if(user == null) {
            throw new UserNotFoundException("id - {} not found " + id);
        }

        //Resource is EntityModel now in hateoas
        //ControllerLinkBuilder is WebMvcLinkBuilder
        EntityModel<User> userEntityModel = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());

        userEntityModel.add(linkTo.withRel("all-users"));

        return userEntityModel;
    }

    @PostMapping(path = "/users")
    public ResponseEntity createUsers(@Valid @RequestBody User user) {
        logger.info("UserController createuser called");
        User addedUser = userDao.addUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(addedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        logger.info("UserController getAllUsers called");
        return userDao.getAllUsers();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable String id) {
        logger.info("UserController deleteUser called");
        User user = userDao.deleteUser(id);
        if(user == null) {
            throw new UserNotFoundException("user not found");
        }
    }
}

package com.springmvc.app.controller;

import com.springmvc.app.beans.Post;
import com.springmvc.app.repository.PostRepository;
import com.springmvc.app.repository.UserRepository;
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
import java.util.Optional;

@RestController
public class UserRepositoryController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable String id) {
        logger.info("UserRepoController getUser called for user");
        Optional<User> userOptional = userRepository.findById(id);
        logger.info("user found", userOptional);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id - {} not found " + id);
        }

        User user = userOptional.get();
        //Resource is EntityModel now in hateoas
        //ControllerLinkBuilder is WebMvcLinkBuilder
        EntityModel<User> userEntityModel = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());

        userEntityModel.add(linkTo.withRel("all-users"));

        return userEntityModel;
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity createUsers(@Valid @RequestBody User user) {
        logger.info("UserRepoController createuser called");
        User addedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(addedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/jpa/users")
    public List<User> getAllUsers() {
        logger.info("UserRepoController getAllUsers called");
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable String id) {
        logger.info("UserRepoController deleteUser called");
        userRepository.deleteById(id);
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity createUserPost(@PathVariable String id,@Valid @RequestBody Post post) {
        logger.info("UserRepoController createUserPost called");
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found with id - " + id);
        }

        User user = userOptional.get();
        post.setUser(user);

        postRepository.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> getAllUsersPost(@PathVariable String id) {
        logger.info("UserRepoController getAllUsersPosts called");
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found with id - " + id);
        }

        return userOptional.get().getPosts();
    }
}

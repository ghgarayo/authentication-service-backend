package br.com.ghx.authenticationservice.controller;

import br.com.ghx.authenticationservice.dto.UserInput;
import br.com.ghx.authenticationservice.dto.UserOutput;
import br.com.ghx.authenticationservice.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Inject
    private UserServiceImpl service;

    @PostMapping
    ResponseEntity<UserOutput> register(@RequestBody @Valid UserInput input, UriComponentsBuilder uriComponentsBuilder){
        var isAdmin = !Objects.isNull(input.isAdmin()) && input.isAdmin();
        var user = service.register(input.name(), input.username(), input.password(), isAdmin);
        var uri = uriComponentsBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserOutput(user));

    }

}

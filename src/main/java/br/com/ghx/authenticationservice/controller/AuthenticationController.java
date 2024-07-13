package br.com.ghx.authenticationservice.controller;

import br.com.ghx.authenticationservice.domain.User;
import br.com.ghx.authenticationservice.dto.AuthenticationRequestDTO;
import br.com.ghx.authenticationservice.dto.TokenJwtDTO;
import br.com.ghx.authenticationservice.services.impl.TokenServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Inject
    private AuthenticationManager manager;

    @Inject
    private TokenServiceImpl tokenService;

    @PostMapping
    public ResponseEntity<TokenJwtDTO> login(@RequestBody @Valid AuthenticationRequestDTO dto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = manager.authenticate(authenticationToken);
        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok().body(new TokenJwtDTO(token));

    }


}

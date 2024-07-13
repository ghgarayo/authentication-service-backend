package br.com.ghx.authenticationservice.services;

import br.com.ghx.authenticationservice.domain.User;

public interface TokenService {

    String generateToken(User user);

    String getSubject(String tokenJwt);

}

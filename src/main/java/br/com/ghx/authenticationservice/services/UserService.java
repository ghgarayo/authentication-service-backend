package br.com.ghx.authenticationservice.services;

import br.com.ghx.authenticationservice.domain.User;

public interface UserService {

    User register(String name, String username, String password, boolean isAdmin);

}

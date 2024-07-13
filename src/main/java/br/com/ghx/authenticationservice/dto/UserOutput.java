package br.com.ghx.authenticationservice.dto;

import br.com.ghx.authenticationservice.domain.User;

public record UserOutput(String name, String username, String password) {

    public UserOutput(User user){
        this(user.getName(), user.getUsername(), user.getPassword());
    }
    
}

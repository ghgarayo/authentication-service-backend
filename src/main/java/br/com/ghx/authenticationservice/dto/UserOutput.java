package br.com.ghx.authenticationservice.dto;

import br.com.ghx.authenticationservice.domain.User;

public record UserOutput(String name, String username) {

    public UserOutput(User user){
        this(user.getName(), user.getUsername());
    }
    
}

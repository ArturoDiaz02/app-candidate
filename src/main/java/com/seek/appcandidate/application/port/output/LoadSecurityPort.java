package com.seek.appcandidate.application.port.output;

import org.springframework.security.core.userdetails.UserDetails;

public interface LoadSecurityPort {
    String generateToken(UserDetails userDetails) ;
}

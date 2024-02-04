package com.seek.appcandidate.infrastructure.adapter.rest.auth;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(IAuthAPI.BASE_URL)
public interface IAuthAPI {
    String BASE_URL = "/api/v1/auth";
}

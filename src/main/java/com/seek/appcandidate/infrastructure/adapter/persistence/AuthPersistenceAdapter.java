package com.seek.appcandidate.infrastructure.adapter.persistence;

import com.seek.appcandidate.infrastructure.common.Adapter;
import com.seek.appcandidate.infrastructure.repository.IAuthRepository;
import lombok.AllArgsConstructor;

@Adapter
@AllArgsConstructor
public class AuthPersistenceAdapter {

    private final IAuthRepository authRepository;
}

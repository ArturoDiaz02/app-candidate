package com.seek.appcandidate.infrastructure.adapter.firebase;

import com.seek.appcandidate.application.port.output.LoadAuthPort;
import com.seek.appcandidate.application.port.output.UpdateAuthPort;
import com.seek.appcandidate.infrastructure.common.Adapter;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class FirebaseAdapter implements LoadAuthPort, UpdateAuthPort {
}

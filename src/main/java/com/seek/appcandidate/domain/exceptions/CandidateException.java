package com.seek.appcandidate.domain.exceptions;

import com.seek.appcandidate.domain.enums.EErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@Builder
public class CandidateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private CustomError customError;

    public CandidateException(CustomError customError) {
        super("", null, false, true);
        this.customError = customError;
    }

    public CandidateException(EErrorCode error, String... args) {
        super();
        List<CustomDetail> details = List.of(CustomDetail.builder()
                .errorCode(error.getStatus().value())
                .errorMessage(String.format(error.getDetails(), args))
                .build()
        );
        this.customError = CustomError.builder()
                .status(error.getStatus())
                .details(details)
                .build();
    }

}

package com.seek.appcandidate.domain.exceptions;

import com.seek.appcandidate.domain.enums.EExceptionDetails;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Builder
public class CandidateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private List<EExceptionDetails> exceptionDetails;

    public CandidateException(HttpStatus status, EExceptionDetails exceptionDetails) {
        super(exceptionDetails.getMessage(), null, false, true);
        this.status = status;
        this.exceptionDetails = List.of(exceptionDetails);
    }

    public CandidateException(HttpStatus status, List<EExceptionDetails> exceptionDetails) {
        super();
        this.status = status;
        this.exceptionDetails = exceptionDetails;
    }
    public CandidateException(String message) {
        super(message);
    }

}

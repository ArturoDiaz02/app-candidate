package com.seek.appcandidate.infrastructure.handlers;

import com.seek.appcandidate.domain.enums.EExceptionDetails;
import com.seek.appcandidate.domain.exceptions.CandidateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<RuntimeException>> handleRuntimeException(RuntimeException runtimeException){
        logger.error(runtimeException.getMessage());
        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(runtimeException)
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Throwable>> handleCodecException(WebExchangeBindException ex){
        logger.error(ex.getMessage() + " " + ex.getBindingResult().getAllErrors());
        List<EExceptionDetails> details = ex.getBindingResult().getAllErrors().stream().map(this::mapBindingResultToError).toList();
        var error = CandidateException.builder().status(HttpStatus.BAD_REQUEST).exceptionDetails(details).build();
        return Mono.just(ResponseEntity.status(error.getStatus()).body(error));
    }

    @ExceptionHandler(CandidateException.class)
    public Mono<ResponseEntity<CandidateException>> handleCandidateException(CandidateException candidateException){
        logger.error(candidateException.getMessage());
        return Mono.just(ResponseEntity.status(candidateException.getStatus()).body(candidateException));
    }

    private EExceptionDetails mapBindingResultToError(ObjectError objectError){
        var field = ((DefaultMessageSourceResolvable) Objects.requireNonNull(objectError.getArguments())[0]).getDefaultMessage();
        var message = EExceptionDetails.ARGUMENT_NOT_VALID.getMessage().formatted(field, objectError.getDefaultMessage());
        return EExceptionDetails.ARGUMENT_NOT_VALID.withMessage(message);
    }

}

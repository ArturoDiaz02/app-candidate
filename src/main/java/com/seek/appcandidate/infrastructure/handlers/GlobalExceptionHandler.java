package com.seek.appcandidate.infrastructure.handlers;

import com.seek.appcandidate.domain.enums.EErrorCode;
import com.seek.appcandidate.domain.exceptions.CandidateException;

import com.seek.appcandidate.domain.exceptions.CustomDetail;
import com.seek.appcandidate.domain.exceptions.CustomError;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.NestedRuntimeException;
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
    public Mono<ResponseEntity<CustomError>> handleRuntimeException(RuntimeException runtimeException){
        logger.error(runtimeException.getMessage());
        CustomDetail customDetail = CustomDetail.builder()
                .errorCode(EErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .errorMessage(runtimeException.getMessage())
                .build();

        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(CustomError.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .details(List.of(customDetail))
                                .build()
                        )
        );
    }

    @ExceptionHandler(NestedRuntimeException.class)
    public Mono<ResponseEntity<CustomError>> handleCodecException(NestedRuntimeException codecException){
        logger.error(codecException.getMessage());
        CustomDetail customDetail = CustomDetail.builder()
                .errorCode(EErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .errorMessage(codecException.getCause().getMessage())
                .build();

        return Mono.just(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(CustomError.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .details(List.of(customDetail))
                                .build()
                        )
        );
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<CustomError>> handleWebExchangeBindException(WebExchangeBindException ex){
        logger.error(ex.getMessage() + " " + ex.getBindingResult().getAllErrors());
        var details = ex.getBindingResult().getAllErrors().stream().map(this::mapBindingResultToError).toList();
        var customError = CustomError.builder().status(HttpStatus.BAD_REQUEST).details(details).build();
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customError));
    }

    @ExceptionHandler(CandidateException.class)
    public Mono<ResponseEntity<CustomError>> handleCandidateException(CandidateException candidateException){
        return Mono.just(
                ResponseEntity.status(
                        candidateException.getCustomError().getStatus()
                ).body(
                        candidateException.getCustomError()
                )
        );
    }

    private CustomDetail mapBindingResultToError(ObjectError objectError){
        var field = ((DefaultMessageSourceResolvable) Objects.requireNonNull(objectError.getArguments())[0]).getDefaultMessage();
        var message = EErrorCode.ARGUMENT_NOT_VALID.getDetails().formatted(field, objectError.getDefaultMessage());
        return CustomDetail.builder()
                .errorCode(EErrorCode.ARGUMENT_NOT_VALID.getStatus().value())
                .errorMessage(message)
                .build();
    }

}

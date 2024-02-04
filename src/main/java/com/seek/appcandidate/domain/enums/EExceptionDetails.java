package com.seek.appcandidate.domain.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum EExceptionDetails {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error: %s"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request: %s %s"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found: %s"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed: %s %s %s"),
    ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "%s with %s: %s not found"),
    PARSE_DATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing date: %s"),
    CANDIDATE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Candidate already exists by email or phone"),
    CANDIDATE_NOT_FOUND(HttpStatus.NOT_FOUND, "Candidate not found: %s"),
    DELETE_CANDIDATE_ERROR(HttpStatus.NOT_FOUND, "Error deleting candidate: %s"),
    DELETE_ALL_CANDIDATES_ERROR(HttpStatus.NOT_FOUND, "Error deleting all candidates: %s");

    private HttpStatus status;
    private String message;

    EExceptionDetails(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    EExceptionDetails(HttpStatus status) {
        this.status = status;
        this.message = null;

    }
    EExceptionDetails() {
        this.status = null;
        this.message = null;
    }

    public EExceptionDetails withMessage(String message){
        this.message = message;
        return this;
    }

    public EExceptionDetails withStatus(HttpStatus status){
        this.status = status;
        return this;
    }

    public EExceptionDetails formatMessage(String... args){
        this.message = String.format(this.message, args);
        return this;
    }
}

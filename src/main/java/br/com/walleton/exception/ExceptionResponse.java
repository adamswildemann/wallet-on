package br.com.walleton.exception;

import java.time.OffsetDateTime;

public record ExceptionResponse(OffsetDateTime timestamp, String message, String details) {

}

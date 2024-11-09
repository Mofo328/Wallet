package wallet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler(NotEnoughFunds.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleRequestConflictException(final NotEnoughFunds e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler(InvalidOperationType.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final InvalidOperationType e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(final Throwable e) {
        return new ErrorResponse(
                e.getMessage()
        );
    }

    @AllArgsConstructor
    @Getter
    private class ErrorResponse {
        private String error;
    }
}

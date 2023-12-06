package pro.sky.animalizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение выбрасывается в случае если приют не найден
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShelterNotFoundException extends RuntimeException {
    public ShelterNotFoundException() {
        super();
    }

    public ShelterNotFoundException(String message) {
        super(message);
    }

    public ShelterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShelterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ShelterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

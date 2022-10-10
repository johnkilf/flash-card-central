package johnkilf.cardservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandling extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final BindingResult bindingResult = ex.getBindingResult();
        return ResponseEntity.badRequest().body(new CustomValidationError(bindingResult));
    }

    @Data
    static class CustomValidationError {
//        private final int status;
        private final String message = "Validation Error";
        private List<CustomFieldError> fieldErrors = new ArrayList<>();

        public CustomValidationError(BindingResult bindingResult) {
            bindingResult.getFieldErrors().forEach(fE -> addFieldError(fE.getField(), fE.getDefaultMessage()));
        }
        public void addFieldError(String path, String message) {
            CustomFieldError error = new CustomFieldError(path, message);
            fieldErrors.add(error);
        }

        public List<CustomFieldError> getFieldErrors() {
            return fieldErrors;
        }
    }

    @Data
    @AllArgsConstructor
    static class CustomFieldError {
        private String field;
        private String message;
    }
}


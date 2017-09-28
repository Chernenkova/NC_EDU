package Elena.Chernenkova.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 123 on 20.09.2017.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DepartmentNotFoundException extends RuntimeException {

    public DepartmentNotFoundException(String departmentId) {
        super("could not find user '" + departmentId + "'.");
    }
}
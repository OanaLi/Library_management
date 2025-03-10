package model.validator;

import java.util.List;
import java.util.stream.Collectors;

public class ResultFetchException extends RuntimeException {

    public final List<String> errors;

    public ResultFetchException(List<String> errors) {
        super("Failed to fetch result as operation encountered errors!");
        this.errors = errors;
    }

    @Override
    public String toString(){
        return errors.stream().map(Object::toString).collect(Collectors.joining(","))
                + super.toString();
    }
}

package io.vvu.study.java.reactor.demo.datastore.exceptions;

public class NotFoundException extends DatasourceError {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable error) {
        super(error);
    }
}

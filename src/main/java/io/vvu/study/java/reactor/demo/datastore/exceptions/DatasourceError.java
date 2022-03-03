package io.vvu.study.java.reactor.demo.datastore.exceptions;

public class DatasourceError extends RuntimeException {
    public DatasourceError() {
        super();
    }

    public DatasourceError(String message) {
        super(message);
    }

    public DatasourceError(Throwable error) {
        super(error);
    }
}

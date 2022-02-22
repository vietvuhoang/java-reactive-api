package io.vvu.study.java.reactor.demo.dtos;

public interface Dto<T extends Dto> {
    T copy();
}

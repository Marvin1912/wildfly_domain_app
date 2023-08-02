package com.marvin.wildfly_domain_app.util;

import java.util.Optional;
import java.util.function.Function;

public class NullSafeUtil {

    public static <R, T, A> R eval(
            R defaultValue, T input,
            Function<T, R> function
    ) {
        return Optional.ofNullable(input)
                .map(function)
                .orElse(defaultValue);
    }

    public static <R, T, A> R eval(
            R defaultValue, T input,
            Function<T, A> function,
            Function<A, R> function2
    ) {
        return Optional.ofNullable(input)
                .map(function)
                .map(function2)
                .orElse(defaultValue);
    }

    public static <R, T, A, B> R eval(
            R defaultValue, T input,
            Function<T, A> function,
            Function<A, B> function2,
            Function<B, R> function3
    ) {
        return Optional.ofNullable(input)
                .map(function)
                .map(function2)
                .map(function3)
                .orElse(defaultValue);
    }

    public static <R, T, A, B, C> R eval(
            R defaultValue, T input,
            Function<T, A> function,
            Function<A, B> function2,
            Function<B, C> function3,
            Function<C, R> function4
    ) {
        return Optional.ofNullable(input)
                .map(function)
                .map(function2)
                .map(function3)
                .map(function4)
                .orElse(defaultValue);
    }
}

/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.data.value;

import java.util.Optional;

import javax.annotation.Nullable;

public interface OptionalValue<E> extends Value<Optional<E>> {


    /**
     * Provides the value such that if the underlying value is
     * {@code null}, the default value is returned as a {@link Value}, if
     * the underlying value is present, the underlying value is returned
     * as a {@link Value}.
     *
     * @param defaultValue The value to substitute, if the underlying value is
     *      null
     * @return A new {@link Mutable} with a non-null value
     */
    Value<E> orElse(E defaultValue);

    /**
     * Represents a {@link Mutable} that can be {@link Optional} such that the
     * underlying value may be present or {@code null}.
     *
     * @param <E> The type of element
     */
    interface MutableOptionalValue<E> extends OptionalValue<E>,
        Mutable<Optional<E>, MutableOptionalValue<E>, ImmutableOptionalValue<E>> {


        /**
         * Sets the underlying value, may be null.
         *
         * @param value The value to set
         * @return This value, for chaining
         */
        MutableOptionalValue<E> setTo(@Nullable E value);

    }

    /**
     * Represents a {@link Immutable} that can be {@link Optional} such that
     * the underlying value may be present or {@code null}.
     *
     * @param <E> The type of element
     */
    interface ImmutableOptionalValue<E> extends OptionalValue<E>,
        Immutable<Optional<E>, ImmutableOptionalValue<E>, MutableOptionalValue<E>> {

        /**
         * Creates a new {@link ImmutableOptionalValue} with the provided value,
         * may be null.
         *
         * @param value The value
         * @return The new value, for chaining
         */
        ImmutableOptionalValue<E> instead(@Nullable E value);

    }
}

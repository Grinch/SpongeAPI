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

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.function.Predicate;

public interface MapValue<K, V> extends Value<Map<K, V>> {


    /**
     * Gets the size of this map.
     *
     * @return The size of this map
     */
    int size();

    /**
     * Checks if the provided key is contained within this map.
     *
     * @param key The key to check
     * @return True if the key is contained
     */
    boolean containsKey(K key);

    /**
     * Checks if the provided value is contained within this map.
     *
     * @param value The value to check
     * @return True if the value is contained
     */
    boolean containsValue(V value);

    /**
     * Gets an {@link ImmutableSet} of all keys contained in this map value.
     *
     * @return The set of keys
     */
    ImmutableSet<K> keySet();

    /**
     * Retrieves an {@link ImmutableSet} of the {@link Map.Entry}s contained
     * within this map value.
     *
     * @return The immutable set of entries
     */
    ImmutableSet<Map.Entry<K, V>> entrySet();

    /**
     * Retrieves an {@link ImmutableCollection} of all available values within
     * this map.
     *
     * @return The collection of values
     */
    ImmutableCollection<V> values();

    /**
     * Represents a specialized type of {@link Value.Mutable} that is different from
     * a {@link CollectionValue.Mutable} such that the "elements" are
     * {@link Map.Entry}. Usually, this type of value is used to represent
     * a particular "type" of "key" that is associated to a particular "value".
     *
     * @param <K> The type of the key
     * @param <V> The type of the value
     */
    interface Mutable<K, V> extends MapValue<K, V>, Value.Mutable<Map<K, V>, Mutable<K, V>, Immutable<K, V>> {


        /**
         * Associates the provided key to the provided value. If there already
         * exists a value for the provided key, the value is replaced.
         *
         * @param key The key to associate to the value
         * @param value The value associated with the key
         * @return This map value, for chaining
         */
        MapValue.Mutable<K, V> put(K key, V value);

        /**
         * Associates all provided {@link Map.Entry} to this map value.
         *
         * @param map The map of key values to set
         * @return This map value, for chaining
         */
        MapValue.Mutable<K, V> putAll(Map<K, V> map);

        /**
         * Removes the association of the provided key to the value currently
         * associated.
         *
         * @param key The key to remove
         * @return This map value, for chaining
         */
        MapValue.Mutable<K, V> remove(K key);

        /**
         * Removes all key value associations of the provided keys.
         *
         * @param keys The keys to remove
         * @return This map value, for chaining
         */
        MapValue.Mutable<K, V> removeAll(Iterable<K> keys);

        /**
         * Applies the {@link Predicate} to all {@link Map.Entry} within this
         * {@link MapValue.Mutable}. Any entries that are false will be removed from the
         * map value.
         *
         * @param predicate The predicate to filer
         * @return This map value, for chaining
         */
        MapValue.Mutable<K, V> removeAll(Predicate<Map.Entry<K, V>> predicate);

    }

    /**
     * Represents a specialized type of {@link Value.Immutable} that is different
     * from an {@link CollectionValue.Immutable} such that the "elements" are
     * {@link Map.Entry}. Usually, this type of value is used to represent
     * a particular "type" of "key" that is associated to a particular "value".
     *
     * @param <K> The type of the key
     * @param <V> The type of the value
     */
    interface Immutable<K, V> extends MapValue<K, V>, Value.Immutable<Map<K, V>, Immutable<K, V>, Mutable<K, V>> {

        /**
         * Associates the provided key to the provided value in the new map. If
         * there already exists a value for the provided key, the value is
         * replaced.
         *
         * @param key The key to associate to the value
         * @param value The value associated with the key
         * @return The new value, for chaining
         */
        MapValue.Immutable<K, V> with(K key, V value);

        /**
         * Associates all provided {@link Map.Entry} along with all pre-existing
         * map entries in a new {@link MapValue.Immutable}.
         *
         * @param map The map of key values to set
         * @return The new value, for chaining
         */
        MapValue.Immutable<K, V> withAll(Map<K, V> map);

        /**
         * Creates a new {@link MapValue.Immutable} without the provided key and the
         * associated value.
         *
         * @param key The key to exclude the association
         * @return The new value, for chaining
         */
        MapValue.Immutable<K, V> without(K key);

        /**
         * Creates a new {@link MapValue.Immutable} without the provided keys and
         * their associated values.
         *
         * @param keys The keys to exclude
         * @return The new value, for chaining
         */
        MapValue.Immutable<K, V> withoutAll(Iterable<K> keys);

        /**
         * Creates a new {@link MapValue.Immutable} such that all entries are
         * filtered by the provided {@link Predicate}, any that return
         * {@code true} are retained in the new value. Elements that return
         * <code>true</code> from {@link Predicate#test(Object)} are kept, and
         * those that return <code>false</code> are excluded.
         *
         * @param predicate The predicate to filter
         * @return The new value, for chaining
         */
        MapValue.Immutable<K, V> withoutAll(Predicate<Map.Entry<K, V>> predicate);

    }
}

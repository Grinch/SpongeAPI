/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered.org <http://www.spongepowered.org>
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
package org.spongepowered.api.service.persistence.data;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.spongepowered.api.service.persistence.serialization.DataSerializable;
import org.spongepowered.api.util.PrimitiveUtil;

import java.util.*;

/**
 * Default implementation of a {@link DataView} being used in memory.
 */
public class MemoryDataView implements DataView {

    protected final Map<String, Object> map = new LinkedHashMap<String, Object>();
    private final DataContainer container;
    private final DataView parent;
    private final DataQuery path;

    protected MemoryDataView() {
        if (!(this instanceof DataContainer)) {
            throw new IllegalStateException("Cannot construct a root MemoryDataView without a container!");
        }
        this.path = new DataQuery();
        this.parent = this;
        this.container = (DataContainer) this;
    }

    protected MemoryDataView(DataView parent, DataQuery path) {
        checkArgument(path.getParts().size() >= 1,
                "Path must have at least one part");

        this.parent = parent;
        this.container = parent.getContainer();
        this.path = parent.getCurrentPath().then(path);
    }

    @Override
    public void remove(DataQuery path) {
        // TODO implement
    }

    @Override
    public DataContainer getContainer() {
        return this.container;
    }

    @Override
    public DataQuery getCurrentPath() {
        return this.path;
    }

    @Override
    public String getName() {
        List<String> parts = path.getParts();
        return parts.get(parts.size() - 1);
    }

    @Override
    public Optional<DataView> getParent() {
        return Optional.fromNullable(parent);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return null; // TODO implement
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        return null; // TODO implement
    }

    @Override
    public boolean contains(DataQuery path) {
        return false; // TODO implement
    }

    @Override
    public Optional<Object> get(DataQuery path) {
        List<DataQuery> queryParts = path.getQueryParts();

        int sz = queryParts.size();

        if (sz == 0) {
            return Optional.<Object>of(this);
        }

        if (sz == 1) {
            String key = queryParts.get(0).getParts().get(0);
            if (map.containsKey(key)) {
                return Optional.<Object>of(key);
            } else {
                return Optional.absent();
            }
        }

        DataView view = this;
        for (int i = 1; i < sz; i++) {
            DataQuery part = queryParts.get(0);
            Optional<DataView> nested = view.getView(part);
            if (nested.isPresent()) {
                view = nested.get();
            } else {
                return Optional.absent();
            }
        }

        DataQuery lastQueryPart = queryParts.get(sz - 1);
        return view.get(lastQueryPart);
    }

    @Override
    public void set(DataQuery path, Object value) {
        // TODO implement
    }

    @Override
    public DataView createView(DataQuery path) {
        List<DataQuery> queryParts = path.getQueryParts();

        int sz = queryParts.size();

        checkArgument(sz == 0, "The size of the query must be at least 1");

        if (sz == 1) {
            DataQuery key = queryParts.get(0);
            DataView result = new MemoryDataView(this, key);
            map.put(key.getParts().get(0), result);
            return result;
        }

        DataView view = this;

        for (int i = 1; i < sz; i++) {
            DataQuery part = queryParts.get(0);
            Optional<DataView> nested = view.getView(part);
            view = nested.get();
        }

        DataQuery lastQueryPart = queryParts.get(sz - 1);
        return view.createView(lastQueryPart);
    }

    @Override
    public DataView createView(DataQuery path, Map<?, ?> map) {
        DataView section = createView(path);

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                section.createView(new DataQuery('.', entry.getKey().toString()), (Map<?, ?>) entry.getValue());
            } else {
                section.set(new DataQuery('.', entry.getKey().toString()), entry.getValue());
            }
        }

        return section;
    }

    @Override
    public Optional<DataView> getView(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            if (val.get() instanceof DataView) {
                return Optional.of((DataView) val.get());
            }
        }
        return Optional.absent();
    }

    @Override
    public Optional<Boolean> getBoolean(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            return PrimitiveUtil.asBoolean(val.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<Integer> getInt(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            return PrimitiveUtil.asInteger(val.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<Long> getLong(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            return PrimitiveUtil.asLong(val.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<Double> getDouble(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            return PrimitiveUtil.asDouble(val.get());
        }
        return Optional.absent();
    }

    @Override
    public Optional<String> getString(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            return PrimitiveUtil.asString(val.get());
        }
        return Optional.absent();
    }
    @Override
    public Optional<List<?>> getList(DataQuery path) {
        Optional<Object> val = get(path);
        if (val.isPresent()) {
            if (val.get() instanceof List<?>) {
                return Optional.<List<?>>of(ImmutableList.copyOf((List<?>) val.get()));
            }
        }
        return Optional.absent();
    }

    @Override
    public Optional<List<String>> getStringList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<String> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<String> optional = PrimitiveUtil.asString(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<String>) builder.build());
    }

    @Override
    public Optional<List<Character>> getCharacterList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Character> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Character> optional = PrimitiveUtil.asCharacter(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Character>) builder.build());
    }

    @Override
    public Optional<List<Boolean>> getBooleanList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Boolean> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Boolean> optional = PrimitiveUtil.asBoolean(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Boolean>) builder.build());
    }

    @Override
    public Optional<List<Byte>> getByteList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Byte> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Byte> optional = PrimitiveUtil.asByte(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Byte>) builder.build());
    }

    @Override
    public Optional<List<Short>> getShortList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Short> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Short> optional = PrimitiveUtil.asShort(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Short>) builder.build());
    }

    @Override
    public Optional<List<Integer>> getIntegerList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Integer> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Integer> optional = PrimitiveUtil.asInteger(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Integer>) builder.build());
    }

    @Override
    public Optional<List<Long>> getLongList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Long> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Long> optional = PrimitiveUtil.asLong(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Long>) builder.build());
    }

    @Override
    public Optional<List<Float>> getFloatList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Float> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Float> optional = PrimitiveUtil.asFloat(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Float>) builder.build());
    }

    @Override
    public Optional<List<Double>> getDoubleList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Double> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            Optional<Double> optional = PrimitiveUtil.asDouble(object);
            if (optional.isPresent()) {
                builder.add(optional.get());
            }
        }
        return Optional.of((List<Double>) builder.build());
    }

    @Override
    public Optional<List<Map<?, ?>>> getMapList(DataQuery path) {
        Optional<List<?>> list = getList(path);

        if (!list.isPresent()) {
            return Optional.absent();
        }

        ImmutableList.Builder<Map<?, ?>> builder = ImmutableList.builder();

        for (Object object : list.get()) {
            if (object instanceof Map) {
                builder.add((Map<?, ?>) object);
            }
        }

        return Optional.of((List<Map<?,?>>) builder.build());
    }

    @Override
    public <T extends DataSerializable> Optional<T> getSerializable(DataQuery path, Class<T> clazz) {
        return null; // TODO implement
    }

}

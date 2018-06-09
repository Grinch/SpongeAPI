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
package org.spongepowered.api.data.manipulator.mutable.entity;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableExpirableData;
import org.spongepowered.api.data.value.BoundedValue;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.monster.Endermite;
import org.spongepowered.api.world.weather.Weather;

/**
 * Signifies that an entity will expire after the value has
 * decayed to the minimum. Usually applicable to {@link Weather},
 * {@link Endermite}s and {@link Item}s.
 */
public interface ExpirableData extends DataManipulator<ExpirableData, ImmutableExpirableData> {

    /**
     * Gets the {@link BoundedValue.Mutable} for the amount of "ticks"
     * remaining before the "expiration" occurs.
     *
     * @return The immutable bounded value for the amount of ticks remaining
     * @see Keys#EXPIRATION_TICKS
     */
    BoundedValue.Mutable<Integer> expireTicks();

}

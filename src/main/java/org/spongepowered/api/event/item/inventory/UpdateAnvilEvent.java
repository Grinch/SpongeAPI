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
package org.spongepowered.api.event.item.inventory;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

import java.util.Optional;

import javax.annotation.Nullable;

/**
 * Fires whenever the left and right slots of an anvil are filled
 * and a new result is computed.
 *
 * <p>Canceling this event prevents the vanilla behaviour and the
 * output is set to empty</p>
 *
 * <p>Changed costs only apply when the result is also set</p>
 */
public interface UpdateAnvilEvent extends TargetInventoryEvent, Cancellable {

    /**
     * Returns the new item name.
     *
     * @return The new item name
     */
    String getItemName();

    /**
     * Returns the left input item.
     *
     * @return The left input item
     */
    ItemStackSnapshot getLeft();

    /**
     * Returns the right input item.
     *
     * @return The right input item
     */
    ItemStackSnapshot getRight();

    /**
     * Returns the custom result.
     *
     * @return The custom result or {@link Optional#empty()} when not set
     */
    Optional<ItemStackSnapshot> getResult();

    /**
     * Sets the custom result. Use {@code null} to reset to vanilla behaviour.
     *
     * @param item The custom result
     */
    void setResult(@Nullable ItemStackSnapshot item);

    /**
     * Returns the custom level cost.
     *
     * @return The custom level cost
     */
    int getLevelCost();

    /**
     * Sets the custom level cost.
     *
     * <p>Only applies when the result was set.</p>
     *
     * @param cost The custom level cost
     */
    void setLevelCost(int cost);

    /**
     * Returns the custom material cost.
     *
     * @return The custom material cost
     */
    int getMaterialCost();

    /**
     * Sets the custom material cost.
     *
     * <p>Only applies when the result was set.</p>
     *
     * @param cost The custom material cost
     */
    void setMaterialCost(int cost);

}

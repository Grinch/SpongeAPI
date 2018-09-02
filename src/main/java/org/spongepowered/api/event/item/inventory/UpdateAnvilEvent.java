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

import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

/**
 * Fires whenever the left and right slots of an anvil are filled and a new result is computed.
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
     * Returns the transaction for the resulting item.
     *
     * @return The transaction for the resulting item.
     */
    Transaction<ItemStackSnapshot> getItem();

    /**
     * Returns the level cost.
     *
     * @return The level cost
     */
    int getLevelCost();

    /**
     * Returns the original level cost.
     *
     * @return The original level cost
     */
    int getOriginalLevelCost();

    /**
     * Sets the level cost
     *
     * @param levelCost The new level cost
     */
    void setLevelCost(int levelCost);

    /**
     * Returns the material cost.
     *
     * @return The material cost
     */
    int getMaterialCost();

    /**
     * Returns the original material cost
     *
     * @return The material cost
     */
    int getOriginalMaterialCost();

    /**
     * Sets the material cost.
     *
     * @param materialCost The material cost
     */
    void setMaterialCost(int materialCost);

}

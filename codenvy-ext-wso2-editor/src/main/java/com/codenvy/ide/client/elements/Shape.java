/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements;

import com.google.gwt.resources.client.ImageResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The main presentation of shape diagram element. It can contain and manage inner diagram elements. Inner diagram elements mean that this
 * kind of elements provides an ability to have own diagram inside himself. This ability is optional.
 *
 * @author Andrey Plotnikov
 */
public interface Shape extends Element {

    int UNDEFINED_POSITION = -1;

    /** @return the x-position of shape in workspace */
    int getX();

    /**
     * Set the x-position of shape in workspace.
     *
     * @param x
     *         x-position
     */
    void setX(int x);

    /** @return the y-position of shape in workspace */
    int getY();

    /**
     * Set y-position of shape in workspace.
     *
     * @param y
     *         y-position
     */
    void setY(int y);

    int getBranchesAmount();

    void setBranchesAmount(int amount);

    @Nonnull
    List<Branch> getBranches();

    /**
     * @param component
     *         current component
     * @return <code>true</code> if components include current component, <code>false</code> it doesn't
     */
    boolean hasComponent(@Nonnull String component);

    /** @return <code>true</code> if this shape has an ability to have inner components, <code>false</code> it doesn't */
    boolean isContainer();

    /** @return <code>true</code> if this shape has an ability to add new branches, <code>false</code> it doesn't */
    boolean isPossibleToAddBranches();

    boolean needsToShowIconAndTitle();

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param content
     *         a serialized content
     */
    void deserialize(@Nonnull String content);

    /** @return the icon of shape */
    @Nullable
    ImageResource getIcon();

    @Nullable
    Shape findElement(@Nonnull String elementName);

}
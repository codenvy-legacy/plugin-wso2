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

import javax.annotation.Nonnegative;
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

    /** @return the x-position of shape in workspace */
    @Nonnegative
    int getX();

    /**
     * Set the x-position of shape in workspace.
     *
     * @param x
     *         x-position
     */
    void setX(@Nonnegative int x);

    /** @return the y-position of shape in workspace */
    @Nonnegative
    int getY();

    /**
     * Set y-position of shape in workspace.
     *
     * @param y
     *         y-position
     */
    void setY(@Nonnegative int y);

    /** @return amount of branches for the current diagram element */
    @Nonnegative
    int getBranchesAmount();

    /**
     * Change amount of branches for the current diagram element.
     *
     * @param amount
     *         new amount of branches for the current diagram element
     */
    void setBranchesAmount(@Nonnegative int amount);

    /** @return list of available branches for the current diagram element */
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

    /** @return <code>true</code> if this shape needs to show title and icon in the widget, <code>false</code> it doesn't */
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

    /**
     * Return instance of found element if it is possible or <code>null</code>.
     *
     * @param elementName
     *         name of element that needs to be created
     * @return ans instance of diagram element or <code>null</code>
     */
    @Nullable
    Shape createElement(@Nonnull String elementName);

}
/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.elements;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The main presentation of diagram elements. All diagram elements will implement this class. It contains general information for all
 * diagram elements. It can contain and manage inner diagram elements. Inner diagram elements mean that this kind of elements provides an
 * ability to have own diagram inside himself. This ability is optional.
 *
 * @author Andrey Plotnikov
 */
public interface Element {

    /**
     * @return an unique element identifier. All instances of elements will have an unique identifier. This means that anyone can find
     * needed diagram element.
     */
    @Nonnull
    String getId();

    /** @return a title of a element diagram element. This title will show in GWT widget. */
    @Nonnull
    String getTitle();

    /**
     * Change title of a element diagram element to a new one.
     *
     * @param title
     *         title that need to be applied
     */
    void setTitle(@Nonnull String title);

    /** @return parent of this graphical element */
    @Nullable
    Element getParent();

    /**
     * Change a parent of the graphical element.
     *
     * @param parent
     *         parent that needs to be applied
     */
    void setParent(@Nullable Element parent);

    /** @return a diagram element name */
    @Nonnull
    String getElementName();

    /** @return serialized representation of the diagram element */
    @Nonnull
    String serialize();

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    void deserialize(@Nonnull Node node);

    /** @return the x-position of element in workspace */
    @Nonnegative
    int getX();

    /**
     * Set the x-position of element in workspace.
     *
     * @param x
     *         x-position
     */
    void setX(@Nonnegative int x);

    /** @return the y-position of element in workspace */
    @Nonnegative
    int getY();

    /**
     * Set y-position of element in workspace.
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

    /** @return <code>true</code> if this element has an ability to have inner components, <code>false</code> it doesn't */
    boolean isContainer();

    /** @return <code>true</code> if this element has an ability to add new branches, <code>false</code> it doesn't */
    boolean isPossibleToAddBranches();

    /** @return <code>true</code> if this element needs to show title and icon in the widget, <code>false</code> it doesn't */
    boolean needsToShowIconAndTitle();

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param content
     *         a serialized content
     */
    void deserialize(@Nonnull String content);

    /** @return the icon of element */
    @Nullable
    ImageResource getIcon();

}
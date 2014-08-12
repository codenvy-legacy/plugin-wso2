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

import com.google.gwt.xml.client.Node;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(BranchImpl.class)
public interface Branch {

    @Nonnull
    String getId();

    void setName(@Nullable String name);

    @Nullable
    String getTitle();

    void setTitle(@Nullable String title);

    @Nullable
    Shape getParent();

    void setParent(@Nullable Shape parent);

    /**
     * Add an inner shape into this one shape.
     *
     * @param shape
     *         shape that need to be added
     */
    void addShape(@Nonnull Shape shape);

    /**
     * Remove a inner shape from this one shape.
     *
     * @param shape
     *         shape that need to be removed
     */
    void removeShape(@Nonnull Shape shape);

    /** @return a list of inner shapes */
    @Nonnull
    List<Shape> getShapes();

    @Nonnull
    String serialize();

    void deserialize(@Nonnull Node node);

}
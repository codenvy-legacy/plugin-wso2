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

import com.codenvy.ide.util.StringUtils;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
public class BranchImpl implements Branch {

    private final String              id;
    private       String              title;
    private       String              name;
    private final List<AbstractShape> shapes;
    private       Shape               parent;

    @Inject
    public BranchImpl() {
        id = UUID.get();

        shapes = new ArrayList<>();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public void setName(@Nullable String name) {
        this.name = name;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    /** {@inheritDoc} */
    @Override
    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public Shape getParent() {
        return parent;
    }

    /** {@inheritDoc} */
    @Override
    public void setParent(@Nullable Shape parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} */
    @Override
    public void addShape(@Nonnull Shape shape) {
        shapes.add((AbstractShape)shape);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    public void removeShape(@Nonnull Shape shape) {
        shapes.remove(shape);
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Shape> getShapes() {
        Collections.sort(shapes);

        List<Shape> result = new ArrayList<>();
        result.addAll(shapes);

        return result;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public String serialize() {
        StringBuilder content = new StringBuilder();

        if (name != null) {
            content.append('<').append(name).append('>');
        }

        for (Shape shape : getShapes()) {
            content.append(shape.serialize());
        }

        if (name != null) {
            content.append("</").append(name).append('>');
        }

        return content.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        String name = node.getNodeName();
        NodeList childNodes = node.getChildNodes();

        this.name = name;
        this.title = StringUtils.capitalizeFirstLetter(name);

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            String nodeName = item.getNodeName();

            Shape shape = parent.createElement(nodeName);

            if (shape == null) {
                continue;
            }

            shape.deserialize(item);
            shapes.add((AbstractShape)shape);
        }
    }

}
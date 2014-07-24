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
package com.codenvy.ide.client;

import com.codenvy.ide.client.elements.Shape;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class provides an ability to manage rules which represent the meta model of the diagram. It is possible to analyze use cases and
 * give a resolution about a possibility to create a connection between elements. Also it provides checking an possibility to reorganize
 * diagram elements (it means that if some element is removed needs to rebind elements with analyzing possibility to create a connection
 * between elements, the same about adding diagram elements).
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class MetaModelValidator {

    private final Map<String, Map<String, List<String>>> rules;

    @Inject
    public MetaModelValidator() {
        rules = new HashMap<>();
    }

    /**
     * Register new rules of connecting of diagram elements.
     *
     * @param sourceElement
     *         element that is a start point of connection
     * @param connection
     *         connection type
     * @param targetElements
     *         elements which is able to be connected with start point element
     */
    public void register(@Nonnull String sourceElement, @Nonnull String connection, @Nonnull List<String> targetElements) {
        Map<String, List<String>> connections;

        if (rules.containsKey(sourceElement)) {
            connections = rules.get(sourceElement);
        } else {
            connections = new HashMap<>();
        }

        connections.put(connection, targetElements);

        rules.put(sourceElement, connections);
    }

    /**
     * Analyze an possibility to create a connection between given elements.
     *
     * @param sourceElement
     *         element that is a start point of connection
     * @param connection
     *         connection that needs to be created
     * @param targetElement
     *         element that is an end point of connection
     * @return <code>true</code> if it is possible to create a connection, <code>false</code> it isn't
     */
    public boolean canCreateConnection(@Nonnull String sourceElement, @Nonnull String connection, @Nonnull String targetElement) {
        if (!rules.containsKey(sourceElement)) {
            return false;
        }

        Map<String, List<String>> connections = rules.get(sourceElement);
        List<String> targetElements = connections.get(connection);

        return targetElements != null && targetElements.contains(targetElement);
    }

    /**
     * Analyze an possibility to insert diagram element in some place.
     *
     * @param parentElement
     *         parent element that needs to be a container of a new element
     * @param connection
     *         connection that need to be created
     * @param newElement
     *         type of element that needs to be inserted
     * @param x
     *         x-position of new element
     * @param y
     *         y-position of new element
     * @return <code>true</code> if it is possible to insert a diagram element in position with x and y coordinates, <code>false</code> it
     * isn't
     */
    public boolean canInsertElement(@Nonnull Shape parentElement, @Nonnull String connection, @Nonnull String newElement, int x, int y) {
        Shape prevElement = findPrevElementByPosition(parentElement, x, y);
        Shape nextElement = findNextElementByPosition(parentElement, x, y);

        if (prevElement == null && nextElement == null) {
            return true;
        }

        if (prevElement != null && nextElement == null) {
            return canCreateConnection(prevElement.getElementName(), connection, newElement);
        }

        if (prevElement == null) {
            return canCreateConnection(newElement, connection, nextElement.getElementName());
        }

        return canCreateConnection(prevElement.getElementName(), connection, newElement) &&
               canCreateConnection(newElement, connection, nextElement.getElementName());
    }

    /**
     * Return previous element of a given position.
     *
     * @param parentElement
     *         parent element that needs to be analyzed
     * @param x
     *         x-position
     * @param y
     *         y-position
     * @return the previous diagram element of a given position
     */
    @Nullable
    private Shape findPrevElementByPosition(@Nonnull Shape parentElement, int x, int y) {
        List<Shape> shapes = parentElement.getShapes();

        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            int shapeX = shape.getX();

            if (shapeX > x || (shapeX == x && shape.getY() > y)) {
                return i == 0 ? null : shapes.get(i - 1);
            }
        }

        return shapes.isEmpty() ? null : shapes.get(shapes.size() - 1);
    }

    /**
     * Return previous element of a given position.
     *
     * @param parentElement
     *         parent element that needs to be analyzed
     * @param x
     *         x-position
     * @param y
     *         y-position
     * @return the next diagram element of a given position
     */
    @Nullable
    private Shape findNextElementByPosition(@Nonnull Shape parentElement, int x, int y) {
        for (Shape shape : parentElement.getShapes()) {
            int shapeX = shape.getX();
            if (shapeX > x || (shapeX == x && shape.getY() > y)) {
                return shape;
            }
        }

        return null;
    }

    /**
     * Analyze an possibility to remove a =diagram element.
     *
     * @param parentElement
     *         parent element that needs to be analyzed
     * @param elementId
     *         id of element that needs to be removed
     * @param connection
     *         connection that needs to be created
     * @return <code>true</code> if it is possible to remove a diagram element, <code>false</code> it isn't
     */
    public boolean canRemoveElement(@Nonnull Shape parentElement, @Nonnull String elementId, @Nonnull String connection) {
        Shape shape = findElementById(parentElement, elementId);

        if (shape == null) {
            return true;
        }

        List<Shape> shapes = parentElement.getShapes();
        int index = shapes.indexOf(shape);

        if (index == 0 || index == shapes.size() - 1) {
            return true;
        }

        Shape prevShape = shapes.get(index - 1);
        Shape nextShape = shapes.get(index + 1);

        return canCreateConnection(prevShape.getElementName(), connection, nextShape.getElementName());
    }

    /**
     * Return element by id.
     *
     * @param parentElement
     *         parent element that needs to be analyzed
     * @param elementId
     *         element that needs to be found
     * @return a diagram element
     */
    @Nullable
    private Shape findElementById(@Nonnull Shape parentElement, @Nonnull String elementId) {
        for (Shape shape : parentElement.getShapes()) {
            if (elementId.equals(shape.getId())) {
                return shape;
            }
        }

        return null;
    }

}
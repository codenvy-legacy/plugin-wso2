/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache  License, Version 2.0 (the "License");
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
package com.codenvy.ide.client;

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
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

    private final Map<String, List<String>> rules;

    @Inject
    public MetaModelValidator() {
        rules = new HashMap<>();
    }

    /**
     * Register new rules of connecting of diagram elements.
     *
     * @param sourceElement
     *         element that is a start point of connection
     * @param targetElements
     *         elements which is able to be connected with start point element
     */
    public void register(@Nonnull String sourceElement, @Nonnull List<String> targetElements) {
        rules.put(sourceElement, targetElements);
    }

    /**
     * Analyze an possibility to create a connection between given elements.
     *
     * @param sourceElement
     *         element that is a start point of connection
     * @param targetElement
     *         element that is an end point of connection
     * @return <code>true</code> if it is possible to create a connection, <code>false</code> it isn't
     */
    public boolean canCreateConnection(@Nonnull String sourceElement, @Nonnull String targetElement) {
        if (!rules.containsKey(sourceElement)) {
            return false;
        }

        List<String> targetElements = rules.get(sourceElement);

        return targetElements != null && targetElements.contains(targetElement);
    }

    /**
     * Analyze an possibility to insert diagram element in some place.
     *
     * @param branch
     *         branch that needs to be a container of a new element
     * @param newElement
     *         type of element that needs to be inserted
     * @param x
     *         x-position of new element
     * @param y
     *         y-position of new element
     * @return <code>true</code> if it is possible to insert a diagram element in position with x and y coordinates, <code>false</code> it
     * isn't
     */
    public boolean canInsertElement(@Nonnull Branch branch, @Nonnull String newElement, int x, int y) {
        Element prevElement = findPrevElementByPosition(branch, x, y);
        Element nextElement = findNextElementByPosition(branch, x, y);

        if (prevElement == null && nextElement == null) {
            return true;
        }

        if (prevElement != null && nextElement == null) {
            return canCreateConnection(prevElement.getElementName(), newElement);
        }

        if (prevElement == null) {
            return canCreateConnection(newElement, nextElement.getElementName());
        }

        return canCreateConnection(prevElement.getElementName(), newElement) &&
               canCreateConnection(newElement, nextElement.getElementName());
    }

    /**
     * Return previous element of a given position.
     *
     * @param branch
     *         branch that needs to be analyzed
     * @param x
     *         x-position
     * @param y
     *         y-position
     * @return the previous diagram element of a given position
     */
    @Nullable
    private Element findPrevElementByPosition(@Nonnull Branch branch, int x, int y) {
        List<Element> elements = branch.getElements();

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            int elementX = element.getX();

            if (elementX > x || (elementX == x && element.getY() > y)) {
                return i == 0 ? null : elements.get(i - 1);
            }
        }

        return elements.isEmpty() ? null : elements.get(elements.size() - 1);
    }

    /**
     * Return previous element of a given position.
     *
     * @param branch
     *         branch that needs to be analyzed
     * @param x
     *         x-position
     * @param y
     *         y-position
     * @return the next diagram element of a given position
     */
    @Nullable
    private Element findNextElementByPosition(@Nonnull Branch branch, int x, int y) {
        for (Element element : branch.getElements()) {
            int elementX = element.getX();
            if (elementX > x || (elementX == x && element.getY() > y)) {
                return element;
            }
        }

        return null;
    }

    /**
     * Analyze an possibility to remove a =diagram element.
     *
     * @param branch
     *         branch that needs to be analyzed
     * @param elementId
     *         id of element that needs to be removed
     * @return <code>true</code> if it is possible to remove a diagram element, <code>false</code> it isn't
     */
    public boolean canRemoveElement(@Nonnull Branch branch, @Nonnull String elementId) {
        Element element = findElementById(branch, elementId);

        if (element == null) {
            return true;
        }

        List<Element> elements = branch.getElements();
        int index = elements.indexOf(element);

        if (index == 0 || index == elements.size() - 1) {
            return true;
        }

        Element prevElement = elements.get(index - 1);
        Element nextElement = elements.get(index + 1);

        return canCreateConnection(prevElement.getElementName(), nextElement.getElementName());
    }

    /**
     * Return element by id.
     *
     * @param branch
     *         branch that needs to be analyzed
     * @param elementId
     *         element that needs to be found
     * @return a diagram element
     */
    @Nullable
    private Element findElementById(@Nonnull Branch branch, @Nonnull String elementId) {
        for (Element element : branch.getElements()) {
            if (elementId.equals(element.getId())) {
                return element;
            }
        }

        return null;
    }

}
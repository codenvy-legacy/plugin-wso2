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
package com.codenvy.ide.client.validators;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class provides an ability to manage rules which represent the meta model of the diagram. It is possible to analyze use cases and
 * give a resolution about a possibility to create a diagram element into a given element.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public class InnerElementsValidator {

    private final Map<String, Set<String>> allowRules;
    private final Map<String, Set<String>> disallowRules;

    @Inject
    public InnerElementsValidator() {
        allowRules = new HashMap<>();
        disallowRules = new HashMap<>();
    }

    /**
     * Adds new rules of allowing creation of diagram elements into selected element.
     *
     * @param parentElement
     *         parent element where child element should be created
     * @param childElements
     *         child elements which need to be created
     */
    public void addAllowRules(@Nonnull String parentElement, @Nonnull List<String> childElements) {
        addRules(allowRules, parentElement, childElements);
    }

    /**
     * Adds new rules of disallowing creation of diagram elements into selected element.
     *
     * @param parentElement
     *         parent element where child element should be created
     * @param childElements
     *         child elements which need to be created
     */
    public void addDisallowRules(@Nonnull String parentElement, @Nonnull List<String> childElements) {
        addRules(disallowRules, parentElement, childElements);
    }

    private void addRules(@Nonnull Map<String, Set<String>> rules, @Nonnull String parentElement, @Nonnull List<String> childElements) {
        Set<String> targets = rules.get(parentElement);

        if (targets == null) {
            targets = new HashSet<>();

            targets.addAll(childElements);

            rules.put(parentElement, targets);
        } else {
            targets.addAll(childElements);
        }
    }

    /**
     * Adds a new rule of allowing creation of diagram elements into selected element.
     *
     * @param parentElement
     *         parent element where child element should be created
     * @param childElement
     *         child element that needs to be created
     */
    public void addAllowRule(@Nonnull String parentElement, @Nonnull String childElement) {
        addRule(allowRules, parentElement, childElement);
    }

    /**
     * Adds a new rule of disallowing creation of diagram elements into selected element.
     *
     * @param parentElement
     *         parent element where child element should be created
     * @param childElement
     *         child element that needs to be created
     */
    public void addDisallowRule(@Nonnull String parentElement, @Nonnull String childElement) {
        addRule(disallowRules, parentElement, childElement);
    }

    private void addRule(@Nonnull Map<String, Set<String>> rules, @Nonnull String parentElement, @Nonnull String childElement) {
        Set<String> targets = rules.get(parentElement);

        if (targets == null) {
            targets = new HashSet<>();

            targets.add(childElement);

            rules.put(parentElement, targets);
        } else {
            targets.add(childElement);
        }
    }

    /**
     * Analyze an possibility to create a diagram element into a given element.
     *
     * @param parentElement
     *         parent element where child element should be created
     * @param childElement
     *         child element that needs to be created
     * @return <code>true</code> if it is possible to create an element, <code>false</code> it isn't
     */
    public boolean canInsertElement(@Nonnull String parentElement, @Nonnull String childElement) {
        if (hasRules(allowRules, parentElement)) {
            return isAllowedConnection(parentElement, childElement);
        }

        return hasRules(disallowRules, parentElement) && !isDisallowedConnection(parentElement, childElement);
    }

    private boolean hasRules(@Nonnull Map<String, Set<String>> rules, @Nonnull String parentElement) {
        return rules.containsKey(parentElement);
    }

    private boolean isAllowedConnection(@Nonnull String parentElement, @Nonnull String childElement) {
        return isContainedRule(allowRules, parentElement, childElement);
    }

    private boolean isDisallowedConnection(@Nonnull String parentElement, @Nonnull String childElement) {
        return isContainedRule(disallowRules, parentElement, childElement);
    }

    private boolean isContainedRule(@Nonnull Map<String, Set<String>> rules, @Nonnull String parentElement, @Nonnull String childElement) {
        if (!rules.containsKey(parentElement)) {
            return false;
        }

        Set<String> childElements = rules.get(parentElement);

        return childElements != null && childElements.contains(childElement);
    }

}
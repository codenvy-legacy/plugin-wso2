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
package com.codenvy.ide.client.elements.addressendpoint;

import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.ValueType;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;

import static com.codenvy.ide.client.elements.ValueType.LITERAL;
import static com.codenvy.ide.client.elements.addressendpoint.Property.Scope.DEFAULT;

/**
 * @author Andrey Plotnikov
 */
public class Property {

    private final Provider<Property> propertyProvider;

    private String           name;
    private String           value;
    private String           expression;
    private ValueType        type;
    private Scope            scope;
    private Array<NameSpace> nameSpaces;

    @Inject
    public Property(Provider<Property> propertyProvider) {
        this.propertyProvider = propertyProvider;

        name = "property_name";
        value = "property_value";
        expression = "/default/expression";
        type = LITERAL;
        scope = DEFAULT;
        nameSpaces = Collections.createArray();
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    public void setValue(@Nonnull String value) {
        this.value = value;
    }

    @Nonnull
    public String getExpression() {
        return expression;
    }

    public void setExpression(@Nonnull String expression) {
        this.expression = expression;
    }

    @Nonnull
    public ValueType getType() {
        return type;
    }

    public void setType(@Nonnull ValueType type) {
        this.type = type;
    }

    @Nonnull
    public Scope getScope() {
        return scope;
    }

    public void setScope(@Nonnull Scope scope) {
        this.scope = scope;
    }

    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    @Nonnull
    public Property clone() {
        Property property = propertyProvider.get();

        property.setName(name);
        property.setExpression(expression);
        property.setValue(value);
        property.setType(type);
        property.setScope(scope);
        property.setNameSpaces(nameSpaces);

        return property;
    }

    public enum Scope {
        DEFAULT("default"), TRANSPORT("transport"), AXIS2("axis2"), AXIS2_CLIENT("axis2-client");

        public static final String TYPE_NAME = "PropertyScopeType";

        private String value;

        Scope(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }

}
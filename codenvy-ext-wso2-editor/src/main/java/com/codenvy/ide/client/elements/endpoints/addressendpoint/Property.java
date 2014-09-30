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
package com.codenvy.ide.client.elements.endpoints.addressendpoint;

import com.codenvy.ide.client.elements.AbstractEntityElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.ValueType;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;
import static com.codenvy.ide.client.elements.NameSpace.copyNameSpaceList;
import static com.codenvy.ide.client.elements.endpoints.addressendpoint.Property.Scope.DEFAULT;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;

/**
 * The class which describes state of Property element of Address endpoint and also has methods for changing it. Also the class contains
 * the business logic that allows to display serialization representation depending of the current state of element. Deserelization
 * mechanism allows to restore the condition of the element when you open ESB project after saving.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class Property extends AbstractEntityElement {

    public static final String SERIALIZE_NAME = "property";

    public static final Key<String>          NAME       = new Key<>("EndPointPropertyName");
    public static final Key<String>          VALUE      = new Key<>("EndPointPropertyValue");
    public static final Key<String>          EXPRESSION = new Key<>("EndPointPropertyExpression");
    public static final Key<ValueType>       TYPE       = new Key<>("EndPointPropertyType");
    public static final Key<Scope>           SCOPE      = new Key<>("EndPointPropertyScope");
    public static final Key<List<NameSpace>> NAMESPACES = new Key<>("EndPointPropertyNamespaces");

    private static final String NAME_ATTRIBUTE       = "name";
    private static final String VALUE_ATTRIBUTE      = "value";
    private static final String EXPRESSION_ATTRIBUTE = "expression";
    private static final String SCOPE_ATTRIBUTE      = "scope";

    private final Provider<Property>  propertyProvider;
    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Property(Provider<Property> propertyProvider, Provider<NameSpace> nameSpaceProvider) {
        this.propertyProvider = propertyProvider;
        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(NAME, "property_name");
        putProperty(VALUE, "property_value");
        putProperty(EXPRESSION, "/default/expression");
        putProperty(TYPE, LITERAL);
        putProperty(SCOPE, DEFAULT);
        putProperty(NAMESPACES, new ArrayList<NameSpace>());
    }

    /** Returns copy of element. */
    public Property copy() {
        Property property = propertyProvider.get();

        property.putProperty(NAME, getProperty(NAME));
        property.putProperty(VALUE, getProperty(VALUE));
        property.putProperty(EXPRESSION, getProperty(EXPRESSION));
        property.putProperty(TYPE, getProperty(TYPE));
        property.putProperty(SCOPE, getProperty(SCOPE));
        property.putProperty(NAMESPACES, copyNameSpaceList(getProperty(NAMESPACES)));

        return property;
    }

    /** @return a serialize representation of Endpoint property */
    @Nonnull
    public String serialize() {
        Scope scope = getProperty(SCOPE);

        if (scope == null) {
            return "";
        }

        String startTag = '<' + SERIALIZE_NAME + ' ';
        String nameAttr = NAME_ATTRIBUTE + "=\"" + getProperty(NAME) + "\" ";
        String scopeValue = (DEFAULT.equals(getProperty(SCOPE)) ? "" : ' ' + SCOPE_ATTRIBUTE + "=\"" + scope.getValue() + '"');

        if (LITERAL.equals(getProperty(TYPE))) {
            return startTag + nameAttr +
                   VALUE_ATTRIBUTE + "=\"" + getProperty(VALUE) + '"' +
                   scopeValue + "/>\n";
        }

        return startTag + convertNameSpacesToXML(getProperty(NAMESPACES)) + nameAttr +
               EXPRESSION_ATTRIBUTE + "=\"" + getProperty(EXPRESSION) + "\" " + scopeValue + "/>\n";
    }

    /**
     * Deserialize diagram element with all inner elements.
     *
     * @param node
     *         XML node that need to be deserialized
     */
    public void deserialize(@Nonnull Node node) {
        readXMLAttributes(node);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case NAME_ATTRIBUTE:
                putProperty(NAME, attributeValue);
                break;

            case VALUE_ATTRIBUTE:
                putProperty(VALUE, attributeValue);
                putProperty(TYPE, LITERAL);
                break;

            case EXPRESSION_ATTRIBUTE:
                putProperty(EXPRESSION, attributeValue);
                putProperty(TYPE, ValueType.EXPRESSION);
                break;

            case SCOPE_ATTRIBUTE:
                putProperty(SCOPE, Scope.getItemByValue(attributeValue));
                break;

            default:
                applyNameSpace(nameSpaceProvider, getProperty(NAMESPACES), attributeName, attributeValue);
        }
    }

    /**
     * Returns copy of list. If list which we send to method is null, method return empty list. If list isn't null
     * method returns copy of list.
     *
     * @param listToCopy
     *         list which need to copy
     */
    public static List<Property> copyEndPointPropertyList(@Nullable List<Property> listToCopy) {
        List<Property> properties = new ArrayList<>();

        if (listToCopy == null) {
            return properties;
        }

        for (Property property : listToCopy) {
            properties.add(property);
        }

        return properties;
    }

    public enum Scope {
        DEFAULT("default"), TRANSPORT("transport"), AXIS2("axis2"), AXIS2_CLIENT("axis2_client");

        public static final String TYPE_NAME = "EndpointScopeType";

        private final String value;

        Scope(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static Scope getItemByValue(String value) {
            switch (value) {
                case "transport":
                    return TRANSPORT;

                case "axis2":
                    return AXIS2;

                case "axis2_client":
                    return AXIS2_CLIENT;

                default:
                    return DEFAULT;
            }
        }
    }

}
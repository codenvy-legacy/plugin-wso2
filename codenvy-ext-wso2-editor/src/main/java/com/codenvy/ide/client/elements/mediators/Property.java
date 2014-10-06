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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;
import static com.codenvy.ide.client.elements.mediators.Action.REMOVE;
import static com.codenvy.ide.client.elements.mediators.Action.SET;
import static com.codenvy.ide.client.elements.mediators.Property.DataType.STRING;
import static com.codenvy.ide.client.elements.mediators.Property.Scope.SYNAPSE;
import static com.codenvy.ide.client.elements.mediators.ValueType.EXPRESSION;
import static com.codenvy.ide.client.elements.mediators.ValueType.LITERAL;

/**
 * The class which describes state of Property mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. For more information about Property mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Property+Mediator"> https://docs.wso2.com/display/ESB460/Property+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Property extends AbstractElement {
    public static final String ELEMENT_NAME       = "Property";
    public static final String SERIALIZATION_NAME = "property";

    public static final Key<String>          PROPERTY_NAME              = new Key<>("PropertyName");
    public static final Key<Action>          PROPERTY_ACTION            = new Key<>("PropertyAction");
    public static final Key<ValueType>       VALUE_TYPE                 = new Key<>("ValueType");
    public static final Key<DataType>        PROPERTY_DATA_TYPE         = new Key<>("PropertyDataType");
    public static final Key<String>          VALUE_LITERAL              = new Key<>("ValueLiteral");
    public static final Key<String>          VALUE_EXPRESSION           = new Key<>("ValueExpression");
    public static final Key<String>          VALUE_STRING_PATTERN       = new Key<>("ValueStringPattern");
    public static final Key<Scope>           PROPERTY_SCOPE             = new Key<>("PropertyScope");
    public static final Key<String>          VALUE_STRING_CAPTURE_GROUP = new Key<>("ValueStringCaptureGroup");
    public static final Key<String>          DESCRIPTION                = new Key<>("Description");
    public static final Key<List<NameSpace>> NAMESPACES                 = new Key<>("NameSpaces");

    private static final String NAME_ATTRIBUTE                 = "name";
    private static final String ACTION_ATTRIBUTE               = "action";
    private static final String DATA_TYPE_ATTRIBUTE            = "type";
    private static final String VALUE_LITERAL_ATTRIBUTE        = "value";
    private static final String VALUE_EXPRESSION_ATTRIBUTE     = "expression";
    private static final String STRING_PATTERN_ATTRIBUTE       = "pattern";
    private static final String STRING_CAPTURE_GROUP_ATTRIBUTE = "group";
    private static final String SCOPE_ATTRIBUTE                = "scope";
    private static final String DESCRIPTION_ATTRIBUTE          = "description";

    private static final List<String> PROPERTIES = Collections.emptyList();

    private final Provider<NameSpace> nameSpaceProvider;

    @Inject
    public Property(EditorResources resources,
                    Provider<Branch> branchProvider,
                    ElementCreatorsManager elementCreatorsManager,
                    Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              false,
              true,
              resources.property(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(PROPERTY_ACTION, SET);
        putProperty(VALUE_TYPE, LITERAL);
        putProperty(PROPERTY_DATA_TYPE, STRING);
        putProperty(PROPERTY_SCOPE, SYNAPSE);
        putProperty(PROPERTY_NAME, "property_name");
        putProperty(VALUE_LITERAL, "value");
        putProperty(VALUE_EXPRESSION, "/default/expression");
        putProperty(VALUE_STRING_PATTERN, "");
        putProperty(VALUE_STRING_CAPTURE_GROUP, "");
        putProperty(DESCRIPTION, "");
        putProperty(NAMESPACES, new ArrayList<NameSpace>());
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();

        setDefaultAttributes(attributes);

        ValueType vType = getProperty(VALUE_TYPE);
        Action propAction = getProperty(PROPERTY_ACTION);

        attributes.remove((vType != null && vType.equals(EXPRESSION)) ? VALUE_LITERAL_ATTRIBUTE : VALUE_EXPRESSION_ATTRIBUTE);

        if (SET.equals(propAction)) {
            attributes.remove(ACTION_ATTRIBUTE);
        }

        if (REMOVE.equals(propAction)) {
            attributes.remove(VALUE_EXPRESSION_ATTRIBUTE);
            attributes.remove(VALUE_LITERAL_ATTRIBUTE);
            attributes.remove(DATA_TYPE_ATTRIBUTE);
        }

        return convertNameSpacesToXML(getProperty(NAMESPACES)) + convertAttributesToXML(attributes);
    }

    /**
     * Sets default visualization of property element attributes.
     *
     * @param attributes
     *         list of default attributes
     */
    private void setDefaultAttributes(@Nonnull Map<String, String> attributes) {
        Scope scope = getProperty(PROPERTY_SCOPE);
        Action propAction = getProperty(PROPERTY_ACTION);
        DataType dataType = getProperty(PROPERTY_DATA_TYPE);

        attributes.put(NAME_ATTRIBUTE, getProperty(PROPERTY_NAME));
        attributes.put(VALUE_EXPRESSION_ATTRIBUTE, getProperty(VALUE_EXPRESSION));
        attributes.put(VALUE_LITERAL_ATTRIBUTE, getProperty(VALUE_LITERAL));
        attributes.put(SCOPE_ATTRIBUTE, scope != null ? scope.getValue() : "");
        attributes.put(ACTION_ATTRIBUTE, propAction != null ? propAction.getValue() : "");
        attributes.put(DATA_TYPE_ATTRIBUTE, dataType != null ? dataType.name() : "");
        attributes.put(STRING_CAPTURE_GROUP_ATTRIBUTE, getProperty(VALUE_STRING_CAPTURE_GROUP));
        attributes.put(STRING_PATTERN_ATTRIBUTE, getProperty(VALUE_STRING_PATTERN));
        attributes.put(DESCRIPTION_ATTRIBUTE, getProperty(DESCRIPTION));
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        switch (attributeName) {
            case NAME_ATTRIBUTE:
                putProperty(PROPERTY_NAME, attributeValue);
                break;

            case ACTION_ATTRIBUTE:
                putProperty(PROPERTY_ACTION, Action.getItemByValue(attributeValue));
                break;

            case DATA_TYPE_ATTRIBUTE:
                putProperty(PROPERTY_DATA_TYPE, DataType.valueOf(attributeValue));
                break;

            case VALUE_LITERAL_ATTRIBUTE:
                putProperty(VALUE_LITERAL, attributeValue);
                break;

            case VALUE_EXPRESSION_ATTRIBUTE:
                putProperty(VALUE_LITERAL, attributeValue);
                putProperty(VALUE_TYPE, EXPRESSION);
                break;

            case STRING_PATTERN_ATTRIBUTE:
                putProperty(VALUE_STRING_PATTERN, attributeValue);
                break;

            case STRING_CAPTURE_GROUP_ATTRIBUTE:
                putProperty(VALUE_STRING_CAPTURE_GROUP, attributeValue);
                break;

            case SCOPE_ATTRIBUTE:
                putProperty(PROPERTY_SCOPE, Scope.getItemByValue(attributeValue));
                break;

            case DESCRIPTION_ATTRIBUTE:
                putProperty(DESCRIPTION, attributeValue);
                break;

            default:
                applyNameSpace(nameSpaceProvider, getProperty(NAMESPACES), attributeName, attributeValue);
        }
    }

    public enum DataType {
        STRING, INTEGER, BOOLEAN, DOUBLE, FLOAT, LONG, SHORT, OM;

        public static final String TYPE_NAME = "PropertyDataType";
    }

    public enum Scope {
        SYNAPSE("Synapse"), TRANSPORT("transport"), AXIS2("axis2"), AXIS2_CLIENT("axis2-client"), OPERATION("operation");

        public static final String TYPE_NAME = "PropertyScopeType";

        private String value;

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

                case "axis2-client":
                    return AXIS2_CLIENT;

                case "operation":
                    return OPERATION;

                case "Synapse":
                    return SYNAPSE;

                default:
                    return SYNAPSE;
            }
        }
    }

}
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
package com.codenvy.ide.client.elements.connectors;

import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.INLINE;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NAME_SPACED_PROPERTY_EDITOR;

/**
 * The class contains state of connectors which are general for all connectors.It contains genera logic of serialization
 * representation of attributes of all SalesForce connectors.Deserealizathion mechanism which allows to restore attributes' state of
 * element after saving also is same for all SalesForce connectors .
 *
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class AbstractConnector extends AbstractElement {

    public static final String CONFIG_REF = "configRef";

    public static final Key<String>              CONFIG                = new Key<>("ConfigRef");
    public static final Key<ParameterEditorType> PARAMETER_EDITOR_TYPE = new Key<>("ParameterEditorType");

    protected AbstractConnector(@Nonnull String elementName,
                                @Nonnull String title,
                                @Nonnull String serializationName,
                                @Nonnull List<String> properties,
                                @Nonnull ImageResource resources,
                                @Nonnull Provider<Branch> branchProvider,
                                @Nonnull ElementCreatorsManager elementCreatorsManager) {
        super(elementName,
              title,
              serializationName,
              properties,
              false,
              false,
              resources,
              branchProvider,
              elementCreatorsManager);

        putProperty(CONFIG, "");
        putProperty(PARAMETER_EDITOR_TYPE, INLINE);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        String config = getProperty(CONFIG);
        return config == null || config.isEmpty() ? "" : CONFIG_REF + "=\"" + config + "\"";
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {
        putProperty(CONFIG, attributeValue);
    }

    /**
     * Apply property from XML node to the diagram element.
     *
     * @param nodeValue
     *         value of XML node that need to be analyzed
     * @param propertyName
     *         name of the simple property
     * @param propertyExpressionName
     *         name of the name space property
     */
    protected void adaptProperty(@Nonnull String nodeValue,
                                 @Nonnull Key<String> propertyName,
                                 @Nonnull Key<String> propertyExpressionName) {
        if (INLINE.equals(getProperty(PARAMETER_EDITOR_TYPE))) {
            putProperty(propertyName, nodeValue);
        } else {
            putProperty(propertyExpressionName, nodeValue);

            putProperty(PARAMETER_EDITOR_TYPE, NAME_SPACED_PROPERTY_EDITOR);
        }
    }

    public enum ParameterEditorType {
        INLINE("Inline"), NAME_SPACED_PROPERTY_EDITOR("NamespacedPropertyEditor");

        public static final String TYPE_NAME = "ParameterEditorType";

        private final String value;

        ParameterEditorType(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }

        @Nonnull
        public static ParameterEditorType getItemByValue(@Nonnull String value) {
            return "Inline".equals(value) ? INLINE : NAME_SPACED_PROPERTY_EDITOR;
        }

    }

    public enum AvailableConfigs {
        SELECT_FROM_CONFIG("Select From Available Configurations"), EMPTY("");

        public static final String TYPE_NAME = "AvailableConfigs";

        private final String value;

        AvailableConfigs(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }
}
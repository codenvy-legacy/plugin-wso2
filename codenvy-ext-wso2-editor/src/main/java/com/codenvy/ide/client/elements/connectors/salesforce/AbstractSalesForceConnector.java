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
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.AbstractSalesForceConnector.ParameterEditorType.Inline;

/**
 * The class contains state of SalesForce connectors which are general for all connectors.It contains genera logic of serialization
 * representation of attributes of all SalesForce connectors.Deserealizathion mechanism which allows to restore attributes' state of
 * element after saving also is same for all SalesForce connectors .
 *
 * @author Dmitry Shnurenko
 */
public class AbstractSalesForceConnector extends AbstractShape {

    public static final String CONFIG_REF = "configRef";

    private   String              configRef;
    protected ParameterEditorType parameterEditorType;

    protected AbstractSalesForceConnector(@Nonnull String elementName,
                                          @Nonnull String title,
                                          @Nonnull String serializationName,
                                          @Nonnull List<String> properties,
                                          boolean isPossibleToAddBranches,
                                          boolean needsToShowIconAndTitle,
                                          @Nonnull EditorResources resources,
                                          @Nonnull Provider<Branch> branchProvider,
                                          @Nonnull MediatorCreatorsManager mediatorCreatorsManager) {
        super(elementName,
              title,
              serializationName,
              properties,
              isPossibleToAddBranches,
              needsToShowIconAndTitle,
              resources,
              branchProvider,
              mediatorCreatorsManager);

        configRef = "";
        parameterEditorType = Inline;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return configRef == null || configRef.isEmpty() ? "" : CONFIG_REF + "=\"" + configRef + "\"";
    }

    /** {@inheritDoc} */
    @Override
    public void applyAttributes(@Nonnull Node node) {
        if (node.hasAttributes()) {
            Node attribute = node.getAttributes().item(0);

            configRef = attribute.getNodeValue();
        }
    }

    @Nonnull
    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(@Nonnull String configRef) {
        this.configRef = configRef;
    }

    @Nonnull
    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    public void setParameterEditorType(@Nonnull ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.salesforce();
    }

    public enum ParameterEditorType {
        Inline, NamespacedPropertyEditor;

        public static final String TYPE_NAME = "ParameterEditorType";
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

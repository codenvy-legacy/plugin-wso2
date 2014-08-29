/*
 * Copyright [2014] Codenvy, S.A.
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
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.elements.AbstractShape;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.AvailableConfigs.EMPTY;
import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.AvailableConfigs.SELECT_FROM_CONFIG;
import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType.Inline;

/**
 * The Class describes a basic data of properties panel panel for salesforce connectors.
 *
 * @author Valeriy Svydenko
 */
public class BaseSalesforce extends AbstractShape {
    public static final  String       ELEMENT_NAME = "BaseSalesforce";
    private static final List<String> PROPERTIES   = Collections.emptyList();

    private String              newConfig;
    private String              configRef;
    private ParameterEditorType parameterEditorType;

    private List<String> availableConfigs = new LinkedList<>();

    @Inject
    protected BaseSalesforce(EditorResources resources,
                             Provider<Branch> branchProvider,
                             MediatorCreatorsManager mediatorCreatorsManager,
                             WSO2EditorLocalizationConstant local) {
        super(ELEMENT_NAME, ELEMENT_NAME, ELEMENT_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        newConfig = local.connectorNewConfigValue();
        parameterEditorType = Inline;
        availableConfigs = new LinkedList<>();
        availableConfigs.addAll(Arrays.asList(EMPTY.getValue(), SELECT_FROM_CONFIG.getValue()));
    }

    public String getConfigRef() {
        return configRef;
    }

    public void setConfigRef(String configRef) {
        this.configRef = configRef;
    }

    public void addAvailableConfig(String availableConfig) {
        this.availableConfigs.add(availableConfig);
    }

    public String getNewConfig() {
        return newConfig;
    }

    public ParameterEditorType getParameterEditorType() {
        return parameterEditorType;
    }

    public void setParameterEditorType(ParameterEditorType parameterEditorType) {
        this.parameterEditorType = parameterEditorType;
    }

    public enum ParameterEditorType {
        Inline, NamespacedPropertyEditor;

        public static final String TYPE_NAME = "ParameterEditorType";
    }

    public enum AvailableConfigs {
        SELECT_FROM_CONFIG("Select From Available Configurations"), EMPTY("");

        public static final String TYPE_NAME = "AvailableConfigs";

        private String value;

        AvailableConfigs(@Nonnull String value) {
            this.value = value;
        }

        @Nonnull
        public String getValue() {
            return value;
        }
    }

}

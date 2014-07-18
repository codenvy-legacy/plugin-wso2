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
package com.codenvy.ide.client.propertiespanel.property;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(PropertyPropertiesPanelViewImpl.class)
public abstract class PropertyPropertiesPanelView extends AbstractView<PropertyPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onPropertyNameChanged();

        void onPropertyActionChanged();

        void onValueTypeChanged();

        void onPropertyDataTypeChanged();

        void onValueLiteralChanged();

        void onValueStringPatternChanged();

        void onValueStringCaptureGroupChanged();

        void onPropertyScopeChanged();

        void onDescriptionChanged();

    }

    public abstract String getPropertyName();

    public abstract void setPropertyName(String propertyName);

    public abstract String getPropertyAction();

    public abstract void selectPropertyAction(String propertyAction);

    public abstract void setPropertyAction(List<String> propertyAction);

    public abstract String getValueType();

    public abstract void selectValueType(String valueType);

    public abstract void setValueType(List<String> valueType);

    public abstract String getPropertyDataType();

    public abstract void setPropertyDataType(String propertyDataType);

    public abstract String getValueLiteral();

    public abstract void setValueLiteral(String valueLiteral);

    public abstract String getValueStringPattern();

    public abstract void setValueStringPattern(String valueStringPattern);

    public abstract Integer getValueStringCaptureGroup();

    public abstract void setValueStringCaptureGroup(Integer valueStringCaptureGroup);

    public abstract String getPropertyScope();

    public abstract void selectPropertyScope(String propertyScope);

    public abstract void setPropertyScope(List<String> propertyScope);

    public abstract String getDescription();

    public abstract void setDescription(String description);

}
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

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The view of {@link PropertyPropertiesPanelPresenter}
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(PropertyPropertiesPanelViewImpl.class)
public abstract class PropertyPropertiesPanelView extends AbstractView<PropertyPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed property name field. */
        void onPropertyNameChanged();

        /** Performs any actions appropriate in response to the user having changed property action field. */
        void onPropertyActionChanged();

        /** Performs any actions appropriate in response to the user having changed value type field. */
        void onValueTypeChanged();

        /** Performs any actions appropriate in response to the user having changed data type field. */
        void onPropertyDataTypeChanged();

        /** Performs any actions appropriate in response to the user having changed value literal field. */
        void onValueLiteralChanged();

        /** Performs any actions appropriate in response to the user having changed value expression field. */
        void onValueExpressionChanged();

        /** Performs any actions appropriate in response to the user having changed value string pattern field. */
        void onValueStringPatternChanged();

        /** Performs any actions appropriate in response to the user having changed capture group field. */
        void onValueStringCaptureGroupChanged();

        /** Performs any actions appropriate in response to the user having changed property scope field. */
        void onPropertyScopeChanged();

        /** Performs any actions appropriate in response to the user having changed description field. */
        void onDescriptionChanged();

        /** Performs some actions in response to a user uses value expression. */
        void onEditValueExpressionButtonClicked();

    }

    /** @return property name. */
    @NotNull
    public abstract String getPropertyName();

    /**
     * Set property name into place on view.
     *
     * @param propertyName
     *         name of property.
     */
    public abstract void setPropertyName(@NotNull String propertyName);

    /** @return property action. */
    @NotNull
    public abstract String getPropertyAction();

    /**
     * Select property action in place on view.
     *
     * @param propertyAction
     *         property action.
     */
    public abstract void selectPropertyAction(@NotNull String propertyAction);

    /**
     * Set property action into place on view.
     *
     * @param propertyActions
     *         value of action.
     */
    public abstract void setPropertyActions(@NotNull List<String> propertyActions);

    /**
     * @return value type.
     */
    @NotNull
    public abstract String getValueType();

    /**
     * Select value type in place on view.
     *
     * @param valueType
     *         value of type.
     */
    public abstract void selectValueType(@NotNull String valueType);

    /**
     * Set value type into place on view.
     *
     * @param valueType
     *         value of type.
     */
    public abstract void setValueTypes(@NotNull List<String> valueTypes);

    /** @return property data type. */
    @NotNull
    public abstract String getPropertyDataType();

    /**
     * Set property data type into place on view.
     *
     * @param propertyDataType
     *         value of data type.
     */
    public abstract void setPropertyDataTypes(@NotNull List<String> propertyDataTypes);

    /**
     * Select property data type in place on view.
     *
     * @param propertyDataType
     *         value of data type.
     */
    public abstract void selectPropertyDataType(@NotNull String propertyDataType);

    /** @return value of literal. */
    @NotNull
    public abstract String getValueLiteral();

    /**
     * Set value of literal into place on view.
     *
     * @param valueLiteral
     *         value of literal.
     */
    public abstract void setValueLiteral(@NotNull String valueLiteral);

    /**
     * @return value of expression.
     */
    @NotNull
    public abstract String getValueExpression();

    /**
     * Set value of expression into place on view.
     *
     * @param valueExpression
     *         value of expression.
     */
    public abstract void setValueExpression(@NotNull String valueExpression);

    /** @return value of string pattern. */
    @NotNull
    public abstract String getValueStringPattern();

    /**
     * Set value of string pattern into place on view.
     *
     * @param valueStringPattern
     *         value of string pattern.
     */
    public abstract void setValueStringPattern(@NotNull String valueStringPattern);

    /** @return value of capture group. */
    @NotNull
    public abstract String getValueStringCaptureGroup();

    /**
     * Set value of capture group into place on view.
     *
     * @param valueStringCaptureGroup
     *         value of capture group.
     */
    public abstract void setValueStringCaptureGroup(@NotNull String valueStringCaptureGroup);

    /** @return property scope. */
    @NotNull
    public abstract String getPropertyScope();

    /**
     * Select property scope in place on view.
     *
     * @param propertyScope
     *         property scope.
     */
    public abstract void selectPropertyScope(@NotNull String propertyScope);

    /**
     * Set property scope into place on view.
     *
     * @param propertyScopes
     *         property scope.
     */
    public abstract void setPropertyScopes(@NotNull List<String> propertyScopes);

    /** @return description. */
    @NotNull
    public abstract String getDescription();

    /**
     * Set description into place on view.
     *
     * @param description
     */
    public abstract void setDescription(@NotNull String description);

    /**
     * Sets default visible of property panel of property mediator
     *
     * @param isVisible
     *         <code>true</code> shows all properties,
     *         <code>false</code> shows only property name, property action, property scope and description.
     */
    public abstract void setDefaultVisible(boolean isVisible);

    /**
     * Set visible expression panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show expression panel, <code>false</code> not to show
     */
    public abstract void setVisibleExpressionPanel(boolean isVisible);

    /**
     * Set visible literal panel.
     *
     * @param isVisible
     *         <code>true</code> to show literal panel, <code>false</code> not to show
     */
    public abstract void setVisibleLiteralPanel(boolean isVisible);

}
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
package com.codenvy.ide.client.propertiespanel.property;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract view's representation of 'Property' mediator properties panel. It provides an ability to show all available properties of
 * the mediator and edit it.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@ImplementedBy(PropertyPropertiesPanelViewImpl.class)
public abstract class PropertyPropertiesPanelView extends AbstractView<PropertyPropertiesPanelView.ActionDelegate> {

    /**
     * Interface defines methods of {@link PropertyPropertiesPanelPresenter} which calls from view. These methods defines
     * some actions when user changes properties of Property mediator.
     */
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

    /** @return property name from special view's place. */
    @Nonnull
    public abstract String getPropertyName();

    /**
     * Set property name to specials place on view.
     *
     * @param propertyName
     *         name of property which need to set.
     */
    public abstract void setPropertyName(@Nullable String propertyName);

    /** @return property action from special view's place. */
    @Nonnull
    public abstract String getPropertyAction();

    /**
     * Select property action in place on view.
     *
     * @param propertyAction
     *         property action.
     */
    public abstract void selectPropertyAction(@Nonnull String propertyAction);

    /**
     * Set values of property action to special place on view.
     *
     * @param propertyActions
     *         list which contains values of property action.
     */
    public abstract void setPropertyActions(@Nonnull List<String> propertyActions);

    /** @return value type from special view's place. */
    @Nonnull
    public abstract String getValueType();

    /**
     * Select value type in place on view.
     *
     * @param valueType
     *         value of type.
     */
    public abstract void selectValueType(@Nonnull String valueType);

    /**
     * Set values of value type to special place on view.
     *
     * @param valueTypes
     *         list which contains values of value type.
     */
    public abstract void setValueTypes(@Nonnull List<String> valueTypes);

    /** @return property data type from special view's place. */
    @Nonnull
    public abstract String getPropertyDataType();

    /**
     * Set property data type to special place on view.
     *
     * @param propertyDataTypes
     *         value of data type.
     */
    public abstract void setPropertyDataTypes(@Nonnull List<String> propertyDataTypes);

    /**
     * Select property data type in place on view.
     *
     * @param propertyDataType
     *         value of data type.
     */
    public abstract void selectPropertyDataType(@Nonnull String propertyDataType);

    /** @return value of literal from special view's place . */
    @Nonnull
    public abstract String getValueLiteral();

    /**
     * Set value of literal into special place on view.
     *
     * @param valueLiteral
     *         value of literal.
     */
    public abstract void setValueLiteral(@Nullable String valueLiteral);

    /** @return value of expression from special view's place. */
    @Nonnull
    public abstract String getValueExpression();

    /**
     * Set value of expression into special place on view.
     *
     * @param valueExpression
     *         value of expression which need to set.
     */
    public abstract void setValueExpression(@Nullable String valueExpression);

    /** @return value of string pattern from special view's place. */
    @Nonnull
    public abstract String getValueStringPattern();

    /**
     * Set value of string pattern into special place on view.
     *
     * @param valueStringPattern
     *         value of string pattern which need to set.
     */
    public abstract void setValueStringPattern(@Nullable String valueStringPattern);

    /** @return value of capture group from special view's place. */
    @Nonnull
    public abstract String getValueStringCaptureGroup();

    /**
     * Set value of capture group into special place on view.
     *
     * @param valueStringCaptureGroup
     *         value of capture group which need to set.
     */
    public abstract void setValueStringCaptureGroup(@Nullable String valueStringCaptureGroup);

    /** @return property scope from special view's place . */
    @Nonnull
    public abstract String getPropertyScope();

    /**
     * Select property scope in place on view.
     *
     * @param propertyScope
     *         property scope.
     */
    public abstract void selectPropertyScope(@Nonnull String propertyScope);

    /**
     * Set property scope into special place on view.
     *
     * @param propertyScopes
     *         property scope which need to set.
     */
    public abstract void setPropertyScopes(@Nonnull List<String> propertyScopes);

    /** @return description of element from special view's place. */
    @Nonnull
    public abstract String getDescription();

    /**
     * Set description into place on view.
     *
     * @param description
     *         value of description which need to set
     */
    public abstract void setDescription(@Nullable String description);

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
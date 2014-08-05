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
package com.codenvy.ide.client.propertiespanel.header;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@ImplementedBy(HeaderPropertiesPanelViewImpl.class)
public abstract class HeaderPropertiesPanelView extends AbstractView<HeaderPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed header action. */
        void onHeaderActionChanged();

        /** Performs any actions appropriate in response to the user having changed scope. */
        void onScopeChanged();

        /** Performs any actions appropriate in response to the user having changed value type. */
        void onValueTypeChanged();

        /** Performs any actions appropriate in response to the user having changed value literal. */
        void onValueChanged();

        /** Performs any actions appropriate in response to the user having changed header name. */
        void onHeaderNameChanged();

        /** Performs any actions appropriate in response to the user having changed action field. */
        void onActionChanged();

        /** Performs any actions appropriate in response to the user having changed value type field. */
        void onTypeChanged();

        /** Shows the dialog window which needed to be for editing name spaces of element. */
        void onAddHeaderNameSpaceBtnClicked();

        /** Shows the dialog window which needed to be for editing expressions and name spaces of element. */
        void onAddExpressionBtnClicked();

        /** Shows the dialog window which needed to be for editing inline of element. */
        void onAddInlineBtnClicked();

    }

    /**
     * Set visible value type panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show value type panel, <code>false</code> not to show
     */
    public abstract void setVisibleValueTypePanel(boolean isVisible);

    /**
     * Set visible value literal panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show value literal panel, <code>false</code> not to show
     */
    public abstract void setVisibleValueLiteralPanel(boolean isVisible);

    /**
     * Set visible value expression panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show value expression panel, <code>false</code> not to show
     */
    public abstract void setVisibleValueExpressionPanel(boolean isVisible);

    /**
     * Set visible value inline panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show value inline panel, <code>false</code> not to show
     */
    public abstract void setVisibleValueInlinePanel(boolean isVisible);

    /**
     * Set visible header name panel on view.
     *
     * @param isVisible
     *         <code>true</code> to show header name panel, <code>false</code> not to show
     */
    public abstract void setVisibleHeaderNamePanel(boolean isVisible);

    /** @return action value from the special place on the view which uses for showing action parameter */
    @Nonnull
    public abstract String getAction();

    /**
     * Select header action in place on view.
     *
     * @param headerAction
     *         header action format value
     */
    public abstract void selectHeaderAction(@Nullable String headerAction);

    /**
     * Sets action value to the special place on the view which uses for showing action parameter.
     *
     * @param headerAction
     *         list of actions which need to set to special list box
     */
    public abstract void setAction(@Nullable List<String> headerAction);

    /** @return scope value from the special place on the view which uses for showing scope parameter */
    @Nonnull
    public abstract String getScope();

    /**
     * Select scope format in place on view.
     *
     * @param scope
     *         scope format value
     */
    public abstract void selectScope(@Nullable String scope);

    /**
     * Sets scope value to the special place on the view which uses for showing scope parameter.
     *
     * @param scope
     *         list of scopes which need to set to special list box
     */
    public abstract void setScope(@Nullable List<String> scope);

    /** @return type value from the special place on the view which uses for showing type parameter */
    @Nonnull
    public abstract String getValueType();

    /**
     * Select value type format in place on view.
     *
     * @param valueType
     *         value type format value
     */
    public abstract void selectValueType(@Nullable String valueType);

    /**
     * Sets type value to the special place on the view which uses for showing type parameter.
     *
     * @param valueType
     *         list of value types which need to set to special list box
     */
    public abstract void setValueType(@Nullable List<String> valueType);

    /** @return literal value from the special place on the view which uses for showing literal parameter */
    @Nonnull
    public abstract String getValue();

    /**
     * Sets literal value to the special place on the view which uses for showing literal parameter.
     *
     * @param valueLiteral
     *         value of literal which need to set to special place of view
     */
    public abstract void setValue(@Nullable String valueLiteral);

    /** @return header name value from the special place on the view which uses for showing header name parameter */
    @Nonnull
    public abstract String getHeaderName();

    /**
     * Sets header name value to the special place on the view which uses for showing header name parameter.
     *
     * @param headerName
     *         value of header name which need to set to special place of view
     */
    public abstract void setHeaderName(@Nullable String headerName);

    /**
     * Sets expression to the special place on the view which uses for showing expression parameter.
     *
     * @param expression
     *         value of expression which need to set to special place of view
     */
    public abstract void setExpression(@Nullable String expression);

    /** @return expression from the special place on the view which uses for showing expression parameter */
    @Nullable
    public abstract String getExpression();

}
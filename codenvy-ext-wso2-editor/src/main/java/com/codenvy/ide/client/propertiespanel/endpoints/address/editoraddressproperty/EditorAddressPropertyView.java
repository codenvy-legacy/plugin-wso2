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
package com.codenvy.ide.client.propertiespanel.endpoints.address.editoraddressproperty;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * The presentation of Editor Address Property view. It contains a general places for properties of address endpoint.
 *
 * @author Dmitry Shnurenko
 */
@ImplementedBy(EditorAddressPropertyViewImpl.class)
public interface EditorAddressPropertyView extends View<EditorAddressPropertyView.ActionDelegate> {

    /** @return name value of property of address end point from special place on view */
    @Nonnull
    String getName();

    /**
     * Sets name value of property of address end point to special place on view.
     *
     * @param name
     *         value which need to set
     */
    void setName(@Nonnull String name);

    /** @return value of property of address end point from special place on view */
    @Nonnull
    String getValue();

    /**
     * Sets value of property of address end point to special place on view.
     *
     * @param value
     *         value which need to set
     */
    void setValue(@Nonnull String value);

    /** @return type value of property of address end point from special place on view */
    @Nonnull
    String getType();

    /**
     * Select type format in special place on view.
     *
     * @param type
     *         type format value
     */
    void selectType(@Nonnull String type);

    /** @return scope value of property of address end point from special place on view */
    @Nonnull
    String getScope();

    /**
     * Select scope format in special place on view.
     *
     * @param scope
     *         scope format value
     */
    void selectScope(@Nonnull String scope);

    /** Shows dialog window for editing property of address endpoint. */
    void showWindow();

    /** Hides dialog window for editing property of address endpoint. */
    void hideWindow();

    /**
     * Sets visible button for adding name spaces on view.
     *
     * @param isVisible
     *         <code>true</code> to show button, <code>false</code> not to show
     */
    void setNameSpaceBtnVisible(boolean isVisible);

    /**
     * Sets enable of value text box on view.
     *
     * @param isEnable
     *         <code>true</code> enable text box, <code>false</code> not enable
     */
    void setTextBoxEnable(boolean isEnable);

    /**
     * Interface defines methods of EditorAddressProperty's presenter which calls from view. These methods defines
     * some actions when user click the button or change value of parameters in list box.
     */
    public interface ActionDelegate {

        /** Performs any actions appropriate in response to the user clicked ok button. */
        void onOkButtonClicked();

        /** Performs any actions appropriate in response to the user clicked cancel button. */
        void onCancelButtonClicked();

        /** Performs any actions appropriate in response to the user having changed type. */
        void onValueTypeChanged();

        /** Performs any actions appropriate in response to the user having changed scope. */
        void onValueScopeChanged();

        /** Performs any actions appropriate in response to the user clicked on add name space button. */
        void onAddNameSpaceBtnClicked();

    }
}

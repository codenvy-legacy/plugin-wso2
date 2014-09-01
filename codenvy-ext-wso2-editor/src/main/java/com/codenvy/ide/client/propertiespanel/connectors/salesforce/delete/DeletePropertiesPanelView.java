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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.delete;

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.codenvy.ide.client.propertiespanel.namespace.NameSpaceEditorPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

/**
 * Defines methods which allows display properties panel of Delete connector differently.
 *
 * @author Dmitry Shnurenko
 */
@ImplementedBy(DeletePropertiesPanelViewImpl.class)
public abstract class DeletePropertiesPanelView extends AbstractView<DeletePropertiesPanelView.ActionDelegate> {

    /**
     * Sets general panel to properties panel. The panel is general for all connectors.
     *
     * @param base
     *         presenter of general panel
     */
    abstract void setGeneralPanel(@Nonnull BaseConnectorPanelPresenter base);

    /** @return value of all or none parameter from special view's place */
    @Nonnull
    abstract String getAllOrNone();

    /**
     * Sets value of all or none parameter to special place on view.
     *
     * @param allOrNone
     *         value which need to set
     */
    abstract void setAllOrNone(@Nonnull String allOrNone);

    /** @return value of subject parameter from special view's place */
    @Nonnull
    abstract String getSubjectValue();

    /**
     * Sets value of subject parameter to special place on view.
     *
     * @param subject
     *         value which need to set
     */
    abstract void setSubjectValue(@Nonnull String subject);

    /**
     * Sets parameter which allows to hide or to show buttons on properties panel of Delete connector.
     *
     * @param isVisible
     *         <code>true</code> button is visible, <code>false</code> button isn't visible
     */
    abstract void setVisibleButton(boolean isVisible);

    /**
     * Sets parameter which allows to do enable or disable text field on properties panel of Delete connector.
     *
     * @param isVisible
     *         <code>true</code> field is enable, <code>false</code> field is disable
     */
    abstract void setEnableTextField(boolean isVisible);


    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Defines some action which are executed when user change all or none parameter in text field of view. */
        void onAllOrNoneChanged();

        /** Defines some action which are executed when user change subjects parameter in text field of view. */
        void onSubjectChanged();

        /**
         * Calls special method from {@link NameSpaceEditorPresenter} which shows dialog window for adding or editing name spaces
         * for all or none parameter.
         */
        void onAllOrNoneBtnClicked();

        /**
         * Calls special method from {@link NameSpaceEditorPresenter} which shows dialog window for adding or editing name spaces
         * for subject parameter.
         */
        void onSubjectBtnClicked();

    }
}

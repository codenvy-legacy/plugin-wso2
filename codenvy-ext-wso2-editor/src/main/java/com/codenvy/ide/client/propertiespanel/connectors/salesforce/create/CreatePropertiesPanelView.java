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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.create;

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.codenvy.ide.client.elements.connectors.salesforce.BaseSalesforce.ParameterEditorType;

/**
 * The view of {@link CreatePropertiesPanelPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(CreatePropertiesPanelViewImpl.class)
public abstract class CreatePropertiesPanelView extends AbstractView<CreatePropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed all or none parameter on properties panel. */
        void onAllOrNoneChanged();

        /** Performs any actions appropriate in response to the user having changed truncate on properties panel. */
        void onTruncateChanged();

        /** Performs any actions appropriate in response to the user having changed subject on properties panel. */
        void onSubjectChanged();

        /** Performs any actions appropriate in response to the user having clicked AllOrNone button on properties panel. */
        void allOrNoneButtonClicked();

        /** Performs any actions appropriate in response to the user having clicked truncate button on properties panel. */
        void truncateButtonClicked();

        /** Performs any actions appropriate in response to the user having clicked forceLogin button on properties panel. */
        void subjectsButtonClicked();

    }

    /** @return login URL value from the special place on the view which uses for showing subjects parameter */
    @Nonnull
    public abstract String getSubjects();

    /**
     * Sets force login URL to the special place on the view which uses for showing subjects parameter.
     *
     * @param subjects
     *         value of subjects parameter
     */
    public abstract void setSubjects(@Nonnull String subjects);

    /** @return password value from the special place on the view which uses for showing truncate parameter */
    @Nonnull
    public abstract String getTruncate();

    /**
     * Sets password value to the special place on the view which uses for showing truncate parameter.
     *
     * @param truncate
     *         value of truncate parameter
     */
    public abstract void setTruncate(@Nonnull String truncate);

    /** @return username value from the special place on the view which uses for showing allOrNone parameter */
    @Nonnull
    public abstract String getAllOrNone();

    /**
     * Sets username value to the special place on the view which uses for showing allOrNone parameter.
     *
     * @param allOrNone
     *         value of allOrNone
     */
    public abstract void setAllOrNone(@Nonnull String allOrNone);

    /**
     * Sets force login URL to the special place on the view which uses for showing namespace subjects parameter.
     *
     * @param subjects
     *         value of subjects parameter
     */
    public abstract void setSubjectsNamespace(@Nullable String subjects);

    /**
     * Sets password value to the special place on the view which uses for showing namespace truncate parameter.
     *
     * @param truncate
     *         value of truncate parameter
     */
    public abstract void setTruncateNamespace(@Nullable String truncate);

    /**
     * Sets username value to the special place on the view which uses for showing allOrNone username parameter.
     *
     * @param allOrNone
     *         value of allOrNone
     */
    public abstract void setAllOrNoneNamespace(@Nullable String allOrNone);

    /**
     * Adds base elements of connector's property panel.
     *
     * @param base
     *         presenter of base connector
     */
    public abstract void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base);

    /**
     * Displays properties panel to a certain value of parameter editor type.
     *
     * @param parameterEditorType
     *         value of parameter editor type that was selected
     */
    public abstract void onParameterEditorTypeChanged(@Nonnull ParameterEditorType parameterEditorType);

}

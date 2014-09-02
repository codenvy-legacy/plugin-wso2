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
package com.codenvy.ide.client.propertiespanel.connectors.salesforce.describesobject;

import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.connectors.base.BaseConnectorPanelPresenter;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The view of {@link DescribeSubjectPropertiesPanelPresenter}
 *
 * @author Valeriy Svydenko
 */
@ImplementedBy(DescribeSubjectPropertiesPanelViewImpl.class)
public abstract class DescribeSubjectPropertiesPanelView extends AbstractView<DescribeSubjectPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs any actions appropriate in response to the user having changed subject on properties panel. */
        void onSubjectChanged();

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

    /**
     * Sets force login URL to the special place on the view which uses for showing namespace subjects parameter.
     *
     * @param subjects
     *         value of subjects parameter
     */
    public abstract void setSubjectsNamespace(@Nullable String subjects);

    /**
     * Adds base elements of connector's property panel.
     *
     * @param base
     *         presenter of base connector
     */
    public abstract void addBaseConnector(@Nonnull BaseConnectorPanelPresenter base);

    /**
     * Sets visible subjects namespace panel on the view.
     *
     * @param isVisible
     *         <code>true</code> to show subjects namespace panel, <code>false</code> not to show
     */
    public abstract void setVisibleSubjectsNamespacePanel(boolean isVisible);

    /**
     * Sets visible subjects field on the view.
     *
     * @param isVisible
     *         <code>true</code> to show subjects field, <code>false</code> not to show
     */
    public abstract void setVisibleSubjects(boolean isVisible);

}

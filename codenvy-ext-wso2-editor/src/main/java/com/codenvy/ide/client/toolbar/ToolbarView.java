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
package com.codenvy.ide.client.toolbar;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

/**
 * The abstract view's representation of tool bar. It provides an ability to show all elements which tool bar contains.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@ImplementedBy(ToolbarViewImpl.class)
public abstract class ToolbarView extends AbstractView<ToolbarView.ActionDelegate> {

    /**
     * Interface defines methods of {@link ToolbarPresenter} which calls from view. These methods defines
     * some actions when user click on an icon of element.
     */
    public interface ActionDelegate extends AbstractView.ActionDelegate {

        /** Performs some actions in response to a user clicked on Log icon of tool bar. */
        void onLogButtonClicked();

        /** Performs some actions in response to a user clicked on Property icon of tool bar. */
        void onPropertyButtonClicked();

        /** Performs some actions in response to a user clicked on Payload icon of tool bar. */
        void onPayloadFactoryButtonClicked();

        /** Performs some actions in response to a user clicked on Send icon of tool bar. */
        void onSendButtonClicked();

        /** Performs some actions in response to a user clicked on Header icon of tool bar. */
        void onHeaderButtonClicked();

        /** Performs some actions in response to a user clicked on Respond icon of tool bar. */
        void onRespondButtonClicked();

        /** Performs some actions in response to a user clicked on Filter icon of tool bar. */
        void onFilterButtonClicked();

        /** Performs some actions in response to a user clicked on Switch icon of tool bar. */
        void onSwitchButtonClicked();

        /** Performs some actions in response to a user clicked on Sequence icon of tool bar. */
        void onSequenceButtonClicked();

        /** Performs some actions in response to a user clicked on Enrich icon of tool bar. */
        void onEnrichButtonClicked();

        /** Performs some actions in response to a user clicked on LoopBack icon of tool bar. */
        void onLoopBackButtonClicked();

        /** Performs some actions in response to a user clicked on CallTemplate icon of tool bar. */
        void onCallTemplateButtonClicked();

        /** Performs some actions in response to a user clicked on Call icon of tool bar. */
        void onCallButtonClicked();

        /** Performs some actions in response to a user clicked on Address endpoint icon of tool bar. */
        void onAddressEndpointButtonClicked();

        /** Performs some actions in response to a user clicked on SalesForce init icon of tool bar. */
        void onSalesForceInitClicked();

        /** Performs some actions in response to a user clicked on SalesForce create icon of tool bar. */
        void onSalesForceCreateClicked();

        /** Performs some actions in response to a user clicked on SalesForce delete icon of tool bar. */
        void onSalesForceDeleteClicked();

        /** Performs some actions in response to a user clicked on SalesForce update icon of tool bar. */
        void onSalesforceUpdateClicked();

    }

}
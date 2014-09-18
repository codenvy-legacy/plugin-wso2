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

package com.codenvy.ide.client.initializers.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.SalesForceConnectorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 */
public class SalesForceConnectorToolbarInitializer extends AbstractToolbarInitializer {

    @Inject
    public SalesForceConnectorToolbarInitializer(ToolbarPresenter toolbar,
                                                 EditorResources resources,
                                                 WSO2EditorLocalizationConstant locale) {
        super(toolbar, resources, locale);
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        toolbar.addGroup(ToolbarGroupIds.SALESFORCE_CONNECTORS, locale.toolbarGroupSalesforceConnector());

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceCreateConnectorTitle(),
                        locale.toolbarSalesforceCreateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.CREATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesForceDeleteTitle(),
                        locale.toolbarSalesForceDeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceDescribeGlobalConnectorTitle(),
                        locale.toolbarSalesforceDescribeGlobalConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_GLOBAL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceDescribeSubjectConnectorTitle(),
                        locale.toolbarSalesforceDescribeSubjectConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceDescribeSubjectsConnectorTitle(),
                        locale.toolbarSalesforceDescribeSubjectsConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECTS);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceEmptyRecycleBinConnectorTitle(),
                        locale.toolbarSalesforceEmptyRecycleBinConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.EMPTY_RECYCLE_BIN);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceGetUserInfoConnectorTitle(),
                        locale.toolbarSalesforceGetUserInfoConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.GET_USER_INFORMATION);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceInitConnectorTitle(),
                        locale.toolbarSalesforceInitConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceLogOutConnectorTitle(),
                        locale.toolbarSalesforceLogOutConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.LOGOUT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceQueryTitle(),
                        locale.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceQueryTitle(),
                        locale.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceQueryAllTitle(),
                        locale.toolbarSalesforceQueryAllTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_ALL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceQueryMoreTitle(),
                        locale.toolbarSalesforceQueryMoreTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_MORE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceResetPasswordTitle(),
                        locale.toolbarSalesforceResetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RESET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceRetriveTitle(),
                        locale.toolbarSalesforceRetriveTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RETRIVE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceSearchTitle(),
                        locale.toolbarSalesforceSearchTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceSendEmailTitle(),
                        locale.toolbarSalesforceSendEmailTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceSendEmailMessageTitle(),
                        locale.toolbarSalesforceSendEmailMessageTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceSetPasswordTitle(),
                        locale.toolbarSalesforceSetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceUndeleteTitle(),
                        locale.toolbarSalesforceUndeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UNDELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceUpdateConnectorTitle(),
                        locale.toolbarSalesforceUpdateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPDATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        locale.toolbarSalesforceUpsetTitle(),
                        locale.toolbarSalesforceUpsetTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPSET);
    }

}
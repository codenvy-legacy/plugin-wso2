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

package com.codenvy.ide.client.initializers.propertiespanel;

import com.codenvy.ide.client.elements.connectors.salesforce.Create;
import com.codenvy.ide.client.elements.connectors.salesforce.Delete;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeGlobal;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeSubject;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeSubjects;
import com.codenvy.ide.client.elements.connectors.salesforce.EmptyRecycleBin;
import com.codenvy.ide.client.elements.connectors.salesforce.GetUserInformation;
import com.codenvy.ide.client.elements.connectors.salesforce.InitSalesforce;
import com.codenvy.ide.client.elements.connectors.salesforce.LogOut;
import com.codenvy.ide.client.elements.connectors.salesforce.Query;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryAll;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryMore;
import com.codenvy.ide.client.elements.connectors.salesforce.ResetPassword;
import com.codenvy.ide.client.elements.connectors.salesforce.Retrieve;
import com.codenvy.ide.client.elements.connectors.salesforce.Search;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmail;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmailMessage;
import com.codenvy.ide.client.elements.connectors.salesforce.SetPassword;
import com.codenvy.ide.client.elements.connectors.salesforce.UnDelete;
import com.codenvy.ide.client.elements.connectors.salesforce.Update;
import com.codenvy.ide.client.elements.connectors.salesforce.Upset;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.CreateConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.DeleteConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.DescribeConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.DescribeGlobalConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.DescribeSubjectConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.EmptyRecycleBinConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.GetUserInformationConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.InitSalesforceConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.LogOutConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryAllConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.QueryMoreConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.ResetPasswordConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.RetrieveConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.SearchConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.SendEmailConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.SendEmailMessageConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.SetPasswordConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.UndeleteConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.UpdateConnectorPresenter;
import com.codenvy.ide.client.propertiespanel.connectors.salesforce.UpsetConnectorPresenter;
import com.google.inject.Inject;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class SalesForceConnectorPropertiesPanelInitializer extends AbstractPropertiesPanelInitializer {

    private final InitSalesforceConnectorPresenter     initPropertiesPanel;
    private final CreateConnectorPresenter             createPropertiesPanel;
    private final UpdateConnectorPresenter             updatePropertiesPanel;
    private final DeleteConnectorPresenter             deletePropertiesPanel;
    private final EmptyRecycleBinConnectorPresenter    emptyRecycleBinPropertiesPanel;
    private final LogOutConnectorPresenter             logOutPropertiesPanel;
    private final GetUserInformationConnectorPresenter getUserInformationPropertiesPanel;
    private final DescribeGlobalConnectorPresenter     describeGlobalPropertiesPanel;
    private final DescribeSubjectConnectorPresenter    describeSubjectPropertiesPanel;
    private final DescribeConnectorPresenter           describePropertiesPanel;
    private final UpsetConnectorPresenter              upsetPropertiesPanel;
    private final UndeleteConnectorPresenter           undeletePropertiesPanel;
    private final SetPasswordConnectorPresenter        passwordPropertiesPanel;
    private final SendEmailMessageConnectorPresenter   sendEmailMessagePropertiesPanel;
    private final SendEmailConnectorPresenter          sendEmailPropertiesPanel;
    private final SearchConnectorPresenter             searchConnectorPresenter;
    private final RetrieveConnectorPresenter           retrievePropertiesPanel;
    private final QueryConnectorPresenter              queryPropertiesPanel;
    private final QueryAllConnectorPresenter           queryAllPropertiesPanel;
    private final QueryMoreConnectorPresenter          queryMorePropertiesPanel;
    private final ResetPasswordConnectorPresenter      resetPasswordPropertiesPanel;

    @Inject
    public SalesForceConnectorPropertiesPanelInitializer(PropertiesPanelManager manager,
                                                         InitSalesforceConnectorPresenter initPropertiesPanel,
                                                         CreateConnectorPresenter createPropertiesPanel,
                                                         UpdateConnectorPresenter updatePropertiesPanel,
                                                         DeleteConnectorPresenter deletePropertiesPanel,
                                                         EmptyRecycleBinConnectorPresenter emptyRecycleBinPropertiesPanel,
                                                         LogOutConnectorPresenter logOutPropertiesPanel,
                                                         GetUserInformationConnectorPresenter getUserInformationPropertiesPanel,
                                                         DescribeGlobalConnectorPresenter describeGlobalPropertiesPanel,
                                                         DescribeSubjectConnectorPresenter describeSubjectPropertiesPanel,
                                                         DescribeConnectorPresenter describePropertiesPanel,
                                                         UpsetConnectorPresenter upsetPropertiesPanel,
                                                         UndeleteConnectorPresenter undeletePropertiesPanel,
                                                         SetPasswordConnectorPresenter passwordPropertiesPanel,
                                                         SendEmailMessageConnectorPresenter sendEmailMessagePropertiesPanel,
                                                         SendEmailConnectorPresenter sendEmailPropertiesPanel,
                                                         SearchConnectorPresenter searchConnectorPresenter,
                                                         RetrieveConnectorPresenter retrievePropertiesPanel,
                                                         QueryConnectorPresenter queryPropertiesPanel,
                                                         QueryAllConnectorPresenter queryAllPropertiesPanel,
                                                         QueryMoreConnectorPresenter queryMorePropertiesPanel,
                                                         ResetPasswordConnectorPresenter resetPasswordPropertiesPanel) {
        super(manager);
        this.initPropertiesPanel = initPropertiesPanel;
        this.createPropertiesPanel = createPropertiesPanel;
        this.updatePropertiesPanel = updatePropertiesPanel;
        this.deletePropertiesPanel = deletePropertiesPanel;
        this.emptyRecycleBinPropertiesPanel = emptyRecycleBinPropertiesPanel;
        this.logOutPropertiesPanel = logOutPropertiesPanel;
        this.getUserInformationPropertiesPanel = getUserInformationPropertiesPanel;
        this.describeGlobalPropertiesPanel = describeGlobalPropertiesPanel;
        this.describeSubjectPropertiesPanel = describeSubjectPropertiesPanel;
        this.describePropertiesPanel = describePropertiesPanel;
        this.upsetPropertiesPanel = upsetPropertiesPanel;
        this.undeletePropertiesPanel = undeletePropertiesPanel;
        this.passwordPropertiesPanel = passwordPropertiesPanel;
        this.sendEmailMessagePropertiesPanel = sendEmailMessagePropertiesPanel;
        this.sendEmailPropertiesPanel = sendEmailPropertiesPanel;
        this.searchConnectorPresenter = searchConnectorPresenter;
        this.retrievePropertiesPanel = retrievePropertiesPanel;
        this.queryPropertiesPanel = queryPropertiesPanel;
        this.queryAllPropertiesPanel = queryAllPropertiesPanel;
        this.queryMorePropertiesPanel = queryMorePropertiesPanel;
        this.resetPasswordPropertiesPanel = resetPasswordPropertiesPanel;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(InitSalesforce.class, initPropertiesPanel);
        manager.register(Create.class, createPropertiesPanel);
        manager.register(Update.class, updatePropertiesPanel);
        manager.register(Delete.class, deletePropertiesPanel);
        manager.register(DescribeGlobal.class, describeGlobalPropertiesPanel);
        manager.register(DescribeSubject.class, describeSubjectPropertiesPanel);
        manager.register(DescribeSubjects.class, describePropertiesPanel);
        manager.register(EmptyRecycleBin.class, emptyRecycleBinPropertiesPanel);
        manager.register(LogOut.class, logOutPropertiesPanel);
        manager.register(GetUserInformation.class, getUserInformationPropertiesPanel);
        manager.register(Upset.class, upsetPropertiesPanel);
        manager.register(UnDelete.class, undeletePropertiesPanel);
        manager.register(SetPassword.class, passwordPropertiesPanel);
        manager.register(SendEmailMessage.class, sendEmailMessagePropertiesPanel);
        manager.register(SendEmail.class, sendEmailPropertiesPanel);
        manager.register(Search.class, searchConnectorPresenter);
        manager.register(Query.class, queryPropertiesPanel);
        manager.register(QueryAll.class, queryAllPropertiesPanel);
        manager.register(QueryMore.class, queryMorePropertiesPanel);
        manager.register(ResetPassword.class, resetPasswordPropertiesPanel);
        manager.register(Retrieve.class, retrievePropertiesPanel);
    }

}
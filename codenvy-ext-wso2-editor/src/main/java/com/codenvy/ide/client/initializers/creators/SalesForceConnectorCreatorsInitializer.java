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

package com.codenvy.ide.client.initializers.creators;

import com.codenvy.ide.client.constants.SalesForceConnectorCreatingState;
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
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Andrey Plotnikov
 */
public class SalesForceConnectorCreatorsInitializer extends AbstractCreatorsInitializer {

    private final Provider<InitSalesforce>     initSalesforceProvider;
    private final Provider<Create>             createSalesforceProvider;
    private final Provider<Update>             updateSalesforceProvider;
    private final Provider<Delete>             deleteSaleForceProvider;
    private final Provider<EmptyRecycleBin>    emptyRecycleBinProvider;
    private final Provider<LogOut>             logOutProvider;
    private final Provider<GetUserInformation> getUserInformationProvider;
    private final Provider<DescribeGlobal>     describeGlobalProvider;
    private final Provider<DescribeSubject>    describeSobjectProvider;
    private final Provider<DescribeSubjects>   describeSobjectsProvider;
    private final Provider<Query>              queryProvider;
    private final Provider<QueryAll>           queryAllProvider;
    private final Provider<QueryMore>          queryMoreProvider;
    private final Provider<ResetPassword>      resetPasswordProvider;
    private final Provider<Retrieve>           retrieveProvider;
    private final Provider<Search>             searchProvider;
    private final Provider<SendEmail>          sendEmailProvider;
    private final Provider<SendEmailMessage>   sendEmailMessageProvider;
    private final Provider<SetPassword>        setPasswordProvider;
    private final Provider<UnDelete>           undeleteProvider;
    private final Provider<Upset>              upsetProvider;

    @Inject
    public SalesForceConnectorCreatorsInitializer(ElementCreatorsManager manager,
                                                  Provider<InitSalesforce> initSalesforceProvider,
                                                  Provider<Create> createSalesforceProvider,
                                                  Provider<Update> updateSalesforceProvider,
                                                  Provider<Delete> deleteSaleForceProvider,
                                                  Provider<EmptyRecycleBin> emptyRecycleBinProvider,
                                                  Provider<LogOut> logOutProvider,
                                                  Provider<GetUserInformation> getUserInformationProvider,
                                                  Provider<DescribeGlobal> describeGlobalProvider,
                                                  Provider<DescribeSubject> describeSobjectProvider,
                                                  Provider<DescribeSubjects> describeSobjectsProvider,
                                                  Provider<Query> queryProvider,
                                                  Provider<QueryAll> queryAllProvider,
                                                  Provider<QueryMore> queryMoreProvider,
                                                  Provider<ResetPassword> resetPasswordProvider,
                                                  Provider<Retrieve> retrieveProvider,
                                                  Provider<Search> searchProvider,
                                                  Provider<SendEmail> sendEmailProvider,
                                                  Provider<SendEmailMessage> sendEmailMessageProvider,
                                                  Provider<SetPassword> setPasswordProvider,
                                                  Provider<UnDelete> undeleteProvider,
                                                  Provider<Upset> upsetProvider) {
        super(manager);
        this.initSalesforceProvider = initSalesforceProvider;
        this.createSalesforceProvider = createSalesforceProvider;
        this.updateSalesforceProvider = updateSalesforceProvider;
        this.deleteSaleForceProvider = deleteSaleForceProvider;
        this.emptyRecycleBinProvider = emptyRecycleBinProvider;
        this.logOutProvider = logOutProvider;
        this.getUserInformationProvider = getUserInformationProvider;
        this.describeGlobalProvider = describeGlobalProvider;
        this.describeSobjectProvider = describeSobjectProvider;
        this.describeSobjectsProvider = describeSobjectsProvider;
        this.queryProvider = queryProvider;
        this.queryAllProvider = queryAllProvider;
        this.queryMoreProvider = queryMoreProvider;
        this.resetPasswordProvider = resetPasswordProvider;
        this.retrieveProvider = retrieveProvider;
        this.searchProvider = searchProvider;
        this.sendEmailProvider = sendEmailProvider;
        this.sendEmailMessageProvider = sendEmailMessageProvider;
        this.setPasswordProvider = setPasswordProvider;
        this.undeleteProvider = undeleteProvider;
        this.upsetProvider = upsetProvider;
    }

    /** {@inheritDoc} */
    @Override
    public void initialize() {
        manager.register(InitSalesforce.ELEMENT_NAME,
                         InitSalesforce.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.INIT,
                         initSalesforceProvider);

        manager.register(Create.ELEMENT_NAME, Create.SERIALIZATION_NAME, SalesForceConnectorCreatingState.CREATE, createSalesforceProvider);
        manager.register(Update.ELEMENT_NAME, Update.SERIALIZATION_NAME, SalesForceConnectorCreatingState.UPDATE, updateSalesforceProvider);
        manager.register(Delete.ELEMENT_NAME, Delete.SERIALIZATION_NAME, SalesForceConnectorCreatingState.DELETE, deleteSaleForceProvider);

        manager.register(EmptyRecycleBin.ELEMENT_NAME,
                         EmptyRecycleBin.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.EMPTY_RECYCLE_BIN,
                         emptyRecycleBinProvider);

        manager.register(LogOut.ELEMENT_NAME, LogOut.SERIALIZATION_NAME, SalesForceConnectorCreatingState.LOGOUT, logOutProvider);

        manager.register(GetUserInformation.ELEMENT_NAME,
                         GetUserInformation.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.GET_USER_INFORMATION,
                         getUserInformationProvider);

        manager.register(DescribeGlobal.ELEMENT_NAME,
                         DescribeGlobal.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.DESCRIBE_GLOBAL,
                         describeGlobalProvider);

        manager.register(DescribeSubject.ELEMENT_NAME,
                         DescribeSubject.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.DESCRIBE_SUBJECT,
                         describeSobjectProvider);

        manager.register(DescribeSubjects.ELEMENT_NAME,
                         DescribeSubjects.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.DESCRIBE_SUBJECTS,
                         describeSobjectsProvider);

        manager.register(Query.ELEMENT_NAME, Query.SERIALIZATION_NAME, SalesForceConnectorCreatingState.QUERY, queryProvider);
        manager.register(QueryAll.ELEMENT_NAME, QueryAll.SERIALIZATION_NAME, SalesForceConnectorCreatingState.QUERY_ALL, queryAllProvider);

        manager.register(QueryMore.ELEMENT_NAME,
                         QueryMore.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.QUERY_MORE,
                         queryMoreProvider);

        manager.register(ResetPassword.ELEMENT_NAME,
                         ResetPassword.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.RESET_PASSWORD,
                         resetPasswordProvider);

        manager.register(Retrieve.ELEMENT_NAME, Retrieve.SERIALIZATION_NAME, SalesForceConnectorCreatingState.RETRIVE, retrieveProvider);
        manager.register(Search.ELEMENT_NAME, Search.SERIALIZATION_NAME, SalesForceConnectorCreatingState.SEARCH, searchProvider);

        manager.register(SendEmail.ELEMENT_NAME,
                         SendEmail.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.SEND_EMAIL,
                         sendEmailProvider);

        manager.register(SendEmailMessage.ELEMENT_NAME,
                         SendEmailMessage.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.SEND_EMAIL_MESSAGE,
                         sendEmailMessageProvider);

        manager.register(SetPassword.ELEMENT_NAME,
                         SetPassword.SERIALIZATION_NAME,
                         SalesForceConnectorCreatingState.SET_PASSWORD,
                         setPasswordProvider);

        manager.register(UnDelete.ELEMENT_NAME, UnDelete.SERIALIZATION_NAME, SalesForceConnectorCreatingState.UNDELETE, undeleteProvider);
        manager.register(Upset.ELEMENT_NAME, Upset.SERIALIZATION_NAME, SalesForceConnectorCreatingState.UPSET, upsetProvider);
    }

}
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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.WSO2EditorLocalizationConstant;
import com.codenvy.ide.client.constants.EndpointCreatingState;
import com.codenvy.ide.client.constants.GoogleSpreadsheedConnectorCreatingState;
import com.codenvy.ide.client.constants.JiraConnectorCreatingState;
import com.codenvy.ide.client.constants.MediatorCreatingState;
import com.codenvy.ide.client.constants.SalesForceConnectorCreatingState;
import com.codenvy.ide.client.constants.ToolbarGroupIds;
import com.codenvy.ide.client.constants.TwitterConnectorCreatingState;
import com.codenvy.ide.client.elements.RootElement;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateSpreadsheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.CreateWorksheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.DeleteWorksheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllCells;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllCellsCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllSpreadsheets;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAllWorksheets;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetAuthors;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRange;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetCellRangeCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetColumnHeaders;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetSpreadsheetsByTitle;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.GetWorksheetsByTitle;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.ImportCSV;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.InitSpreadsheet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.PurgeWorkshet;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.SearchCell;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.SetRow;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.UpdateWorksheetMetadata;
import com.codenvy.ide.client.elements.connectors.googlespreadsheet.UsernameLogin;
import com.codenvy.ide.client.elements.connectors.jira.AddAttachmentToIssueId;
import com.codenvy.ide.client.elements.connectors.jira.CreateFilter;
import com.codenvy.ide.client.elements.connectors.jira.CreateIssue;
import com.codenvy.ide.client.elements.connectors.jira.DeleteAvatarForProject;
import com.codenvy.ide.client.elements.connectors.jira.DeleteComment;
import com.codenvy.ide.client.elements.connectors.jira.DeleteFilter;
import com.codenvy.ide.client.elements.connectors.jira.DoTransition;
import com.codenvy.ide.client.elements.connectors.jira.GetAvatarsForProject;
import com.codenvy.ide.client.elements.connectors.jira.GetComments;
import com.codenvy.ide.client.elements.connectors.jira.GetComponentsOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetDashboard;
import com.codenvy.ide.client.elements.connectors.jira.GetDashboardById;
import com.codenvy.ide.client.elements.connectors.jira.GetFavouriteFilters;
import com.codenvy.ide.client.elements.connectors.jira.GetFilterById;
import com.codenvy.ide.client.elements.connectors.jira.GetGroup;
import com.codenvy.ide.client.elements.connectors.jira.GetIssue;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuePriorities;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuePriorityById;
import com.codenvy.ide.client.elements.connectors.jira.GetIssueTypeById;
import com.codenvy.ide.client.elements.connectors.jira.GetIssueTypes;
import com.codenvy.ide.client.elements.connectors.jira.GetIssuesForUser;
import com.codenvy.ide.client.elements.connectors.jira.GetProject;
import com.codenvy.ide.client.elements.connectors.jira.GetRolesByIdOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetRolesOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetStatusesOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetTransitions;
import com.codenvy.ide.client.elements.connectors.jira.GetUser;
import com.codenvy.ide.client.elements.connectors.jira.GetUserAssignableProjects;
import com.codenvy.ide.client.elements.connectors.jira.GetUserPermissions;
import com.codenvy.ide.client.elements.connectors.jira.GetVersionsOfProject;
import com.codenvy.ide.client.elements.connectors.jira.GetVotesForIssue;
import com.codenvy.ide.client.elements.connectors.jira.InitJira;
import com.codenvy.ide.client.elements.connectors.jira.PostComment;
import com.codenvy.ide.client.elements.connectors.jira.SearchAssignableUser;
import com.codenvy.ide.client.elements.connectors.jira.SearchAssignableUserMultiProject;
import com.codenvy.ide.client.elements.connectors.jira.SearchIssueViewableUsers;
import com.codenvy.ide.client.elements.connectors.jira.SearchJira;
import com.codenvy.ide.client.elements.connectors.jira.SearchUser;
import com.codenvy.ide.client.elements.connectors.jira.SetActorsToRoleOfProject;
import com.codenvy.ide.client.elements.connectors.jira.UpdateComment;
import com.codenvy.ide.client.elements.connectors.jira.UpdateFilterById;
import com.codenvy.ide.client.elements.connectors.jira.UpdateIssue;
import com.codenvy.ide.client.elements.connectors.jira.UpdateIssueAssignee;
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
import com.codenvy.ide.client.elements.connectors.twitter.DestroyStatus;
import com.codenvy.ide.client.elements.connectors.twitter.GetClosesTrends;
import com.codenvy.ide.client.elements.connectors.twitter.GetDirectMessages;
import com.codenvy.ide.client.elements.connectors.twitter.GetFollowers;
import com.codenvy.ide.client.elements.connectors.twitter.GetFollowersIds;
import com.codenvy.ide.client.elements.connectors.twitter.GetFriends;
import com.codenvy.ide.client.elements.connectors.twitter.GetFriendsIds;
import com.codenvy.ide.client.elements.connectors.twitter.GetHomeTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.GetMentionsTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.GetRetweetsOfMine;
import com.codenvy.ide.client.elements.connectors.twitter.GetSentDirectMessages;
import com.codenvy.ide.client.elements.connectors.twitter.GetTopTrendPlaces;
import com.codenvy.ide.client.elements.connectors.twitter.GetUserTimeLine;
import com.codenvy.ide.client.elements.connectors.twitter.InitTwitter;
import com.codenvy.ide.client.elements.connectors.twitter.Retweet;
import com.codenvy.ide.client.elements.connectors.twitter.SearchPlaces;
import com.codenvy.ide.client.elements.connectors.twitter.SearchTwitter;
import com.codenvy.ide.client.elements.connectors.twitter.SendDirectMessage;
import com.codenvy.ide.client.elements.connectors.twitter.ShowStatus;
import com.codenvy.ide.client.elements.connectors.twitter.UpdateStatus;
import com.codenvy.ide.client.elements.endpoints.addressendpoint.AddressEndpoint;
import com.codenvy.ide.client.elements.mediators.Call;
import com.codenvy.ide.client.elements.mediators.CallTemplate;
import com.codenvy.ide.client.elements.mediators.Filter;
import com.codenvy.ide.client.elements.mediators.Header;
import com.codenvy.ide.client.elements.mediators.LoopBack;
import com.codenvy.ide.client.elements.mediators.Property;
import com.codenvy.ide.client.elements.mediators.Respond;
import com.codenvy.ide.client.elements.mediators.Send;
import com.codenvy.ide.client.elements.mediators.Sequence;
import com.codenvy.ide.client.elements.mediators.Switch;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.payload.PayloadFactory;
import com.codenvy.ide.client.elements.widgets.element.ElementChangedListener;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.initializers.Initializer;
import com.codenvy.ide.client.inject.factories.ElementWidgetFactory;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.codenvy.ide.client.propertiespanel.PropertyChangedListener;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The presenter that provides a business logic of WSO2Editor. It provides ability to configure the editor.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class WSO2Editor extends AbstractPresenter<WSO2EditorView> implements PropertyChangedListener,
                                                                             WSO2EditorView.ActionDelegate,
                                                                             ElementChangedListener {

    private final ToolbarPresenter           toolbar;
    private final RootElement                rootElement;
    private final ElementPresenter           rootElementPresenter;
    private final List<EditorChangeListener> listeners;

    @Inject
    public WSO2Editor(WSO2EditorView view,
                      ElementWidgetFactory elementWidgetFactory,
                      ToolbarPresenter toolbar,
                      RootElement rootElement,
                      PropertiesPanelManager propertiesPanelManager,
                      Set<Initializer> initializers) {
        super(view);
        onShowPropertyButtonClicked();

        this.listeners = new ArrayList<>();
        this.toolbar = toolbar;
        this.rootElement = rootElement;

        this.rootElementPresenter = elementWidgetFactory.createElementPresenter(rootElement);
        this.rootElementPresenter.addElementChangedListener(this);

        propertiesPanelManager.addPropertyChangedListener(this);
        propertiesPanelManager.setContainer(view.getPropertiesPanel());

        for (Initializer initializer : initializers) {
            initializer.initialize();
        }
    }

    @Inject
    private void configureMediatorCreatorsManager(MediatorCreatorsManager mediatorCreatorsManager,
                                                  Provider<Log> logProvider,
                                                  Provider<Enrich> enrichProvider,
                                                  Provider<Filter> filterProvider,
                                                  Provider<Header> headerProvider,
                                                  Provider<Call> callProvider,
                                                  Provider<CallTemplate> callTemplateProvider,
                                                  Provider<LoopBack> loopBackProvider,
                                                  Provider<PayloadFactory> payloadFactoryProvider,
                                                  Provider<Property> propertyProvider,
                                                  Provider<Respond> respondProvider,
                                                  Provider<Send> sendProvider,
                                                  Provider<Sequence> sequenceProvider,
                                                  Provider<Switch> switchProvider,
                                                  Provider<AddressEndpoint> addressEndpointProvider,
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
                                                  Provider<Upset> upsetProvider,
                                                  Provider<AddAttachmentToIssueId> addAttachmentToIssueIdProvider,
                                                  Provider<CreateFilter> createFilterProvider,
                                                  Provider<CreateIssue> createIssueProvider,
                                                  Provider<DeleteAvatarForProject> deleteAvatarForProjectProvider,
                                                  Provider<DeleteComment> deleteCommentProvider,
                                                  Provider<DeleteFilter> deleteFilterProvider,
                                                  Provider<GetDashboard> getDashboardProvider,
                                                  Provider<DoTransition> doTransitionProvider,
                                                  Provider<GetAvatarsForProject> getAvatarsForProjectProvider,
                                                  Provider<GetComments> getCommentsProvider,
                                                  Provider<GetComponentsOfProject> getComponentsOfProjectProvider,
                                                  Provider<GetDashboardById> getDashboardByIdProvider,
                                                  Provider<GetFavouriteFilters> getFavouriteFiltersProvider,
                                                  Provider<GetFilterById> getFilterByIdProvider,
                                                  Provider<GetGroup> getGroupProvider,
                                                  Provider<GetIssue> getIssueProvider,
                                                  Provider<GetIssuePriorities> getIssuePrioritiesProvider,
                                                  Provider<GetIssuePriorityById> getIssuePriorityByIdProvider,
                                                  Provider<GetIssueTypeById> getIssueTypeByIdProvider,
                                                  Provider<GetIssueTypes> getIssueTypesProvider,
                                                  Provider<GetIssuesForUser> getIssuesForUserProvider,
                                                  Provider<GetProject> getProjectProvider,
                                                  Provider<GetRolesByIdOfProject> getRolesByIdOfProjectProvider,
                                                  Provider<GetRolesOfProject> getRolesOfProjectProvider,
                                                  Provider<GetStatusesOfProject> getStatusesOfProjectProvider,
                                                  Provider<GetTransitions> getTransitionsProvider,
                                                  Provider<GetUser> getUserProvider,
                                                  Provider<GetUserAssignableProjects> getUserAssignableProjectsProvider,
                                                  Provider<GetUserPermissions> getUserPermissionsProvider,
                                                  Provider<GetVersionsOfProject> getVersionsOfProjectProvider,
                                                  Provider<GetVotesForIssue> getVotesForIssueProvider,
                                                  Provider<InitJira> initJiraProvider,
                                                  Provider<PostComment> postCommentProvider,
                                                  Provider<SearchAssignableUser> searchAssignableUserProvider,
                                                  Provider<SearchAssignableUserMultiProject> searchAssignableUserMultiProjectProvider,
                                                  Provider<SearchIssueViewableUsers> searchIssueViewableUsersProvider,
                                                  Provider<SearchJira> searchJiraProvider,
                                                  Provider<SearchUser> searchUserProvider,
                                                  Provider<SetActorsToRoleOfProject> setActorsToRoleOfProjectProvider,
                                                  Provider<UpdateComment> updateCommentProvider,
                                                  Provider<UpdateFilterById> updateFilterByIdProvider,
                                                  Provider<UpdateIssue> updateIssueProvider,
                                                  Provider<UpdateIssueAssignee> updateIssueAssigneeProvider,
                                                  Provider<DestroyStatus> destroyStatusProvider,
                                                  Provider<GetClosesTrends> getClosesTrendsProvider,
                                                  Provider<GetDirectMessages> getDirectMessagesProvider,
                                                  Provider<GetFollowers> getFollowersProvider,
                                                  Provider<GetFollowersIds> getFollowersIdsProvider,
                                                  Provider<GetFriends> getFriendsProvider,
                                                  Provider<GetFriendsIds> getFriendsIdsProvider,
                                                  Provider<GetHomeTimeLine> getHomeTimeLineProvider,
                                                  Provider<GetMentionsTimeLine> getMentionsTimeLineProvider,
                                                  Provider<GetRetweetsOfMine> getRetweetsOfMineProvider,
                                                  Provider<GetSentDirectMessages> getSentDirectMessagesProvider,
                                                  Provider<GetTopTrendPlaces> getTopTrendPlacesProvider,
                                                  Provider<GetUserTimeLine> getUserTimeLineProvider,
                                                  Provider<InitTwitter> initTwitterProvider,
                                                  Provider<Retweet> retweetProvider,
                                                  Provider<SearchTwitter> searchTwitterProvider,
                                                  Provider<SearchPlaces> searchPlacesProvider,
                                                  Provider<SendDirectMessage> sendDirectMessageProvider,
                                                  Provider<ShowStatus> showStatusProvider,
                                                  Provider<UpdateStatus> updateStatusProvider,
                                                  Provider<CreateSpreadsheet> createSpreadsheetProvider,
                                                  Provider<CreateWorksheet> createWorksheetProvider,
                                                  Provider<DeleteWorksheet> deleteWorksheetProvider,
                                                  Provider<GetAllCells> getAllCellsProvider,
                                                  Provider<GetAllCellsCSV> getAllCellsCSVProvider,
                                                  Provider<GetAllSpreadsheets> getAllSpreadsheetsProvider,
                                                  Provider<GetAllWorksheets> getAllWorksheetsProvider,
                                                  Provider<GetAuthors> getAuthorsProvider,
                                                  Provider<GetCellRange> getCellRangeProvider,
                                                  Provider<GetColumnHeaders> getColumnHeadersProvider,
                                                  Provider<GetSpreadsheetsByTitle> getSpreadsheetsByTitleProvider,
                                                  Provider<GetWorksheetsByTitle> getWorksheetsByTitleProvider,
                                                  Provider<ImportCSV> importCSVProvider,
                                                  Provider<InitSpreadsheet> initSpreadsheetProvider,
                                                  Provider<PurgeWorkshet> purgeWorkshetProvider,
                                                  Provider<SearchCell> searchCellProvider,
                                                  Provider<SetRow> setRowProvider,
                                                  Provider<UpdateWorksheetMetadata> updateWorksheetMetadataProvider,
                                                  Provider<UsernameLogin> usernameLoginProvider,
                                                  Provider<GetCellRangeCSV> getCellRangeCSVProvider) {

        mediatorCreatorsManager.register(Log.ELEMENT_NAME,
                                         Log.SERIALIZATION_NAME,
                                         MediatorCreatingState.LOG,
                                         logProvider);

        mediatorCreatorsManager.register(Enrich.ELEMENT_NAME,
                                         Enrich.SERIALIZATION_NAME,
                                         MediatorCreatingState.ENRICH,
                                         enrichProvider);

        mediatorCreatorsManager.register(Filter.ELEMENT_NAME,
                                         Filter.SERIALIZATION_NAME,
                                         MediatorCreatingState.FILTER,
                                         filterProvider);

        mediatorCreatorsManager.register(Header.ELEMENT_NAME,
                                         Header.SERIALIZATION_NAME,
                                         MediatorCreatingState.HEADER,
                                         headerProvider);

        mediatorCreatorsManager.register(Call.ELEMENT_NAME,
                                         Call.SERIALIZATION_NAME,
                                         MediatorCreatingState.CALL_MEDIATOR,
                                         callProvider);

        mediatorCreatorsManager.register(CallTemplate.ELEMENT_NAME,
                                         CallTemplate.SERIALIZATION_NAME,
                                         MediatorCreatingState.CALLTEMPLATE,
                                         callTemplateProvider);

        mediatorCreatorsManager.register(LoopBack.ELEMENT_NAME,
                                         LoopBack.SERIALIZATION_NAME,
                                         MediatorCreatingState.LOOPBACK,
                                         loopBackProvider);

        mediatorCreatorsManager.register(PayloadFactory.ELEMENT_NAME,
                                         PayloadFactory.SERIALIZATION_NAME,
                                         MediatorCreatingState.PAYLOAD,
                                         payloadFactoryProvider);

        mediatorCreatorsManager.register(Property.ELEMENT_NAME,
                                         Property.SERIALIZATION_NAME,
                                         MediatorCreatingState.PROPERTY,
                                         propertyProvider);

        mediatorCreatorsManager.register(Respond.ELEMENT_NAME,
                                         Respond.SERIALIZATION_NAME,
                                         MediatorCreatingState.RESPOND,
                                         respondProvider);

        mediatorCreatorsManager.register(Send.ELEMENT_NAME,
                                         Send.SERIALIZATION_NAME,
                                         MediatorCreatingState.SEND,
                                         sendProvider);

        mediatorCreatorsManager.register(Sequence.ELEMENT_NAME,
                                         Sequence.SERIALIZATION_NAME,
                                         MediatorCreatingState.SEQUENCE,
                                         sequenceProvider);

        mediatorCreatorsManager.register(Switch.ELEMENT_NAME,
                                         Switch.SERIALIZATION_NAME,
                                         MediatorCreatingState.SWITCH,
                                         switchProvider);

        mediatorCreatorsManager.register(AddressEndpoint.ELEMENT_NAME,
                                         AddressEndpoint.SERIALIZATION_NAME,
                                         EndpointCreatingState.ADDRESS,
                                         addressEndpointProvider);

        mediatorCreatorsManager.register(InitSalesforce.ELEMENT_NAME,
                                         InitSalesforce.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.INIT,
                                         initSalesforceProvider);

        mediatorCreatorsManager.register(Create.ELEMENT_NAME,
                                         Create.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.CREATE,
                                         createSalesforceProvider);

        mediatorCreatorsManager.register(Update.ELEMENT_NAME,
                                         Update.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.UPDATE,
                                         updateSalesforceProvider);

        mediatorCreatorsManager.register(Delete.ELEMENT_NAME,
                                         Delete.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.DELETE,
                                         deleteSaleForceProvider);

        mediatorCreatorsManager.register(EmptyRecycleBin.ELEMENT_NAME,
                                         EmptyRecycleBin.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.EMPTY_RECYCLE_BIN,
                                         emptyRecycleBinProvider);

        mediatorCreatorsManager.register(LogOut.ELEMENT_NAME,
                                         LogOut.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.LOGOUT,
                                         logOutProvider);

        mediatorCreatorsManager.register(GetUserInformation.ELEMENT_NAME,
                                         GetUserInformation.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.GET_USER_INFORMATION,
                                         getUserInformationProvider);

        mediatorCreatorsManager.register(DescribeGlobal.ELEMENT_NAME,
                                         DescribeGlobal.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.DESCRIBE_GLOBAL,
                                         describeGlobalProvider);

        mediatorCreatorsManager.register(DescribeSubject.ELEMENT_NAME,
                                         DescribeSubject.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.DESCRIBE_SUBJECT,
                                         describeSobjectProvider);

        mediatorCreatorsManager.register(DescribeSubjects.ELEMENT_NAME,
                                         DescribeSubjects.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.DESCRIBE_SUBJECTS,
                                         describeSobjectsProvider);

        mediatorCreatorsManager.register(Query.ELEMENT_NAME,
                                         Query.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.QUERY,
                                         queryProvider);

        mediatorCreatorsManager.register(QueryAll.ELEMENT_NAME,
                                         QueryAll.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.QUERY_ALL,
                                         queryAllProvider);

        mediatorCreatorsManager.register(QueryMore.ELEMENT_NAME,
                                         QueryMore.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.QUERY_MORE,
                                         queryMoreProvider);

        mediatorCreatorsManager.register(ResetPassword.ELEMENT_NAME,
                                         ResetPassword.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.RESET_PASSWORD,
                                         resetPasswordProvider);

        mediatorCreatorsManager.register(Retrieve.ELEMENT_NAME,
                                         Retrieve.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.RETRIVE,
                                         retrieveProvider);

        mediatorCreatorsManager.register(Search.ELEMENT_NAME,
                                         Search.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.SEARCH,
                                         searchProvider);

        mediatorCreatorsManager.register(SendEmail.ELEMENT_NAME,
                                         SendEmail.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.SEND_EMAIL,
                                         sendEmailProvider);

        mediatorCreatorsManager.register(SendEmailMessage.ELEMENT_NAME,
                                         SendEmailMessage.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.SEND_EMAIL_MESSAGE,
                                         sendEmailMessageProvider);

        mediatorCreatorsManager.register(SetPassword.ELEMENT_NAME,
                                         SetPassword.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.SET_PASSWORD,
                                         setPasswordProvider);

        mediatorCreatorsManager.register(UnDelete.ELEMENT_NAME,
                                         UnDelete.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.UNDELETE,
                                         undeleteProvider);

        mediatorCreatorsManager.register(Upset.ELEMENT_NAME,
                                         Upset.SERIALIZATION_NAME,
                                         SalesForceConnectorCreatingState.UPSET,
                                         upsetProvider);

        mediatorCreatorsManager.register(AddAttachmentToIssueId.ELEMENT_NAME,
                                         AddAttachmentToIssueId.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID,
                                         addAttachmentToIssueIdProvider);

        mediatorCreatorsManager.register(CreateFilter.ELEMENT_NAME,
                                         CreateFilter.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.CREATE_FILTER,
                                         createFilterProvider);

        mediatorCreatorsManager.register(CreateIssue.ELEMENT_NAME,
                                         CreateIssue.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.CREATE_ISSUE,
                                         createIssueProvider);

        mediatorCreatorsManager.register(DeleteAvatarForProject.ELEMENT_NAME,
                                         DeleteAvatarForProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.DELETE_AVATAR_FOR_PROJECT,
                                         deleteAvatarForProjectProvider);

        mediatorCreatorsManager.register(DeleteComment.ELEMENT_NAME,
                                         DeleteComment.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.DELETE_COMMENT,
                                         deleteCommentProvider);

        mediatorCreatorsManager.register(DeleteFilter.ELEMENT_NAME,
                                         DeleteFilter.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.DELETE_FILTER,
                                         deleteFilterProvider);

        mediatorCreatorsManager.register(GetDashboard.ELEMENT_NAME,
                                         GetDashboard.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_DASHBOARD,
                                         getDashboardProvider);

        mediatorCreatorsManager.register(DoTransition.ELEMENT_NAME,
                                         DoTransition.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.DO_TRANSITION,
                                         doTransitionProvider);

        mediatorCreatorsManager.register(GetAvatarsForProject.ELEMENT_NAME,
                                         GetAvatarsForProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_AVATARS_FOR_PROJECT,
                                         getAvatarsForProjectProvider);

        mediatorCreatorsManager.register(GetComments.ELEMENT_NAME,
                                         GetComments.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_COMMENTS,
                                         getCommentsProvider);

        mediatorCreatorsManager.register(GetComponentsOfProject.ELEMENT_NAME,
                                         GetComponentsOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_COMPONENTS_OF_PROJECT,
                                         getComponentsOfProjectProvider);

        mediatorCreatorsManager.register(GetDashboardById.ELEMENT_NAME,
                                         GetDashboardById.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_DASHBOARD_BY_ID,
                                         getDashboardByIdProvider);

        mediatorCreatorsManager.register(GetFavouriteFilters.ELEMENT_NAME,
                                         GetFavouriteFilters.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_FAVOURITE_FILTERS,
                                         getFavouriteFiltersProvider);

        mediatorCreatorsManager.register(GetFilterById.ELEMENT_NAME,
                                         GetFilterById.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_FILTER_BY_ID,
                                         getFilterByIdProvider);

        mediatorCreatorsManager.register(GetGroup.ELEMENT_NAME,
                                         GetGroup.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_GROUP,
                                         getGroupProvider);

        mediatorCreatorsManager.register(GetIssue.ELEMENT_NAME,
                                         GetIssue.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUE,
                                         getIssueProvider);

        mediatorCreatorsManager.register(GetIssuePriorities.ELEMENT_NAME,
                                         GetIssuePriorities.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUE_PRIORITIES,
                                         getIssuePrioritiesProvider);

        mediatorCreatorsManager.register(GetIssuePriorityById.ELEMENT_NAME,
                                         GetIssuePriorityById.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUE_PRIORITY_BY_ID,
                                         getIssuePriorityByIdProvider);

        mediatorCreatorsManager.register(GetIssueTypeById.ELEMENT_NAME,
                                         GetIssueTypeById.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUE_TYPE_BY_ID,
                                         getIssueTypeByIdProvider);

        mediatorCreatorsManager.register(GetIssueTypes.ELEMENT_NAME,
                                         GetIssueTypes.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUE_TYPES,
                                         getIssueTypesProvider);

        mediatorCreatorsManager.register(GetIssuesForUser.ELEMENT_NAME,
                                         GetIssuesForUser.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ISSUES_FOR_USER,
                                         getIssuesForUserProvider);

        mediatorCreatorsManager.register(GetProject.ELEMENT_NAME,
                                         GetProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_PROJECT,
                                         getProjectProvider);

        mediatorCreatorsManager.register(GetRolesByIdOfProject.ELEMENT_NAME,
                                         GetRolesByIdOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ROLES_BY_ID_OF_PROJECT,
                                         getRolesByIdOfProjectProvider);

        mediatorCreatorsManager.register(GetRolesOfProject.ELEMENT_NAME,
                                         GetRolesOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_ROLES_OF_PROJECT,
                                         getRolesOfProjectProvider);

        mediatorCreatorsManager.register(GetStatusesOfProject.ELEMENT_NAME,
                                         GetStatusesOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_STATUSES_OF_PROJECT,
                                         getStatusesOfProjectProvider);

        mediatorCreatorsManager.register(GetTransitions.ELEMENT_NAME,
                                         GetTransitions.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_TRANSITIONS,
                                         getTransitionsProvider);

        mediatorCreatorsManager.register(GetUser.ELEMENT_NAME,
                                         GetUser.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_USER,
                                         getUserProvider);

        mediatorCreatorsManager.register(GetUserAssignableProjects.ELEMENT_NAME,
                                         GetUserAssignableProjects.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_USER_ASSIGNABLE_PROJECT,
                                         getUserAssignableProjectsProvider);

        mediatorCreatorsManager.register(GetUserPermissions.ELEMENT_NAME,
                                         GetUserPermissions.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_USER_PERMISSIONS,
                                         getUserPermissionsProvider);

        mediatorCreatorsManager.register(GetVersionsOfProject.ELEMENT_NAME,
                                         GetVersionsOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_VERSIONS_OF_PROJECT,
                                         getVersionsOfProjectProvider);

        mediatorCreatorsManager.register(GetVotesForIssue.ELEMENT_NAME,
                                         GetVotesForIssue.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.GET_VOTES_FOR_ISSUE,
                                         getVotesForIssueProvider);

        mediatorCreatorsManager.register(InitJira.ELEMENT_NAME,
                                         InitJira.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.INIT,
                                         initJiraProvider);

        mediatorCreatorsManager.register(PostComment.ELEMENT_NAME,
                                         PostComment.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.POST_COMMENT,
                                         postCommentProvider);

        mediatorCreatorsManager.register(SearchAssignableUser.ELEMENT_NAME,
                                         SearchAssignableUser.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER,
                                         searchAssignableUserProvider);

        mediatorCreatorsManager.register(SearchAssignableUserMultiProject.ELEMENT_NAME,
                                         SearchAssignableUserMultiProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER_MULTI_PROJECT,
                                         searchAssignableUserMultiProjectProvider);

        mediatorCreatorsManager.register(SearchIssueViewableUsers.ELEMENT_NAME,
                                         SearchIssueViewableUsers.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SEARCH_ISSUE_VIEWABLE_USERS,
                                         searchIssueViewableUsersProvider);

        mediatorCreatorsManager.register(SearchJira.ELEMENT_NAME,
                                         SearchJira.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SEARCH_JIRA,
                                         searchJiraProvider);

        mediatorCreatorsManager.register(SearchUser.ELEMENT_NAME,
                                         SearchUser.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SEARCH_USER,
                                         searchUserProvider);

        mediatorCreatorsManager.register(SetActorsToRoleOfProject.ELEMENT_NAME,
                                         SetActorsToRoleOfProject.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.SET_ACTORS_TO_ROLE_OF_PROJECT,
                                         setActorsToRoleOfProjectProvider);

        mediatorCreatorsManager.register(UpdateComment.ELEMENT_NAME,
                                         UpdateComment.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.UPDATE_COMMENT,
                                         updateCommentProvider);

        mediatorCreatorsManager.register(UpdateFilterById.ELEMENT_NAME,
                                         UpdateFilterById.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.UPDATE_FILTER_BY_ID_JIRA,
                                         updateFilterByIdProvider);

        mediatorCreatorsManager.register(UpdateIssue.ELEMENT_NAME,
                                         UpdateIssue.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.UPDATE_ISSUE,
                                         updateIssueProvider);

        mediatorCreatorsManager.register(UpdateIssueAssignee.ELEMENT_NAME,
                                         UpdateIssueAssignee.SERIALIZATION_NAME,
                                         JiraConnectorCreatingState.UPDATE_ISSUE_ASSIGNEE,
                                         updateIssueAssigneeProvider);

        mediatorCreatorsManager.register(DestroyStatus.ELEMENT_NAME,
                                         DestroyStatus.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.DESTROY_STATUS,
                                         destroyStatusProvider);

        mediatorCreatorsManager.register(GetClosesTrends.ELEMENT_NAME,
                                         GetClosesTrends.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_CLOTHES_TRENDS,
                                         getClosesTrendsProvider);

        mediatorCreatorsManager.register(GetDirectMessages.ELEMENT_NAME,
                                         GetDirectMessages.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_DIRECT_MESSAGES,
                                         getDirectMessagesProvider);

        mediatorCreatorsManager.register(GetFollowers.ELEMENT_NAME,
                                         GetFollowers.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_FOLLOWERS,
                                         getFollowersProvider);

        mediatorCreatorsManager.register(GetFollowersIds.ELEMENT_NAME,
                                         GetFollowersIds.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_FOLLOWERS_IDS,
                                         getFollowersIdsProvider);

        mediatorCreatorsManager.register(GetFriends.ELEMENT_NAME,
                                         GetFriends.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_FRIENDS,
                                         getFriendsProvider);

        mediatorCreatorsManager.register(GetFriendsIds.ELEMENT_NAME,
                                         GetFriendsIds.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_FRIENDS_IDS,
                                         getFriendsIdsProvider);

        mediatorCreatorsManager.register(GetHomeTimeLine.ELEMENT_NAME,
                                         GetHomeTimeLine.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_HOME_TIME_LINE,
                                         getHomeTimeLineProvider);

        mediatorCreatorsManager.register(GetMentionsTimeLine.ELEMENT_NAME,
                                         GetMentionsTimeLine.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_MENTIONS_TIME_LINE,
                                         getMentionsTimeLineProvider);

        mediatorCreatorsManager.register(GetRetweetsOfMine.ELEMENT_NAME,
                                         GetRetweetsOfMine.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_RETWEETS_OF_MINE,
                                         getRetweetsOfMineProvider);

        mediatorCreatorsManager.register(GetSentDirectMessages.ELEMENT_NAME,
                                         GetSentDirectMessages.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_SENT_DIRECT_MESSAGE,
                                         getSentDirectMessagesProvider);

        mediatorCreatorsManager.register(GetTopTrendPlaces.ELEMENT_NAME,
                                         GetTopTrendPlaces.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_TOP_TREND_PLACES,
                                         getTopTrendPlacesProvider);

        mediatorCreatorsManager.register(GetUserTimeLine.ELEMENT_NAME,
                                         GetUserTimeLine.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.GET_USER_TIME_LINE,
                                         getUserTimeLineProvider);

        mediatorCreatorsManager.register(InitTwitter.ELEMENT_NAME,
                                         InitTwitter.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.INIT,
                                         initTwitterProvider);

        mediatorCreatorsManager.register(Retweet.ELEMENT_NAME,
                                         Retweet.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.RETWEET,
                                         retweetProvider);

        mediatorCreatorsManager.register(SearchTwitter.ELEMENT_NAME,
                                         SearchTwitter.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.SEARCH,
                                         searchTwitterProvider);

        mediatorCreatorsManager.register(SearchPlaces.ELEMENT_NAME,
                                         SearchPlaces.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.SEARCH_PLACES,
                                         searchPlacesProvider);

        mediatorCreatorsManager.register(SendDirectMessage.ELEMENT_NAME,
                                         SendDirectMessage.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.SEND_DIRECT_MESSAGE,
                                         sendDirectMessageProvider);

        mediatorCreatorsManager.register(ShowStatus.ELEMENT_NAME,
                                         ShowStatus.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.SHOW_STATUS,
                                         showStatusProvider);

        mediatorCreatorsManager.register(UpdateStatus.ELEMENT_NAME,
                                         UpdateStatus.SERIALIZATION_NAME,
                                         TwitterConnectorCreatingState.UPDATE_STATUS,
                                         updateStatusProvider);

        mediatorCreatorsManager.register(CreateSpreadsheet.ELEMENT_NAME,
                                         CreateSpreadsheet.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.CREATING_SPREADSHEET,
                                         createSpreadsheetProvider);

        mediatorCreatorsManager.register(CreateWorksheet.ELEMENT_NAME,
                                         CreateWorksheet.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.CREATING_WORKSHEET,
                                         createWorksheetProvider);

        mediatorCreatorsManager.register(DeleteWorksheet.ELEMENT_NAME,
                                         DeleteWorksheet.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.DELETE_WORKSHEET,
                                         deleteWorksheetProvider);

        mediatorCreatorsManager.register(GetAllCells.ELEMENT_NAME,
                                         GetAllCells.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS,
                                         getAllCellsProvider);

        mediatorCreatorsManager.register(GetAllCellsCSV.ELEMENT_NAME,
                                         GetAllCellsCSV.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS_CSV,
                                         getAllCellsCSVProvider);

        mediatorCreatorsManager.register(GetAllWorksheets.ELEMENT_NAME,
                                         GetAllWorksheets.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_WORKSHEETS,
                                         getAllWorksheetsProvider);

        mediatorCreatorsManager.register(GetAllSpreadsheets.ELEMENT_NAME,
                                         GetAllSpreadsheets.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_ALL_SPREADSHEETS,
                                         getAllSpreadsheetsProvider);

        mediatorCreatorsManager.register(GetAuthors.ELEMENT_NAME,
                                         GetAuthors.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_AUTHORS,
                                         getAuthorsProvider);

        mediatorCreatorsManager.register(GetCellRange.ELEMENT_NAME,
                                         GetCellRange.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE,
                                         getCellRangeProvider);

        mediatorCreatorsManager.register(GetCellRangeCSV.ELEMENT_NAME,
                                         GetCellRangeCSV.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE_CSV,
                                         getCellRangeCSVProvider);

        mediatorCreatorsManager.register(GetColumnHeaders.ELEMENT_NAME,
                                         GetColumnHeaders.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_COLUMN_HEADERS,
                                         getColumnHeadersProvider);

        mediatorCreatorsManager.register(GetSpreadsheetsByTitle.ELEMENT_NAME,
                                         GetSpreadsheetsByTitle.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_SPREADSHEET_BY_TITLE,
                                         getSpreadsheetsByTitleProvider);

        mediatorCreatorsManager.register(GetWorksheetsByTitle.ELEMENT_NAME,
                                         GetWorksheetsByTitle.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.GET_WORKSHEET_BY_TITLE,
                                         getWorksheetsByTitleProvider);

        mediatorCreatorsManager.register(ImportCSV.ELEMENT_NAME,
                                         ImportCSV.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.IMPORT_CSV,
                                         importCSVProvider);

        mediatorCreatorsManager.register(InitSpreadsheet.ELEMENT_NAME,
                                         InitSpreadsheet.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.INIT,
                                         initSpreadsheetProvider);

        mediatorCreatorsManager.register(PurgeWorkshet.ELEMENT_NAME,
                                         PurgeWorkshet.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.PURGE_WORKSHEET,
                                         purgeWorkshetProvider);

        mediatorCreatorsManager.register(SearchCell.ELEMENT_NAME,
                                         SearchCell.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.SEARCH_CELL,
                                         searchCellProvider);

        mediatorCreatorsManager.register(SetRow.ELEMENT_NAME,
                                         SetRow.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.SET_ROW,
                                         setRowProvider);

        mediatorCreatorsManager.register(UpdateWorksheetMetadata.ELEMENT_NAME,
                                         UpdateWorksheetMetadata.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.UPDATE_WORKSHEET_METADATA,
                                         updateWorksheetMetadataProvider);

        mediatorCreatorsManager.register(UsernameLogin.ELEMENT_NAME,
                                         UsernameLogin.SERIALIZATION_NAME,
                                         GoogleSpreadsheedConnectorCreatingState.USERNAME_LOGIN,
                                         usernameLoginProvider);
    }

    @Inject
    private void configureToolbar(ToolbarPresenter toolbar,
                                  EditorResources resources,
                                  WSO2EditorLocalizationConstant localizationConstant) {

        toolbar.addGroup(ToolbarGroupIds.MEDIATORS, localizationConstant.toolbarGroupMediators());

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarCallTitle(),
                        localizationConstant.toolbarCallTooltip(),
                        resources.callToolbar(),
                        MediatorCreatingState.CALL_MEDIATOR);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarCallTemplateTitle(),
                        localizationConstant.toolbarCallTemplateTooltip(),
                        resources.callTemplateToolbar(),
                        MediatorCreatingState.CALLTEMPLATE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarLogTitle(),
                        localizationConstant.toolbarLogTooltip(),
                        resources.logToolbar(),
                        MediatorCreatingState.LOG);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarLoopBackTitle(),
                        localizationConstant.toolbarLoopBackTooltip(),
                        resources.loopBackToolbar(),
                        MediatorCreatingState.LOOPBACK);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarPropertyTitle(),
                        localizationConstant.toolbarPropertyTooltip(),
                        resources.propertyToolbar(),
                        MediatorCreatingState.PROPERTY);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarRespondTitle(),
                        localizationConstant.toolbarRespondTooltip(),
                        resources.respondToolbar(),
                        MediatorCreatingState.RESPOND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSendTitle(),
                        localizationConstant.toolbarSendTooltip(),
                        resources.sendToolbar(),
                        MediatorCreatingState.SEND);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSequenceTitle(),
                        localizationConstant.toolbarSequenceTooltip(),
                        resources.sequenceToolbar(),
                        MediatorCreatingState.SEQUENCE);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarFilterTitle(),
                        localizationConstant.toolbarFilterTooltip(),
                        resources.filterToolbar(),
                        MediatorCreatingState.FILTER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarSwitchTitle(),
                        localizationConstant.toolbarSwitchTooltip(),
                        resources.switchToolbar(),
                        MediatorCreatingState.SWITCH);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarHeaderTitle(),
                        localizationConstant.toolbarHeaderTooltip(),
                        resources.headerToolbar(),
                        MediatorCreatingState.HEADER);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarPayloadFactoryTitle(),
                        localizationConstant.toolbarPayloadFactoryTooltip(),
                        resources.payloadFactoryToolbar(),
                        MediatorCreatingState.PAYLOAD);

        toolbar.addItem(ToolbarGroupIds.MEDIATORS,
                        localizationConstant.toolbarEnrichTitle(),
                        localizationConstant.toolbarEnrichTooltip(),
                        resources.enrichToolbar(),
                        MediatorCreatingState.ENRICH);

        toolbar.addGroup(ToolbarGroupIds.ENDPOINTS, localizationConstant.toolbarGroupEndpoints());

        toolbar.addItem(ToolbarGroupIds.ENDPOINTS,
                        localizationConstant.toolbarAddressEndpointTitle(),
                        localizationConstant.toolbarAddressEndpointTooltip(),
                        resources.addressToolbar(),
                        EndpointCreatingState.ADDRESS);

        toolbar.addGroup(ToolbarGroupIds.SALESFORCE_CONNECTORS, localizationConstant.toolbarGroupSalesforceConnector());

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceCreateConnectorTitle(),
                        localizationConstant.toolbarSalesforceCreateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.CREATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesForceDeleteTitle(),
                        localizationConstant.toolbarSalesForceDeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeGlobalConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeGlobalConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_GLOBAL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeSubjectConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeSubjectConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceDescribeSubjectsConnectorTitle(),
                        localizationConstant.toolbarSalesforceDescribeSubjectsConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.DESCRIBE_SUBJECTS);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceEmptyRecycleBinConnectorTitle(),
                        localizationConstant.toolbarSalesforceEmptyRecycleBinConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.EMPTY_RECYCLE_BIN);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceGetUserInfoConnectorTitle(),
                        localizationConstant.toolbarSalesforceGetUserInfoConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.GET_USER_INFORMATION);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceInitConnectorTitle(),
                        localizationConstant.toolbarSalesforceInitConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceLogOutConnectorTitle(),
                        localizationConstant.toolbarSalesforceLogOutConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.LOGOUT);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryTitle(),
                        localizationConstant.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryTitle(),
                        localizationConstant.toolbarSalesforceQueryTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryAllTitle(),
                        localizationConstant.toolbarSalesforceQueryAllTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_ALL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceQueryMoreTitle(),
                        localizationConstant.toolbarSalesforceQueryMoreTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.QUERY_MORE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceResetPasswordTitle(),
                        localizationConstant.toolbarSalesforceResetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RESET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceRetriveTitle(),
                        localizationConstant.toolbarSalesforceRetriveTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.RETRIVE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSearchTitle(),
                        localizationConstant.toolbarSalesforceSearchTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSendEmailTitle(),
                        localizationConstant.toolbarSalesforceSendEmailTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSendEmailMessageTitle(),
                        localizationConstant.toolbarSalesforceSendEmailMessageTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SEND_EMAIL_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceSetPasswordTitle(),
                        localizationConstant.toolbarSalesforceSetPasswordTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.SET_PASSWORD);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUndeleteTitle(),
                        localizationConstant.toolbarSalesforceUndeleteTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UNDELETE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUpdateConnectorTitle(),
                        localizationConstant.toolbarSalesforceUpdateConnectorTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPDATE);

        toolbar.addItem(ToolbarGroupIds.SALESFORCE_CONNECTORS,
                        localizationConstant.toolbarSalesforceUpsetTitle(),
                        localizationConstant.toolbarSalesforceUpsetTooltip(),
                        resources.salesforceConnectorToolbar(),
                        SalesForceConnectorCreatingState.UPSET);

        toolbar.addGroup(ToolbarGroupIds.JIRA_CONNECTORS, localizationConstant.toolbarGroupJiraConnector());

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraAddAttachmentToIssueIdTitle(),
                        localizationConstant.toolbarJiraAddAttachmentToIssueIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraCreateFilterTitle(),
                        localizationConstant.toolbarJiraCreateFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraCreateIssueTitle(),
                        localizationConstant.toolbarJiraCreateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.CREATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteAvatarForProjectTitle(),
                        localizationConstant.toolbarJiraDeleteAvatarForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_AVATAR_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteCommentTitle(),
                        localizationConstant.toolbarJiraDeleteCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDeleteFilterTitle(),
                        localizationConstant.toolbarJiraDeleteFilterTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DELETE_FILTER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetDashboardTitle(),
                        localizationConstant.toolbarJiraGetDashboardTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraDoTransitionTitle(),
                        localizationConstant.toolbarJiraDoTransitionTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.DO_TRANSITION);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetAvatarsForProjectTitle(),
                        localizationConstant.toolbarJiraGetAvatarsForProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_AVATARS_FOR_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetCommentsTitle(),
                        localizationConstant.toolbarJiraGetCommentsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMMENTS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetComponentsOfProjectTitle(),
                        localizationConstant.toolbarJiraGetComponentsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_COMPONENTS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetDashboardByIdTitle(),
                        localizationConstant.toolbarJiraGetDashboardByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_DASHBOARD_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetFavouriteFiltersTitle(),
                        localizationConstant.toolbarJiraGetFavouriteFiltersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FAVOURITE_FILTERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetFilterByIdTitle(),
                        localizationConstant.toolbarJiraGetFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_FILTER_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetGroupTitle(),
                        localizationConstant.toolbarJiraGetGroupTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_GROUP);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTitle(),
                        localizationConstant.toolbarJiraGetIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuePrioritiesTitle(),
                        localizationConstant.toolbarJiraGetIssuePrioritiesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITIES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuePriorityByIdTitle(),
                        localizationConstant.toolbarJiraGetIssuePriorityByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_PRIORITY_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTypeByIdTitle(),
                        localizationConstant.toolbarJiraGetIssueTypeByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPE_BY_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssueTypesTitle(),
                        localizationConstant.toolbarJiraGetIssueTypesTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUE_TYPES);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetIssuesForUserTitle(),
                        localizationConstant.toolbarJiraGetIssuesForUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ISSUES_FOR_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetProjectTitle(),
                        localizationConstant.toolbarJiraGetProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetRolesByIdOfProjectTitle(),
                        localizationConstant.toolbarJiraGetRolesByIdOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_BY_ID_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetRolesOfProjectTitle(),
                        localizationConstant.toolbarJiraGetRolesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_ROLES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetStatusesOfProjectTitle(),
                        localizationConstant.toolbarJiraGetStatusesOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_STATUSES_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetTransitionsTitle(),
                        localizationConstant.toolbarJiraGetTransitionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_TRANSITIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserTitle(),
                        localizationConstant.toolbarJiraGetUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserAssignableProjectsTitle(),
                        localizationConstant.toolbarJiraGetUserAssignableProjectsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_ASSIGNABLE_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetUserPermissionsTitle(),
                        localizationConstant.toolbarJiraGetUserPermissionsTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_USER_PERMISSIONS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetVersionsOfProjectTitle(),
                        localizationConstant.toolbarJiraGetVersionsOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VERSIONS_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraGetVotesForIssueTitle(),
                        localizationConstant.toolbarJiraGetVotesForIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.GET_VOTES_FOR_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraInitTitle(),
                        localizationConstant.toolbarJiraInitTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraPostCommentTitle(),
                        localizationConstant.toolbarJiraPostCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.POST_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchAssignableUserTitle(),
                        localizationConstant.toolbarJiraSearchAssignableUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchAssignableUserMultiProjectTitle(),
                        localizationConstant.toolbarJiraSearchAssignableUserMultiProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ASSIGNABLE_USER_MULTI_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchIssueViewableUsersTitle(),
                        localizationConstant.toolbarJiraSearchIssueViewableUsersTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_ISSUE_VIEWABLE_USERS);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchJiraTitle(),
                        localizationConstant.toolbarJiraSearchJiraTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSearchUserTitle(),
                        localizationConstant.toolbarJiraSearchUserTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SEARCH_USER);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraSetActorsToRoleOfProjectTitle(),
                        localizationConstant.toolbarJiraSetActorsToRoleOfProjectTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.SET_ACTORS_TO_ROLE_OF_PROJECT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateCommentTitle(),
                        localizationConstant.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.ADD_ATTACHMENT_TO_ISSUE_ID);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateCommentTitle(),
                        localizationConstant.toolbarJiraUpdateCommentTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_COMMENT);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateFilterByIdTitle(),
                        localizationConstant.toolbarJiraUpdateFilterByIdTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_FILTER_BY_ID_JIRA);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateIssueTitle(),
                        localizationConstant.toolbarJiraUpdateIssueTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE);

        toolbar.addItem(ToolbarGroupIds.JIRA_CONNECTORS,
                        localizationConstant.toolbarJiraUpdateIssueAssigneeTitle(),
                        localizationConstant.toolbarJiraUpdateIssueAssigneeTooltip(),
                        resources.jiraConnectorToolbar(),
                        JiraConnectorCreatingState.UPDATE_ISSUE_ASSIGNEE);

        toolbar.addGroup(ToolbarGroupIds.TWITTER_CONNECTORS, localizationConstant.toolbarGroupTwitterConnector());

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterDestroyStatusTitle(),
                        localizationConstant.toolbarTwitterDestroyStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.DESTROY_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetClosesTrendsTitle(),
                        localizationConstant.toolbarTwitterGetClosesTrendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_CLOTHES_TRENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetDirectMessagesTitle(),
                        localizationConstant.toolbarTwitterGetDirectMessagesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_DIRECT_MESSAGES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFollowersTitle(),
                        localizationConstant.toolbarTwitterGetFollowersTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFollowersIdsTitle(),
                        localizationConstant.toolbarTwitterGetFollowersIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FOLLOWERS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFriendsTitle(),
                        localizationConstant.toolbarTwitterGetFriendsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetFriendsIdsTitle(),
                        localizationConstant.toolbarTwitterGetFriendsIdsTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_FRIENDS_IDS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetHomeTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetHomeTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_HOME_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetMentionsTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetMentionsTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_MENTIONS_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetRetweetsOfMineTitle(),
                        localizationConstant.toolbarTwitterGetRetweetsOfMineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_RETWEETS_OF_MINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetSentDirectMessageTitle(),
                        localizationConstant.toolbarTwitterGetSentDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_SENT_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetTopTrendPlacesTitle(),
                        localizationConstant.toolbarTwitterGetTopTrendPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_TOP_TREND_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterGetUserTimeLineTitle(),
                        localizationConstant.toolbarTwitterGetUserTimeLineTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.GET_USER_TIME_LINE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterInitTitle(),
                        localizationConstant.toolbarTwitterInitTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterRetweetTitle(),
                        localizationConstant.toolbarTwitterRetweetTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.RETWEET);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSearchTitle(),
                        localizationConstant.toolbarTwitterSearchTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSearchPlacesTitle(),
                        localizationConstant.toolbarTwitterSearchPlacesTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEARCH_PLACES);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterSendDirectMessageTitle(),
                        localizationConstant.toolbarTwitterSendDirectMessageTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SEND_DIRECT_MESSAGE);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterShowStatusTitle(),
                        localizationConstant.toolbarTwitterShowStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.SHOW_STATUS);

        toolbar.addItem(ToolbarGroupIds.TWITTER_CONNECTORS,
                        localizationConstant.toolbarTwitterUpdateStatusTitle(),
                        localizationConstant.toolbarTwitterUpdateStatusTooltip(),
                        resources.twitterToolbar(),
                        TwitterConnectorCreatingState.UPDATE_STATUS);

        toolbar.addGroup(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS, localizationConstant.toolbarGroupGoogleSpreadsheetConnector());

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetCreateSpreadsheetTitle(),
                        localizationConstant.toolbarSpreadsheetCreateSpreadsheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_SPREADSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetCreateWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetCreateWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.CREATING_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetDeleteWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetDeleteWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.DELETE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllCellsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllCellsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllCellsCSVTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllCellsCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_CELLS_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllSpreadsheetsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllSpreadsheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_SPREADSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAllWorksheetsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAllWorksheetsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_ALL_WORKSHEETS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetAuthorsTitle(),
                        localizationConstant.toolbarSpreadsheetGetAuthorsTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_AUTHORS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetCellRangeTitle(),
                        localizationConstant.toolbarSpreadsheetGetCellRangeTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetCellRangeCSVTitle(),
                        localizationConstant.toolbarSpreadsheetGetCellRangeCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_CELL_RANGE_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetColumnHeadersTitle(),
                        localizationConstant.toolbarSpreadsheetGetColumnHeadersTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_COLUMN_HEADERS);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetSpreadsheetByTitleTitle(),
                        localizationConstant.toolbarSpreadsheetGetSpreadsheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_SPREADSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetGetWorksheetByTitleTitle(),
                        localizationConstant.toolbarSpreadsheetGetWorksheetByTitleTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.GET_WORKSHEET_BY_TITLE);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetImportCSVTitle(),
                        localizationConstant.toolbarSpreadsheetImportCSVTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.IMPORT_CSV);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetInitTitle(),
                        localizationConstant.toolbarSpreadsheetInitTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.INIT);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetPurgeWorksheetTitle(),
                        localizationConstant.toolbarSpreadsheetPurgeWorksheetTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.PURGE_WORKSHEET);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarspreadsheetSearchCellTitle(),
                        localizationConstant.toolbarSpreadsheetSearchCellTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SEARCH_CELL);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetSetRowTitle(),
                        localizationConstant.toolbarSpreadsheetSetRowTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.SET_ROW);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetUpdateWorksheetMetaTitle(),
                        localizationConstant.toolbarSpreadsheetUpdateWorksheetMetaTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.UPDATE_WORKSHEET_METADATA);

        toolbar.addItem(ToolbarGroupIds.GOOGLE_SPREADSHEET_CONNECTORS,
                        localizationConstant.toolbarSpreadsheetUsernameLoginTitle(),
                        localizationConstant.toolbarSpreadsheetUsernameLoginTooltip(),
                        resources.googleSpreadsheetToolbar(),
                        GoogleSpreadsheedConnectorCreatingState.USERNAME_LOGIN);
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        toolbar.go(view.getToolbarPanel());
        rootElementPresenter.go(view.getWorkspacePanel());

        super.go(container);
    }

    /** {@inheritDoc} */
    @Override
    public void onElementChanged() {
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onPropertyChanged() {
        notifyListeners();
    }

    public void addListener(@Nonnull EditorChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        for (EditorChangeListener listener : listeners) {
            listener.onChanged();
        }
    }

    /** @return a serialized text type of diagram */
    @Nonnull
    public String serialize() {
        return rootElement.serialize();
    }

    /**
     * Convert a text type of diagram to a graphical type.
     *
     * @param content
     *         content that need to be parsed
     */
    public void deserialize(@Nonnull String content) {
        rootElement.deserialize(content);
        rootElementPresenter.updateView();
    }

    /** {@inheritDoc} */
    @Override
    public void onHidePanelButtonClicked() {
        view.setVisiblePropertyPanel(false);
    }

    /** {@inheritDoc} */
    @Override
    public void onShowPropertyButtonClicked() {
        view.setVisiblePropertyPanel(true);
    }

    public interface EditorChangeListener {
        /** Performs some actions when editor was changed. */
        void onChanged();
    }
}
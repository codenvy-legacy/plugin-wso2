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
package com.codenvy.ide.client;

import com.google.gwt.i18n.client.Messages;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Contains all names of graphical elements needed for WSO2 editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
@Singleton
public interface WSO2EditorLocalizationConstant extends Messages {

    @Key("toolbar.log.title")
    String toolbarLogTitle();

    @Key("toolbar.call.title")
    String toolbarCallTitle();

    @Key("toolbar.property.title")
    String toolbarPropertyTitle();

    @Key("toolbar.payloadFactory.title")
    String toolbarPayloadFactoryTitle();

    @Key("toolbar.send.title")
    String toolbarSendTitle();

    @Key("toolbar.header.title")
    String toolbarHeaderTitle();

    @Key("toolbar.respond.title")
    String toolbarRespondTitle();

    @Key("toolbar.filter.title")
    String toolbarFilterTitle();

    @Key("toolbar.switch.title")
    String toolbarSwitchTitle();

    @Key("toolbar.sequence.title")
    String toolbarSequenceTitle();

    @Key("toolbar.enrich.title")
    String toolbarEnrichTitle();

    @Key("toolbar.loopBack.title")
    String toolbarLoopBackTitle();

    @Key("toolbar.callTemplate.title")
    String toolbarCallTemplateTitle();

    @Key("toolbar.addressEndpoint.title")
    String toolbarAddressEndpointTitle();

    @Key("toolbar.log.tooltip")
    String toolbarLogTooltip();

    @Key("toolbar.call.tooltip")
    String toolbarCallTooltip();

    @Key("toolbar.property.tooltip")
    String toolbarPropertyTooltip();

    @Key("toolbar.payloadFactory.tooltip")
    String toolbarPayloadFactoryTooltip();

    @Key("toolbar.send.tooltip")
    String toolbarSendTooltip();

    @Key("toolbar.header.tooltip")
    String toolbarHeaderTooltip();

    @Key("toolbar.respond.tooltip")
    String toolbarRespondTooltip();

    @Key("toolbar.filter.tooltip")
    String toolbarFilterTooltip();

    @Key("toolbar.switch.tooltip")
    String toolbarSwitchTooltip();

    @Key("toolbar.sequence.tooltip")
    String toolbarSequenceTooltip();

    @Key("toolbar.enrich.tooltip")
    String toolbarEnrichTooltip();

    @Key("toolbar.loopBack.tooltip")
    String toolbarLoopBackTooltip();

    @Key("toolbar.callTemplate.tooltip")
    String toolbarCallTemplateTooltip();

    @Key("toolbar.addressEndpoint.tooltip")
    String toolbarAddressEndpointTooltip();

    @Key("propertiespanel.argumentsconfig.table.Evaluator")
    String columnEvaluator();

    @Key("propertiespanel.argumentsconfig.table.name")
    String columnName();

    @Key("propertiespanel.argumentsconfig.table.value")
    String columnValue();

    @Key("propertiespanel.argumentsconfig.table.type")
    String columnType();

    @Key("propertiespanel.argumentsconfig.table.scope")
    String columnScope();

    @Key("propertiespanel.namespace.property")
    String namespacedProperty();

    @Key("propertiespanel.namespace.namespaces")
    String namespaces();

    @Key("propertiespanel.namespace.prefix")
    String namespacePrefix();

    @Key("propertiespanel.namespace.uri")
    String namespaceUri();

    @Key("propertiespanel.namespace.defaultexpression")
    String defaultExpression();

    @Key("propertiespanel.namespace.property.expression")
    String propertyExpression();

    @Key("propertiespanel.configwindow.button.add")
    String buttonAdd();

    @Key("propertiespanel.configwindow.button.edit")
    String buttonEdit();

    @Key("propertiespanel.configwindow.button.remove")
    String buttonRemove();

    @Key("propertiespanel.configwindow.button.selectxpath")
    String buttonSelectxpath();

    @Key("propertiespanel.configwindow.button.editConfig")
    String buttonEditConfig();

    @Key("propertiespanel.registerkey.key.from.specify")
    String keyFromSpecify();

    @Key("propertiespanel.registerkey.new.resource")
    String newResource();

    @Key("propertiespanel.resource.key.editor")
    String resourceKeyEditor();

    @Key("propertiespanel.resource.key")
    String resourceKey();

    @Key("propertiespanel.resource.key.editor.title")
    String resourceKeyEditorTitle();

    @Key("button.cancel")
    String buttonCancel();

    @Key("button.ok")
    String buttonOk();

    @Key("propertiespanel.log.logcategory")
    String logCategory();

    @Key("propertiespanel.log.loglevel")
    String logLevel();

    @Key("propertiespanel.log.logseparator")
    String logSeparator();

    @Key("propertiespanel.log.properties")
    String properties();

    @Key("propertiespanel.log.name")
    String logName();

    @Key("propertiespanel.log.valueexpression")
    String valueExpression();

    @Key("propertiespanel.log.configuration.title")
    String configurationTitle();

    @Key("propertiespanel.header.headername")
    String labelHeader();

    @Key("propertiespanel.header.headerexpression.label")
    String labelExpression();

    @Key("propertiespanel.header.headerAction")
    String headerAction();

    @Key("propertiespanel.header.headerScope")
    String headerScope();

    @Key("propertiespanel.header.valueType")
    String headerValueType();

    @Key("propertiespanel.header.valueLiteral")
    String hHeaderValueLiteral();

    @Key("propertiespanel.header.valueExpression")
    String headerValueExpression();

    @Key("propertiespanel.header.valueInline")
    String headerValueInline();

    @Key("propertiespanel.header.headerName")
    String headerName();

    @Key("propertiespanel.header.inlinewindow.title")
    String inlineTitle();

    @Key("propertiespanel.sequence.referringType")
    String referringType();

    @Key("propertiespanel.sequence.dynamicReferenceKey")
    String dynamicReferenceKey();

    @Key("propertiespanel.sequence.staticReferenceKey")
    String staticReferenceKey();

    @Key("propertiespanel.sequence.expression.title")
    String sequenceExpressionTitle();

    @Key("propertiespanel.call.template.configuration.title")
    String callTemplateConfigTitle();

    @Key("propertiespanel.call.endpointType")
    String endpointType();

    @Key("propertiespanel.call.endpointRegistryKey")
    String endpointRegistryKey();

    @Key("propertiespanel.call.endpointXpath")
    String endpointXpath();

    @Key("propertiespanel.call.description")
    String description();

    @Key("propertiespanel.call.expression.title")
    String callExpressionTitle();

    @Key("propertiespanel.filter.conditionType")
    String conditionType();

    @Key("propertiespanel.filter.source")
    String filterSource();

    @Key("propertiespanel.filter.regularExpression")
    String regularExpression();

    @Key("propertiespanel.filter.xpath")
    String filterXpath();

    @Key("propertiespanel.filter.xpath.title")
    String filterXpathTitle();

    @Key("propertiespanel.filter.source.title")
    String filterSourceTitle();

    @Key("propertiespanel.enrich.source.misc")
    String enrichSourceMisc();

    @Key("propertiespanel.enrich.source")
    String enrichSource();

    @Key("propertiespanel.enrich.source.description")
    String enrichDescription();

    @Key("propertiespanel.enrich.source.cloneSource")
    String enrichCloneSource();

    @Key("propertiespanel.enrich.source.sourceType")
    String enrichSourceType();

    @Key("propertiespanel.enrich.source.sourceXPath")
    String enrichSourceXPath();

    @Key("propertiespanel.enrich.source.sourceProperty")
    String enrichSourceProperty();

    @Key("propertiespanel.enrich.source.inlineType")
    String enrichSourceInlineType();

    @Key("propertiespanel.enrich.source.sourceXml")
    String enrichSourceXml();

    @Key("propertiespanel.enrich.source.inlineRegistryKey")
    String enrichInlineRegistryKey();

    @Key("propertiespanel.enrich.target")
    String enrichTarget();

    @Key("propertiespanel.enrich.target.targetAction")
    String enrichTargetAction();

    @Key("propertiespanel.enrich.target.targetType")
    String enrichTargetType();

    @Key("propertiespanel.enrich.target.targetXPath")
    String enrichTargetXPath();

    @Key("propertiespanel.enrich.target.targetProperty")
    String enrichTargetProperty();

    @Key("propertiespanel.enrich.source.namespace.label")
    String enrichSrcLabel();

    @Key("propertiespanel.enrich.source.inline.titleEnrichInline")
    String titleEnrichInline();

    @Key("propertiespanel.property.editor.title")
    String editorTitle();

    @Key("propertiespanel.payload.args.configuration.title")
    String argsConfigurationTitle();

    @Key("propertiespanel.payload.args.label")
    String argsLabel();

    @Key("propertiespanel.payload.payloadFormat")
    String payloadFormat();

    @Key("propertiespanel.payload.formatKey")
    String payloadFormatKey();

    @Key("propertiespanel.payload.args")
    String payloadArgs();

    @Key("propertiespanel.payload.mediaType")
    String payloadMediaType();

    @Key("propertiespanel.payload.description")
    String payloadDescription();

    @Key("propertiespanel.payload.factoryArguments")
    String payloadFactoryArguments();

    @Key("propertiespanel.payload.format")
    String format();

    @Key("propertiespanel.property.propertyName")
    String propertyName();

    @Key("propertiespanel.property.propertyAction")
    String propertyAction();

    @Key("propertiespanel.property.propertyValueType")
    String propertyValueType();

    @Key("propertiespanel.property.propertyDataType")
    String propertyDataType();

    @Key("propertiespanel.property.valueLiteral")
    String propertyValueLiteral();

    @Key("propertiespanel.property.valueStringPattern")
    String valueStringPattern();

    @Key("propertiespanel.property.capturingGroup")
    String capturingGroup();

    @Key("propertiespanel.property.propertyScope")
    String propertyScope();

    @Key("propertiespanel.property.valueExpression")
    String propertyValueExpression();

    @Key("propertiespanel.send.namespace.label")
    String sendNamespaceLabel();

    @Key("propertiespanel.send.skipSerialization")
    String skipSerialization();

    @Key("propertiespanel.send.receivingSequenceType")
    String receivingSequenceType();

    @Key("propertiespanel.send.buildMessageBeforeSending")
    String buildMessageBeforeSending();

    @Key("propertiespanel.send.dynamicReceivingSequence")
    String dynamicReceivingSequence();

    @Key("propertiespanel.send.staticReceivingSequence")
    String staticReceivingSequence();

    @Key("propertiespanel.calltemplate.availableTemplate")
    String availableTemplate();

    @Key("propertiespanel.calltemplate.targetTemplate")
    String targetTemplate();

    @Key("propertiespanel.calltemplate.parameters")
    String calltemplateParameters();

    @Key("propertiespanel.calltemplate.label.parameters")
    String calltemplateLabelParameters();

    @Key("propertiespanel.switch.sourceXPath")
    String switchSourceXPath();

    @Key("propertiespanel.switch.xpath.title")
    String switchXpathTitle();

    @Key("propertiespanel.argumentsconfig.table.Expression")
    String columnExpression();

    @Key("propertiespanel.argumentsconfig.table.arguments")
    String arguments();

    @Key("propertiespanel.root.name")
    String rootName();

    @Key("propertiespanel.root.onError")
    String rootOnError();

    @Key("propertiespanel.panel.labelName")
    String panelLabelName();

    @Key("propertiespanel.addressEndpoint.basic")
    String addressEndpointBasic();

    @Key("propertiespanel.addressEndpoint.format")
    String addressEndpointFormat();

    @Key("propertiespanel.addressEndpoint.uri")
    String addressEndpointUri();

    @Key("propertiespanel.addressEndpoint.suspendState")
    String addressEndpointSuspendState();

    @Key("propertiespanel.addressEndpoint.suspendErrorCodes")
    String addressEndpointSuspendErrorCodes();

    @Key("propertiespanel.addressEndpoint.suspendInitialDuration")
    String addressEndpointSuspendInitialDuration();

    @Key("propertiespanel.addressEndpoint.suspendMaximumDuration")
    String addressEndpointSuspendMaximumDuration();

    @Key("propertiespanel.addressEndpoint.suspendProgressionFactory")
    String addressEndpointSuspendProgressionFactory();

    @Key("propertiespanel.addressEndpoint.endpointTimeoutState")
    String addressEndpointEndpointTimeoutState();

    @Key("propertiespanel.addressEndpoint.retryErrorCodes")
    String addressEndpointRetryErrorCodes();

    @Key("propertiespanel.addressEndpoint.retryCount")
    String addressEndpointRetryCount();

    @Key("propertiespanel.addressEndpoint.retryDelay")
    String addressEndpointRetryDelay();

    @Key("propertiespanel.addressEndpoint.misc")
    String addressEndpointMisc();

    @Key("propertiespanel.addressEndpoint.properties")
    String addressEndpointProperties();

    @Key("propertiespanel.addressEndpoint.optimize")
    String addressEndpointOptimize();

    @Key("propertiespanel.addressEndpoint.description")
    String addressEndpointDescription();

    @Key("propertiespanel.addressEndpoint.endpointProperty")
    String addressEndpointEndpointProperty(@Nonnull String propertyName);

    @Key("propertiespanel.addressEndpoint.qos")
    String addressEndpointQos();

    @Key("propertiespanel.addressEndpoint.reliableMessagingEnabled")
    String addressEndpointReliableMessagingEnabled();

    @Key("propertiespanel.addressEndpoint.reliableMessagingPolicy")
    String addressEndpointReliableMessagingPolicy();

    @Key("propertiespanel.addressEndpoint.securityEnabled")
    String addressEndpointSecurityEnabled();

    @Key("propertiespanel.addressEndpoint.securityPolicy")
    String addressEndpointSecurityPolicy();

    @Key("propertiespanel.addressEndpoint.addressingEnabled")
    String addressEndpointAddressingEnabled();

    @Key("propertiespanel.addressEndpoint.addressingVersion")
    String addressEndpointAddressingVersion();

    @Key("propertiespanel.addressEndpoint.addressingSeparateListener")
    String addressEndpointAddressingSeparateListener();

    @Key("propertiespanel.addressEndpoint.timeout")
    String addressEndpointTimeout();

    @Key("propertiespanel.addressEndpoint.timeoutDuration")
    String addressEndpointTimeoutDuration();

    @Key("propertiespanel.addressEndpoint.timeoutAction")
    String addressEndpointTimeoutAction();

    @Key("propertiespanel.addressEndpoint.table.addresspropertyedit.name")
    String addressPropertyEditName();

    @Key("propertiespanel.addressEndpoint.table.addresspropertyedit.value")
    String addressPropertyEditValue();

    @Key("propertiespanel.addressEndpoint.table.addresspropertyedit.type")
    String addressPropertyEditType();

    @Key("propertiespanel.addressEndpoint.table.addresspropertyedit.scope")
    String addressPropertyEditScope();

    @Key("propertiespanel.endpoint.properties.title")
    String endpointPropertiesTitle();

    @Key("propertiespanel.connector.salesForce.configRef")
    String connectorConfigRef();

    @Key("propertiespanel.connector.salesForce.availableConfigs")
    String connectorAvailableConfigs();

    @Key("propertiespanel.connector.salesForce.newConfig")
    String connectorNewConfig();

    @Key("propertiespanel.connector.salesForce.parameterEditorType")
    String connectorParameterEditorType();

    @Key("propertiespanel.connector.salesForce.username")
    String connectorUsername();

    @Key("propertiespanel.connector.salesForce.password")
    String connectorPassword();

    @Key("propertiespanel.connector.salesForce.loginUrl")
    String connectorLoginUrl();

    @Key("propertiespanel.connector.salesForce.createNewConfiguration")
    String connectorNewConfigValue();

    @Key("propertiespanel.connector.salesForce.forceLogin")
    String connectorForceLogin();

    @Key("propertiespanel.addressEndpoint.table.title")
    String editPropAdrrEndTableTitle();

    @Key("propertiespanel.addressEndpoint.table.error")
    String endpointTableError();

    @Key("propertiespanel.error.message")
    String errorMessage();

    @Key("propertiespanel.connector.salesForce.parameterExpression")
    String connectorExpression();

    @Key("propertiespanel.connector.salesForce.connectorConfiguration")
    String propertiespanelConnectorConfigurationTitle();

    @Key("propertiespanel.connector.salesForce.allOrNone")
    String connectorAllOrNone();

    @Key("propertiespanel.connector.salesForce.allowFieldTruncate")
    String connectorAllowFieldTruncate();

    @Key("propertiespanel.connector.salesForce.subjects")
    String connectorSubjects();

    @Key("propertiespanel.connector.salesForce.subject")
    String propertiespanelConnectorSubject();

    @Key("propertiespanel.connector.salesForce.externalid")
    String connectorExternalId();

    @Key("propertiespanel.connector.salesForce.sendEmailMessage")
    String connectorSendEmailMessage();

    @Key("propertiespanel.connector.salesForce.sendEmail")
    String connectorSendEmail();

    @Key("propertiespanel.connector.salesForce.searchString")
    String connectroSearchString();

    @Key("propertiespanel.connector.salesForce.fieldList")
    String connectorFieldList();

    @Key("propertiespanel.connector.salesForce.objectType")
    String connectorObjectType();

    @Key("propertiespanel.connector.salesForce.objectIDS")
    String connectorObjectIDS();

    @Key("toolbar.connector.salesforceInitConnector.title")
    String toolbarSalesforceInitConnectorTitle();

    @Key("toolbar.connector.salesforceCreateConnector.title")
    String toolbarSalesforceCreateConnectorTitle();

    @Key("toolbar.connector.salesforceDelete.title")
    String toolbarSalesForceDeleteTitle();

    @Key("toolbar.connector.salesforceDescribeGlobalConnector.title")
    String toolbarSalesforceDescribeGlobalConnectorTitle();

    @Key("toolbar.connector.salesforceDescribeSobjectConnector.title")
    String toolbarSalesforceDescribeSubjectConnectorTitle();

    @Key("toolbar.connector.salesforceUpdateConnector.title")
    String toolbarSalesforceUpdateConnectorTitle();

    @Key("toolbar.connector.salesforceDescribeSobjectsConnector.title")
    String toolbarSalesforceDescribeSubjectsConnectorTitle();

    @Key("toolbar.connector.salesforceEmptyRecycleBin.title")
    String toolbarSalesforceEmptyRecycleBinConnectorTitle();

    @Key("toolbar.connector.salesforceLogOut.title")
    String toolbarSalesforceLogOutConnectorTitle();

    @Key("toolbar.connector.salesforceGetUserInfo.title")
    String toolbarSalesforceGetUserInfoConnectorTitle();

    @Key("toolbar.connector.salesforceQuery.title")
    String toolbarSalesforceQueryTitle();

    @Key("toolbar.connector.salesforceQueryAll.title")
    String toolbarSalesforceQueryAllTitle();

    @Key("toolbar.connector.salesforceQueryMore.title")
    String toolbarSalesforceQueryMoreTitle();

    @Key("toolbar.connector.salesforceResetPassword.title")
    String toolbarSalesforceResetPasswordTitle();

    @Key("toolbar.connector.salesforceRetrive.title")
    String toolbarSalesforceRetriveTitle();

    @Key("toolbar.connector.salesforceSearch.title")
    String toolbarSalesforceSearchTitle();

    @Key("toolbar.connector.salesforceSendEmail.title")
    String toolbarSalesforceSendEmailTitle();

    @Key("toolbar.connector.salesforceSendEmailMessage.title")
    String toolbarSalesforceSendEmailMessageTitle();

    @Key("toolbar.connector.salesforceSetPassword.title")
    String toolbarSalesforceSetPasswordTitle();

    @Key("toolbar.connector.salesforceUndelete.title")
    String toolbarSalesforceUndeleteTitle();

    @Key("toolbar.connector.salesforceUpset.title")
    String toolbarSalesforceUpsetTitle();

    @Key("toolbar.connector.salesforceInitConnector.tooltip")
    String toolbarSalesforceInitConnectorTooltip();

    @Key("toolbar.connector.salesforceCreateConnector.tooltip")
    String toolbarSalesforceCreateConnectorTooltip();

    @Key("toolbar.connector.salesforceDelete.tooltip")
    String toolbarSalesForceDeleteTooltip();

    @Key("toolbar.connector.salesforceDescribeGlobalConnector.tooltip")
    String toolbarSalesforceDescribeGlobalConnectorTooltip();

    @Key("toolbar.connector.salesforceDescribeSobjectConnector.tooltip")
    String toolbarSalesforceDescribeSubjectConnectorTooltip();

    @Key("toolbar.connector.salesforceUpdateConnector.tooltip")
    String toolbarSalesforceUpdateConnectorTooltip();

    @Key("toolbar.connector.salesforceDescribeSobjectsConnector.tooltip")
    String toolbarSalesforceDescribeSubjectsConnectorTooltip();

    @Key("toolbar.connector.salesforceEmptyRecycleBin.tooltip")
    String toolbarSalesforceEmptyRecycleBinConnectorTooltip();

    @Key("toolbar.connector.salesforceLogOut.tooltip")
    String toolbarSalesforceLogOutConnectorTooltip();

    @Key("toolbar.connector.salesforceGetUserInfo.tooltip")
    String toolbarSalesforceGetUserInfoConnectorTooltip();

    @Key("toolbar.connector.salesforceQuery.tooltip")
    String toolbarSalesforceQueryTooltip();

    @Key("toolbar.connector.salesforceQueryAll.tooltip")
    String toolbarSalesforceQueryAllTooltip();

    @Key("toolbar.connector.salesforceQueryMore.tooltip")
    String toolbarSalesforceQueryMoreTooltip();

    @Key("toolbar.connector.salesforceResetPassword.tooltip")
    String toolbarSalesforceResetPasswordTooltip();

    @Key("toolbar.connector.salesforceRetrive.tooltip")
    String toolbarSalesforceRetriveTooltip();

    @Key("toolbar.connector.salesforceSearch.tooltip")
    String toolbarSalesforceSearchTooltip();

    @Key("toolbar.connector.salesforceSendEmail.tooltip")
    String toolbarSalesforceSendEmailTooltip();

    @Key("toolbar.connector.salesforceSendEmailMessage.tooltip")
    String toolbarSalesforceSendEmailMessageTooltip();

    @Key("toolbar.connector.salesforceSetPassword.tooltip")
    String toolbarSalesforceSetPasswordTooltip();

    @Key("toolbar.connector.salesforceUndelete.tooltip")
    String toolbarSalesforceUndeleteTooltip();

    @Key("toolbar.connector.salesforceUpset.tooltip")
    String toolbarSalesforceUpsetTooltip();

    @Key("propertiespanel.connector.salesForce.batchSize")
    String connectorBatchSize();

    @Key("propertiespanel.connector.salesForce.queryString")
    String connectorQueryString();

    @Key("propertiespanel.connector.salesForce.userId")
    String connectorUserId();

    @Key("propertiespanel.connector.jira.addAttachmentToisseId")
    String jiraIssueIdOrKey();

    @Key("propertiespanel.connector.jira.createFilter.filterName")
    String jiraFilterName();

    @Key("propertiespanel.connector.jira.createFilter.jqlType")
    String jiraJqlType();

    @Key("propertiespanel.connector.jira.createFilter.description")
    String jiraDescription();

    @Key("propertiespanel.connector.jira.createFilter.favourite")
    String jiraFavourite();

    @Key("propertiespanel.connector.jira.createIssue.projectKey")
    String jiraProjectKey();

    @Key("propertiespanel.connector.jira.createIssue.summary")
    String jiraSummary();

    @Key("propertiespanel.connector.jira.createIssue.issueType")
    String jiraIssueType();

    @Key("propertiespanel.connector.jira.deleteAvatarForProject.avatarId")
    String jiraAvatarId();

    @Key("propertiespanel.connector.jira.deleteComment.commentId")
    String jiraCommentId();

    @Key("propertiespanel.connector.jira.deleteFilter.filterId")
    String jiraFilterId();

    @Key("propertiespanel.connector.jira.doTransition.updateComment")
    String jiraUpdateComment();

    @Key("propertiespanel.connector.jira.doTransition.updateAssignee")
    String jiraUpdateAssignee();

    @Key("propertiespanel.connector.jira.doTransition.resolution")
    String jiraResolution();

    @Key("propertiespanel.connector.jira.doTransition.transitionId")
    String jiraTransitionId();

    @Key("propertiespanel.connector.jira.getDashboardById.id")
    String jiraId();

    @Key("propertiespanel.connector.jira.getGroup.groupName")
    String jiraGroupName();

    @Key("propertiespanel.connector.jira.getIssuePriorityById.issuePriorityId")
    String jiraIssuePriorityId();

    @Key("propertiespanel.connector.jira.getIssueTypeById.issueTypeyId")
    String jiraIssueTypeId();

    @Key("propertiespanel.connector.jira.getRolesByIdOfProject.roleId")
    String jiraRoleId();

    @Key("propertiespanel.connector.jira.getUserAssignableProjects.maxResult")
    String jiraMaxResult();

    @Key("propertiespanel.connector.jira.getUserAssignableProjects.startFrom")
    String jiraStartFrom();

    @Key("propertiespanel.connector.jira.getUserPermissions.projectId")
    String jiraProjectId();

    @Key("propertiespanel.connector.jira.getUserPermissions.issueKey")
    String jiraIssueKey();

    @Key("propertiespanel.connector.jira.getUserPermissions.issueId")
    String jiraIssueId();

    @Key("propertiespanel.connector.jira.init.uri")
    String jiraUri();

    @Key("propertiespanel.connector.jira.postComment.comment")
    String jiraComment();

    @Key("propertiespanel.connector.jira.postComment.visibleRole")
    String jiraVisibleRole();

    @Key("propertiespanel.connector.jira.searchAssignableUser.project")
    String jiraProject();

    @Key("propertiespanel.connector.jira.searchAssignableUser.startAt")
    String jiraStartAt();

    @Key("propertiespanel.connector.jira.searchAssignableUser.maxResults")
    String jiraMaxResults();

    @Key("propertiespanel.connector.jira.searchAssignableUserMultiProject.projectKeys")
    String jiraProjectKeys();

    @Key("propertiespanel.connector.jira.searchJira.query")
    String jiraQuery();

    @Key("propertiespanel.connector.jira.searchUser.includeActive")
    String jiraIncludeActive();

    @Key("propertiespanel.connector.jira.searchUser.includeInactive")
    String jiraIncludeInactive();

    @Key("toolbar.connector.jira.addAttachmentToIssueId.title")
    String jiraAddAttachmentToIssueIdTitle();

    @Key("toolbar.connector.jira.createFilter.title")
    String jiraCreateFilterTitle();

    @Key("toolbar.connector.jira.createIssue.title")
    String jiraCreateIssueTitle();

    @Key("toolbar.connector.jira.deleteAvatarForProject.title")
    String jiraDeleteAvatarForProjectTitle();

    @Key("toolbar.connector.jira.deleteComment.title")
    String jiraDeleteCommentTitle();

    @Key("toolbar.connector.jira.deleteFilter.title")
    String jiraDeleteFilterTitle();

    @Key("toolbar.connector.jira.getDashboard.title")
    String jiraGetDashboardTitle();

    @Key("toolbar.connector.jira.doTransition.title")
    String jiraDoTransitionTitle();

    @Key("toolbar.connector.jira.getAvatarsForProject.title")
    String jiraGetAvatarsForProjectTitle();

    @Key("toolbar.connector.jira.getComments.title")
    String jiraGetCommentsTitle();

    @Key("toolbar.connector.jira.getComponentsOfProject.title")
    String jiraGetComponentsOfProjectTitle();

    @Key("toolbar.connector.jira.getDashboardById.title")
    String jiraGetDashboardByIdTitle();

    @Key("toolbar.connector.jira.getFavouriteFilters.title")
    String jiraGetFavouriteFiltersTitle();

    @Key("toolbar.connector.jira.getFilterById.title")
    String jiraGetFilterByIdTitle();

    @Key("toolbar.connector.jira.getGroup.title")
    String jiraGetGroupTitle();

    @Key("toolbar.connector.jira.getIssue.title")
    String jiraGetIssueTitle();

    @Key("toolbar.connector.jira.getIssuePriorityes.title")
    String jiraGetIssuePrioritiesTitle();

    @Key("toolbar.connector.jira.getIssuePriorityById.title")
    String jiraGetIssuePriorityByIdTitle();

    @Key("toolbar.connector.jira.getIssueTypeById.title")
    String jiraGetIssueTypeByIdTitle();

    @Key("toolbar.connector.jira.getIssueTypes.title")
    String jiraGetIssueTypesTitle();

    @Key("toolbar.connector.jira.getIssuesForUser.title")
    String jiraGetIssuesForUserTitle();

    @Key("toolbar.connector.jira.getProject.title")
    String jiraGetProjectTitle();

    @Key("toolbar.connector.jira.getRolesByIdOfProject.title")
    String jiraGetRolesByIdOfProjectTitle();

    @Key("toolbar.connector.jira.getRolesOfProject.title")
    String jiraGetRolesOfProjectTitle();

    @Key("toolbar.connector.jira.getStatusesOfProject.title")
    String jiraGetStatusesOfProjectTitle();

    @Key("toolbar.connector.jira.getTransitions.title")
    String jiraGetTransitionsTitle();

    @Key("toolbar.connector.jira.getUser.title")
    String jiraGetUserTitle();

    @Key("toolbar.connector.jira.getUserAssignableProject.title")
    String jiraGetUserAssignableProjectsTitle();

    @Key("toolbar.connector.jira.getUserPermissions.title")
    String jiraGetUserPermissionsTitle();

    @Key("toolbar.connector.jira.getVersionsOfProject.title")
    String jiraGetVersionsOfProjectTitle();

    @Key("toolbar.connector.jira.getVotesForIssue.title")
    String jiraGetVotesForIssueTitle();

    @Key("toolbar.connector.jira.init.title")
    String jiraInitTitle();

    @Key("toolbar.connector.jira.postComment.title")
    String jiraPostCommentTitle();

    @Key("toolbar.connector.jira.searchAssignableUser.title")
    String jiraSearchAssignableUserTitle();

    @Key("toolbar.connector.jira.searchAssignableUserMultiProject.title")
    String jiraSearchAssignableUserMultiProjectTitle();

    @Key("toolbar.connector.jira.searchIssueViewableUsers.title")
    String jiraSearchIssueViewableUsersTitle();

    @Key("toolbar.connector.jira.searchJira.title")
    String jiraSearchJiraTitle();

    @Key("toolbar.connector.jira.searchUser.title")
    String jiraSearchUserTitle();

    @Key("toolbar.connector.jira.setActorsToRoleOfProject.title")
    String jiraSetActorsToRoleOfProjectTitle();

    @Key("toolbar.connector.jira.updateComment.title")
    String jiraUpdateCommentTitle();

    @Key("toolbar.connector.jira.updateFilterById.title")
    String jiraUpdateFilterByIdTitle();

    @Key("toolbar.connector.jira.updateIssue.title")
    String jiraUpdateIssueTitle();

    @Key("toolbar.connector.jira.updateIssueAssignee.title")
    String jiraUpdateIssueAssigneeTitle();

    @Key("toolbar.connector.jira.addAttachmentToIssueId.tooltip")
    String jiraAddAttachmentToIssueIdTooltip();

    @Key("toolbar.connector.jira.createFilter.tooltip")
    String jiraCreateFilterTooltip();

    @Key("toolbar.connector.jira.createIssue.tooltip")
    String jiraCreateIssueTooltip();

    @Key("toolbar.connector.jira.deleteAvatarForProject.tooltip")
    String jiraDeleteAvatarForProjectTooltip();

    @Key("toolbar.connector.jira.deleteComment.tooltip")
    String jiraDeleteCommentTooltip();

    @Key("toolbar.connector.jira.deleteFilter.tooltip")
    String jiraDeleteFilterTooltip();

    @Key("toolbar.connector.jira.getDashboard.tooltip")
    String jiraGetDashboardTooltip();

    @Key("toolbar.connector.jira.doTransition.tooltip")
    String jiraDoTransitionTooltip();

    @Key("toolbar.connector.jira.getAvatarsForProject.tooltip")
    String jiraGetAvatarsForProjectTooltip();

    @Key("toolbar.connector.jira.getComments.tooltip")
    String jiraGetCommentsTooltip();

    @Key("toolbar.connector.jira.getComponentsOfProject.tooltip")
    String jiraGetComponentsOfProjectTooltip();

    @Key("toolbar.connector.jira.getDashboardById.tooltip")
    String jiraGetDashboardByIdTooltip();

    @Key("toolbar.connector.jira.getFavouriteFilters.tooltip")
    String jiraGetFavouriteFiltersTooltip();

    @Key("toolbar.connector.jira.getFilterById.tooltip")
    String jiraGetFilterByIdTooltip();

    @Key("toolbar.connector.jira.getGroup.tooltip")
    String jiraGetGroupTooltip();

    @Key("toolbar.connector.jira.getIssue.tooltip")
    String jiraGetIssueTooltip();

    @Key("toolbar.connector.jira.getIssuePriorityes.tooltip")
    String jiraGetIssuePrioritiesTooltip();

    @Key("toolbar.connector.jira.getIssuePriorityById.tooltip")
    String jiraGetIssuePriorityByIdTooltip();

    @Key("toolbar.connector.jira.getIssueTypeById.tooltip")
    String jiraGetIssueTypeByIdTooltip();

    @Key("toolbar.connector.jira.getIssueTypes.tooltip")
    String jiraGetIssueTypesTooltip();

    @Key("toolbar.connector.jira.getIssuesForUser.tooltip")
    String jiraGetIssuesForUserTooltip();

    @Key("toolbar.connector.jira.getProject.tooltip")
    String jiraGetProjectTooltip();

    @Key("toolbar.connector.jira.getRolesByIdOfProject.tooltip")
    String jiraGetRolesByIdOfProjectTooltip();

    @Key("toolbar.connector.jira.getRolesOfProject.tooltip")
    String jiraGetRolesOfProjectTooltip();

    @Key("toolbar.connector.jira.getStatusesOfProject.tooltip")
    String jiraGetStatusesOfProjectTooltip();

    @Key("toolbar.connector.jira.getTransitions.tooltip")
    String jiraGetTransitionsTooltip();

    @Key("toolbar.connector.jira.getUser.tooltip")
    String jiraGetUserTooltip();

    @Key("toolbar.connector.jira.getUserAssignableProject.tooltip")
    String jiraGetUserAssignableProjectsTooltip();

    @Key("toolbar.connector.jira.getUserPermissions.tooltip")
    String jiraGetUserPermissionsTooltip();

    @Key("toolbar.connector.jira.getVersionsOfProject.tooltip")
    String jiraGetVersionsOfProjectTooltip();

    @Key("toolbar.connector.jira.getVotesForIssue.tooltip")
    String jiraGetVotesForIssueTooltip();

    @Key("toolbar.connector.jira.init.tooltip")
    String jiraInitTooltip();

    @Key("toolbar.connector.jira.postComment.tooltip")
    String jiraPostCommentTooltip();

    @Key("toolbar.connector.jira.searchAssignableUser.tooltip")
    String jiraSearchAssignableUserTooltip();

    @Key("toolbar.connector.jira.searchAssignableUserMultiProject.tooltip")
    String jiraSearchAssignableUserMultiProjectTooltip();

    @Key("toolbar.connector.jira.searchIssueViewableUsers.tooltip")
    String jiraSearchIssueViewableUsersTooltip();

    @Key("toolbar.connector.jira.searchJira.tooltip")
    String jiraSearchJiraTooltip();

    @Key("toolbar.connector.jira.searchUser.tooltip")
    String jiraSearchUserTooltip();

    @Key("toolbar.connector.jira.setActorsToRoleOfProject.tooltip")
    String jiraSetActorsToRoleOfProjectTooltip();

    @Key("toolbar.connector.jira.updateComment.tooltip")
    String jiraUpdateCommentTooltip();

    @Key("toolbar.connector.jira.updateFilterById.tooltip")
    String jiraUpdateFilterByIdTooltip();

    @Key("toolbar.connector.jira.updateIssue.tooltip")
    String jiraUpdateIssueTooltip();

    @Key("toolbar.connector.jira.updateIssueAssignee.tooltip")
    String jiraUpdateIssueAssigneeTooltip();

    @Key("propertiespanel.connector.twitter.destroyStatus.consumerKey")
    String twitterConsumerKey();

    @Key("propertiespanel.connector.twitter.destroyStatus.consumerSecret")
    String twitterConsumerSecret();

    @Key("propertiespanel.connector.twitter.destroyStatus.accessToken")
    String twitterAccessToken();

    @Key("propertiespanel.connector.twitter.destroyStatus.accessTokenSecret")
    String twitterAccessTokenSecret();

    @Key("propertiespanel.connector.twitter.destroyStatus.statusId")
    String twitterStatusId();

    @Key("propertiespanel.connector.twitter.getClosesTrends.latitude")
    String twitterLatitude();

    @Key("propertiespanel.connector.twitter.getClosesTrends.longitude")
    String twitterLongitude();

    @Key("propertiespanel.connector.twitter.getDirectMessages.count")
    String twitterCount();

    @Key("propertiespanel.connector.twitter.getDirectMessages.page")
    String twitterPage();

    @Key("propertiespanel.connector.twitter.getDirectMessages.sinceId")
    String twitterSinceId();

    @Key("propertiespanel.connector.twitter.getDirectMessages.maxId")
    String twitterMaxId();

    @Key("propertiespanel.connector.twitter.getFollowers.screenName")
    String twitterScreenName();

    @Key("propertiespanel.connector.twitter.getFollowers.userId")
    String twitterUserId();

    @Key("propertiespanel.connector.twitter.getFollowers.cursor")
    String twitterCursor();

    @Key("propertiespanel.connector.twitter.search.search")
    String twitterSearch();

    @Key("propertiespanel.connector.twitter.search.lang")
    String twitterLang();

    @Key("propertiespanel.connector.twitter.search.locale")
    String twitterLocale();

    @Key("propertiespanel.connector.twitter.search.since")
    String twitterSince();

    @Key("propertiespanel.connector.twitter.search.geocode")
    String twitterGeocode();

    @Key("propertiespanel.connector.twitter.search.radius")
    String twitterRadius();

    @Key("propertiespanel.connector.twitter.search.unit")
    String twitterUnit();

    @Key("propertiespanel.connector.twitter.search.until")
    String twitterUntil();

    @Key("propertiespanel.connector.twitter.searchPlaces.query")
    String twitterQuery();

    @Key("propertiespanel.connector.twitter.sendDirectMessages.message")
    String twitterMessage();

    @Key("toolbar.connector.twitter.destroyStatus.title")
    String twitterDestroyStatusTitle();

    @Key("toolbar.connector.twitter.getClothesTrends.title")
    String twitterGetClosesTrendsTitle();

    @Key("toolbar.connector.twitter.getDirectMessages.title")
    String twitterGetDirectMessagesTitle();

    @Key("toolbar.connector.twitter.getFollowers.title")
    String twitterGetFollowersTitle();

    @Key("toolbar.connector.twitter.getFollowersIds.title")
    String twitterGetFollowersIdsTitle();

    @Key("toolbar.connector.twitter.getFriends.title")
    String twitterGetFriendsTitle();

    @Key("toolbar.connector.twitter.getFriendsIds.title")
    String twitterGetFriendsIdsTitle();

    @Key("toolbar.connector.twitter.getHomeTimeLine.title")
    String twitterGetHomeTimeLineTitle();

    @Key("toolbar.connector.twitter.getMentionsTimeLine.title")
    String twitterGetMentionsTimeLineTitle();

    @Key("toolbar.connector.twitter.getRetweetsOfMine.title")
    String twitterGetRetweetsOfMineTitle();

    @Key("toolbar.connector.twitter.getSentDirectMessage.title")
    String twitterGetSentDirectMessageTitle();

    @Key("toolbar.connector.twitter.getTopTrendPlaces.title")
    String twitterGetTopTrendPlacesTitle();

    @Key("toolbar.connector.twitter.getUserTimeLine.title")
    String twitterGetUserTimeLineTitle();

    @Key("toolbar.connector.twitter.initTwitter.title")
    String twitterInitTitle();

    @Key("toolbar.connector.twitter.retweet.title")
    String twitterRetweetTitle();

    @Key("toolbar.connector.twitter.searchTwitter.title")
    String twitterSearchTitle();

    @Key("toolbar.connector.twitter.searchPlaces.title")
    String twitterSearchPlacesTitle();

    @Key("toolbar.connector.twitter.sendDirectMessage.title")
    String twitterSendDirectMessageTitle();

    @Key("toolbar.connector.twitter.showStatus.title")
    String twitterShowStatusTitle();

    @Key("toolbar.connector.twitter.updateStatus.title")
    String twitterUpdateStatusTitle();

    @Key("toolbar.connector.twitter.destroyStatus.tooltip")
    String twitterDestroyStatusTooltip();

    @Key("toolbar.connector.twitter.getClothesTrends.tooltip")
    String twitterGetClosesTrendsTooltip();

    @Key("toolbar.connector.twitter.getDirectMessages.tooltip")
    String twitterGetDirectMessagesTooltip();

    @Key("toolbar.connector.twitter.getFollowers.tooltip")
    String twitterGetFollowersTooltip();

    @Key("toolbar.connector.twitter.getFollowersIds.tooltip")
    String twitterGetFollowersIdsTooltip();

    @Key("toolbar.connector.twitter.getFriends.tooltip")
    String twitterGetFriendsTooltip();

    @Key("toolbar.connector.twitter.getFriendsIds.tooltip")
    String twitterGetFriendsIdsTooltip();

    @Key("toolbar.connector.twitter.getHomeTimeLine.tooltip")
    String twitterGetHomeTimeLineTooltip();

    @Key("toolbar.connector.twitter.getMentionsTimeLine.tooltip")
    String twitterGetMentionsTimeLineTooltip();

    @Key("toolbar.connector.twitter.getRetweetsOfMine.tooltip")
    String twitterGetRetweetsOfMineTooltip();

    @Key("toolbar.connector.twitter.getSentDirectMessage.tooltip")
    String twitterGetSentDirectMessageTooltip();

    @Key("toolbar.connector.twitter.getTopTrendPlaces.tooltip")
    String twitterGetTopTrendPlacesTooltip();

    @Key("toolbar.connector.twitter.getUserTimeLine.tooltip")
    String twitterGetUserTimeLineTooltip();

    @Key("toolbar.connector.twitter.initTwitter.tooltip")
    String twitterInitTooltip();

    @Key("toolbar.connector.twitter.retweet.tooltip")
    String twitterRetweetTooltip();

    @Key("toolbar.connector.twitter.searchTwitter.tooltip")
    String twitterSearchTooltip();

    @Key("toolbar.connector.twitter.searchPlaces.tooltip")
    String twitterSearchPlacesTooltip();

    @Key("toolbar.connector.twitter.sendDirectMessage.tooltip")
    String twitterSendDirectMessageTooltip();

    @Key("toolbar.connector.twitter.showStatus.tooltip")
    String twitterShowStatusTooltip();

    @Key("toolbar.connector.twitter.updateStatus.tooltip")
    String twitterUpdateStatusTooltip();

    @Key("toolbar.group.mediators")
    String toolbarGroupMediators();

    @Key("toolbar.group.endpoints")
    String toolbarGroupEndpoints();

    @Key("toolbar.group.connector.salesforce")
    String toolbarGroupSalesforceConnector();

    @Key("toolbar.group.connector.jira")
    String toolbarGroupJiraConnector();

    @Key("toolbar.group.connector.twitter")
    String toolbarGroupTwitterConnector();

    @Key("propertiespanel.connector.spreadsheet.createSpreadsheet.spreadsheetName")
    String spreadsheetCreateSpreadsheetSpreadsheetName();

    @Key("propertiespanel.connector.spreadsheet.createSpreadsheet.worksheetCount")
    String spreadsheetCreateSpreadsheetWorksheetCount();

    @Key("toolbar.connector.spreadsheet.create.spreadsheet.tooltip")
    String spreadsheetCreateSpreadsheetTooltip();

    @Key("toolbar.connector.spreadsheet.create.worksheet.tooltip")
    String spreadsheetCreateWorksheetTooltip();

    @Key("toolbar.connector.spreadsheet.delete.worksheet.tooltip")
    String spreadsheetDeleteWorksheetTooltip();

    @Key("toolbar.connector.spreadsheet.getAllCells.tooltip")
    String spreadsheetGetAllCellsTooltip();

    @Key("toolbar.connector.spreadsheet.getAllCellsCSV.tooltip")
    String spreadsheetGetAllCellsCSVTooltip();

    @Key("toolbar.connector.spreadsheet.getAllSpreadsheets.tooltip")
    String spreadsheetGetAllSpreadsheetsTooltip();

    @Key("toolbar.connector.spreadsheet.getAuthors.tooltip")
    String spreadsheetGetAuthorsTooltip();

    @Key("toolbar.connector.spreadsheet.getCellRange.tooltip")
    String spreadsheetGetCellRangeTooltip();

    @Key("toolbar.connector.spreadsheet.getCellRangeCSV.tooltip")
    String spreadsheetGetCellRangeCSVTooltip();

    @Key("toolbar.connector.spreadsheet.getColumnHeaders.tooltip")
    String spreadsheetGetColumnHeadersTooltip();

    @Key("toolbar.connector.spreadsheet.getSpreadsheetByTitle.tooltip")
    String spreadsheetGetSpreadsheetByTitleTooltip();

    @Key("toolbar.connector.spreadsheet.getWorksheetByTitle.tooltip")
    String spreadsheetGetWorksheetByTitleTooltip();

    @Key("toolbar.connector.spreadsheet.importCSV.tooltip")
    String spreadsheetImportCSVTooltip();

    @Key("toolbar.connector.spreadsheet.init.tooltip")
    String spreadsheetInitTooltip();

    @Key("toolbar.connector.spreadsheet.purgeWorksheet.tooltip")
    String spreadsheetPurgeWorksheetTooltip();

    @Key("toolbar.connector.spreadsheet.searchCell.tooltip")
    String spreadsheetSearchCellTooltip();

    @Key("toolbar.connector.spreadsheet.setRow.tooltip")
    String spreadsheetSetRowTooltip();

    @Key("toolbar.connector.spreadsheet.updateWorksheetMeta.tooltip")
    String spreadsheetUpdateWorksheetMetaTooltip();

    @Key("toolbar.connector.spreadsheet.usernameLogin.tooltip")
    String spreadsheetUsernameLoginTooltip();

    @Key("toolbar.connector.spreadsheet.getAllWorksheets.tooltip")
    String spreadsheetGetAllWorksheetsTooltip();

    @Key("propertiespanel.connector.spreadsheet.createWorksheet.worksheetName")
    String spreadsheetCreateWorksheetWorksheetName();

    @Key("propertiespanel.connector.spreadsheet.createWorksheet.spreadsheetName")
    String spreadsheetCreateWorksheetSpreadsheetName();

    @Key("propertiespanel.connector.spreadsheet.createWorksheet.worksheetRows")
    String spreadsheetCreateWorksheetWorksheetRows();

    @Key("propertiespanel.connector.spreadsheet.createWorksheet.worksheetColumns")
    String spreadsheetCreateWorksheetWorksheetColumns();

    @Key("propertiespanel.connector.spreadsheet.getCellRange.minRow")
    String spreadsheetGetCellRangeMinRow();

    @Key("propertiespanel.connector.spreadsheet.getCellRange.maxRow")
    String spreadsheetGetCellRangeMaxRow();

    @Key("propertiespanel.connector.spreadsheet.getCellRange.minColumn")
    String spreadsheetGetCellRangeMinColumn();

    @Key("propertiespanel.connector.spreadsheet.getCellRange.maxColumn")
    String spreadsheetGetCellRangeMaxColumn();

    @Key("propertiespanel.connector.spreadsheet.getSpreadsheetsByTitle.title")
    String spreadsheetGetSpreadsheetsTitle();

    @Key("propertiespanel.connector.spreadsheet.init.oauthConsumerSecret")
    String spreadsheetInitOauthConsumerSecret();

    @Key("propertiespanel.connector.spreadsheet.init.oauthAccessToken")
    String spreadsheetInitOauthAccessToken();

    @Key("propertiespanel.connector.spreadsheet.init.oauthConsumerKey")
    String spreadsheetInitOauthConsumerKey();

    @Key("propertiespanel.connector.spreadsheet.init.oauthAccessTokenSecret")
    String spreadsheetInitOauthAccessTokenSecret();

    @Key("propertiespanel.connector.spreadsheet.searchCell.searchString")
    String spreadsheetSearchCellSearchString();

    @Key("propertiespanel.connector.spreadsheet.setRow.rowId")
    String spreadsheetSetRowRowId();

    @Key("propertiespanel.connector.spreadsheet.setRow.rowData")
    String spreadsheetSetRowRowData();

    @Key("propertiespanel.connector.spreadsheet.updateWorksheetMetadata.worksheetOldName")
    String spreadsheetUpdateWorksheetMetadataWorksheetOldName();

    @Key("propertiespanel.connector.spreadsheet.updateWorksheetMetadata.worksheetNewName")
    String spreadsheetUpdateWorksheetMetadataWorksheetNewName();

    @Key("propertiespanel.connector.spreadsheet.updateWorksheetMetadata.worksheetRows")
    String spreadsheetUpdateWorksheetMetadataWorksheetRows();

    @Key("propertiespanel.connector.spreadsheet.updateWorksheetMetadata.worksheetColumns")
    String spreadsheetUpdateWorksheetMetadataWorksheetColumns();

    @Key("propertiespanel.connector.spreadsheet.usernameLogin.username")
    String spreadsheetUsernameLoginUsername();

    @Key("propertiespanel.connector.spreadsheet.usernameLogin.password")
    String spreadsheetUsernameLoginPassword();

    @Key("propertiespanel.connector.spreadsheet.importCSV.filePath")
    String spreadsheetImportCSVFilePath();

    @Key("propertiespanel.connector.spreadsheet.importCSV.batchEnable")
    String spreadsheetImportCSVBatchEnable();

    @Key("propertiespanel.connector.spreadsheet.importCSV.batchSize")
    String spreadsheetImportCSVBatchSize();

    @Key("toolbar.group.connector.google.spreadsheet")
    String toolbarGroupGoogleSpreadsheetConnector();

    @Key("toolbar.connector.spreadsheet.create.spreadsheet.title")
    String spreadsheetCreateSpreadsheetTitle();

    @Key("toolbar.connector.spreadsheet.create.worksheet.title")
    String spreadsheetCreateWorksheetTitle();

    @Key("toolbar.connector.spreadsheet.delete.worksheet.title")
    String spreadsheetDeleteWorksheetTitle();

    @Key("toolbar.connector.spreadsheet.getAllCells.title")
    String spreadsheetGetAllCellsTitle();

    @Key("toolbar.connector.spreadsheet.getAllCellsCSV.title")
    String spreadsheetGetAllCellsCSVTitle();

    @Key("toolbar.connector.spreadsheet.getAllSpreadsheets.title")
    String spreadsheetGetAllSpreadsheetsTitle();

    @Key("toolbar.connector.spreadsheet.getAllWorksheets.title")
    String spreadsheetGetAllWorksheetsTitle();

    @Key("toolbar.connector.spreadsheet.getAuthors.title")
    String spreadsheetGetAuthorsTitle();

    @Key("toolbar.connector.spreadsheet.getCellRange.title")
    String spreadsheetGetCellRangeTitle();

    @Key("toolbar.connector.spreadsheet.getCellRangeCSV.title")
    String spreadsheetGetCellRangeCSVTitle();

    @Key("toolbar.connector.spreadsheet.getColumnHeaders.title")
    String spreadsheetGetColumnHeadersTitle();

    @Key("toolbar.connector.spreadsheet.getSpreadsheetByTitle.title")
    String spreadsheetGetSpreadsheetByTitleTitle();

    @Key("toolbar.connector.spreadsheet.getWorksheetByTitle.title")
    String spreadsheetGetWorksheetByTitleTitle();

    @Key("toolbar.connector.spreadsheet.importCSV.title")
    String spreadsheetImportCSVTitle();

    @Key("toolbar.connector.spreadsheet.init.title")
    String spreadsheetInitTitle();

    @Key("toolbar.connector.spreadsheet.purgeWorksheet.title")
    String spreadsheetPurgeWorksheetTitle();

    @Key("toolbar.connector.spreadsheet.searchCell.title")
    String spreadsheetSearchCellTitle();

    @Key("toolbar.connector.spreadsheet.setRow.title")
    String spreadsheetSetRowTitle();

    @Key("toolbar.connector.spreadsheet.updateWorksheetMeta.title")
    String spreadsheetUpdateWorksheetMetaTitle();

    @Key("toolbar.connector.spreadsheet.usernameLogin.title")
    String spreadsheetUsernameLoginTitle();
}
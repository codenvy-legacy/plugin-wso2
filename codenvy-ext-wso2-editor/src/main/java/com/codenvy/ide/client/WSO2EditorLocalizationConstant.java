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

    @Key("toolbar.connector.salesforceInitConnector.tooltip")
    String toolbarSalesforceInitConnectorTooltip();

    @Key("propertiespanel.connector.config.ref")
    String connectorConfigRef();

    @Key("propertiespanel.connector.available.configs")
    String connectorAvailableConfigs();

    @Key("propertiespanel.connector.new.config")
    String connectorNewConfig();

    @Key("propertiespanel.connector.parameter.editor.type")
    String connectorParameterEditorType();

    @Key("propertiespanel.connector.username")
    String connectorUsername();

    @Key("propertiespanel.connector.password")
    String connectorPassword();

    @Key("propertiespanel.connector.login.url")
    String connectorLoginUrl();

    @Key("propertiespanel.connector.new.config.value")
    String connectorNewConfigValue();

    @Key("propertiespanel.connector.force.login")
    String connectorForceLogin();

    @Key("propertiespanel.addressEndpoint.table.title")
    String editPropAdrrEndTableTitle();

    @Key("propertiespanel.addressEndpoint.table.error")
    String endpointTableError();

    @Key("propertiespanel.error.message")
    String errorMessage();

    @Key("propertiespanel.connector.expression")
    String connectorExpression();

    @Key("propertiespanel.connector.configuration.title")
    String propertiespanelConnectorConfigurationTitle();

    @Key("toolbar.connector.salesforceCreateConnector.tooltip")
    String toolbarSalesforceCreateConnectorTooltip();

    @Key("toolbar.connector.salesforceDelete.tooltip")
    String salesForceDeleteTooltip();

    @Key("propertiespanel.connector.allOrNone")
    String connectorAllOrNone();

    @Key("propertiespanel.connector.allowFieldTruncate")
    String connectorAllowFieldTruncate();

    @Key("propertiespanel.connector.subjects")
    String connectorSubjects();

    @Key("toolbar.connector.salesforceUpdateConnector.tooltip")
    String toolbarSalesforceUpdateConnectorTooltip();

    @Key("propertiespanel.connector.subject")
    String propertiespanelConnectorSubject();

    @Key("toolbar.connector.salesforceDescribeGlobalConnector.tooltip")
    String toolbarSalesforceDescribeGlobalConnectorTooltip();

    @Key("toolbar.connector.salesforceDescribeSobjectConnector.tooltip")
    String toolbarSalesforceDescribeSobjectConnectorTooltip();

    @Key("toolbar.connector.salesforceDescribeSobjectsConnector.tooltip")
    String toolbarSalesforceDescribeSobjectsConnectorTooltip();

    @Key("toolbar.connector.salesforceEmptyRecycleBin.tooltip")
    String toolbarEmptyRecycleBinConnector();

    @Key("toolbar.connector.salesforceLogOut.tooltip")
    String toolbarLogOutConnector();

    @Key("toolbar.connector.salesforceGetUserInfo.tooltip")
    String toolbarGetUserInfoConnector();

    @Key("toolbar.connector.salesforceQuery.tooltip")
    String connectorSalesforceQueryTooltip();

    @Key("toolbar.connector.salesforceQueryAll.tooltip")
    String connectorSalesforceQueryAllTooltip();

    @Key("toolbar.connector.salesforceQueryMore.tooltip")
    String connectorSalesforceQueryMoreTooltip();

    @Key("toolbar.connector.salesforceResetPassword.tooltip")
    String connectorSalesforceResetPasswordTooltip();

    @Key("toolbar.connector.salesforceRetrive.tooltip")
    String connectorSalesforceRetriveTooltip();

    @Key("toolbar.connector.salesforceSearch.tooltip")
    String connectorSalesforceSearchTooltip();

    @Key("toolbar.connector.salesforceSendEmail.tooltip")
    String connectorSalesforceSendEmailTooltip();

    @Key("toolbar.connector.salesforceSendEmailMessage.tooltip")
    String connectorSalesforceSendEmailMessageTooltip();

    @Key("toolbar.connector.salesforceSetPassword.tooltip")
    String connectorSalesforceSetPasswordTooltip();

    @Key("toolbar.connector.salesforceUndelete.tooltip")
    String connectorSalesforceUndeleteTooltip();

    @Key("toolbar.connector.salesforceUpset.tooltip")
    String connectorSalesforceUpsetTooltip();

    @Key("toolbar.connector.salesforceInitConnector.tooltip")
    String connectorSalesforceInitConnectorTooltip();

    @Key("toolbar.connector.salesforceCreateConnector.tooltip")
    String connectorSalesforceCreateConnectorTooltip();

}
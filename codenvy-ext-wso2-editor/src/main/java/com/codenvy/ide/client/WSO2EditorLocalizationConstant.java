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

/**
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
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

    @Key("propertiespanel.argumentsconfig.table.value")
    String tableValue();

    @Key("propertiespanel.argumentsconfig.table.Evaluator")
    String tableEvaluator();

    @Key("propertiespanel.argumentsconfig.table.name")
    String columnName();

    @Key("propertiespanel.argumentsconfig.table.type")
    String columnType();

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
}
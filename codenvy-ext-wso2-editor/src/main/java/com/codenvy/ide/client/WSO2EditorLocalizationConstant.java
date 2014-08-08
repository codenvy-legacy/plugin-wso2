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
 */
public interface WSO2EditorLocalizationConstant extends Messages {

    @Key("toolbar.log.tooltip")
    String toolbarLogTooltip();

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

    @Key("toolbar.call.tooltip")
    String toolbarCallTooltip();

    @Key("toolbar.connection.tooltip")
    String toolbarConnectionTooltip();

    @Key("propertiespanel.logconfig.logcategory")
    String logCategory();

    @Key("propertiespanel.logconfig.loglevel")
    String logLevel();

    @Key("propertiespanel.logconfig.logseparator")
    String logSeparator();

    @Key("propertiespanel.logconfig.properties")
    String properties();

    @Key("propertiespanel.logconfig.name")
    String logName();

    @Key("propertiespanel.logconfig.valueexpression")
    String valueExpression();

    @Key("propertiespanel.table.name")
    String columnName();

    @Key("propertiespanel.table.type")
    String columnType();

    @Key("propertiespanel.table.Expression")
    String columnExpression();

    @Key("propertiespanel.namespace.property")
    String namespacedProperty();

    @Key("propertiespanel.namespace.namespaces")
    String namespaces();

    @Key("propertiespanel.namespace.prefix")
    String namespacePrefix();

    @Key("propertiespanel.namespace.uri")
    String namespaceUri();

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

    @Key("propertiespanel.namespace.defaultexpression")
    String defaultExpression();

    @Key("propertiespanel.header.headername")
    String labelHeader();

    @Key("propertiespanel.header.headerexpression.label")
    String labelExpression();

    @Key("propertiespanel.namespace.property.expression")
    String propertiespanelNamespacePropertyExpression();

    @Key("propertiespanel.property.editor.title")
    String propertiespanelPropertyEditorTitle();

    @Key("propertiespanel.payload.format")
    String propertiespanelPayloadFormat();

    @Key("propertiespanel.log.configuration.title")
    String propertiespanelLogConfigurationTitle();

    @Key("button.cancel")
    String buttonCancel();

    @Key("button.ok")
    String buttonOk();

    @Key("propertiespanel.resource.key.editor")
    String propertiespanelResourceKeyEditor();

    @Key("propertiespanel.key.from.specify")
    String propertiespanelKeyFromSpecify();

    @Key("propertiespanel.new.resource")
    String propertiespanelNewResource();

    @Key("propertiespanel.resource.key")
    String propertiespanelResourceKey();

    @Key("propertiespanel.resource.key.editor.title")
    String propertiespanelResourceKeyEditorTitle();

    @Key("propertiespanel.payload.args.configuration.title")
    String propertiespanelPayloadArgsConfigurationTitle();

    @Key("propertiespanel.table.value")
    String propertiespanelTableValue();

    @Key("propertiespanel.table.Evaluator")
    String propertiespanelTableEvaluator();

    @Key("propertiespanel.payload.args.label")
    String propertiespanelPayloadArgsLabel();

    @Key("propertiespanel.enrich.source.namespace.label")
    String enrichSrcLabel();

    @Key("propertiespanel.enrich.source.inline.titleEnrichInline")
    String titleEnrichInline();

    @Key("propertiespanel.header.inlinewindow.title")
    String inlineTitle();

    @Key("propertiespanel.call.template.configuration.title")
    String propertiespanelCallTemplateConfigurationTitle();
}
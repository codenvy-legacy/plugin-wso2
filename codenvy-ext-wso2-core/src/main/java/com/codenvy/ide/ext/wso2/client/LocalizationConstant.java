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
package com.codenvy.ide.ext.wso2.client;

import com.google.gwt.i18n.client.Messages;
import com.google.inject.Singleton;

/**
 * Contains all message needed for WSO2 plugin.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@Singleton
public interface LocalizationConstant extends Messages {

    @Key("wizard.project.artifactId")
    String artifactId();

    @Key("wizard.project.version")
    String version();

    @Key("wizard.project.parentGroupId")
    String parentGroupId();

    @Key("wizard.project.parentArtifactId")
    String parentArtifactId();

    @Key("wizard.project.parentVersion")
    String parentVersion();

    @Key("wizard.project.specifyParentPom")
    String specifyParentPom();

    @Key("wso2.main.action.title")
    String wso2MainActionTitle();

    @Key("wso2.import.action.title")
    String wso2ImportActionTitle();

    @Key("wso2.import.synapse.description")
    String wso2ImportActionDescription();

    @Key("wso2.button.cancel")
    String wso2ButtonCancel();

    @Key("wso2.button.import")
    String wso2ButtonImport();

    @Key("wso2.file.name.title")
    String wso2FileNameTitle();

    @Key("wso2.url.label.title")
    String wso2UrlLabelTitle();

    @Key("wso2.import.dialog.title")
    String wso2ImportDialogTitle();

    @Key("wso2.import.dialog.error")
    String wso2ImportDialogError();

    @Key("wso2.import.file.format.error")
    String wso2ImportFileFormatError();

    @Key("wizard.file.endpoint.title")
    String wizardFileEndpointTitle();

    @Key("wizard.file.endpoint.notice.emptyName")
    String wizardFileEndpointNoticeEmptyName();

    @Key("wizard.file.endpoint.fields.name")
    String wizardFileEndpointFieldsName();

    @Key("wso2.actions.createEndpoint.title")
    String wso2ActionsCreateEndpointTitle();

    @Key("wso2.action.new")
    String wso2ActionNew();

    @Key("wizard.file.resource.notice.fileExists")
    String wizardFileResourceNoticeFileExists();

    @Key("wizard.file.resource.notice.parentFolderNotExists")
    String wizardFileResourceNoticeParentFolderNotExists();

    @Key("wizard.file.resource.notice.incorrectName")
    String wizardFileResourceNoticeIncorrectName();

    @Key("wizard.file.sequence.title")
    String wizardFileSequenceTitle();

    @Key("wizard.file.sequence.notice.emptyName")
    String wizardFileSequenceNoticeEmptyName();

    @Key("wizard.file.sequence.fields.name")
    String wizardFileSequenceFieldsName();

    @Key("wso2.actions.createSequence.title")
    String wso2ActionsCreateSequenceTitle();

    @Key("wizard.file.proxyService.title")
    String wizardFileProxyServiceTitle();

    @Key("wizard.file.proxyService.notice.emptyName")
    String wizardFileProxyServiceNoticeEmptyName();

    @Key("wizard.file.proxyService.fields.name")
    String wizardFileProxyServiceFieldsName();

    @Key("wso2.actions.createProxyService.title")
    String wso2ActionsCreateProxyServiceTitle();

    @Key("wizard.file.localEntry.title")
    String wizardFileLocalEntryTitle();

    @Key("wizard.file.localEntry.notice.emptyName")
    String wizardFileLocalEntryNoticeEmptyName();

    @Key("wizard.file.localEntry.fields.name")
    String wizardFileLocalEntryFieldsName();

    @Key("wso2.actions.createLocalEntry.title")
    String wso2ActionsCreateLocalEntryTitle();

    @Key("wso2.import.synapse.config")
    String wso2ImportSynapseConfig();

    @Key("wso2.file.overwrite.title")
    String wso2FileOverwriteTitle();

    @Key("wso2.import.file.already.exists")
    String wso2ImportFileAlreadyExists();

    @Key("wso2.button.overwrite")
    String wso2ButtonOverwrite();

    @Key("wso2.button.rename")
    String wso2ButtonRename();

    @Key("authorize.need.body.oauth")
    String authorizeNeedBodyOauth(String vendorProvider);

    @Key("authorize.need.title.oauth")
    String authorizeNeedTitleOauth();

    @Key("editor.text")
    String editorText();

    @Key("editor.graphical")
    String editorGraphical();

    @Key("editor.associate")
    String editorAssociate();

    @Key("importingFileMessage")
    String importingFileMessage();

    @Key("modifyingFileMessage")
    String modifyingFileMessage();

    @Key("toolbar.palette")
    String toolbarPalette();

    @Key("editor.property.panel")
    String editorProperty();
}
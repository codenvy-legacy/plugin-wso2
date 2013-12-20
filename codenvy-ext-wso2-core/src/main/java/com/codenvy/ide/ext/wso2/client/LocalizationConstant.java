/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client;

import com.google.gwt.i18n.client.Messages;

/**
 * Contains all message needed for WSO2 plugin.
 *
 * @author Andrey Plotnikov
 */
public interface LocalizationConstant extends Messages {

    @Key("wizard.project.groupId")
    String groupID();

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

    @Key("wizard.project.title")
    String wizardProjectTitle();

    @Key("wizard.project.notice.emptyGroupId")
    String wizardProjectNoticeEmptyGroupId();

    @Key("wizard.project.notice.emptyArtifactId")
    String wizardProjectNoticeEmptyArtifactId();

    @Key("wizard.project.notice.emptyVersion")
    String wizardProjectNoticeEmptyVersion();

    @Key("wizard.project.notice.emptyParentGroupId")
    String wizardProjectNoticeEmptyParentGroupId();

    @Key("wizard.project.notice.emptyParentArtifactId")
    String wizardProjectNoticeEmptyParentArtifactId();

    @Key("wizard.project.notice.emptyParentVersion")
    String wizardProjectNoticeEmptyParentVersion();

    @Key("wso2.project.title")
    String wso2ProjectTitle();

    @Key("wso2.project.esb.title")
    String wso2ProjectEsbTitle();

    @Key("wso2.main.action.title")
    String wso2MainActionTitle();

    @Key("wso2.import.action.title")
    String wso2ImportActionTitle();

    @Key("wso2.import.action.description")
    String wso2ImportActionDescription();

    @Key("wso2.button.cancel")
    String wso2ButtonCancel();

    @Key("wso2.button.import")
    String wso2ButtonImport();

    @Key("wso2.file.name.title")
    String wso2FileNameTitle();

    @Key("wso2.use.url.label")
    String wso2UseUrlLabel();

    @Key("wso2.url.label.title")
    String wso2UrlLabelTitle();

    @Key("wso2.import.dialog.title")
    String wso2ImportDialogTitle();

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
}
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
}
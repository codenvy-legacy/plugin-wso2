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
package com.codenvy.ide.ext.wso2.shared;

import com.codenvy.dto.shared.DTO;

/**
 * Contains information about creating ESB configuration project.
 *
 * @author Andrey Plotnikov
 */
@DTO
public interface ESBProjectInfo {

    String getProjectName();

    void setProjectName(String projectName);

    ESBProjectInfo withProjectName(String projectName);

    String getGroupID();

    void setGroupID(String groupID);

    ESBProjectInfo withGroupID(String groupID);

    String getArtifactID();

    void setArtifactID(String artifactID);

    ESBProjectInfo withArtifactID(String artifactID);

    String getVersion();

    void setVersion(String version);

    ESBProjectInfo withVersion(String version);

    boolean isParentPomConf();

    void setParentPomConf(boolean isParentPomConf);

    ESBProjectInfo withParentPomConf(boolean isParentPomConf);

    String getParentGroupID();

    void setParentGroupID(String groupID);

    ESBProjectInfo withParentGroupID(String groupID);

    String getParentArtifactID();

    void setParentArtifactID(String artifactID);

    ESBProjectInfo withParentArtifactID(String artifactID);

    String getParentVersion();

    void setParentVersion(String version);

    ESBProjectInfo withParentVersion(String version);

}
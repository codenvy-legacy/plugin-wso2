/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] Codenvy, S.A. 
 *  All Rights Reserved.
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
 * Contains information about importing file.
 *
 * @author Valeriy Svydenko
 */

@DTO
public interface FileInfo {
    String getFileName();

    void setFileName(String fileName);

    FileInfo withFileName(String fileName);

    String getProjectName();

    void setProjectName(String projectName);

    FileInfo withProjectName(String projectName);

    String getNewFileName();

    void setNewFileName(String newFileName);

    FileInfo withNewFileName(String newFileName);
}

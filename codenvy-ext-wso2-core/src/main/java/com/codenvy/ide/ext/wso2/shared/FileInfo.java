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
package com.codenvy.ide.ext.wso2.shared;

import com.codenvy.dto.shared.DTO;

import javax.annotation.Nonnull;

/**
 * Contains information about importing file.
 *
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
@DTO
public interface FileInfo {

    /** @return value of file name */
    @Nonnull
    String getFileName();

    /**
     * Sets value of file name to file.
     *
     * @param fileName
     *         name which need to set
     */
    void setFileName(@Nonnull String fileName);

    /**
     * Returns file info entity with current file name.
     *
     * @param fileName
     *         name of file
     */
    @Nonnull
    FileInfo withFileName(@Nonnull String fileName);

    /** @return name of project */
    @Nonnull
    String getProjectName();

    /**
     * Sets name to project.
     *
     * @param projectName
     *         name which need to set to project
     */
    void setProjectName(@Nonnull String projectName);

    /**
     * Returns file info entity with current project name.
     *
     * @param projectName
     *         name of project
     */
    @Nonnull
    FileInfo withProjectName(@Nonnull String projectName);

    /** @return new name of file */
    @Nonnull
    String getNewFileName();

    /**
     * Sets new name to file.
     *
     * @param newFileName
     *         name which need to set to file
     */
    void setNewFileName(@Nonnull String newFileName);

    /**
     * Sets new name to file and return FileInfo entity.
     *
     * @param newFileName
     *         new name of file which need set to file
     */
    @Nonnull
    FileInfo withNewFileName(@Nonnull String newFileName);
}

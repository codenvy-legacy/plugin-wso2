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
package com.codenvy.ide.ext.wso2.client.wizard.project;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

import javax.validation.constraints.NotNull;

/**
 * The view of {@link CreateESBConfProjectPage}. Provides an ability to input information that pom.xml file need be contained.
 *
 * @author Andrey Plotnikov
 */
@ImplementedBy(CreateESBConfProjectViewImpl.class)
public interface CreateESBConfProjectView extends View<CreateESBConfProjectView.ActionDelegate> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate {

        /** Performs some actions in response to a user's changing information about pom file. */
        void onValueChanged();

        /** Performs some actions in response to a user's changing enable state of parent pom configuration panel. */
        void onParentPomConfChanged();
    }

    /** @return inputted group id */
    @NotNull
    String getGroupID();

    /**
     * Changes content of group id field on view.
     *
     * @param groupID
     *         content that need to be inputted
     */
    void setGroupID(@NotNull String groupID);

    /** @return inputted artifact id */
    @NotNull
    String getArtifactID();

    /**
     * Changes content of artifact id field on view.
     *
     * @param artifactID
     *         content that need to be inputted
     */
    void setArtifactID(@NotNull String artifactID);

    /** @return inputted version */
    @NotNull
    String getVersion();

    /**
     * Changes content of version field on view.
     *
     * @param version
     *         content that need to be inputted
     */
    void setVersion(@NotNull String version);

    /** @return <code>true</code> if parent pom configuration panel is enable, and <code>false</code> otherwise */
    boolean isParentPomConfEnable();

    /**
     * Sets whether parent pom configuration is enabled.
     *
     * @param enable
     *         <code>true</code> to enable the button, <code>false</code> to disable it
     */
    void setParentPomConfEnable(boolean enable);

    /** @return inputted parent group id */
    @NotNull
    String getParentGroupID();

    /**
     * Changes content of parent group id field on view.
     *
     * @param groupID
     *         content that need to be inputted
     */
    void setParentGroupID(@NotNull String groupID);

    /** @return inputted parent artifact id */
    @NotNull
    String getParentArtifactID();

    /**
     * Changes content of parent artifact id field on view.
     *
     * @param artifactID
     *         content that need to be inputted
     */
    void setParentArtifactID(@NotNull String artifactID);

    /** @return inputted version */
    @NotNull
    String getParentVersion();

    /**
     * Changes content of parent version field on view.
     *
     * @param version
     *         content that need to be inputted
     */
    void setParentVersion(@NotNull String version);
}
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

import com.codenvy.ide.annotations.NotNull;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * The implementation of {@link CreateESBConfProjectView}.
 *
 * @author Andrey Plotnikov
 */
public class CreateESBConfProjectViewImpl extends Composite implements CreateESBConfProjectView {
    interface CreateESBConfProjectViewImplUiBinder extends UiBinder<Widget, CreateESBConfProjectViewImpl> {
    }

    @UiField
    TextBox  groupID;
    @UiField
    TextBox  artifactID;
    @UiField
    TextBox  version;
    @UiField
    CheckBox parentPomConf;
    @UiField
    TextBox  parentGroupID;
    @UiField
    TextBox  parentArtifactID;
    @UiField
    TextBox  parentVersion;

    private ActionDelegate delegate;

    @Inject
    public CreateESBConfProjectViewImpl(CreateESBConfProjectViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public String getGroupID() {
        return groupID.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setGroupID(@NotNull String groupID) {
        this.groupID.setText(groupID);
    }

    /** {@inheritDoc} */
    @Override
    public String getArtifactID() {
        return artifactID.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setArtifactID(@NotNull String artifactID) {
        this.artifactID.setText(artifactID);
    }

    /** {@inheritDoc} */
    @Override
    public String getVersion() {
        return version.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setVersion(@NotNull String version) {
        this.version.setText(version);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isParentPomConfEnable() {
        return parentPomConf.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public void setParentPomConfEnable(boolean enable) {
        parentPomConf.setValue(enable);

        parentArtifactID.setEnabled(enable);
        parentGroupID.setEnabled(enable);
        parentVersion.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public String getParentGroupID() {
        return parentGroupID.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setParentGroupID(@NotNull String groupID) {
        parentGroupID.setText(groupID);
    }

    /** {@inheritDoc} */
    @Override
    public String getParentArtifactID() {
        return parentArtifactID.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setParentArtifactID(@NotNull String artifactID) {
        parentArtifactID.setText(artifactID);
    }

    /** {@inheritDoc} */
    @Override
    public String getParentVersion() {
        return parentVersion.getText();
    }

    /** {@inheritDoc} */
    @Override
    public void setParentVersion(@NotNull String version) {
        parentVersion.setText(version);
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler({"groupID", "artifactID", "version", "parentGroupID", "parentArtifactID", "parentVersion"})
    public void onValueChanged(KeyUpEvent event) {
        delegate.onValueChanged();
    }

    @SuppressWarnings("UnusedParameters")
    @UiHandler("parentPomConf")
    public void onParentPomConfChanged(ClickEvent event) {
        delegate.onParentPomConfChanged();
    }
}
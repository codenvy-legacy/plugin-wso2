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
package com.codenvy.ide.client.editor;

import com.codenvy.ide.api.parts.PartStackUIResources;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.vectomatic.dom.svg.ui.SVGImage;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of WSO2 editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class WSO2EditorViewImpl extends AbstractView<WSO2EditorView.ActionDelegate> implements WSO2EditorView {

    @Singleton
    interface EditorViewImplUiBinder extends UiBinder<Widget, WSO2EditorViewImpl> {
    }

    @UiField
    SimpleLayoutPanel toolbar;
    @UiField
    SimpleLayoutPanel propertiesPanel;
    @UiField
    ScrollPanel       workspace;
    @UiField
    Button            hideBtn;
    @UiField
    Button            showBtn;
    @UiField
    FlowPanel         showButtonPanel;
    @UiField
    DockLayoutPanel   mainPanel;
    @UiField
    FlowPanel         mainPropertiesPanel;

    @Inject
    public WSO2EditorViewImpl(EditorViewImplUiBinder ourUiBinder, PartStackUIResources resources) {
        initWidget(ourUiBinder.createAndBindUi(this));

        SVGImage image = new SVGImage(resources.minimize());
        image.getElement().setAttribute("name", "workBenchIconMinimize");

        showBtn.getElement().setInnerHTML(image.toString());
        hideBtn.getElement().setInnerHTML(image.toString());
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getToolbarPanel() {
        return toolbar;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getPropertiesPanel() {
        return propertiesPanel;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public AcceptsOneWidget getWorkspacePanel() {
        return workspace;
    }

    /** {@inheritDoc} */
    @Override
    public void setVisiblePropertyPanel(boolean isVisible) {
        mainPanel.setWidgetHidden(mainPropertiesPanel, !isVisible);
        mainPanel.setWidgetHidden(showButtonPanel, isVisible);

        mainPanel.onResize();
    }

    @UiHandler("hideBtn")
    public void onHideButtonClicked(ClickEvent event) {
        delegate.onHidePanelButtonClicked();
    }

    @UiHandler("showBtn")
    public void onShowButtonClicked(ClickEvent event) {
        delegate.onShowPropertyButtonClicked();
    }

}
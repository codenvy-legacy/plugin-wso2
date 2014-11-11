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
package com.codenvy.ide.ext.wso2.client.editor;

import com.codenvy.ide.api.editor.AbstractEditorPresenter;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.layout.client.Layout;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * The implementation of {@link ESBConfEditorView}.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 * @author Valeriy Svydenko
 */
public class ESBConfEditorViewImpl extends Composite implements ESBConfEditorView {

    interface ESBConfEditorViewImplUiBinder extends UiBinder<Widget, ESBConfEditorViewImpl> {
    }

    private static final ESBConfEditorViewImplUiBinder uiBinder = GWT.create(ESBConfEditorViewImplUiBinder.class);

    private static final int DURATION = 200;

    @UiField
    Button          textEditorChoose;
    @UiField
    Button          graphicalEditorChoose;
    @UiField
    Button          associateEditorChoose;
    @UiField
    Button          horizontalBtn;
    @UiField
    Button          verticalBtn;
    @UiField
    FlowPanel       toolbarBtn;
    @UiField
    FlowPanel       showPropertyPanel;
    @UiField
    SimplePanel     graphicalEditorPanel;
    @UiField
    SimplePanel     textEditorPanel;
    @UiField
    DockLayoutPanel editorMainPanel;

    @UiField(provided = true)
    final LocalizationConstant locale;
    @UiField(provided = true)
    final WSO2Resources        resources;

    private ActionDelegate delegate;

    @Inject
    public ESBConfEditorViewImpl(LocalizationConstant locale, WSO2Resources resources) {
        this.locale = locale;
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));

        toolbarBtn.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onChangeToolbarVisibilityClicked();
            }
        }, ClickEvent.getType());

        showPropertyPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onPropertyButtonClicked();
            }
        }, ClickEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull ActionDelegate delegate) {
        this.delegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableSourceViewButton(boolean enable) {
        textEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableDesignViewButton(boolean enable) {
        graphicalEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void setEnableDualViewButton(boolean enable) {
        associateEditorChoose.setEnabled(enable);
    }

    /** {@inheritDoc} */
    @Override
    public void showSourceView() {
        showEditors(true, false);
    }

    /** {@inheritDoc} */
    @Override
    public void showDesignView() {
        showEditors(false, true);
    }

    /** {@inheritDoc} */
    @Override
    public void showDualView() {
        showEditors(true, true);
    }

    private void showEditors(boolean isTextEditorVisible, boolean isGraphicalEditorVisible) {
        editorMainPanel.setWidgetHidden(graphicalEditorPanel, !isGraphicalEditorVisible);

        int textEditorSize;

        if (isTextEditorVisible) {
            textEditorSize = isGraphicalEditorVisible ? 50 : 100;
        } else {
            textEditorSize = 0;
        }

        editorMainPanel.setWidgetSize(textEditorPanel, textEditorSize);

        editorMainPanel.animate(DURATION, new Layout.AnimationCallback() {
            @Override
            public void onAnimationComplete() {
                delegate.onEditorDOMChanged();
            }

            @Override
            public void onLayout(Layout.Layer layer, double progress) {
                // do nothing
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void addTextEditorWidget(@Nonnull EmbeddedTextEditorPresenter textEditor) {
        textEditor.go(textEditorPanel);
    }

    /** {@inheritDoc} */
    @Override
    public void addGraphicalEditorWidget(@Nonnull AbstractEditorPresenter graphicalEditor) {
        graphicalEditor.go(graphicalEditorPanel);
    }

    @UiHandler("textEditorChoose")
    public void onTextEditorButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onSourceViewButtonClicked();
    }

    @UiHandler("graphicalEditorChoose")
    public void onGraphicalEditorButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onDesignViewButtonClicked();
    }

    @UiHandler("associateEditorChoose")
    public void onAssociateEditorButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        delegate.onDualViewButtonClicked();
    }

    @UiHandler("horizontalBtn")
    public void onHorizontalButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        changeVisibilityOrientationButton(true);

        delegate.onHorizontalOrientationClicked();
    }

    @UiHandler("verticalBtn")
    public void onVerticalButtonClicked(@SuppressWarnings("UnusedParameters") ClickEvent event) {
        changeVisibilityOrientationButton(false);

        delegate.onVerticalOrientationClicked();
    }

    private void changeVisibilityOrientationButton(boolean isHorizontalClicked) {
        horizontalBtn.setVisible(!isHorizontalClicked);
        verticalBtn.setVisible(isHorizontalClicked);
    }

}
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
import com.codenvy.ide.ext.wso2.client.editor.ESBConfEditorView.ActionDelegate;
import com.codenvy.ide.jseditor.client.texteditor.EmbeddedTextEditorPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.googlecode.gwt.test.Mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;

import static com.google.gwt.layout.client.Layout.AnimationCallback;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(GwtMockitoTestRunner.class)
public class ESBConfEditorViewImplTest {

    @Captor
    private ArgumentCaptor<ClickHandler>      clickHandlerCaptor;
    @Captor
    private ArgumentCaptor<AnimationCallback> animationCallbackCaptor;

    @Mock
    private EmbeddedTextEditorPresenter embeddedTextEditorPresenter;
    @Mock
    private AbstractEditorPresenter     editorPresenter;
    @Mock
    private LocalizationConstant        locale;
    @Mock
    private WSO2Resources               resources;
    @Mock
    private DockLayoutPanel             editorMainPanel;
    @Mock
    private ActionDelegate              delegate;
    @Mock
    private ClickEvent                  clickEvent;
    @InjectMocks
    private ESBConfEditorViewImpl       view;

    @Before
    public void setUp() throws Exception {
        view.setDelegate(delegate);
    }

    @Test
    public void constructorShouldBeDone() throws Exception {
        verify(view.toolbarBtn).addDomHandler(any(ClickHandler.class), eq(ClickEvent.getType()));
        verify(view.showPropertyPanel).addDomHandler(any(ClickHandler.class), eq(ClickEvent.getType()));
    }

    @Test
    public void enableSourceViewButtonShouldBeSet() throws Exception {
        view.setEnableSourceViewButton(true);

        verify(view.textEditorChoose).setEnabled(true);
    }

    @Test
    public void disableSourceButtonShouldBeSet() throws Exception {
        view.setEnableSourceViewButton(false);

        verify(view.textEditorChoose).setEnabled(false);
    }

    @Test
    public void enableDesignViewButtonShouldBeSet() throws Exception {
        view.setEnableDesignViewButton(true);

        verify(view.graphicalEditorChoose).setEnabled(true);
    }

    @Test
    public void disableDesignButtonShouldBeSet() throws Exception {
        view.setEnableDesignViewButton(false);

        verify(view.graphicalEditorChoose).setEnabled(false);
    }

    @Test
    public void enableDualViewButtonShouldBeSet() throws Exception {
        view.setEnableDualViewButton(true);

        verify(view.associateEditorChoose).setEnabled(true);
    }

    @Test
    public void disableDualButtonShouldBeSet() throws Exception {
        view.setEnableDualViewButton(false);

        verify(view.associateEditorChoose).setEnabled(false);
    }

    @Test
    public void sourceEditorViewShouldBeShown() throws Exception {
        view.showSourceView();

        verify(view.editorMainPanel).setWidgetHidden(view.graphicalEditorPanel, true);
        verify(view.editorMainPanel).setWidgetSize(view.textEditorPanel, 100);
        verify(view.editorMainPanel).animate(eq(200), any(AnimationCallback.class));
    }

    @Test
    public void designEditorViewShouldBeShown() throws Exception {
        view.showDesignView();

        verify(view.editorMainPanel).setWidgetHidden(view.graphicalEditorPanel, false);
        verify(view.editorMainPanel).setWidgetSize(view.textEditorPanel, 0);
        verify(view.editorMainPanel).animate(eq(200), any(AnimationCallback.class));
    }

    @Test
    public void dualEditorViewShouldBeShown() throws Exception {
        view.showDualView();

        verify(view.editorMainPanel).setWidgetHidden(view.graphicalEditorPanel, false);
        verify(view.editorMainPanel).setWidgetSize(view.textEditorPanel, 50);
        verify(view.editorMainPanel).animate(eq(200), any(AnimationCallback.class));
    }

    @Test
    public void textEditorWidgetShouldBeShown() throws Exception {
        view.addTextEditorWidget(embeddedTextEditorPresenter);

        verify(embeddedTextEditorPresenter).go(view.textEditorPanel);
    }

    @Test
    public void graphicEditorWidgetShouldBeShown() throws Exception {
        view.addGraphicalEditorWidget(editorPresenter);

        verify(editorPresenter).go(view.graphicalEditorPanel);
    }

    @Test
    public void textEditorButtonShouldBeClicked() throws Exception {
        view.onTextEditorButtonClicked(clickEvent);

        verify(delegate).onSourceViewButtonClicked();
    }

    @Test
    public void graphicalEditorButtonShouldBeClicked() throws Exception {
        view.onGraphicalEditorButtonClicked(clickEvent);

        verify(delegate).onDesignViewButtonClicked();
    }

    @Test
    public void designEditorButtonShouldBeClicked() throws Exception {
        view.onAssociateEditorButtonClicked(clickEvent);

        verify(delegate).onDualViewButtonClicked();
    }

    @Test
    public void horizontalButtonShouldBeClicked() throws Exception {
        view.onHorizontalButtonClicked(clickEvent);

        verify(view.horizontalBtn).setVisible(false);
        verify(view.verticalBtn).setVisible(true);
        verify(delegate).onHorizontalOrientationClicked();
    }

    @Test
    public void verticalButtonShouldBeClicked() throws Exception {
        view.onVerticalButtonClicked(clickEvent);

        verify(view.horizontalBtn).setVisible(true);
        verify(view.verticalBtn).setVisible(false);
        verify(delegate).onVerticalOrientationClicked();
    }

    @Test
    public void toolbarButtonCallBackShouldBeDone() throws Exception {
        verify(view.toolbarBtn).addDomHandler(clickHandlerCaptor.capture(), eq(ClickEvent.getType()));

        ClickHandler clickHandler = clickHandlerCaptor.getValue();
        clickHandler.onClick(clickEvent);

        verify(delegate).onChangeToolbarVisibilityClicked();
    }

    @Test
    public void propertyPanelCallBackShouldBeDone() throws Exception {
        verify(view.showPropertyPanel).addDomHandler(clickHandlerCaptor.capture(), eq(ClickEvent.getType()));

        ClickHandler clickHandler = clickHandlerCaptor.getValue();
        clickHandler.onClick(clickEvent);

        verify(delegate).onPropertyButtonClicked();
    }

    @Test
    public void animationCallBackShouldBeDone() throws Exception {
        view.showSourceView();

        verify(view.editorMainPanel).animate(eq(200), animationCallbackCaptor.capture());

        AnimationCallback animationCallback = animationCallbackCaptor.getValue();
        animationCallback.onAnimationComplete();

        verify(delegate).onEditorDOMChanged();
    }
}
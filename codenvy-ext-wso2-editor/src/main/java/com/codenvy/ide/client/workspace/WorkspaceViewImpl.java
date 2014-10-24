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
package com.codenvy.ide.client.workspace;

import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.mvp.AbstractView;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of the workspace.
 *
 * @author Andrey Plotnikov
 */
public class WorkspaceViewImpl extends AbstractView<WorkspaceView.ActionDelegate> implements WorkspaceView, RequiresResize {

    @Singleton
    interface WorkspaceViewImplUiBinder extends UiBinder<Widget, WorkspaceViewImpl> {
    }

    @UiField
    FlowPanel element;

    @Inject
    public WorkspaceViewImpl(WorkspaceViewImplUiBinder ourUiBinder) {
        initWidget(ourUiBinder.createAndBindUi(this));

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                delegate.onWindowResize();
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void onResize() {
        delegate.onWindowResize();
    }

    /** {@inheritDoc} */
    @Override
    public void setElement(@Nonnull ElementPresenter elementPresenter) {
        element.clear();
        element.add(elementPresenter.getView());
    }

    /** {@inheritDoc} */
    @Override
    public int getHeight() {
        // we have to use parent size because element size isn't full. Minus margin size and border size.
        return getParent().getElement().getOffsetHeight() - 22;
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return getElement().getOffsetWidth();
    }

}
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
package com.codenvy.ide.client.toolbar;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.toolbar.group.ToolbarGroupPresenter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of toolbar.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class ToolbarViewImpl extends AbstractView<ToolbarView.ActionDelegate> implements ToolbarView {

    @Singleton
    interface ToolbarViewImplUiBinder extends UiBinder<Widget, ToolbarViewImpl> {
    }

    @UiField
    FlowPanel       mainPanel;
    @UiField
    ScrollPanel     scrollPanel;
    @UiField(provided = true)
    EditorResources res;

    @Inject
    public ToolbarViewImpl(ToolbarViewImplUiBinder ourUiBinder, EditorResources resources) {
        this.res = resources;

        initWidget(ourUiBinder.createAndBindUi(this));

        scrollPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onMainPanelClicked();

                event.stopPropagation();
            }
        }, ClickEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void addGroup(@Nonnull ToolbarGroupPresenter toolbarGroup) {
        mainPanel.add(toolbarGroup.getView());
    }

}
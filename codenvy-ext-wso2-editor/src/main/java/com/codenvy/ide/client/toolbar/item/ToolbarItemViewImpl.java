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
package com.codenvy.ide.client.toolbar.item;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Provides a graphical representation of the toolbar item.
 *
 * @author Andrey Plotnikov
 */
public class ToolbarItemViewImpl extends ToolbarItemView {

    @Singleton
    interface ToolbarItemViewImplUiBinder extends UiBinder<Widget, ToolbarItemViewImpl> {
    }

    @UiField
    Image     icon;
    @UiField
    Label     title;
    @UiField
    FlowPanel panel;

    @AssistedInject
    public ToolbarItemViewImpl(ToolbarItemViewImplUiBinder ourUiBinder,
                               @Assisted("title") String title,
                               @Assisted("tooltip") String tooltip,
                               @Assisted ImageResource icon) {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.icon.setResource(icon);
        this.title.setText(title);
        this.panel.setTitle(tooltip);

        this.panel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onItemClicked();

                event.stopPropagation();
            }
        }, ClickEvent.getType());
    }

}
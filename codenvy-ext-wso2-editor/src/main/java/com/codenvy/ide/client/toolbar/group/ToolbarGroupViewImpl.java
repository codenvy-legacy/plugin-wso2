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
package com.codenvy.ide.client.toolbar.group;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.toolbar.item.ToolbarItemPresenter;
import com.codenvy.ide.util.AnimationController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.Assisted;

import org.vectomatic.dom.svg.ui.SVGImage;

import javax.annotation.Nonnull;

/**
 * Provides a graphical representation of the toolbar group.
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class ToolbarGroupViewImpl extends AbstractView<ToolbarGroupView.ActionDelegate> implements ToolbarGroupView {

    @Singleton
    interface ToolbarGroupViewImplUiBinder extends UiBinder<Widget, ToolbarGroupViewImpl> {
    }

    @UiField
    FlowPanel         mainPanel;
    @UiField
    SimpleLayoutPanel icon;
    @UiField
    Label             title;
    @UiField
    FlowPanel         itemsPanel;

    private final AnimationController animator;
    private final EditorResources     resources;

    @Inject
    public ToolbarGroupViewImpl(ToolbarGroupViewImplUiBinder ourUiBinder, EditorResources resources, @Assisted String title) {
        this.resources = resources;

        initWidget(ourUiBinder.createAndBindUi(this));

        SVGImage image = new SVGImage(resources.expandIcon());
        this.icon.getElement().setInnerHTML(image.toString());
        this.title.setText(title);

        defaultIcon();

        mainPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onItemClicked();

                event.stopPropagation();
            }
        }, ClickEvent.getType());

        animator = new AnimationController.Builder().setCollapse(true).setFade(true).build();
        animator.showWithoutAnimating((elemental.dom.Element)itemsPanel.getElement());
        animator.hideWithoutAnimating((elemental.dom.Element)itemsPanel.getElement());
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleItemsPanel(boolean visible) {
        itemsPanel.setVisible(visible);
    }

    /** {@inheritDoc} */
    @Override
    public void addItem(@Nonnull ToolbarItemPresenter toolbarItem) {
        itemsPanel.add(toolbarItem.getView());
    }

    /** {@inheritDoc} */
    @Override
    public void rotateIcon() {
        icon.removeStyleName(resources.editorCSS().normalImage());
        icon.addStyleName(resources.editorCSS().expandedImage());
    }

    /** {@inheritDoc} */
    @Override
    public void defaultIcon() {
        icon.removeStyleName(resources.editorCSS().expandedImage());
        icon.addStyleName(resources.editorCSS().normalImage());
    }

    /** {@inheritDoc} */
    @Override
    public void expandOrCollapse(boolean expanded) {
        if (!expanded) {
            animator.show((elemental.dom.Element)itemsPanel.getElement());
            rotateIcon();
        } else {
            animator.hide((elemental.dom.Element)itemsPanel.getElement());
            defaultIcon();
        }
    }

}
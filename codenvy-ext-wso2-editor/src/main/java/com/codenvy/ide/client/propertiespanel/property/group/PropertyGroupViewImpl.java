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
package com.codenvy.ide.client.propertiespanel.property.group;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.mvp.AbstractView;
import com.codenvy.ide.client.propertiespanel.property.general.AbstractPropertyPresenter;
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
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class PropertyGroupViewImpl extends AbstractView<PropertyGroupView.ActionDelegate> implements PropertyGroupView {

    @Singleton
    interface PropertyGroupViewImplUiBinder extends UiBinder<Widget, PropertyGroupViewImpl> {
    }

    @UiField
    FlowPanel         mainPanel;
    @UiField
    Label             title;
    @UiField
    SimpleLayoutPanel icon;
    @UiField
    FlowPanel         groupNamePanel;
    @UiField
    FlowPanel         propertiesPanel;
    @UiField(provided = true)
    final EditorResources res;

    private final AnimationController animator;

    @Inject
    public PropertyGroupViewImpl(PropertyGroupViewImplUiBinder ourUiBinder,
                                 EditorResources resources,
                                 @Assisted String title) {
        res = resources;

        initWidget(ourUiBinder.createAndBindUi(this));

        SVGImage image = new SVGImage(resources.expandIcon());
        this.icon.getElement().setInnerHTML(image.toString());
        this.title.setText(title);

        mainPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                delegate.onPropertyGroupClicked();

                event.stopPropagation();
            }
        }, ClickEvent.getType());

        propertiesPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                event.stopPropagation();
            }
        }, ClickEvent.getType());

        animator = new AnimationController.Builder().setCollapse(true).setFade(true).build();
        animator.hideWithoutAnimating((elemental.dom.Element)propertiesPanel.getElement());
        animator.showWithoutAnimating((elemental.dom.Element)propertiesPanel.getElement());

        collapsePropertyGroup();
    }

    /** {@inheritDoc} */
    @Override
    public void expendPropertyGroup() {
        animator.hide((elemental.dom.Element)propertiesPanel.getElement());

        icon.removeStyleName(res.editorCSS().normalImage());
        icon.addStyleName(res.editorCSS().expandedImage());
    }

    /** {@inheritDoc} */
    @Override
    public void collapsePropertyGroup() {
        animator.show((elemental.dom.Element)propertiesPanel.getElement());

        icon.removeStyleName(res.editorCSS().expandedImage());
        icon.addStyleName(res.editorCSS().normalImage());
    }

    /** {@inheritDoc} */
    @Override
    public void setBorderVisible(boolean visible) {
        String style = res.editorCSS().groupBottomBorder();

        if (visible) {
            addStyleName(style);
        } else {
            removeStyleName(style);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addProperty(@Nonnull AbstractPropertyPresenter property) {
        propertiesPanel.add(property.getView());
    }

    /** {@inheritDoc} */
    @Override
    public void removeProperty(@Nonnull AbstractPropertyPresenter property) {
        propertiesPanel.remove(property.getView());
    }

    /** {@inheritDoc} */
    @Override
    public void setTitleVisible(boolean isVisible) {
        groupNamePanel.setVisible(isVisible);
    }

}
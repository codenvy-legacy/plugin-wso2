/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.client.propertiespanel.sequence;

import com.codenvy.ide.client.elements.Sequence;
import com.codenvy.ide.client.propertiespanel.AbstractPropertiesPanel;
import com.codenvy.ide.client.propertytypes.PropertyTypeManager;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Andrey Plotnikov
 */
public class SequencePropertiesPanelPresenter extends AbstractPropertiesPanel<Sequence>
        implements SequencePropertiesPanelView.ActionDelegate {

    @Inject
    public SequencePropertiesPanelPresenter(SequencePropertiesPanelView view, PropertyTypeManager propertyTypeManager) {
        super(view, propertyTypeManager);
    }

    /** {@inheritDoc} */
    @Override
    public void onReferringSequenceTypeChanged() {
        element.setReferringSequenceType(((SequencePropertiesPanelView)view).getReferringSequenceType());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onStaticReferenceKeyChanged() {
        element.setStaticReferenceKey(((SequencePropertiesPanelView)view).getStaticReferenceKey());
        notifyListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void go(@Nonnull AcceptsOneWidget container) {
        super.go(container);

        ((SequencePropertiesPanelView)view).setReferringSequenceType(propertyTypeManager.getValuesOfTypeByName("ReceivingSequenceType"));
        ((SequencePropertiesPanelView)view).selectReferringSequenceType(element.getReferringSequenceType());
        ((SequencePropertiesPanelView)view).setStaticReferenceKey(element.getStaticReferenceKey());
    }

}
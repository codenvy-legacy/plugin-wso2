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
package com.codenvy.ide.client.propertiespanel.call;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(CallPropertiesPanelViewImpl.class)
public abstract class CallPropertiesPanelView extends AbstractView<CallPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onEndpointTypeChanged();

        void onDescriptionChanged();

    }

    public abstract String getEndpointType();

    public abstract void selectEndpointType(String endpointType);

    public abstract void setEndpointType(List<String> endpointType);

    public abstract String getDescription();

    public abstract void setDescription(String description);

}
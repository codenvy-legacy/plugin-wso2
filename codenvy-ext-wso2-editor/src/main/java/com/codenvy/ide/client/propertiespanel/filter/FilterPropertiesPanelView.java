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
package com.codenvy.ide.client.propertiespanel.filter;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;

import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(FilterPropertiesPanelViewImpl.class)
public abstract class FilterPropertiesPanelView extends AbstractView<FilterPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onConditionTypeChanged();

        void onSourceChanged();

        void onRegularExpressionChanged();

    }

    public abstract String getConditionType();

    public abstract void selectConditionType(String conditionType);

    public abstract void setConditionType(List<String> conditionType);

    public abstract String getSource();

    public abstract void setSource(String source);

    public abstract String getRegularExpression();

    public abstract void setRegularExpression(String regularExpression);

}
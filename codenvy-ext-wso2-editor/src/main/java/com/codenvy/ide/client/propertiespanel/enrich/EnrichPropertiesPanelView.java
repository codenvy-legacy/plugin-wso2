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
package com.codenvy.ide.client.propertiespanel.enrich;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(EnrichPropertiesPanelViewImpl.class)
public abstract class EnrichPropertiesPanelView extends AbstractView<EnrichPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onCloneSourceChanged();

        void onSourceTypeChanged();

        void onSourceXpathChanged();

        void onTargetActionChanged();

        void onTargetTypeChanged();

        void onTargetXpathChanged();

        void onDescriptionChanged();

    }

    public abstract String getCloneSource();

    public abstract void selectCloneSource(String cloneSource);

    public abstract void setCloneSource(List<String> cloneSource);

    public abstract String getSourceType();

    public abstract void selectSourceType(String sourceType);

    public abstract void setSourceType(List<String> sourceType);

    public abstract String getSourceXpath();

    public abstract void setSourceXpath(String sourceXpath);

    public abstract String getTargetAction();

    public abstract void selectTargetAction(String targetAction);

    public abstract void setTargetAction(List<String> targetAction);

    public abstract String getTargetType();

    public abstract void selectTargetType(String targetType);

    public abstract void setTargetType(List<String> targetType);

    public abstract String getTargetXpath();

    public abstract void setTargetXpath(String targetXpath);

    public abstract String getDescription();

    public abstract void setDescription(String description);

}
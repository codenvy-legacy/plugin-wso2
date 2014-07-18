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
package com.codenvy.ide.client.propertiespanel.payloadfactory;

import com.codenvy.ide.client.mvp.AbstractView;
import com.google.inject.ImplementedBy;
import java.util.List;

/**
 * @author Andrey Plotnikov
 */
@ImplementedBy(PayloadFactoryPropertiesPanelViewImpl.class)
public abstract class PayloadFactoryPropertiesPanelView extends AbstractView<PayloadFactoryPropertiesPanelView.ActionDelegate> {

    public interface ActionDelegate extends AbstractView.ActionDelegate {

        void onPayloadFormatChanged();

        void onFormatChanged();

        void onArgsChanged();

        void onMediaTypeChanged();

        void onDescriptionChanged();

    }

    public abstract String getPayloadFormat();

    public abstract void selectPayloadFormat(String payloadFormat);

    public abstract void setPayloadFormat(List<String> payloadFormat);

    public abstract String getFormat();

    public abstract void setFormat(String format);

    public abstract String getArgs();

    public abstract void setArgs(String args);

    public abstract String getMediaType();

    public abstract void selectMediaType(String mediaType);

    public abstract void setMediaType(List<String> mediaType);

    public abstract String getDescription();

    public abstract void setDescription(String description);

}
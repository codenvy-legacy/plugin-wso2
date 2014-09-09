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
package com.codenvy.ide.client.elements.connectors.salesforce;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.connectors.AbstractConnector;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.Inline;
import static com.codenvy.ide.client.elements.connectors.AbstractConnector.ParameterEditorType.NamespacedPropertyEditor;

/**
 * The Class describes SendEmailMessageMessage connector for Salesforce group connectors. Also the class contains the business logic
 * that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving.
 *
 * @author Valeriy Svydenko
 */
public class SendEmailMessage extends AbstractConnector {

    public static final String ELEMENT_NAME       = "SendEmailMessage";
    public static final String SERIALIZATION_NAME = "salesforce.sendEmailMessage";
    public static final String SEND_EMAIL_MESSAGE = "sendEmailMessage";

    private static final List<String> PROPERTIES = Arrays.asList(SEND_EMAIL_MESSAGE);

    private String           sendEmailMessage;
    private String           sendEmailMessageInline;
    private Array<NameSpace> sendEmailMessageNameSpaces;

    @Inject
    public SendEmailMessage(EditorResources resources, Provider<Branch> branchProvider, MediatorCreatorsManager mediatorCreatorsManager) {
        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, false, true, resources, branchProvider, mediatorCreatorsManager);

        sendEmailMessage = "";
        sendEmailMessageInline = "";

        sendEmailMessageNameSpaces = Collections.createArray();
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    protected String serializeProperties() {
        return Inline.equals(parameterEditorType) ? prepareProperties(sendEmailMessageInline) : prepareProperties(sendEmailMessage);
    }

    @Nonnull
    private String prepareProperties(@Nonnull String sendEmailMessage) {
        return !sendEmailMessage.isEmpty() ? '<' + SEND_EMAIL_MESSAGE + '>' + sendEmailMessage + "</" + SEND_EMAIL_MESSAGE + '>' : "";
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();
        boolean isInline = Inline.equals(parameterEditorType);

        switch (nodeName) {
            case SEND_EMAIL_MESSAGE:
                if (isInline) {
                    sendEmailMessageInline = nodeValue;
                } else {
                    sendEmailMessage = nodeValue;

                    parameterEditorType = NamespacedPropertyEditor;
                }
                break;
        }
    }

    @Nonnull
    public String getSendEmailMessage() {
        return sendEmailMessage;
    }

    public void setSendEmailMessage(@Nullable String sendEmail) {
        this.sendEmailMessage = sendEmail;
    }

    @Nonnull
    public String getSendEmailMessageInline() {
        return sendEmailMessageInline;
    }

    public void setSendEmailMessageInline(@Nonnull String sendEmailInline) {
        this.sendEmailMessageInline = sendEmailInline;
    }

    @Nonnull
    public Array<NameSpace> getSendEmailMessageNameSpaces() {
        return sendEmailMessageNameSpaces;
    }

    public void setSendEmailNameSpaces(@Nonnull Array<NameSpace> searchStringMessageNameSpaces) {
        this.sendEmailMessageNameSpaces = searchStringMessageNameSpaces;
    }
}

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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.enrich.Enrich;
import com.codenvy.ide.client.elements.log.Log;
import com.codenvy.ide.client.elements.payload.PayloadFactory;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 */
public class Switch extends RootElement {
    public static final String ELEMENT_NAME       = "Switch";
    public static final String SERIALIZATION_NAME = "switch";

    private static final String SOURCE_XPATH_PROPERTY_NAME       = "SourceXpath";
    private static final String CASE_BRANCHES_TYPE_PROPERTY_NAME = "CaseBranches";

    private static final String CASE_TITLE         = "Case";
    private static final String DEFAULT_CASE_TITLE = "Default";

    private static final String CASE_SERIALIZATION_NAME    = "case";
    private static final String DEFAULT_SERIALIZATION_NAME = "default";

    private static final List<String> PROPERTIES = Arrays.asList(SOURCE_XPATH_PROPERTY_NAME,
                                                                 CASE_BRANCHES_TYPE_PROPERTY_NAME);
    private static final List<String> COMPONENTS = Arrays.asList(Log.ELEMENT_NAME,
                                                                 Property.ELEMENT_NAME,
                                                                 PayloadFactory.ELEMENT_NAME,
                                                                 Send.ELEMENT_NAME,
                                                                 Header.ELEMENT_NAME,
                                                                 Respond.ELEMENT_NAME,
                                                                 Filter.ELEMENT_NAME,
                                                                 Switch.ELEMENT_NAME,
                                                                 Sequence.ELEMENT_NAME,
                                                                 Enrich.ELEMENT_NAME,
                                                                 LoopBack.ELEMENT_NAME,
                                                                 CallTemplate.ELEMENT_NAME,
                                                                 Call.ELEMENT_NAME);

    private String sourceXpath;
    private String caseBranches;

    private Branch firstBranch;
    private Branch defaultBranch;

    @Inject
    public Switch(EditorResources resources,
                  Provider<Branch> branchProvider,
                  Provider<Log> logProvider,
                  Provider<Enrich> enrichProvider,
                  Provider<Filter> filterProvider,
                  Provider<Header> headerProvider,
                  Provider<Call> callProvider,
                  Provider<CallTemplate> callTemplateProvider,
                  Provider<LoopBack> loopBackProvider,
                  Provider<PayloadFactory> payloadFactoryProvider,
                  Provider<Property> propertyProvider,
                  Provider<Respond> respondProvider,
                  Provider<Send> sendProvider,
                  Provider<Sequence> sequenceProvider,
                  Provider<Switch> switchProvider) {
        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              resources,
              branchProvider,
              true,
              true,
              logProvider,
              enrichProvider,
              filterProvider,
              headerProvider,
              callProvider,
              callTemplateProvider,
              loopBackProvider,
              payloadFactoryProvider,
              propertyProvider,
              respondProvider,
              sendProvider,
              sequenceProvider,
              switchProvider);

        sourceXpath = "default/xpath";
        caseBranches = "enter_case_branches";

        components.addAll(COMPONENTS);

        firstBranch = branchProvider.get();
        firstBranch.setParent(this);
        firstBranch.setTitle(CASE_TITLE);
        firstBranch.setName(CASE_SERIALIZATION_NAME);

        defaultBranch = branchProvider.get();
        defaultBranch.setParent(this);
        defaultBranch.setTitle(DEFAULT_CASE_TITLE);
        defaultBranch.setName(DEFAULT_SERIALIZATION_NAME);
    }

    /** {@inheritDoc} */
    @Override
    public void setBranchesAmount(int amount) {
        if (amount < 2) {
            return;
        }

        super.setBranchesAmount(amount - 2);

        for (Branch branch : branches) {
            branch.setTitle(CASE_TITLE);
            branch.setName(CASE_SERIALIZATION_NAME);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getBranchesAmount() {
        return super.getBranchesAmount() + 2;
    }

    /** {@inheritDoc} */
    @Nonnull
    @Override
    public List<Branch> getBranches() {
        List<Branch> branches = new ArrayList<>();

        branches.add(firstBranch);
        branches.addAll(super.getBranches());
        branches.add(defaultBranch);

        return branches;
    }

    @Nullable
    public String getSourceXpath() {
        return sourceXpath;
    }

    public void setSourceXpath(@Nullable String sourceXpath) {
        this.sourceXpath = sourceXpath;
    }

    @Nullable
    public String getCaseBranches() {
        return caseBranches;
    }

    public void setCaseBranches(@Nullable String caseBranches) {
        this.caseBranches = caseBranches;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        return "sourceXpath=\"" + sourceXpath + "\" " +
               "caseBranches=\"" + caseBranches + "\" ";
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        super.deserialize(node);

        firstBranch = branches.remove(0);
        defaultBranch = branches.remove(branches.size() - 1);
    }

    /** {@inheritDoc} */
    @Override
    public void applyProperty(@Nonnull Node node) {
        String nodeName = node.getNodeName();
        String nodeValue = node.getChildNodes().item(0).getNodeValue();

        switch (nodeName) {
            case SOURCE_XPATH_PROPERTY_NAME:
                sourceXpath = String.valueOf(nodeValue);
                break;

            case CASE_BRANCHES_TYPE_PROPERTY_NAME:
                caseBranches = String.valueOf(nodeValue);
                break;

            default:
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.switch_mediator();
    }

}
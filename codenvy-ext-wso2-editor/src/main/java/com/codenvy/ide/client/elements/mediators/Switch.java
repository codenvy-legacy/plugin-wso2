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
package com.codenvy.ide.client.elements.mediators;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.connectors.salesforce.Create;
import com.codenvy.ide.client.elements.connectors.salesforce.Delete;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeGlobal;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeSubject;
import com.codenvy.ide.client.elements.connectors.salesforce.DescribeSubjects;
import com.codenvy.ide.client.elements.connectors.salesforce.EmptyRecycleBin;
import com.codenvy.ide.client.elements.connectors.salesforce.GetUserInformation;
import com.codenvy.ide.client.elements.connectors.salesforce.Init;
import com.codenvy.ide.client.elements.connectors.salesforce.LogOut;
import com.codenvy.ide.client.elements.connectors.salesforce.Query;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryAll;
import com.codenvy.ide.client.elements.connectors.salesforce.QueryMore;
import com.codenvy.ide.client.elements.connectors.salesforce.ResetPassword;
import com.codenvy.ide.client.elements.connectors.salesforce.Retrieve;
import com.codenvy.ide.client.elements.connectors.salesforce.Search;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmail;
import com.codenvy.ide.client.elements.connectors.salesforce.SendEmailMessage;
import com.codenvy.ide.client.elements.connectors.salesforce.SetPassword;
import com.codenvy.ide.client.elements.connectors.salesforce.Undelete;
import com.codenvy.ide.client.elements.connectors.salesforce.Update;
import com.codenvy.ide.client.elements.connectors.salesforce.Upset;
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.elements.mediators.enrich.Enrich;
import com.codenvy.ide.client.elements.mediators.log.Log;
import com.codenvy.ide.client.elements.mediators.payload.PayloadFactory;
import com.codenvy.ide.client.managers.MediatorCreatorsManager;
import com.codenvy.ide.collections.Array;
import com.codenvy.ide.collections.Collections;
import com.codenvy.ide.util.StringUtils;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.PREFIX;

/**
 * The class which describes state of Switch mediator and also has methods for changing it. Also the class contains the business
 * logic that allows to display serialization representation depending of the current state of element. Deserelization mechanism allows to
 * restore the condition of the element when you open ESB project after saving. Switch mediator may contains some branches which may
 * include an elements.By default Switch mediator contains two branches 'case' and 'default'.
 * For more information about Property mediator go to
 * <a href=" https://docs.wso2.com/display/ESB460/Property+Mediator"> https://docs.wso2.com/display/ESB460/Property+Mediator</a>
 *
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public class Switch extends AbstractElement {
    public static final String ELEMENT_NAME       = "Switch";
    public static final String SERIALIZATION_NAME = "switch";

    public static final String REGEXP_ATTRIBUTE_NAME          = "regex";
    public static final String REGEXP_ATTRIBUTE_DEFAULT_VALUE = ".*+";

    private static final String SOURCE_ATTRIBUTE_NAME = "source";

    private static final String CASE_TITLE         = "Case";
    public static final  String DEFAULT_CASE_TITLE = "Default";

    private static final String CASE_SERIALIZATION_NAME    = "case";
    private static final String DEFAULT_SERIALIZATION_NAME = "default";

    private static final List<String> PROPERTIES = java.util.Collections.emptyList();
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
                                                                 Call.ELEMENT_NAME,
                                                                 Init.ELEMENT_NAME,
                                                                 Create.ELEMENT_NAME,
                                                                 Update.ELEMENT_NAME,
                                                                 Delete.ELEMENT_NAME,
                                                                 EmptyRecycleBin.ELEMENT_NAME,
                                                                 LogOut.ELEMENT_NAME,
                                                                 GetUserInformation.ELEMENT_NAME,
                                                                 Delete.ELEMENT_NAME,
                                                                 DescribeGlobal.ELEMENT_NAME,
                                                                 DescribeSubject.ELEMENT_NAME,
                                                                 DescribeSubjects.ELEMENT_NAME,
                                                                 Query.ELEMENT_NAME,
                                                                 QueryAll.ELEMENT_NAME,
                                                                 QueryMore.ELEMENT_NAME,
                                                                 ResetPassword.ELEMENT_NAME,
                                                                 Retrieve.ELEMENT_NAME,
                                                                 Search.ELEMENT_NAME,
                                                                 SendEmail.ELEMENT_NAME,
                                                                 SendEmailMessage.ELEMENT_NAME,
                                                                 SetPassword.ELEMENT_NAME,
                                                                 Undelete.ELEMENT_NAME,
                                                                 Upset.ELEMENT_NAME);

    private final Provider<NameSpace> nameSpaceProvider;

    private String           sourceXpath;
    private Array<NameSpace> nameSpaces;

    private Branch firstBranch;
    private Branch defaultBranch;

    @Inject
    public Switch(EditorResources resources,
                  Provider<Branch> branchProvider,
                  MediatorCreatorsManager mediatorCreatorsManager,
                  Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME, ELEMENT_NAME, SERIALIZATION_NAME, PROPERTIES, true, true, resources, branchProvider, mediatorCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        sourceXpath = "default/xpath";
        nameSpaces = Collections.createArray();

        components.addAll(COMPONENTS);

        firstBranch = branchProvider.get();
        firstBranch.setParent(this);
        firstBranch.setTitle(CASE_TITLE);
        firstBranch.setName(CASE_SERIALIZATION_NAME);
        firstBranch.addAttribute(REGEXP_ATTRIBUTE_NAME, REGEXP_ATTRIBUTE_DEFAULT_VALUE);

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
            branch.addAttribute(REGEXP_ATTRIBUTE_NAME, REGEXP_ATTRIBUTE_DEFAULT_VALUE);
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

    /** @return source xpath of switch mediator */
    @Nullable
    public String getSourceXpath() {
        return sourceXpath;
    }

    /**
     * Sets source xpath parameter to switch mediator.
     *
     * @param sourceXpath
     *         value which need to set to element
     */
    public void setSourceXpath(@Nullable String sourceXpath) {
        this.sourceXpath = sourceXpath;
    }

    /** @return namespaces which contain in switch */
    @Nonnull
    public Array<NameSpace> getNameSpaces() {
        return nameSpaces;
    }

    /**
     * Changes list of name spaces.
     *
     * @param nameSpaces
     *         list of name spaces which needs to set in element
     */
    public void setNameSpaces(@Nonnull Array<NameSpace> nameSpaces) {
        this.nameSpaces = nameSpaces;
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        StringBuilder spaces = new StringBuilder();

        for (NameSpace nameSpace : nameSpaces.asIterable()) {
            spaces.append(nameSpace.toString()).append(' ');
        }

        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put(SOURCE_ATTRIBUTE_NAME, sourceXpath);

        return spaces + convertPropertiesToXMLFormat(attributes);
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
    protected void applyAttributes(@Nonnull Node node) {
        nameSpaces.clear();

        NamedNodeMap attributeMap = node.getAttributes();

        for (int i = 0; i < attributeMap.getLength(); i++) {
            Node attributeNode = attributeMap.item(i);

            String nodeValue = attributeNode.getNodeValue();
            String nodeName = attributeNode.getNodeName();

            switch (nodeName) {
                case SOURCE_ATTRIBUTE_NAME:
                    sourceXpath = String.valueOf(nodeValue);
                    break;

                default:
                    if (StringUtils.startsWith(PREFIX, nodeName, true)) {
                        String name = StringUtils.trimStart(nodeName, PREFIX + ':');

                        NameSpace nameSpace = nameSpaceProvider.get();

                        nameSpace.setPrefix(name);
                        nameSpace.setUri(nodeValue);

                        nameSpaces.add(nameSpace);
                    }
            }
        }
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public ImageResource getIcon() {
        return resources.switchMediator();
    }

}
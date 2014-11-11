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
import com.codenvy.ide.client.elements.AbstractElement;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.NameSpace;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.codenvy.ide.client.elements.NameSpace.applyNameSpace;
import static com.codenvy.ide.client.elements.NameSpace.convertNameSpacesToXML;

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

    public static final Key<String>          SOURCE_XPATH = new Key<>("SourceXpath");
    public static final Key<List<NameSpace>> NAMESPACES   = new Key<>("NameSpaces");

    public static final String REGEXP_ATTRIBUTE_NAME          = "regex";
    public static final String REGEXP_ATTRIBUTE_DEFAULT_VALUE = ".*+";

    public static final String SOURCE_ATTRIBUTE_NAME = "source";

    public static final String CASE_TITLE         = "Case";
    public static final String DEFAULT_CASE_TITLE = "Default";

    public static final String CASE_SERIALIZATION_NAME    = "case";
    public static final String DEFAULT_SERIALIZATION_NAME = "default";

    private static final List<String> PROPERTIES = Collections.emptyList();

    private final Provider<NameSpace> nameSpaceProvider;

    private Branch firstBranch;
    private Branch defaultBranch;

    @Inject
    public Switch(EditorResources resources,
                  Provider<Branch> branchProvider,
                  ElementCreatorsManager elementCreatorsManager,
                  Provider<NameSpace> nameSpaceProvider) {

        super(ELEMENT_NAME,
              ELEMENT_NAME,
              SERIALIZATION_NAME,
              PROPERTIES,
              true,
              false,
              resources.switchMediator(),
              branchProvider,
              elementCreatorsManager);

        this.nameSpaceProvider = nameSpaceProvider;

        putProperty(SOURCE_XPATH, "default/xpath");
        putProperty(NAMESPACES, new ArrayList<NameSpace>());

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

        return Collections.unmodifiableList(branches);
    }

    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected String serializeAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put(SOURCE_ATTRIBUTE_NAME, getProperty(SOURCE_XPATH));

        List<NameSpace> nameSpaces = getProperty(NAMESPACES);

        return nameSpaces == null ? "" : convertNameSpacesToXML(nameSpaces) + convertAttributesToXML(attributes);
    }

    /** {@inheritDoc} */
    @Override
    public void deserialize(@Nonnull Node node) {
        List<NameSpace> nameSpaces = new ArrayList<>();

        putProperty(NAMESPACES, nameSpaces);

        super.deserialize(node);

        firstBranch = branches.remove(0);
        defaultBranch = branches.remove(branches.size() - 1);
    }

    /** {@inheritDoc} */
    @Override
    protected void applyAttribute(@Nonnull String attributeName, @Nonnull String attributeValue) {

        if (SOURCE_ATTRIBUTE_NAME.equals(attributeName)) {
            putProperty(SOURCE_XPATH, attributeValue);
        } else {
            applyNameSpace(nameSpaceProvider, getProperty(NAMESPACES), attributeName, attributeValue);
        }
    }

}
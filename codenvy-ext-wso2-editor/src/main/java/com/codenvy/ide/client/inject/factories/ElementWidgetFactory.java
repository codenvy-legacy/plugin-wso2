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
package com.codenvy.ide.client.inject.factories;

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Element;
import com.codenvy.ide.client.elements.widgets.branch.BranchPresenter;
import com.codenvy.ide.client.elements.widgets.branch.BranchView;
import com.codenvy.ide.client.elements.widgets.element.ElementPresenter;
import com.codenvy.ide.client.elements.widgets.element.ElementView;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating parts of the diagram element widget of WSO2 editor.
 *
 * @author Andrey Plotnikov
 */
@Singleton
public interface ElementWidgetFactory {

    /**
     * Create an instance of {@link BranchPresenter} with a given branch model instance.
     *
     * @param branch
     *         element which need to be used
     * @return an instance of {@link BranchPresenter}
     */
    @Nonnull
    BranchPresenter createContainer(@Nonnull Branch branch);

    /**
     * Create an instance of {@link BranchView} with a given branch model instance.
     *
     * @param branch
     *         element which need to be used
     * @return an instance of {@link BranchView}
     */
    @Nonnull
    BranchView createContainerView(@Nonnull Branch branch);

    /**
     * Create an instance of {@link ElementPresenter} for a given element.
     *
     * @param element
     *         element for which presenter will be created
     * @return an instance of {@link ElementPresenter}
     */
    @Nonnull
    ElementPresenter createElementPresenter(@Nonnull Element element);

    /**
     * Create an instance of {@link ElementView} with allow to enhance branches.
     *
     * @param element
     *         element for which view will be created
     * @return an instance of {@link ElementView}
     */
    @Nonnull
    ElementView createElementView(@Nonnull Element element);

}
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
package com.codenvy.ide.client.inject;

import com.codenvy.ide.client.EditorState;
import com.codenvy.ide.client.State;
import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.elements.Shape;
import com.codenvy.ide.client.elements.shape.ShapePresenter;
import com.codenvy.ide.client.elements.shape.ShapeView;
import com.codenvy.ide.client.elements.shape.branch.BranchPresenter;
import com.codenvy.ide.client.managers.PropertiesPanelManager;
import com.codenvy.ide.client.managers.SelectionManager;
import com.codenvy.ide.client.propertiespanel.switchmediator.branch.BranchFiledPresenter;
import com.codenvy.ide.client.toolbar.ToolbarPresenter;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Singleton;

import javax.annotation.Nonnull;

/**
 * The factory that is used for creating elements of WSO2 editor.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
@Singleton
public interface EditorFactory {

    /**
     * Create an instance of {@link ToolbarPresenter} with a given state of editor.
     *
     * @param editorState
     *         editor that need to be used
     * @return an instance of {@link ToolbarPresenter}
     */
    @Nonnull
    ToolbarPresenter createToolbar(@Nonnull EditorState<State> editorState);

    /**
     * Create an instance of {@link PropertiesPanelManager} with a given widget.
     *
     * @param container
     *         widget that need to be used
     * @return an instance of {@link PropertiesPanelManager}
     */
    @Nonnull
    PropertiesPanelManager createPropertiesPanelManager(@Nonnull AcceptsOneWidget container);

    /**
     * Create an instance of {@link BranchPresenter} with a given state of editor.
     *
     * @param editorState
     *         editor that need to be used
     * @param selectionManager
     *         selection manager which need to select an element
     * @param branch
     *         element which need to be used
     * @return an instance of {@link BranchPresenter}
     */
    @Nonnull
    BranchPresenter createContainer(@Nonnull EditorState<State> editorState,
                                    @Nonnull SelectionManager selectionManager,
                                    @Nonnull Branch branch);

    /**
     * Create an instance of {@link ShapePresenter} with a given state of editor and for given shape.
     *
     * @param editorState
     *         editor that need to be used
     * @param shape
     *         element for which presenter will be created
     * @return an instance of {@link ShapePresenter}
     */
    @Nonnull
    ShapePresenter createShapePresenter(@Nonnull EditorState<State> editorState,
                                        @Nonnull Shape shape);

    /**
     * Create an instance of {@link ShapeView} with allow to enhance branches.
     *
     * @param isPossibleChangeCases
     *         <code>true</code> possible to add branches,<code>false</code> impossible to add branches
     * @return an instance of {@link ShapeView}
     */
    @Nonnull
    ShapeView createShapeView(boolean isPossibleChangeCases);

    /**
     * Create an instance of {@link BranchFiledPresenter} with a given branch.
     *
     * @param branch
     *         branch for which need to create presenter
     * @param index
     *         number of branch
     * @return an instance of {@link BranchFiledPresenter}
     */
    @Nonnull
    BranchFiledPresenter createBranchFieldPresenter(@Nonnull Branch branch, int index);

}
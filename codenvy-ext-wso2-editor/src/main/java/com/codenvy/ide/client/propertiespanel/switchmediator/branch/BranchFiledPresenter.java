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
package com.codenvy.ide.client.propertiespanel.switchmediator.branch;

import com.codenvy.ide.client.elements.Branch;
import com.codenvy.ide.client.mvp.AbstractPresenter;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.codenvy.ide.client.elements.mediators.Switch.REGEXP_ATTRIBUTE_NAME;

/**
 * The class that provides business logic of the reg exp property of case branch in 'Switch' mediator.
 *
 * @author Andrey Plotnikov
 * @author Dmitry Shnurenko
 */
public class BranchFiledPresenter extends AbstractPresenter<BranchFiledView> implements BranchFiledView.ActionDelegate {

    private final Branch                      branch;
    private final List<BranchChangedListener> listeners;

    @Inject
    public BranchFiledPresenter(BranchFiledView view, @Assisted Branch branch, @Assisted int index) {
        super(view);

        this.branch = branch;
        this.listeners = new ArrayList<>();

        this.view.setRegExp(branch.getAttributeByName(REGEXP_ATTRIBUTE_NAME));
        this.view.setTitle(branch.getTitle() + ' ' + index);
    }

    /** @return the GWT widget that is controlled by the presenter */
    @Nonnull
    public BranchFiledView getView() {
        return view;
    }

    /** {@inheritDoc} */
    @Override
    public void onRegExpChanged() {
        branch.addAttribute(REGEXP_ATTRIBUTE_NAME, view.getRegExp());

        notifyBranchChangedListeners();
    }

    /**
     * Adds listener that listens to changing of the regexp property of case branch.
     *
     * @param listener
     *         listener that needs to be added
     */
    public void addBranchChangedListener(@Nonnull BranchChangedListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes listener that listens to changing of the regexp property of case branch.
     *
     * @param listener
     *         listener that needs to be removed
     */
    public void removeBranchChangedListener(@Nonnull BranchChangedListener listener) {
        listeners.remove(listener);
    }

    /** Notify all listeners about changing regexp property of case branch. */
    public void notifyBranchChangedListeners() {
        for (BranchChangedListener listener : listeners) {
            listener.onBranchChanged();
        }
    }

    public interface BranchChangedListener {

        /** Performs some actions when branch was changed. */
        void onBranchChanged();
    }

}
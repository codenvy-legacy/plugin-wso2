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
package com.codenvy.ide.client.mvp;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * The abstract implementation of view. It contains the implementation of general methods which might not be changed.
 *
 * @param <T>
 *         type of action delegate
 * @author Andrey Plotnikov
 */
public abstract class AbstractView<T extends AbstractView.ActionDelegate> extends Composite implements View<T> {

    /** Required for delegating functions in the view. */
    public interface ActionDelegate {
    }

    protected T delegate;

    /** {@inheritDoc} */
    @Override
    public void setDelegate(@Nonnull T delegate) {
        this.delegate = delegate;
    }

    /**
     * Sets available items for a given field.
     *
     * @param field
     *         field that needs to contain a given list of items
     * @param types
     *         available items of field
     */
    protected void setTypes(@Nonnull ListBox field, @Nullable List<String> types) {
        if (types == null) {
            return;
        }

        field.clear();

        for (String value : types) {
            field.addItem(value);
        }
    }

    /**
     * Select item in the field.
     *
     * @param field
     *         field that needs to be changed
     * @param type
     *         a new selected item
     */
    protected void selectType(@Nonnull ListBox field, @Nullable String type) {
        for (int i = 0; i < field.getItemCount(); i++) {
            if (field.getValue(i).equals(type)) {
                field.setItemSelected(i, true);
                return;
            }
        }
    }

    /**
     * Returns a selected item of field.
     *
     * @param field
     *         field that contains item
     * @return a selected item
     */
    @Nonnull
    protected String getSelectedItem(@Nonnull ListBox field) {
        int index = field.getSelectedIndex();
        return index != -1 ? field.getValue(field.getSelectedIndex()) : "";
    }

}
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
package com.codenvy.ide.client.elements;

import com.codenvy.ide.client.EditorResources;
import com.codenvy.ide.client.managers.ElementCreatorsManager;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Provider;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Plotnikov
 * @author Valeriy Svydenko
 * @author Dmitry Shnurenko
 */
public abstract class AbstractElementTest<T extends AbstractElement> extends AbstractEntityTest<T> {

    @Mock
    protected EditorResources        resources;
    @Mock
    protected Provider<Branch>       branchProvider;
    @Mock
    protected ElementCreatorsManager elementCreatorsManager;
    @Mock
    protected ImageResource          icon;

    @Mock
    private Branch          branch;
    @Mock
    private AbstractElement element;

    @Test
    public void elementIdShouldBeNotNull() throws Exception {
        assertNotNull(entity.getId());
    }

    @Test
    public abstract void elementTitleShouldBeInitializedWithDefaultValue() throws Exception;

    @Test
    public void elementTitleShouldBeChanged() throws Exception {
        String title = "title";

        entity.setTitle(title);

        assertEquals(title, entity.getTitle());
    }

    @Test
    public void parentShouldBeEmptyWhenNoOneSetsIt() throws Exception {
        assertNull(entity.getParent());
    }

    @Test
    public void parentShouldBeChanged() throws Exception {
        assertThat(entity.getParent(), nullValue());

        Element parent = mock(Element.class);
        entity.setParent(parent);
        assertEquals(parent, entity.getParent());
    }

    @Test
    public abstract void elementNameShouldBeDefinedWithDefaultValue() throws Exception;

    @Test
    public abstract void serializeNameShouldBeDefinedWithDefaultValue() throws Exception;

    @Test
    public void branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero() throws Exception {
        branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero(0);
    }

    protected void branchesAmountShouldBeNotChangedWhenNewAmountIsLessThanZero(int countBranches) throws Exception {
        assertEquals(countBranches, entity.getBranchesAmount());

        entity.setBranchesAmount(-5);

        assertEquals(countBranches, entity.getBranchesAmount());
    }

    @Test
    public void branchesAmountShouldBeNotChangedWhenSizeTheSame() throws Exception {
        branchesAmountShouldBeNotChangedWhenSizeTheSame(0);
    }

    protected void branchesAmountShouldBeNotChangedWhenSizeTheSame(int countBranches) throws Exception {
        assertEquals(countBranches, entity.getBranchesAmount());

        entity.setBranchesAmount(3);

        if (!entity.isPossibleToAddBranches()) {
            verify(branchProvider, times(countBranches)).get();
            return;
        }

        assertEquals(3, entity.getBranchesAmount());

        entity.setBranchesAmount(3);
        assertEquals(3, entity.getBranchesAmount());

        verify(branchProvider, times(3)).get();
    }

    @Test
    public void branchesAmountShouldBeChanged() throws Exception {
        branchesAmountShouldBeChanged(0);
    }

    protected void branchesAmountShouldBeChanged(int countBranches) throws Exception {
        assertEquals(countBranches, entity.getBranchesAmount());

        entity.setBranchesAmount(3);

        if (!entity.isPossibleToAddBranches()) {
            verify(branchProvider, times(countBranches)).get();
            return;
        }

        assertEquals(3, entity.getBranchesAmount());
        verify(branchProvider, times(3)).get();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addingBranchManuallyShouldBeImpossible() throws Exception {
        addingBranchManuallyShouldBeImpossible(0);
    }

    protected void addingBranchManuallyShouldBeImpossible(int countBranches) throws Exception {
        when(branchProvider.get()).thenReturn(branch);

        assertEquals(countBranches, entity.getBranchesAmount());

        entity.setBranchesAmount(3);

        List<Branch> branches = entity.getBranches();
        branches.add(branch);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removingBranchManuallyShouldBeImpossible() throws Exception {
        removingBranchManuallyShouldBeImpossible(0);
    }

    protected void removingBranchManuallyShouldBeImpossible(int countBranches) throws Exception {
        when(branchProvider.get()).thenReturn(branch);

        assertEquals(countBranches, entity.getBranchesAmount());

        entity.setBranchesAmount(3);

        List<Branch> branches = entity.getBranches();
        branches.remove(branch);
    }

    @Test
    public abstract void possibleToChangeBranchAmountParamShouldBeInitializedWithDefaultValue() throws Exception;

    @Test
    public abstract void needToShowTitleAndIconParamShouldBeInitializedWithDefaultValue() throws Exception;

    @Test
    public void xPositionShouldBeChanged() throws Exception {
        int newXPosition = 100;

        assertEquals(0, entity.getX());

        entity.setX(newXPosition);
        assertEquals(newXPosition, entity.getX());
    }

    @Test
    public void yPositionShouldBeChanged() throws Exception {
        int newYPosition = 100;

        assertEquals(0, entity.getY());

        entity.setY(newYPosition);
        assertEquals(newYPosition, entity.getY());
    }

    @Test
    public abstract void elementIconShouldBeInitializedWithDefaultValue() throws Exception;

    @Test
    public void elementShouldBeLessWhenOtherElementHasBiggerXPosition() throws Exception {
        entity.setX(100);
        entity.setY(100);

        when(element.getX()).thenReturn(150);

        assertEquals(-1, entity.compareTo(element));
    }

    @Test
    public void elementShouldBeLessWhenOtherElementHasTheSameXPositionAndBiggerYPosition() throws Exception {
        entity.setX(100);
        entity.setY(100);

        when(element.getX()).thenReturn(100);
        when(element.getY()).thenReturn(150);

        assertEquals(-1, entity.compareTo(element));
    }

    @Test
    public void elementShouldBeBiggerWhenOtherElementHasLessXPosition() throws Exception {
        entity.setX(150);
        entity.setY(100);

        when(element.getX()).thenReturn(100);

        assertEquals(1, entity.compareTo(element));
    }

    @Test
    public void elementShouldBeBiggerWhenOtherElementHasTheSameXPositionAndLessYPosition() throws Exception {
        entity.setX(100);
        entity.setY(150);

        when(element.getX()).thenReturn(100);
        when(element.getY()).thenReturn(100);

        assertEquals(1, entity.compareTo(element));
    }

    @Test
    public void elementsShouldHasTheSamePosition() throws Exception {
        entity.setX(100);
        entity.setY(100);

        when(element.getX()).thenReturn(100);
        when(element.getY()).thenReturn(100);

        assertEquals(0, entity.compareTo(element));
    }

    @Test
    public void sameElementsShouldBeEqualed() throws Exception {
        assertTrue(entity.equals(entity));
    }

    @Test
    public void otherObjectShouldBeNotEqualed() throws Exception {
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(entity.equals("some text"));
    }

    @Test
    public abstract void elementShouldBeNotEqualedForAnotherInstanceOfElement() throws Exception;

    @Test
    public void elementShouldBeNotEqualedForAnotherElement() throws Exception {
        AbstractElement otherElement = mock(AbstractElement.class);
        assertFalse(entity.equals(otherElement));
    }

    @Test
    public abstract void constructorPrepareOperationShouldBeDone() throws Exception;

}
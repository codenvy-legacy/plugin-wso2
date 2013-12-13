/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2013] Codenvy, S.A. 
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.wso2.client.wizard.project;

import com.codenvy.ide.api.paas.PaaS;
import com.codenvy.ide.api.template.Template;
import com.codenvy.ide.api.ui.wizard.Wizard;
import com.codenvy.ide.api.ui.wizard.WizardContext;
import com.codenvy.ide.api.ui.wizard.WizardPage;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.PAAS;
import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.TEMPLATE;
import static com.codenvy.ide.ext.wso2.client.WSO2Extension.ESB_CONFIGURATION_PROJECT_ID;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing {@link CreateESBConfProjectPage} functionality.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateESBConfProjectPageTest {
    public static final String SOME_TEXT = "some text";

    @Mock
    private Wizard.UpdateDelegate    delegate;
    @Mock
    private WizardContext            wizardContext;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private CreateESBConfProjectView view;
    @Mock
    private LocalizationConstant     locale;
    @Mock
    private PaaS                     paas;
    @Mock
    private Template                 template;
    private CreateESBConfProjectPage page;

    @Before
    public void setUp() throws Exception {
        when(wizardContext.getData(PAAS)).thenReturn(paas);
        when(wizardContext.getData(TEMPLATE)).thenReturn(template);

        page = new CreateESBConfProjectPage(view, locale);
        page.setContext(wizardContext);
        page.setUpdateDelegate(delegate);
    }

    @Test
    public void pageShouldBeNotSkipped() throws Exception {
        assertEquals(false, page.canSkip());
    }

    @Test
    public void emptyGroupIdNoticeShouldBeShown() throws Exception {
        prepareTestWhenGroupIdFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyGroupId();
    }

    @Test
    public void pageShouldBeNotCompletedWhenGroupIdFieldIsEmpty() throws Exception {
        prepareTestWhenGroupIdFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenGroupIdFieldIsEmpty() {
        when(locale.wizardProjectNoticeEmptyGroupId()).thenReturn(SOME_TEXT);
    }

    @Test
    public void emptyArtifactIdNoticeShouldBeShown() throws Exception {
        prepareTestWhenArtifactIdFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyArtifactId();
    }

    @Test
    public void pageShouldBeNotCompletedWhenArtifactIdFieldIsEmpty() throws Exception {
        prepareTestWhenArtifactIdFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenArtifactIdFieldIsEmpty() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);

        when(locale.wizardProjectNoticeEmptyArtifactId()).thenReturn(SOME_TEXT);
    }

    @Test
    public void emptyVersionNoticeShouldBeShown() throws Exception {
        prepareTestWhenVersionFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyVersion();
    }

    @Test
    public void pageShouldBeNotCompletedWhenVersionFieldIsEmpty() throws Exception {
        prepareTestWhenVersionFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenVersionFieldIsEmpty() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);
        when(view.getArtifactID()).thenReturn(SOME_TEXT);

        when(locale.wizardProjectNoticeEmptyVersion()).thenReturn(SOME_TEXT);
    }

    @Test
    public void emptyParentGroupIdNoticeShouldBeShown() throws Exception {
        prepareTestWhenParentGroupIdFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyParentGroupId();
    }

    @Test
    public void pageShouldBeNotCompletedWhenParentGroupIdFieldIsEmpty() throws Exception {
        prepareTestWhenParentGroupIdFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenParentGroupIdFieldIsEmpty() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);
        when(view.getArtifactID()).thenReturn(SOME_TEXT);
        when(view.getVersion()).thenReturn(SOME_TEXT);

        when(view.isParentPomConfEnable()).thenReturn(true);

        when(locale.wizardProjectNoticeEmptyParentGroupId()).thenReturn(SOME_TEXT);
    }

    @Test
    public void emptyParentArtifactIdNoticeShouldBeShown() throws Exception {
        prepareTestWhenParentArtifactIdFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyParentGroupId();
    }

    @Test
    public void pageShouldBeNotCompletedWhenParentArtifactIdFieldIsEmpty() throws Exception {
        prepareTestWhenParentArtifactIdFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenParentArtifactIdFieldIsEmpty() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);
        when(view.getArtifactID()).thenReturn(SOME_TEXT);
        when(view.getVersion()).thenReturn(SOME_TEXT);

        when(view.isParentPomConfEnable()).thenReturn(true);

        when(view.getGroupID()).thenReturn(SOME_TEXT);

        when(locale.wizardProjectNoticeEmptyParentGroupId()).thenReturn(SOME_TEXT);
    }

    @Test
    public void emptyParentVersionNoticeShouldBeShown() throws Exception {
        prepareTestWhenParentVersionFieldIsEmpty();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeEmptyParentVersion();
    }

    @Test
    public void pageShouldBeNotCompletedWhenParentVersionFieldIsEmpty() throws Exception {
        prepareTestWhenParentVersionFieldIsEmpty();

        assertEquals(false, page.isCompleted());
    }

    private void prepareTestWhenParentVersionFieldIsEmpty() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);
        when(view.getArtifactID()).thenReturn(SOME_TEXT);
        when(view.getVersion()).thenReturn(SOME_TEXT);

        when(view.isParentPomConfEnable()).thenReturn(true);

        when(view.getParentGroupID()).thenReturn(SOME_TEXT);
        when(view.getParentArtifactID()).thenReturn(SOME_TEXT);

        when(locale.wizardProjectNoticeEmptyParentVersion()).thenReturn(SOME_TEXT);
    }

    @Test
    public void generalNoticeShouldBeShown() throws Exception {
        prepareTestWhenAllFieldsAreFilled();

        assertEquals(SOME_TEXT, page.getNotice());
        verify(locale).wizardProjectNoticeGeneral();
    }

    @Test
    public void pageShouldBeCompletedWhenAllFieldsAreFilled() throws Exception {
        prepareTestWhenAllFieldsAreFilled();

        assertEquals(true, page.isCompleted());
    }

    private void prepareTestWhenAllFieldsAreFilled() {
        when(view.getGroupID()).thenReturn(SOME_TEXT);
        when(view.getArtifactID()).thenReturn(SOME_TEXT);
        when(view.getVersion()).thenReturn(SOME_TEXT);

        when(view.isParentPomConfEnable()).thenReturn(true);

        when(view.getParentGroupID()).thenReturn(SOME_TEXT);
        when(view.getParentArtifactID()).thenReturn(SOME_TEXT);
        when(view.getParentVersion()).thenReturn(SOME_TEXT);

        when(locale.wizardProjectNoticeGeneral()).thenReturn(SOME_TEXT);
    }

    @Test
    public void viewShouldBeInjectedInMainContainer() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        page.go(container);

        verify(container).setWidget(eq(view));
    }

    @Test
    public void commitCallbackShouldBeExecuted() throws Exception {
        WizardPage.CommitCallback callback = mock(WizardPage.CommitCallback.class);

        page.commit(callback);

        verify(callback).onSuccess();
    }

    @Test
    public void updateControlMethodShouldBeExecutedWhenSomeFieldContentIsChanged() throws Exception {
        page.onValueChanged();

        verify(delegate).updateControls();
    }

    @Test
    public void pageShouldNotBeInContextWhenPaasProvidesTemplate() {
        when(paas.isProvideTemplate()).thenReturn(true);

        assertEquals(page.inContext(), false);
    }

    @Test
    public void pageShouldNotBeInContextWhenOtherTemplateIsChosen() {
        when(paas.isProvideTemplate()).thenReturn(true);
        when(template.getId()).thenReturn(SOME_TEXT);

        assertEquals(page.inContext(), false);
    }

    @Test
    public void pageShouldBeInContextWhenItsTemplateIsChosenAndPaasDoesntProvideTemplate() {
        when(paas.isProvideTemplate()).thenReturn(false);
        when(template.getId()).thenReturn(ESB_CONFIGURATION_PROJECT_ID);

        assertEquals(page.inContext(), true);
    }
}
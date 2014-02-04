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
import com.codenvy.ide.api.resources.ResourceProvider;
import com.codenvy.ide.api.template.Template;
import com.codenvy.ide.api.ui.wizard.Wizard;
import com.codenvy.ide.api.ui.wizard.WizardContext;
import com.codenvy.ide.api.ui.wizard.WizardPage;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.wso2.client.LocalizationConstant;
import com.codenvy.ide.ext.wso2.client.WSO2ClientService;
import com.codenvy.ide.ext.wso2.client.WSO2Resources;
import com.codenvy.ide.ext.wso2.client.commons.WSO2AsyncRequestCallback;
import com.codenvy.ide.ext.wso2.shared.ESBProjectInfo;
import com.codenvy.ide.resources.model.Project;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Method;

import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.PAAS;
import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.PROJECT_NAME;
import static com.codenvy.ide.api.ui.wizard.newproject.NewProjectWizard.TEMPLATE;
import static com.codenvy.ide.ext.wso2.shared.Constants.ESB_CONFIGURATION_PROJECT_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Answers.RETURNS_MOCKS;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testing {@link CreateESBConfProjectPage} functionality.
 *
 * @author Andrey Plotnikov
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateESBConfProjectPageTest {
    public static final String SOME_TEXT  = "some text";
    public static final String EMPTY_TEXT = "";

    @Mock
    private Wizard.UpdateDelegate    delegate;
    @Mock
    private WizardContext            wizardContext;
    @Mock(answer = RETURNS_MOCKS)
    private CreateESBConfProjectView view;
    @Mock
    private LocalizationConstant     locale;
    @Mock
    private PaaS                     paas;
    @Mock
    private Template                 template;
    @Mock
    private WSO2ClientService        service;
    @Mock
    private ResourceProvider         resourceProvider;
    @Mock
    private DtoFactory               dtoFactory;
    @Mock
    private WSO2Resources            resources;
    @InjectMocks
    private CreateESBConfProjectPage page;

    @Before
    public void setUp() throws Exception {
        page.setContext(wizardContext);
        page.setUpdateDelegate(delegate);

        /*TODO workaround a Maven Information page.
        verify(locale).wizardProjectTitle();
        verify(resources).esb_project_wizard();*/

        verify(view).setDelegate(eq(page));
        verify(view).setArtifactID(eq(EMPTY_TEXT));
        verify(view).setGroupID(eq(EMPTY_TEXT));
        verify(view).setVersion(eq(EMPTY_TEXT));
        verify(view).setParentPomConfEnable(eq(false));
        verify(view).setParentArtifactID(eq(EMPTY_TEXT));
        verify(view).setParentGroupID(eq(EMPTY_TEXT));
        verify(view).setParentVersion(eq(EMPTY_TEXT));

        when(wizardContext.getData(PROJECT_NAME)).thenReturn(SOME_TEXT);
        when(wizardContext.getData(PAAS)).thenReturn(paas);
        when(wizardContext.getData(TEMPLATE)).thenReturn(template);

        when(dtoFactory.createDto(Matchers.<Class<ESBProjectInfo>>anyObject()))
                .thenReturn(mock(ESBProjectInfo.class, Mockito.RETURNS_MOCKS));
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

        assertNull(page.getNotice());
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
    }

    @Test
    public void viewShouldBeInjectedInMainContainer() throws Exception {
        AcceptsOneWidget container = mock(AcceptsOneWidget.class);

        page.go(container);

        verify(container).setWidget(eq(view));
    }

    @Test
    public void updateControlMethodShouldBeExecutedWhenSomeFieldContentIsChanged() throws Exception {
        page.onValueChanged();

        verify(delegate).updateControls();
    }

    @Test
    public void enableStateShouldBeChangedForParentPomConfPanel() throws Exception {
        reset(view);

        page.onParentPomConfChanged();

        verify(view).setParentPomConfEnable(anyBoolean());
        verify(delegate).updateControls();
    }

    @Test
    public void pageShouldNotBeInContextWhenPaasProvidesTemplate() {
        when(paas.isProvideTemplate()).thenReturn(true);

        assertEquals(false, page.inContext());
    }

    @Test
    public void pageShouldNotBeInContextWhenOtherTemplateIsChosen() {
        when(paas.isProvideTemplate()).thenReturn(true);
        when(template.getId()).thenReturn(SOME_TEXT);

        assertEquals(false, page.inContext());
    }

    @Test
    public void pageShouldBeInContextWhenItsTemplateIsChosenAndPaasDoesntProvideTemplate() {
        when(paas.isProvideTemplate()).thenReturn(false);
        when(template.getId()).thenReturn(ESB_CONFIGURATION_PROJECT_ID);

        assertEquals(true, page.inContext());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void onFailureMethodInCommitCallbackShouldBeExecutedWhenSomeProblemHappened() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];
                Method onFailure = GwtReflectionUtils.getMethod(callback.getClass(), "onFailure");
                onFailure.invoke(callback, throwable);
                return callback;
            }
        }).when(service).createESBConfProject((ESBProjectInfo)anyObject(), (AsyncRequestCallback<Void>)anyObject());

        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.commit(commitCallback);

        verify(commitCallback).onFailure(throwable);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void onFailureMethodInCommitCallbackShouldBeExecutedWhenRequestExceptionHappened() throws Exception {
        doThrow(RequestException.class).when(service)
                .createESBConfProject((ESBProjectInfo)anyObject(), (WSO2AsyncRequestCallback<Void>)anyObject());

        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.commit(commitCallback);

        verify(commitCallback).onFailure((RequestException)anyObject());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void onSuccessMethodInCommitCallbackShouldBeExecutedWhenGetProjectRequestIsFailed() throws Exception {
        final Throwable throwable = mock(Throwable.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, (Void)null);
                return callback;
            }
        }).when(service).createESBConfProject((ESBProjectInfo)anyObject(), (AsyncRequestCallback<Void>)anyObject());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<Project> callback = (AsyncCallback<Project>)arguments[1];
                callback.onFailure(throwable);
                return callback;
            }
        }).when(resourceProvider).getProject(anyString(), (AsyncCallback<Project>)anyObject());

        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.commit(commitCallback);

        verify(commitCallback).onFailure(throwable);
        verify(resourceProvider).getProject(eq(SOME_TEXT), (AsyncCallback<Project>)anyObject());
    }

    @SuppressWarnings({"unchecked", "NonJREEmulationClassesInClientCode"})
    @Test
    public void onSuccessMethodInCommitCallbackShouldBeExecutedWhenNoProblemHappened() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncRequestCallback<Void> callback = (AsyncRequestCallback<Void>)arguments[1];
                Method onSuccess = GwtReflectionUtils.getMethod(callback.getClass(), "onSuccess");
                onSuccess.invoke(callback, (Void)null);
                return callback;
            }
        }).when(service).createESBConfProject((ESBProjectInfo)anyObject(), (AsyncRequestCallback<Void>)anyObject());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                AsyncCallback<Project> callback = (AsyncCallback<Project>)arguments[1];
                callback.onSuccess(mock(Project.class));
                return callback;
            }
        }).when(resourceProvider).getProject(anyString(), (AsyncCallback<Project>)anyObject());
        when(view.isParentPomConfEnable()).thenReturn(true);

        WizardPage.CommitCallback commitCallback = mock(WizardPage.CommitCallback.class);

        page.commit(commitCallback);

/*        verify(view).getArtifactID();
        verify(view).getGroupID();
        verify(view).getVersion();
        verify(view).getParentArtifactID();
        verify(view).getParentGroupID();
        verify(view).getParentVersion();*/

        verify(commitCallback).onSuccess();
        verify(resourceProvider).getProject(eq(SOME_TEXT), (AsyncCallback<Project>)anyObject());
    }
}
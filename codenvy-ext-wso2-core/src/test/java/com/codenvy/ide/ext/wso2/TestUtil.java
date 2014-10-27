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
package com.codenvy.ide.ext.wso2;

import com.google.gwt.http.client.RequestCallback;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * The class contains methods, which allow us to invoke methods of {@link RequestCallback}.
 *
 * @author Dmitry Shnurenko
 */
public class TestUtil {

    private static final String ON_SUCCESS_METHOD = "onSuccess";
    private static final String ON_FAILURE_METHOD = "onFailure";

    public static <T extends RequestCallback> void invokeOnSuccessCallbackMethod(@Nonnull Class<?> clazz,
                                                                                 @Nonnull T callback,
                                                                                 @Nonnull Object... parameters) throws Exception {

        //noinspection NonJREEmulationClassesInClientCode
        Method onSuccess = clazz.getDeclaredMethod(ON_SUCCESS_METHOD, Object.class);
        onSuccess.setAccessible(true);
        onSuccess.invoke(callback, parameters);
    }

    public static <T extends RequestCallback> void invokeOnFailureCallbackMethod(@Nonnull Class<?> clazz,
                                                                                 @Nonnull T callback,
                                                                                 @Nonnull Throwable throwable) throws Exception {
        //noinspection NonJREEmulationClassesInClientCode
        Method onFailure = clazz.getDeclaredMethod(ON_FAILURE_METHOD, Throwable.class);
        onFailure.setAccessible(true);
        onFailure.invoke(callback, throwable);
    }

}
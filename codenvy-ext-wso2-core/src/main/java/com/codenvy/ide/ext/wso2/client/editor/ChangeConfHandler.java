/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 * [2012] - [2014] Codenvy, S.A. 
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
package com.codenvy.ide.ext.wso2.client.editor;

import genmymodel.commands.serializable.SerializableCommand;
import genmymodel.commands.serializable.type.EObjectUUID;

import com.codenvy.ide.util.loging.Log;
import com.genmymodel.ecoreonline.graphic.NodeWidget;
import com.genmymodel.ecoreonline.graphic.event.handler.AutoResizeHandler;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.genmymodel.gmmf.common.CollaborationEventRequestHandler;
import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.common.MessageChatRequestEvent;
import org.genmymodel.gmmf.common.RedoRequestEvent;
import org.genmymodel.gmmf.common.UndoRequestEvent;

import java.util.Set;

/**
 * TODO JavaDoc
 *
 * @author Andrey Plotnikov
 */
public class ChangeConfHandler implements CollaborationEventRequestHandler, AutoResizeHandler {

    private EditingDomain editingDomain;

    public ChangeConfHandler() {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        final CommandStack commandStack = new BasicCommandStack();
        editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, commandStack);
    }

    /** {@inheritDoc} */
    @Override
    public void commandRequest(CommandRequestEvent event) {
        Command emfCommand = tryConvert(event.getCommands(), event.getModel());

        if (!emfCommand.canExecute()) {
            Log.error(this.getClass(), "The command cannot be executed.");
            return;
        }

        editingDomain.getCommandStack().execute(emfCommand);
    }

    /** {@inheritDoc} */
    @Override
    public void undoRequest(UndoRequestEvent event) {

    }

    /** {@inheritDoc} */
    @Override
    public void redoRequest(RedoRequestEvent event) {

    }

    /** {@inheritDoc} */
    @Override
    public void setSelection(EObject model, Set<EObjectUUID> selectedElements) {

    }

    /** {@inheritDoc} */
    @Override
    public void messageRequest(MessageChatRequestEvent messageChatEvent) {

    }

    /** {@inheritDoc} */
    @Override
    public void autoResize(NodeWidget node, int width, int height) {

    }

    protected Command tryConvert(SerializableCommand command, EObject root) {
        Command cmd;
        try {
            cmd = command.convert(editingDomain, root);
        } catch (Exception e) {
            Log.error(this.getClass(), e, "Error while converting command");
            throw new RuntimeException("Command cannot be executed!");
        }

        return cmd;
    }
}
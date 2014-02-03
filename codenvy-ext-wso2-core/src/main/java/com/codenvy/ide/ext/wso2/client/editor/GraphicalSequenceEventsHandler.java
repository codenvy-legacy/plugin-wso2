package com.codenvy.ide.ext.wso2.client.editor;

import genmymodel.commands.serializable.SerializableCommand;
import genmymodel.commands.serializable.type.EObjectUUID;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.genmymodel.ecoreonline.graphic.NodeWidget;
import com.genmymodel.ecoreonline.graphic.event.handler.AutoResizeHandler;

/**
 * Get modeling events and executes the appropriated EMF commands
 * 
 * @author Alexis Muller
 */
// TODO This class has never used
public class GraphicalSequenceEventsHandler implements CollaborationEventRequestHandler, AutoResizeHandler
{
    private static final Logger logger = Logger.getLogger(GraphicalSequenceEventsHandler.class.getName());

    private EditingDomain       editingDomain;

    public GraphicalSequenceEventsHandler()
    {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        final CommandStack commandStack = new BasicCommandStack();
        editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, commandStack);
    }

    @Override
    public void commandRequest(CommandRequestEvent event)
    {
        Command emfCommand = tryConvert(event.getCommands(), event.getModel());

        if (!emfCommand.canExecute())
        {
            logger.severe("The command cannot be executed.");
            return;
        }

        editingDomain.getCommandStack().execute(emfCommand);
    }

    @Override
    public void undoRequest(UndoRequestEvent event)
    {

    }

    @Override
    public void redoRequest(RedoRequestEvent event)
    {

    }

    @Override
    public void setSelection(EObject model, Set<EObjectUUID> selectedElements)
    {

    }

    @Override
    public void messageRequest(MessageChatRequestEvent messageChatEvent)
    {

    }

    @Override
    public void autoResize(NodeWidget node, int width, int height)
    {

    }

    protected Command tryConvert(SerializableCommand command, EObject root)
    {
        Command cmd = null;
        try
        {
            cmd = command.convert(editingDomain, root);
        } catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error while converting command", e);
            throw new RuntimeException("Command cannot be executed!");
        }

        return cmd;
    }

}

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
package com.codenvy.ide.ext.wso2.client.editor.graphical;

import genmymodel.commands.UnexecutableDeleteCommand;
import genmymodel.commands.custom.GMMCommand;
import genmymodel.commands.serializable.SerializableCommand;
import genmymodel.commands.serializable.type.EObjectUUID;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.genmymodel.gmmf.common.CollaborationEventRequestHandler;
import org.genmymodel.gmmf.common.CommandRequestEvent;
import org.genmymodel.gmmf.common.MessageChatRequestEvent;
import org.genmymodel.gmmf.common.RedoRequestEvent;
import org.genmymodel.gmmf.common.UndoRequestEvent;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbLink;
import org.wso2.developerstudio.eclipse.gmf.esb.EsbSequence;
import org.wso2.developerstudio.eclipse.gmf.esb.Mediator;
import org.wso2.developerstudio.eclipse.gmf.esb.ModelObject;
import org.wso2.developerstudio.eclipse.gmf.esb.util.ObjectValidator;

import com.codenvy.ide.util.loging.Log;
import com.genmymodel.ecoreonline.graphic.Anchor;
import com.genmymodel.ecoreonline.graphic.Connector;
import com.genmymodel.ecoreonline.graphic.DiagramElement;
import com.genmymodel.ecoreonline.graphic.GraphicPackage;
import com.genmymodel.ecoreonline.graphic.Node;
import com.genmymodel.ecoreonline.graphic.NodeWidget;
import com.genmymodel.ecoreonline.graphic.PlaneElement;
import com.genmymodel.ecoreonline.graphic.Segment;
import com.genmymodel.ecoreonline.graphic.event.handler.AutoResizeHandler;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Get modeling events and executes the appropriated EMF commands.
 * 
 * @author Alexis Muller
 */
public class SeqEventsHandler implements CollaborationEventRequestHandler, AutoResizeHandler {

    private EditingDomain editingDomain;
    private EventBus      eventBus;

    public SeqEventsHandler(EventBus eventBus) {
        this.eventBus = eventBus;
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

        final CommandStack commandStack = new BasicCommandStack();
        editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, commandStack);
    }

    @Override
    public void commandRequest(CommandRequestEvent event) {
        Command emfCommand = tryConvert(event.getCommands(), event.getModel());

        enableCalculations(emfCommand); // Activate calculations for client

        if (emfCommand instanceof UnexecutableDeleteCommand) {
            emfCommand =
                         createDeleteCommand(event.getModel(), editingDomain, ((UnexecutableDeleteCommand)emfCommand).geteObjectsToRemove());
        }

        if (!emfCommand.canExecute()) {
            Log.error(getClass(), "The command cannot be executed.");
            return;
        }

        editingDomain.getCommandStack().execute(emfCommand);

        // warn when the model has changed
        fireModelChange(event, emfCommand);
    }

    @Override
    public void undoRequest(UndoRequestEvent event) {

    }

    @Override
    public void redoRequest(RedoRequestEvent event) {

    }

    @Override
    public void setSelection(EObject model, Set<EObjectUUID> selectedElements) {

    }

    @Override
    public void messageRequest(MessageChatRequestEvent messageChatEvent) {

    }

    @Override
    public void autoResize(NodeWidget node, int width, int height) {

    }

    protected Command tryConvert(SerializableCommand command, EObject root) {
        Command cmd;
        try {
            cmd = command.convert(editingDomain, root);
        } catch (Exception e) {
            Log.error(getClass(), "Error while converting command", e);
            throw new RuntimeException("Command cannot be executed!");
        }

        return cmd;
    }

    private void enableCalculations(Command command) {
        if (command instanceof GMMCommand) {
            ((GMMCommand)command).enableCalculations();
        }

        if (command instanceof CompoundCommand) {
            for (Command innerCommand : ((CompoundCommand)command).getCommandList())
                enableCalculations(innerCommand);
        }
    }

    /**
     * Fire a GraphicalSequenceChangeEvent only if the model has changed.
     * 
     * @param event
     */
    private void fireModelChange(CommandRequestEvent event, Command emfCommand) {
        @SuppressWarnings("unchecked")
        Collection<EObject> affectedObjects = (Collection<EObject>)emfCommand.getAffectedObjects();
        boolean hasModelChanged = false;

        // determine if the model -not the diagram - has changed
        for (EObject eo : affectedObjects) {

            if (eo instanceof ModelObject || eo instanceof EsbLink || eo instanceof EsbConnector) {
                hasModelChanged = true;
                break;
            }
        }

        if (hasModelChanged) {

            // gather failed validity constraints
            HashMap<String, ObjectValidator> validityConstraints = new HashMap<String, ObjectValidator>();
            for (EObject eo : affectedObjects) {
                if (eo instanceof Mediator)
                {
                    validityConstraints.putAll(((Mediator)eo).validate());
                }
            }

            // warn handlers the model has just changed
            EsbSequence esbSequence = (EsbSequence)event.getModel();
            GraphicalSequenceChangeEvent graphicSequenceHasChangedEvent =
                                                                          new GraphicalSequenceChangeEvent(esbSequence, validityConstraints);
            eventBus.fireEvent(graphicSequenceHasChangedEvent);
        }
    }


    /*
     * Theses operations will replaced by a service in the gmmf framework in future versions.
     */
    private static Command createDeleteCommand(EObject root, EditingDomain editingDomain,
                                               Collection<EObject> objectsToRemove) {
        Comparator<EObject> comparator = new Comparator<EObject>() {

            private int getDepth(EObject element) {
                if (element instanceof Segment) {
                    return 0;
                }

                if (!(element instanceof Node)) {
                    return Integer.MAX_VALUE;
                }

                if (element instanceof Connector) {
                    return 1;
                }

                Node node = (Node)element;
                if (node.getRefElement() == null) {
                    return 2;
                }

                return getDepth(node.getRefElement()) + 1;
            }

            @Override
            public int compare(EObject o1, EObject o2) {
                return getDepth(o1) - getDepth(o2);
            }
        };

        final Set<EObject> toRemoveSet = new HashSet<EObject>(objectsToRemove);

        // Adding referenced objects and widgets
        Set<EObject> modelElementsToRemove;
        // Set<EObject> referencedObjects;
        final CompoundCommand compoundCommand = new CompoundCommand();

        modelElementsToRemove = findReferencedModelElements(toRemoveSet);
        toRemoveSet.addAll(modelElementsToRemove);

        toRemoveSet.addAll(findWidgetsToRemove(toRemoveSet, root));

        // Adding command to allow undo/redo on property navigations and anchor attached elements

        Set<EObject> destroy = new HashSet<EObject>();
        for (EObject eObject : toRemoveSet) {
            if (eObject instanceof Anchor) {
                compoundCommand.append(new SetCommand(editingDomain, eObject, GraphicPackage.eINSTANCE.getAnchor_AttachedElement(), null));
            }

            if (eObject instanceof Node) {
                final Node node = (Node)eObject;

                if (node.getRefElement() != null) {
                    compoundCommand.append(new SetCommand(editingDomain, eObject, GraphicPackage.eINSTANCE.getNode_RefElement(), null));
                }
                compoundCommand.append(new SetCommand(editingDomain, eObject, GraphicPackage.eINSTANCE.getNode_DeltaX(), 0));
                compoundCommand.append(new SetCommand(editingDomain, eObject, GraphicPackage.eINSTANCE.getNode_DeltaY(), 0));
            }

            if (toRemoveSet.contains(eObject.eContainer())) { // We must clean the objects that will be auto-destroyed
                destroy.add(eObject);
            }

            if (eObject.eContainer() == null) // eObject is not in the model
            {
                destroy.add(eObject);
            }
        }
        toRemoveSet.removeAll(destroy);

        if (!toRemoveSet.isEmpty()) // Can be empty (for example when the user deletes a template parameter)
        {
            List<EObject> remove = new LinkedList<EObject>(toRemoveSet);
            Collections.sort(remove, comparator);

            // Adding delete command
            compoundCommand.append(new DeleteCommand(editingDomain, remove));
        }

        return compoundCommand.unwrap();
    }

    private static Set<EObject> findReferencedModelElements(Collection<EObject> eObjects) {
        final Set<EObject> modelElements = new HashSet<EObject>();

        for (EObject eObject : eObjects) {
            if (eObject instanceof DiagramElement) {
                DiagramElement diagramElement = (DiagramElement)eObject;

                if (diagramElement.getModelElement() != null && !eObjects.contains(diagramElement.getModelElement()))
                    modelElements.add(diagramElement.getModelElement());
            }
        }

        return modelElements;
    }

    private static Set<EObject> findWidgetsToRemove(Set<EObject> toRemoveModelElements, EObject modelRoot) {
        Set<EObject> allElements = new HashSet<EObject>();

        TreeIterator<EObject> contents = modelRoot.eAllContents();
        while (contents.hasNext()) {
            EObject eObject = contents.next();
            if (eObject instanceof PlaneElement) {
                PlaneElement planeElement = (PlaneElement)eObject;
                EObject element = planeElement.getModelElement();

                if (element == null && toRemoveModelElements.contains(planeElement) && planeElement instanceof Segment) {
                    // Fix for segment with no model element
                    final Segment segment = (Segment)planeElement;
                    allElements.addAll(segment.getRelativeNodes());
                    addConnectorToDelete(allElements, segment.getSourceConnector());
                    addConnectorToDelete(allElements, segment.getTargetConnector());
                } else if (toRemoveModelElements.contains(element)) {
                    for (Anchor anchor : ((PlaneElement)planeElement).getAnchors())
                        addConnectorToDelete(allElements, anchor);

                    if (planeElement instanceof Anchor)
                        addConnectorToDelete(allElements, (Connector)planeElement);
                    if (planeElement instanceof Segment)
                        addSegmentToDelete(allElements, (Segment)planeElement);

                    allElements.add(planeElement);
                }
            }
        }

        return allElements;
    }

    private static void addConnectorToDelete(Set<EObject> allElements, Connector connector) {
        if (connector == null) {
            // the segment is already corrupted (missing connector)
            // let the user deleting the segment with no error
            return;
        }
        if (!allElements.add(connector))
            return; // connector already in the list

        for (Segment s : connector.getSegments())
            addSegmentToDelete(allElements, s);
        for (Connector opposite : connector.getOpposites())
            addConnectorToDelete(allElements, opposite);

    }

    private static void addSegmentToDelete(Set<EObject> allElements, Segment segment) {
        if (!allElements.add(segment))
            return; // segment already in the list

        allElements.addAll(segment.getRelativeNodes());
        addConnectorToDelete(allElements, segment.getSourceConnector());
        addConnectorToDelete(allElements, segment.getTargetConnector());
    }
}

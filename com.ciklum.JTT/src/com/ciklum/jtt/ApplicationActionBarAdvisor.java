package com.ciklum.jtt;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    private IWorkbenchAction exitAction;   
   
    protected void makeActions(IWorkbenchWindow window) {   
        exitAction = ActionFactory.QUIT.create(window);   
        register(exitAction);   
    }   
   
    protected void fillTrayItem(IMenuManager trayItem) {
        trayItem.add(exitAction);   
    }   
    
}

package com.ciklum.jtt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormLayout;
import com.ciklum.jtt.swt.HeadComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import com.ciklum.jtt.swt.TasksComposite;

public class TasksView extends ViewPart {
    public static final String ID = "com.ciklum.JTT.views.tasks";
    
    public TasksView() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new FormLayout());
        
        HeadComposite headComposite = new HeadComposite(parent, SWT.NONE);
        FormData fd_headComposite = new FormData();
        fd_headComposite.bottom = new FormAttachment(0, 45);
        fd_headComposite.right = new FormAttachment(100);
        fd_headComposite.top = new FormAttachment(0, 10);
        fd_headComposite.left = new FormAttachment(0);
        headComposite.setLayoutData(fd_headComposite);
        
        final TasksComposite tasksComposite = new TasksComposite(parent, SWT.NONE);
        FormData fd_tasksComposite = new FormData();
        fd_tasksComposite.bottom = new FormAttachment(100, -10);
        fd_tasksComposite.top = new FormAttachment(headComposite, 1);
        fd_tasksComposite.left = new FormAttachment(0, 10);
        fd_tasksComposite.right = new FormAttachment(100, -10);
        tasksComposite.setLayoutData(fd_tasksComposite);
        
        headComposite.getBtnJiraCredentials().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                tasksComposite.drawItem(5);
            }
        });
    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub
    }
}

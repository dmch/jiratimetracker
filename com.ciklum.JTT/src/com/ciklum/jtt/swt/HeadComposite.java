package com.ciklum.jtt.swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;

public class HeadComposite extends Composite {
    Button btnJiraCredentials;

    public Button getBtnJiraCredentials() {
        return btnJiraCredentials;
    }

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public HeadComposite(Composite parent, int style) {
        super(parent, style);
        setLayout(new FormLayout());
        
        Label lblNewLabel = new Label(this, SWT.NONE);
        FormData fd_lblNewLabel = new FormData();
        fd_lblNewLabel.top = new FormAttachment(0, 10);
        fd_lblNewLabel.left = new FormAttachment(0, 10);
        lblNewLabel.setLayoutData(fd_lblNewLabel);
        lblNewLabel.setText("Sort Tasks");
        
        Combo combo = new Combo(this, SWT.NONE);
        FormData fd_combo = new FormData();
        fd_combo.right = new FormAttachment(40);
        fd_combo.bottom = new FormAttachment(lblNewLabel, 5, SWT.BOTTOM);
        fd_combo.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
        fd_combo.left = new FormAttachment(lblNewLabel, 5);
        combo.setLayoutData(fd_combo);
        
        btnJiraCredentials = new Button(this, SWT.NONE);
        FormData fd_btnJiraCredentials = new FormData();
        fd_btnJiraCredentials.top = new FormAttachment(lblNewLabel, -5, SWT.TOP);
        fd_btnJiraCredentials.right = new FormAttachment(100, -10);
        btnJiraCredentials.setLayoutData(fd_btnJiraCredentials);
        btnJiraCredentials.setText("Jira Credentials");

    }

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
}

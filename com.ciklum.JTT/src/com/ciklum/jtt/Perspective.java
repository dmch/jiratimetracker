package com.ciklum.jtt;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
	    layout.setEditorAreaVisible(false);
	    //layout.addView(TasksView.ID, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	    layout.addStandaloneView(TasksView.ID, false, IPageLayout.LEFT, 1.0f, layout.getEditorArea());
	}
}

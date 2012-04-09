package com.ciklum.jtt.resources;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class ColorSchemes {
    public static Color taskColor = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
    public static Color taskBorderColor = Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BORDER);
    public static Color taskGradientColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
    public static Color taskStartColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
    public static Color taskStartGradientColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
    public static Color taskStartTextColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    public static Color taskStopColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    public static Color taskStopGradientColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED);
    public static Color taskSelectedColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
}

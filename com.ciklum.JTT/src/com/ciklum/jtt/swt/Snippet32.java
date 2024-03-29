package com.ciklum.jtt.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;
public class Snippet32 {
  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell(display);
    shell.setSize(200, 200);
    shell.open();
    Listener listener = new Listener() {
      Point point = null;
      static final int JITTER = 8;
      public void handleEvent(Event event) {
        switch (event.type) {
        case SWT.MouseDown:
          point = new Point(event.x, event.y);
          break;
        case SWT.MouseMove:
          if (point == null)
            return;
          int deltaX = point.x - event.x,
          deltaY = point.y - event.y;
          if (Math.abs(deltaX) < JITTER && Math.abs(deltaY) < JITTER) {
            return;
          }
          Tracker tracker = new Tracker(display, SWT.NONE);
          Rectangle rect = display.map(shell, null, shell
              .getClientArea());
          rect.x -= deltaX;
          rect.y -= deltaY;
          tracker.setRectangles(new Rectangle[] { rect });
          tracker.open();
        // FALL THROUGH
        case SWT.MouseUp:
          point = null;
          break;
        }
      }
    };
    shell.addListener(SWT.MouseDown, listener);
    shell.addListener(SWT.MouseMove, listener);
    shell.addListener(SWT.MouseUp, listener);
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
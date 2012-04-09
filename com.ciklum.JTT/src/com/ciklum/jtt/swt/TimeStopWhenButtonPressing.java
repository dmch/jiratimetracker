package com.ciklum.jtt.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TimeStopWhenButtonPressing {

  public static void main(String[] args) {
    final Display display = new Display();
    final Color red = display.getSystemColor(SWT.COLOR_RED);
    final Color blue = display.getSystemColor(SWT.COLOR_BLUE);
    Shell shell = new Shell(display);
    shell.setLayout(new RowLayout());
    Button button = new Button(shell, SWT.PUSH);
    button.setText("Stop Timer");
    final Label label = new Label(shell, SWT.BORDER);
    label.setBackground(red);
    final int time = 500;
    final Runnable timer = new Runnable() {
      public void run() {
        if (label.isDisposed())
          return;
        Color color = label.getBackground().equals(red) ? blue : red;
        label.setBackground(color);
        display.timerExec(time, this);
      }
    };
    display.timerExec(time, timer);
    button.addListener(SWT.Selection, new Listener() {
      public void handleEvent(Event event) {
        display.timerExec(-1, timer);
      }
    });
    button.pack();
    label.setLayoutData(new RowData(button.getSize()));
    shell.pack();
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
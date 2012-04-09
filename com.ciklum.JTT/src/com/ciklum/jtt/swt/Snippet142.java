package com.ciklum.jtt.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
public class Snippet142 {
public static void main(String[] args) {
  final Display display = new Display();
  final Shell shell = new Shell(display);
  final Button button = new Button(shell,SWT.NONE);
  button.setSize(100,100);
  button.setText("Click");
  shell.pack();
  shell.open();
  button.addListener(SWT.MouseDown, new Listener() {
    public void handleEvent(Event e){
      System.out.println("Mouse Down (button: " + e.button + " x: " + e.x + " y: " + e.y + ")");
    }
  });
  final Point pt = display.map(shell, null, 50, 50);
  new Thread(){
    Event event;
    public void run(){
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {}
      event = new Event();
      event.type = SWT.MouseMove;
      event.x = pt.x;
      event.y = pt.y;
      display.post(event);
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {}
      event.type = SWT.MouseDown;
      event.button = 1;
      display.post(event);
      try {
        Thread.sleep(300);
      } catch (InterruptedException e) {}
      event.type = SWT.MouseUp;
      display.post(event);
    }  
  }.start();
  while (!shell.isDisposed()) {
    if (!display.readAndDispatch()) display.sleep();
  }
  display.dispose();
}
}

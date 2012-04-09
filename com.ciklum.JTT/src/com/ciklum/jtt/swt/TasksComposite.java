package com.ciklum.jtt.swt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.ciklum.jtt.resources.ColorSchemes;
import org.eclipse.wb.swt.SWTResourceManager;

public class TasksComposite extends Composite {
    private GC tCompGC;
    private Image imageTasks;
    private final Cursor CURSOR_SIZEALL;
    private final Cursor CURSOR_ARROW;
    private final int LEFT_OFFSET = 0;
    private final int RIGHT_OFFSET = 2;
    private final int GRADIENT_OFFSET = 2;
    private final int WIDTH_ARC = 7;
    private final int ITEM_HEIGHT = 50;
    
    private final int BTN_OFFSET = 5;
    private final int BTN_WIDTH = 40;
    private final int BTN_HEIGHT = ITEM_HEIGHT - BTN_OFFSET * 2;
    
    private final String START = "START";
    private final String STOP = "STOP";
    private final String TIME_FORMAT = "00:00:00";
    
    private int btnX;
    private int btnY;
    private int widthComposite;
    private boolean isMouseBtnOver;
    private boolean isStartedTask;
    private int startTextOffsetX;
    private int stopTextOffsetX;
    private int timeTextOffsetX;
    private Font timeFont;
    private Display display;
    private long startTime;
    private Date testDate;
    private DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    private Calendar calendar;
    
    Runnable timer = new Runnable() {
        public void run() {
            if (!isStartedTask) {
                return;
            }
            if (display != null && !display.isDisposed()) {
                long nowTime = System.currentTimeMillis();
                calendar.setTimeInMillis(nowTime - startTime);
                drawBtnStart(false);
                display.timerExec(1000, timer);
            }
        }
    };

    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public TasksComposite(Composite parent, int style) {
        super(parent, style);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        Rectangle rect = new Rectangle(0, 0, 1600, 1200);
        display = parent.getParent().getDisplay();
        imageTasks = new Image(display, rect);
        tCompGC = new GC(imageTasks);
        tCompGC.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
        tCompGC.fillRectangle(0, 0, 1600, 1200);
        int x = 0;
        for (int i = 0; i < START.length(); i ++) {
            x += tCompGC.getCharWidth(START.charAt(i));
        }
        startTextOffsetX = (BTN_WIDTH - x) / 2;
        x = 0;
        for (int i = 0; i < STOP.length(); i ++) {
            x += tCompGC.getCharWidth(STOP.charAt(i));
        }
        stopTextOffsetX = (BTN_WIDTH - x) / 2;
        Font prevFont = tCompGC.getFont();
        timeFont = new Font(display, "Arial", 7, SWT.BOLD);
        tCompGC.setFont(timeFont);
        x = 0;
        for (int i = 0; i < TIME_FORMAT.length(); i ++) {
            x += tCompGC.getCharWidth(TIME_FORMAT.charAt(i));
        }
        tCompGC.setFont(prevFont);
        timeTextOffsetX = (BTN_WIDTH - x) / 2;
        //CURSOR_SIZEALL = new Cursor(display, SWT.CURSOR_HAND);
        CURSOR_ARROW = new Cursor(display, SWT.CURSOR_ARROW);
        
        PaletteData paletteData = new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(255, 255, 255) });
        ImageData sourceData = new ImageData(32, 32, 1, paletteData);
        ImageData maskData = new ImageData(32, 32, 1, paletteData);

               int[] cursorSource = new int[] {
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,
                    1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,
                    1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                    1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 };

               int[] cursorMask = new int[] {
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,
                    0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,
                    0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,
                    0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,
                    0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,1,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                    0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, };

        sourceData.setPixels(0, 0, 1024, cursorSource, 0);
        maskData.setPixels(0, 0, 1024, cursorMask, 0);
        CURSOR_SIZEALL = new Cursor(display, sourceData, maskData, 16, 16);
        
        addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                e.gc.drawImage(imageTasks, e.x, e.y, e.width, e.height, e.x, e.y, e.width, e.height);
            }
        });
        
        addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent arg) {
                if (arg.x > btnX && arg.x < btnX + BTN_WIDTH && arg.y > btnY && arg.y < btnY + BTN_HEIGHT) {
                    setCursor(CURSOR_ARROW);
                    if (!isMouseBtnOver) {
                        isMouseBtnOver = true;
                        drawBtnStart(false);
                    }
                } else {
                    if (isMouseBtnOver) {
                        isMouseBtnOver = false;
                        drawBtnStart(false);
                    }
                    if (arg.x > LEFT_OFFSET && arg.x < widthComposite - RIGHT_OFFSET && arg.y > 5 && arg.y < ITEM_HEIGHT + 5) {
                        setCursor(CURSOR_SIZEALL);
                    } else {
                        setCursor(CURSOR_ARROW);
                    }
                }
            }
        });
        
        addMouseListener(new MouseListener() {
            public void mouseDown(MouseEvent e) {
                if (e.x > btnX && e.x < btnX + BTN_WIDTH && e.y > btnY && e.y < btnY + BTN_HEIGHT) {
                    drawBtnStart(true);
                }
            }

            public void mouseUp(MouseEvent e) {
                if (e.x > btnX && e.x < btnX + BTN_WIDTH && e.y > btnY && e.y < btnY + BTN_HEIGHT) {
                    isStartedTask = !isStartedTask;
                    if (isStartedTask) {
                        startTime = System.currentTimeMillis();
                        display.timerExec(1000, timer);
                    }
                    drawBtnStart(false);
                }
            }

            public void mouseDoubleClick(MouseEvent e) {
            }
        });
        
        /*addListener(SWT.MouseDown, new Listener() {
            public void handleEvent(Event e) {
                System.out.println("Mouse Down (button: " + e.button + " x: " + e.x + " y: " + e.y + ")");
            }
        });*/
        
        addControlListener(new ControlAdapter() {
            public void controlResized(ControlEvent e) {
                widthComposite = getClientArea().width;
                drawItem(5);
            }
        });
    }
    
    public void drawItem(int y) {
        int width = getClientArea().width;
        tCompGC.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
        tCompGC.fillRectangle(LEFT_OFFSET, y, width, ITEM_HEIGHT + 1);
        width = width - LEFT_OFFSET - RIGHT_OFFSET;
        btnX = width - BTN_OFFSET - BTN_WIDTH;
        btnY = y + BTN_OFFSET;
        tCompGC.setBackground(ColorSchemes.taskColor);
        tCompGC.fillRoundRectangle(LEFT_OFFSET, y, width, ITEM_HEIGHT, WIDTH_ARC, WIDTH_ARC);
        tCompGC.setBackground(ColorSchemes.taskGradientColor);
        tCompGC.setForeground(ColorSchemes.taskColor);
        tCompGC.fillGradientRectangle(LEFT_OFFSET + GRADIENT_OFFSET, y + GRADIENT_OFFSET, width - GRADIENT_OFFSET, ITEM_HEIGHT / 2 - GRADIENT_OFFSET, true);
        tCompGC.setBackground(ColorSchemes.taskColor);
        tCompGC.setForeground(ColorSchemes.taskGradientColor);
        tCompGC.fillGradientRectangle(LEFT_OFFSET + GRADIENT_OFFSET, y + ITEM_HEIGHT / 2, width - GRADIENT_OFFSET, ITEM_HEIGHT / 2 - GRADIENT_OFFSET, true);
        tCompGC.setBackground(ColorSchemes.taskBorderColor);
        tCompGC.drawRoundRectangle(LEFT_OFFSET, y, width, ITEM_HEIGHT, WIDTH_ARC, WIDTH_ARC);
        tCompGC.drawRoundRectangle(LEFT_OFFSET + 1, y + 1, width - 2, ITEM_HEIGHT - 2, WIDTH_ARC, WIDTH_ARC);
        
        drawBtnStart(false);
        redraw(LEFT_OFFSET, y, width + 1, ITEM_HEIGHT + 1, false);
    }
    
    protected void drawBtnStart(boolean isClick) {
        Color mainColor = ColorSchemes.taskStartColor;
        Color gradColor = ColorSchemes.taskStartGradientColor;
        if (isStartedTask) {
            mainColor = ColorSchemes.taskStopColor;
            gradColor = ColorSchemes.taskStopGradientColor;
        }
        tCompGC.setBackground(mainColor);
        tCompGC.fillRoundRectangle(btnX, btnY, BTN_WIDTH, BTN_HEIGHT, WIDTH_ARC, WIDTH_ARC);
        tCompGC.setForeground(gradColor);
        
        if (!isMouseBtnOver && !isClick) {
            tCompGC.fillGradientRectangle(btnX + GRADIENT_OFFSET, btnY + BTN_HEIGHT / 2, BTN_WIDTH - GRADIENT_OFFSET, BTN_HEIGHT / 2 - GRADIENT_OFFSET, true);
            tCompGC.setBackground(gradColor);
            tCompGC.setForeground(mainColor);
            tCompGC.fillGradientRectangle(btnX + GRADIENT_OFFSET, btnY + GRADIENT_OFFSET, BTN_WIDTH - GRADIENT_OFFSET, BTN_HEIGHT / 2 - GRADIENT_OFFSET, true);
        } else {
            if (isClick) {
                tCompGC.setBackground(gradColor);
                tCompGC.setForeground(mainColor);
            }
            tCompGC.fillGradientRectangle(btnX + GRADIENT_OFFSET, btnY + GRADIENT_OFFSET, BTN_WIDTH - GRADIENT_OFFSET, BTN_HEIGHT - GRADIENT_OFFSET, true);
        }
        tCompGC.setForeground(gradColor);
        tCompGC.drawRoundRectangle(btnX, btnY, BTN_WIDTH, BTN_HEIGHT, WIDTH_ARC, WIDTH_ARC);
        tCompGC.drawRoundRectangle(btnX + 1, btnY + 1, BTN_WIDTH - 2, BTN_HEIGHT - 2, WIDTH_ARC, WIDTH_ARC);
        tCompGC.setForeground(ColorSchemes.taskStartTextColor);
        if (!isStartedTask) {
            tCompGC.drawText(START, btnX + startTextOffsetX, btnY + BTN_HEIGHT / 2 - tCompGC.getFontMetrics().getHeight() / 2, true);
        } else {
            tCompGC.drawText(STOP, btnX + stopTextOffsetX, btnY + BTN_HEIGHT / 2, true);
            Font prevFont = tCompGC.getFont();
            tCompGC.setFont(timeFont);
            tCompGC.drawText(/*TIME_FORMAT*/formatter.format(calendar.getTime()), btnX + timeTextOffsetX, btnY + GRADIENT_OFFSET, true);
            tCompGC.setFont(prevFont);
        }
        redraw(btnX, btnY, btnX + BTN_WIDTH, btnY + BTN_HEIGHT, false);
    }
    
    

    @Override
    protected void checkSubclass() {
        // Disable the check that prevents subclassing of SWT components
    }
    
    @Override
    public void dispose() {
        CURSOR_SIZEALL.dispose();
        CURSOR_ARROW.dispose();
        super.dispose();
    }
}

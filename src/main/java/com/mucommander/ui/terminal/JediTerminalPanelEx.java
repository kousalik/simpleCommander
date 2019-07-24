package com.mucommander.ui.terminal;

import com.jediterm.terminal.model.StyleState;
import com.jediterm.terminal.model.TerminalTextBuffer;
import com.jediterm.terminal.ui.settings.SettingsProvider;
import com.mucommander.commons.runtime.OsFamily;
import com.mucommander.ui.action.ActionKeymap;
import com.mucommander.ui.action.impl.TerminalPanelAction;
import com.mucommander.ui.main.MainFrame;
import org.jetbrains.annotations.NotNull;
import ru.trolsoft.calculator.CalculatorDialog;

import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class JediTerminalPanelEx extends com.jediterm.terminal.ui.TerminalPanel {

    private final MainFrame mainFrame;
    private final int keyModifier;
    private int lineHeight;

    JediTerminalPanelEx(@NotNull SettingsProvider settingsProvider, @NotNull TerminalTextBuffer terminalTextBuffer,
                        @NotNull StyleState styleState, MainFrame mainFrame) {
        super(settingsProvider, terminalTextBuffer, styleState);
        this.mainFrame = mainFrame;
        this.keyModifier = OsFamily.MAC_OS_X.isCurrent() ? KeyEvent.META_MASK : KeyEvent.CTRL_MASK;
    }


    @Override
    public void processKeyEvent(KeyEvent e) {
        final int id = e.getID();

//        if (e.getModifiers() == InputEvent.ALT_MASK && e.getKeyCode() == KeyEvent.VK_C) {
//            if (id == KeyEvent.KEY_RELEASED) {
//                new CalculatorDialog(mainFrame).showDialog();
//            }
//            e.consume();
//            return;
//        }

        if (id == KeyEvent.KEY_PRESSED) {
            String actionId = ActionKeymap.getRegisteredActionIdForKeystroke(KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiers(), false));
            if (TerminalPanelAction.Descriptor.ACTION_ID.equals(actionId)) {
                mainFrame.showTerminalPanel(false);
                return;
            }
            if (e.getModifiers() == keyModifier) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        resizePanel(1);
                        return;
                    case KeyEvent.VK_DOWN:
                        resizePanel(-1);
                        return;
                    case KeyEvent.VK_LEFT:
                        resizePanel(-10);
                        return;
                    case KeyEvent.VK_RIGHT:
                        resizePanel(10);
                        return;
                    case KeyEvent.VK_1:
                        mainFrame.getLeftPanel().getFileTable().requestFocus();
                        return;
                    case KeyEvent.VK_2:
                        mainFrame.getRightPanel().getFileTable().requestFocus();
                        return;
                }
            }
            myKeyListener.keyPressed(e);
        } else if (id == KeyEvent.KEY_TYPED) {
            myKeyListener.keyTyped(e);
        }

        if (e.getModifiers() == InputEvent.ALT_MASK && e.getKeyCode() == KeyEvent.VK_C) {
            if (id == KeyEvent.KEY_RELEASED) {
                new CalculatorDialog(mainFrame).showDialog();
            }
            e.consume();
            return;
        }

        e.consume();
        //mainFrame.dispatchEvent(e);
        //super.processKeyEvent(e);
    }

    private void resizePanel(int delta) {
        int lineHeight = getHeight() / getTerminalTextBuffer().getHeight();
        if (lineHeight > 0 && (lineHeight < this.lineHeight || this.lineHeight == 0)) {
            this.lineHeight = lineHeight;
        }
        lineHeight = this.lineHeight;
        int height = mainFrame.getTerminalPanelHeight() + delta * lineHeight;
        int minHeight = 2*lineHeight;
        int maxHeight = mainFrame.getHeight() - minHeight;

        if (delta < -2) {
            height = minHeight;
        } else if (delta > 2) {
            height = maxHeight;
            mainFrame.setTerminalPanelHeight(height);
        } else {
            if (height < minHeight) {
                return;
            } else if (height > maxHeight) {
                return;
            }
        }
//System.out.println("min " + minHeight + "  max " + maxHeight + "  line " + lineHeight + "  terminal " + mainFrame.getTerminalPanelHeight() + "  frame " + mainFrame.getHeight() + "  table " + mainFrame.getSplitPane().getHeight() + "   h " + height);

        mainFrame.setTerminalPanelHeight(height);
    }


}

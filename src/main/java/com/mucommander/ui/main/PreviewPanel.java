package com.mucommander.ui.main;

import com.mucommander.commons.file.AbstractFile;
import com.mucommander.ui.theme.Theme;
import com.mucommander.ui.theme.ThemeManager;
import com.mucommander.ui.viewer.FileViewer;
import com.mucommander.ui.viewer.UserCancelledException;
import com.mucommander.ui.viewer.ViewerRegistrar;
import com.mucommander.ui.viewer.text.TextArea;
import com.mucommander.ui.viewer.text.TextViewer;
import net.sf.jftp.gui.tasks.ImageViewer;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @author Oleg Trifonov
 *
 * Created on 26/09/2016.
 */
class PreviewPanel extends JPanel {

    private TextArea textArea;

    private TextViewer textViewer;
    private ImageViewer imageViewer;

    PreviewPanel() {
        super(new BorderLayout());

        // No decoration for this panel
        setBorder(null);
    }


    private void setupTextArea() {
        textArea.setCurrentLineHighlightColor(ThemeManager.getCurrentColor(Theme.EDITOR_CURRENT_BACKGROUND_COLOR));
        textArea.setAntiAliasingEnabled(true);
        textArea.setEditable(false);
        //textArea.addKeyListener(textAreaKeyListener);

        try {
            ThemeManager.readEditorTheme(ThemeManager.getCurrentSyntaxThemeName()).apply(textArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //textArea.setCodeFoldingEnabled(true);

        // Use theme colors and font
        textArea.setForeground(ThemeManager.getCurrentColor(Theme.EDITOR_FOREGROUND_COLOR));
        textArea.setCaretColor(ThemeManager.getCurrentColor(Theme.EDITOR_FOREGROUND_COLOR));
        Color background = ThemeManager.getCurrentColor(Theme.EDITOR_BACKGROUND_COLOR);
        textArea.setBackground(background);

        for (int i = 1; i <= textArea.getSecondaryLanguageCount(); i++) {
            textArea.setSecondaryLanguageBackground(i, background);
        }
        textArea.setSelectedTextColor(ThemeManager.getCurrentColor(Theme.EDITOR_SELECTED_FOREGROUND_COLOR));
        textArea.setSelectionColor(ThemeManager.getCurrentColor(Theme.EDITOR_SELECTED_BACKGROUND_COLOR));
        textArea.setFont(ThemeManager.getCurrentFont(Theme.EDITOR_FONT));
        textArea.setCodeFoldingEnabled(true);

        textArea.setWrapStyleWord(true);

        textArea.addMouseWheelListener(e -> {
            boolean isCtrlPressed = (e.getModifiers() & KeyEvent.CTRL_MASK) != 0;
            if (isCtrlPressed) {
                Font currentFont = textArea.getFont();
                int currentFontSize = currentFont.getSize();
                boolean rotationUp = e.getWheelRotation() < 0;
                if (rotationUp || currentFontSize > 1) {
                    Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFontSize + (rotationUp ? 1 : -1));
                    textArea.setFont(newFont);
                }
            } else {
                textArea.getParent().dispatchEvent(e);
            }
        });

        //textArea.addCaretListener(caretListener);
    }


    void loadFile(AbstractFile file) {
        if (file == null) {
            clearPreviewArea();
            doLayout();
            repaint();
        } else if (file.isDirectory()) {
            clearPreviewArea();
            doLayout();
            repaint();
        } else {
            previewFile(file);
        }

    }


    private void previewFile(AbstractFile file) {
        try {
            FileViewer viewer = ViewerRegistrar.createFileViewer(file, null, null);
            clearPreviewArea();
            if (viewer != null) {
                viewer.open(file);
                add(viewer);
            }
        } catch (UserCancelledException | IOException e) {
            e.printStackTrace();
        }
    }


    private void clearPreviewArea() {
        while (getComponents().length > 0) {
            remove(0);
        }
    }
}

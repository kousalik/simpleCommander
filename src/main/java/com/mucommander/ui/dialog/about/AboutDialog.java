/*
 * This file is part of muCommander, http://www.mucommander.com
 * Copyright (C) 2002-2012 Maxence Bernard
 *
 * muCommander is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * muCommander is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mucommander.ui.dialog.about;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.mucommander.RuntimeConstants;
import com.mucommander.desktop.DesktopManager;
import com.mucommander.ui.action.ActionProperties;
import com.mucommander.ui.action.impl.GoToWebsiteAction;
import com.mucommander.ui.action.impl.ShowAboutAction;
import com.mucommander.ui.dialog.FocusDialog;
import com.mucommander.ui.icon.IconManager;
import com.mucommander.ui.layout.FluentPanel;
import com.mucommander.ui.main.MainFrame;
import com.mucommander.ui.theme.Theme;
import com.mucommander.ui.theme.ThemeManager;

/**
 * Dialog displaying information about muCommander.
 * @author Maxence Bernard, Nicolas Rinaudo
 */
public class AboutDialog extends FocusDialog implements ActionListener {
    // - Styles -----------------------------------------------------------------
    // --------------------------------------------------------------------------
    /** Style for normal text. */
    private static final String STYLE_NORMAL  = "normal";
    /** Style for headers. */
    private static final String STYLE_HEADER  = "header";
    /** Style for URLs. */
    private static final String STYLE_URL     = "url";
    /** Style for an item's details. */
    private static final String STYLE_DETAILS = "details";
    /** Style for a section title. */
    private static final String STYLE_TITLE   = "title";
    /** Line break string. */
    private static final String LINE_BREAK    = System.getProperty("line.separator");



    // - UI components ----------------------------------------------------------
    // --------------------------------------------------------------------------
    /** Button that closes the dialog. */
    private JButton btnOk;
    /** Button that opens trolCommander's homepage in a browser. */
    private JButton btnHome;
    /** Button that opens the trolCommander's license. */
    private JButton btnLicense;
    /** Panel in which all the textual information is displayed. */
    private JScrollPane textPanel;



    // - Initialization ---------------------------------------------------------
    // --------------------------------------------------------------------------
    /**
     * Creates a new AboutDialog.
     * @param mainFrame frame this dialog is relative to.
     */
    public AboutDialog(MainFrame mainFrame) {
        super(mainFrame, ActionProperties.getActionLabel(ShowAboutAction.Descriptor.ACTION_ID), mainFrame);

        // Initializes the dialog's content.
        Container contentPane = getContentPane();
        contentPane.add(createIconPanel(), BorderLayout.WEST);
        contentPane.add(createCreditsPanel(), BorderLayout.EAST);
        setResizable(false);

        // Makes sure the scroll pane is properly initialized.
        SwingUtilities.invokeLater(() -> textPanel.getViewport().setViewPosition(new Point(0, 0)));

        pack();

        // Makes OK the default action.
        setInitialFocusComponent(btnOk);
        getRootPane().setDefaultButton(btnOk);
    }

    /**
     * Creates the panel that contains all of the about box's text.
     */
    private JScrollPane createCreditsPanel() {
        JTextPane text = new JTextPane();
        StyledDocument doc  = text.getStyledDocument();

        text.setBackground(ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));

        setStyles(doc);
        text.setEditable(false);
        try {
            // Team.
            insertTitle(doc,          "The trolCommander team");

            // Core developers.
            insertHeader(doc,         "Developers and contributors");
            insertNormalString(doc,   "Maxence Bernard");
            insertNormalString(doc,   "Arik Hadas");
            insertNormalString(doc,   "Mariusz Jakubowski");
            insertNormalString(doc,   "Nicolas Rinaudo");
            insertNormalString(doc,   "Oleg Trifonov");
            insertNormalString(doc,   "Ondřej Zima");
            insertNormalString(doc,   "Martin Kortkamp");
//            insertLineBreak(doc);

            // Contributors.
  //          insertHeader(doc,         "Contributors");
            insertNormalString(doc,   "Ivan Baidakov");
            insertNormalString(doc,   "Vassil Dichev");
            insertNormalString(doc,   "Karel Klic");
            insertNormalString(doc,   "David Kovar");
            insertNormalString(doc,   "Joshua Lebo");
            insertNormalString(doc,   "LeO");
            insertNormalString(doc,   "Xavier Martin");
            insertNormalString(doc,   "Alejandro Scandroli");
            insertNormalString(doc,   "Alexander Yerenkow");
            insertNormalString(doc,   "Johann Schmitz");
            insertLineBreak(doc);

            // QA
            insertHeader(doc,         "QA");
            insertNormalString(doc,   "Joshua Lebo");
            insertLineBreak(doc);

            // Translators.
            insertHeader(doc,         "Translators");
            insertDetailedString(doc, "4X_Pro",              "Russian");
            insertDetailedString(doc, "Roberto Angeletti",   "Italian");
            //insertDetailedString(doc, "Emre Aytaç",          "Turkish");
            insertDetailedString(doc, "Tamás Balogh-Walder", "Hungarian");
            insertDetailedString(doc, "Mykola Bilovus",      "Ukrainian");
            insertDetailedString(doc, "ChArLoK_16",          "Arabic");
            insertDetailedString(doc, "György Varga",        "Hungarian");
            insertDetailedString(doc, "Frank Berger",        "German");
            insertDetailedString(doc, "Tony Klüver",         "German");
            insertDetailedString(doc, "Marcos Cobeña",       "Spanish");
            insertDetailedString(doc, "Cristiano Duarte",    "Brazilian Portuguese");
            insertDetailedString(doc, "Jakob Ekström",       "Swedish");
            insertDetailedString(doc, "Catalin Hritcu",      "Romanian");
            insertDetailedString(doc, "Kent Hsu",            "Traditional Chinese");
            insertDetailedString(doc, "Jioh L. Jung",        "Korean");
            insertDetailedString(doc, "Andrzej Kosiński",    "Polish");
            insertDetailedString(doc, "Joze Kovacic",        "Slovenian");
            insertDetailedString(doc, "Oleksandr Kovalchuk", "Ukrainian");
            insertDetailedString(doc, "Pieter Kristensen",   "Dutch");
            insertDetailedString(doc, "Ján Ľudvík",          "Slovak");
            insertDetailedString(doc, "Jaromír Mára",        "Czech");
            insertDetailedString(doc, "Xavi Miró",           "Spanish");
            insertDetailedString(doc, "Evgeny Morozov",      "Russian");
            insertDetailedString(doc, "Jonathan Murphy",     "British English");
            insertDetailedString(doc, "Nardog",              "Japanese");
            insertDetailedString(doc, "Jordi Plantalech",    "Catalan");
            insertDetailedString(doc, "Alexey Sirotov",      "Russian");
            insertDetailedString(doc, "Jeppe Toustrup",      "Danish");
            insertDetailedString(doc, "Peter Vasko",         "Czech");
            insertDetailedString(doc, "vboo",                "Belarusian");
            insertDetailedString(doc, "whiteriver",          "Simplified Chinese");
            insertLineBreak(doc);

            // Special thanks.
            insertHeader(doc,         "Special thanks");
            insertDetailedString(doc, "Semyon Filippov",    "muCommander icon");
            insertDetailedString(doc, "Stefano Perelli",    "Former muCommander icon");
            insertLineBreak(doc);
            insertLineBreak(doc);

            // Powered by.
            insertTitle(doc,         "Powered by");

            // External Libraries.
            insertHeader(doc,         "Libraries");
            insertDetailedUrl(doc,    "Ant",                 "Apache License",                       "http://ant.apache.org");
            insertDetailedUrl(doc,    "Apache Commons",      "Apache License",                       "http://commons.apache.org");
            insertDetailedUrl(doc,    "Apache Hadoop",       "Apache License",                       "http://hadoop.apache.org");
            insertDetailedUrl(doc,    "Furbelow",            "LGPL",                                 "http://sourceforge.net/projects/furbelow");
            insertDetailedUrl(doc,    "ICU4J",               "ICU License",                          "http://www.icu-project.org");
            insertDetailedUrl(doc,    "J2SSH",               "LGPL",                                 "http://sourceforge.net/projects/sshtools");
            insertDetailedUrl(doc,    "J7Zip",               "LGPL",                                 "http://sourceforge.net/projects/p7zip/");
            insertDetailedUrl(doc,    "jCIFS",               "LGPL",                                 "http://jcifs.samba.org");
            insertDetailedUrl(doc,    "JetS3t",              "Apache License",                       "http://jets3t.s3.amazonaws.com/index.html");
            insertDetailedUrl(doc,    "JmDNS",               "LGPL",                                 "http://jmdns.sourceforge.net");
            insertDetailedUrl(doc,    "JNA",                 "LGPL",                                 "http://jna.dev.java.net");
            insertDetailedUrl(doc,    "JUnRar",              "Freeware",                             "https://github.com/edmund-wagner/junrar");
            insertDetailedUrl(doc,    "Yanfs",               "BSD",                                  "http://yanfs.dev.java.net");
            insertDetailedUrl(doc,    "RSyntaxTextArea",     "Modified BSD",                         "http://fifesoft.com/rsyntaxtextarea");
            insertDetailedUrl(doc,    "ICEpdf",              "Apache License",                       "http://www.icesoft.org/java/home.jsf");
            insertDetailedUrl(doc,    "image4j",             "LGPL",                                 "http://image4j.sourceforge.net");
            insertDetailedUrl(doc,    "JediTerm",            "LGPL",                                 "https://github.com/JetBrains/jediterm");
            insertDetailedUrl(doc,    "JADB",                "Apache License 2.0",                   "https://github.com/vidstige/jadb");

            insertDetailedUrl(doc,    "Mark James' icons",   "Creative Commons Attribution License", "http://famfamfam.com");
            insertLineBreak(doc);

            // External tools.
            insertHeader(doc,         "Tools");
            insertDetailedUrl(doc,    "Ant",                 "Apache Software License",              "http://ant.apache.org");
            insertDetailedUrl(doc,    "AntDoclet",           "GPL",                                  "http://antdoclet.neuroning.com/");
            insertDetailedUrl(doc,    "jdeb",                "Apache Software License",              "http://vafer.org/projects/jdeb/");
            insertDetailedUrl(doc,    "Launch4j",            "GPL",                                  "http://launch4j.sourceforge.net");
            insertDetailedUrl(doc,    "NSIS",                "zlib/libpng license",                  "http://nsis.sourceforge.net");
            insertDetailedUrl(doc,    "p7zip",               "LGPL",                                 "http://p7zip.sourceforge.net");
            insertDetailedUrl(doc,    "ProGuard",            "GPL",                                  "http://proguard.sourceforge.net");
            insertLineBreak(doc);
            insertLineBreak(doc);

            // Version information
            insertTitle(doc,         "Version information");

            // VM information.
            insertHeader(doc,         "trolCommander");
            insertNormalString(doc,   "Version: " + RuntimeConstants.VERSION);
            insertNormalString(doc,   "Build date: " + getFormatedDate());
            insertLineBreak(doc);

            // VM information.
            insertHeader(doc,         "Java");
            insertNormalString(doc,   "Runtime version: " + System.getProperty("java.version"));
            insertNormalString(doc,   "VM name: " + System.getProperty("java.vm.name"));
            insertNormalString(doc,   "VM version: " + System.getProperty("java.vm.version"));
            insertNormalString(doc,   "VM vendor: " + System.getProperty("java.vm.vendor"));
            insertLineBreak(doc);

            // OS information.
            insertHeader(doc,         "OS");
            insertNormalString(doc,   "Name: " + System.getProperty("os.name"));
            insertNormalString(doc,   "Version: " + System.getProperty("os.version"));
            insertNormalString(doc,   "Architecture: " + System.getProperty("os.arch"));
            insertLineBreak(doc);

            // Locale information.
            Locale locale = Locale.getDefault();
            insertHeader(doc,         "Locale");
            insertNormalString(doc,   "Language: " + locale.getLanguage());
            insertNormalString(doc,   "Country: " + locale.getCountry());
            insertNormalString(doc,   "Encoding: " + System.getProperty("file.encoding"));
        } catch(Exception ignore) {}

        textPanel = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textPanel.getViewport().setPreferredSize(new Dimension((int)text.getPreferredSize().getWidth(), 350));

        return textPanel;
    }

    /**
     * Creates the about box's left panel.
     */
	private JPanel createIconPanel() {

		// Makes sure the panel's a bit roomier than the default configuration.
		return new FluentPanel(new BorderLayout()) {
			@Override
			public Insets getInsets() {
				return new Insets(10, 10, 0, 10);
			}
		}.add(new FluentPanel(new BorderLayout())
                  .add(new JLabel(IconManager.getIcon(IconManager.IconSet.TROLCOMMANDER, "icon128_24.png")), BorderLayout.NORTH)
                  .add(new FluentPanel(new FlowLayout(FlowLayout.CENTER)).add(createAppString()), BorderLayout.CENTER)
  				  .add(new FluentPanel(new FlowLayout(FlowLayout.CENTER)).add(createCopyright()), BorderLayout.SOUTH),

              BorderLayout.NORTH)
		 .add(new FluentPanel(new BorderLayout())
                         .add(createHomeComponent(), BorderLayout.NORTH)
                         .add(createLicenseButton(), BorderLayout.CENTER)
                         .add(createOkButton(), BorderLayout.SOUTH),
                 BorderLayout.SOUTH);
	}

    private JLabel createCopyright() {
        return new JLabel("<html>©" + RuntimeConstants.COPYRIGHT+ " Oleg Trifonov<p/><br/>Based on <b>muCommander</b><br>© 2002 - 2013 Maxence Bernard</br>");
    }

	private JLabel createAppString() {
		return createBoldLabel(RuntimeConstants.APP_STRING);
	}

	private Component createHomeComponent() {
		return DesktopManager.canBrowse() ?
      		  createHomeButton()
      		  : new FluentPanel(new FlowLayout(FlowLayout.CENTER)).add(new JLabel(RuntimeConstants.HOMEPAGE_URL));
	}

	private JButton createHomeButton() {
		btnHome = new JButton(ActionProperties.getActionLabel(GoToWebsiteAction.Descriptor.ACTION_ID));
		btnHome.addActionListener(this);
		return btnHome;
	}

	private JButton createLicenseButton() {
		btnLicense = new JButton(i18n("license"));
		btnLicense.addActionListener(this);
		return btnLicense;
	}

	private JButton createOkButton() {
		btnOk = new JButton(i18n("ok"));
		btnOk.addActionListener(this);
		return btnOk;
	}



    // - Text panel handling ----------------------------------------------------
    // --------------------------------------------------------------------------
    /**
     * Creates different styles in the specified <code>StyledDocument</code>
     * @param doc document in which to create the styles.
     */
    private static void setStyles(StyledDocument doc) {
        Style master = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Font font = ThemeManager.getCurrentFont(Theme.FILE_TABLE_FONT);

        // Normal style.
        master = doc.addStyle(STYLE_NORMAL, master);
        StyleConstants.setFontFamily(master, font.getFamily());
        StyleConstants.setFontSize(master, font.getSize());
        StyleConstants.setForeground(master, ThemeManager.getCurrentColor(Theme.FILE_FOREGROUND_COLOR));
        StyleConstants.setBackground(master, ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));
        StyleConstants.setLeftIndent(master, 10);
        StyleConstants.setRightIndent(master, 10);
        StyleConstants.setLineSpacing(master, (float)0.2);
        doc.setParagraphAttributes(0, 0, master, true);

        // Header style.
        Style currentStyle = doc.addStyle(STYLE_HEADER, master);
        StyleConstants.setBold(currentStyle, true);
        StyleConstants.setFontSize(currentStyle, font.getSize() + 2);
        StyleConstants.setForeground(currentStyle, ThemeManager.getCurrentColor(Theme.FOLDER_FOREGROUND_COLOR));
        StyleConstants.setBackground(currentStyle, ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));

        // Title style.
        currentStyle = doc.addStyle(STYLE_TITLE, currentStyle);
        StyleConstants.setAlignment(currentStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(currentStyle, ThemeManager.getCurrentColor(Theme.ARCHIVE_FOREGROUND_COLOR));
        StyleConstants.setBackground(currentStyle, ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));

        // Details style.
        currentStyle = doc.addStyle(STYLE_DETAILS, master);
        StyleConstants.setForeground(currentStyle, ThemeManager.getCurrentColor(Theme.HIDDEN_FILE_FOREGROUND_COLOR));
        StyleConstants.setBackground(currentStyle, ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));

        // URL style.
        currentStyle = doc.addStyle(STYLE_URL, master);
        StyleConstants.setForeground(currentStyle, ThemeManager.getCurrentColor(Theme.SYMLINK_FOREGROUND_COLOR));
        StyleConstants.setBackground(currentStyle, ThemeManager.getCurrentColor(Theme.FILE_TABLE_BACKGROUND_COLOR));
        StyleConstants.setUnderline(currentStyle, true);
    }

    /**
     * Inserts the specified header in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  string               text to insert.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertHeader(StyledDocument doc, String string) throws BadLocationException {
        doc.insertString(doc.getLength(), string + LINE_BREAK, doc.getStyle(STYLE_HEADER));
    }

    /**
     * Inserts the specified string in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  string               text to insert.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertNormalString(StyledDocument doc, String string) throws BadLocationException {
        doc.insertString(doc.getLength(), string + LINE_BREAK, doc.getStyle(STYLE_NORMAL));
    }

    /**
     * Inserts the specified string and details in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  string               text to insert.
     * @param  details              details that will be added to the text.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertDetailedString(StyledDocument doc, String string, String details) throws BadLocationException {
        doc.insertString(doc.getLength(), string + " ", doc.getStyle(STYLE_NORMAL));
        doc.insertString(doc.getLength(), "(" + details + ")" + LINE_BREAK, doc.getStyle(STYLE_DETAILS));
    }

    /**
     * Inserts the specified URL in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  url                  url to insert.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertUrl(StyledDocument doc, String url) throws BadLocationException {
        doc.insertString(doc.getLength(), "    ", doc.getStyle(STYLE_NORMAL));
        doc.insertString(doc.getLength(), url + LINE_BREAK, doc.getStyle(STYLE_URL));
    }

    /**
     * Inserts a line break in the specified document.
     * @param  doc                  document in which to insert the text.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertLineBreak(StyledDocument doc) throws BadLocationException {
        doc.insertString(doc.getLength(), LINE_BREAK, doc.getStyle(STYLE_NORMAL));
    }

    /**
     * Inserts the specified string, details and URL in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  string               text to insert.
     * @param  details              details that will be added to the text.
     * @param  url                  url that will be added to the text.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertDetailedUrl(StyledDocument doc, String string, String details, String url) throws BadLocationException {
        insertDetailedString(doc, string, details);
        insertUrl(doc, url);
    }

    /**
     * Inserts the specified title in the specified document.
     * @param  doc                  document in which to insert the text.
     * @param  string               text to insert.
     * @throws BadLocationException thrown if something wrong happened to the document.
     */
    private static void insertTitle(StyledDocument doc, String string) throws BadLocationException {
        int pos = doc.getLength();
        Style style = doc.getStyle(STYLE_TITLE);

        string += LINE_BREAK;
        doc.insertString(pos, string, style);
        doc.setParagraphAttributes(pos, string.length(), style, true);
        doc.setParagraphAttributes(doc.getLength(), 0, doc.getStyle(STYLE_NORMAL), true);

        insertLineBreak(doc);
    }



    // - Action listening -------------------------------------------------------
    // --------------------------------------------------------------------------
    /**
     * Reacts to validations of the <code>OK</code> or <code>home</code> buttons.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnOk) {
            dispose();
        } else if(e.getSource() == btnHome) {
            try {
                DesktopManager.browse(new URL(RuntimeConstants.HOMEPAGE_URL));
            } catch(IOException ignored) {} // Ignores errors here as there really isn't anything we can do.

        } else if(e.getSource() == btnLicense) {
            new LicenseDialog(this).showDialog();
        }
    }



    // - Misc. methods ----------------------------------------------------------
    // --------------------------------------------------------------------------
    /**
     * Returns a formatted version of muCommander's build date.
     * @return a formatted version of muCommander's build date.
     */
    private String getFormatedDate() {
        return RuntimeConstants.BUILD_DATE.substring(0, 4) + '/' +
                RuntimeConstants.BUILD_DATE.substring(4, 6) +
                '/' +
                RuntimeConstants.BUILD_DATE.substring(6, 8);
    }

    /**
     * Creates a <code>JLabel</code> displaying the specified text using a bold font.
     * @param  text text to display in the label.
     * @return       a <code>JLabel</code> displaying the specified text using a bold font.
     */
    private static JLabel createBoldLabel(String text) {
        JLabel label = new JLabel(text);
        Font font  = label.getFont();
        label.setFont(new Font(font.getFontName(), font.getStyle() | Font.BOLD, font.getSize()));

        return label;
    }

}
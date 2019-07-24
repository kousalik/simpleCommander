package com.mucommander.ui.viewer.html;

import com.mucommander.commons.file.AbstractFile;
import com.mucommander.commons.file.impl.local.LocalFile;
import com.mucommander.commons.io.StreamUtils;
import com.mucommander.ui.viewer.FileViewer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.fife.ui.StatusBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HtmlViewer extends FileViewer {

    private WebView webView;
    private String url;
    private String content;

    HtmlViewer() {
        super();
    }

    @Override
    protected void show(final AbstractFile file) throws IOException {
        boolean localFile = file.getTopAncestor() instanceof LocalFile;
        if (localFile) {
            url = file.getJavaNetURL().toString();
        } else {
            InputStream is = file.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StreamUtils.copyStream(is, out);
            content = out.toString();
        }
        try {
            final JFXPanel jfxPanel = new JFXPanel();
            setComponentToPresent(jfxPanel);
            getViewport().addChangeListener(e -> {
                if (webView != null) {
                    int w = getViewport().getWidth();
                    int h = getViewport().getHeight();
                    webView.setMinWidth(w);
                    webView.setMaxWidth(w);
                    webView.setMinHeight(h);
                    webView.setMaxHeight(h);
                }
            });

            Platform.setImplicitExit(false);
            Platform.runLater(() -> initFX(jfxPanel));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /* Creates a WebView and fires up google.com */
    private void initFX(final JFXPanel fxPanel) {
        try {
            Group group = new Group();

            Scene scene = new Scene(group);
            fxPanel.setScene(scene);

            webView = new WebView();
            group.getChildren().add(webView);

            WebEngine webEngine = webView.getEngine();
            if (url != null) {
                webEngine.load(url);
            } else {
                webEngine.loadContent(content);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected StatusBar getStatusBar() {
        return null;
    }

    @Override
    protected void saveStateOnClose() {
    }

    @Override
    protected void restoreStateOnStartup() {

    }
}

package com.mucommander.ui.viewer.pdf;

import com.mucommander.commons.file.AbstractFile;
import com.mucommander.ui.viewer.FileViewer;
import org.fife.ui.StatusBar;
//import org.icepdf.ri.common.MyAnnotationCallback;
//import org.icepdf.ri.common.SwingController;
//import org.icepdf.ri.common.SwingViewBuilder;
// TODO Kousalik - icepdf depndency fcks up gradle - fugire out
import java.io.IOException;
import java.io.InputStream;

public class PdfViewer extends FileViewer {

//    private SwingController controller;

    PdfViewer() {
//        // create a controller and a swing factory
//        controller = new SwingController();
//        SwingViewBuilder factory = new SwingViewBuilder(controller);
//        // add interactive mouse link annotation support via callback
//        controller.getDocumentViewController().setAnnotationCallback(
//                new org.icepdf.ri.common.MyAnnotationCallback(
//                        controller.getDocumentViewController()));
//
//        // build viewer component and add it to the applet content pane.
//        MyAnnotationCallback myAnnotationCallback = new MyAnnotationCallback(
//                controller.getDocumentViewController());
//        controller.getDocumentViewController().setAnnotationCallback(myAnnotationCallback);
//
//        // build the viewer with a menubar
//        //getContentPane().setLayout(new BorderLayout());
//        //getContentPane().add(factory.buildViewerPanel(), BorderLayout.CENTER);
//        //getContentPane().add(factory.buildCompleteMenuBar(), BorderLayout.NORTH);
//        setComponentToPresent(factory.buildViewerPanel());
    }

    @Override
    protected void show(AbstractFile file) throws IOException {
        String description = "";
        String path = file.getPath();
        try (InputStream is = file.getInputStream()) {
//            controller.openDocument(is, description, path);
        }
    }

    @Override
    protected StatusBar getStatusBar() {
        return null;
    }

    @Override
    protected void saveStateOnClose() {
//        org.icepdf.core.util.Library.shutdownThreadPool();
    }

    @Override
    protected void restoreStateOnStartup() {
//        org.icepdf.core.util.Library.initializeThreadPool();
    }

}

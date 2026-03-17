package org.example.labo5;

import javax.swing.*;
import java.awt.*;

public class ControllerDebugPanel {

    private ImageViewerApp app;
    private MouseController mouseController;
    private MenuController menuController;

    private JLabel zoomP1Label;
    private JLabel zoomP2Label;
    private JLabel offsetP1Label;
    private JLabel offsetP2Label;
    private JLabel stackLabel;
    private JTextArea logArea;

    public ControllerDebugPanel(ImageViewerApp app) {
        this.app = app;
    }

    public void attach() {
        JPanel debugPanel = buildDebugPanel();
        app.add(debugPanel, BorderLayout.SOUTH);
        app.revalidate();
    }

    private JPanel buildDebugPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Debug Contrôleur"));
        panel.setPreferredSize(new Dimension(0, 220));

        panel.add(buildStatePanel(), BorderLayout.WEST);
        panel.add(buildButtonPanel(), BorderLayout.CENTER);
        panel.add(buildLogPanel(), BorderLayout.EAST);

        return panel;
    }

    private JPanel buildStatePanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 2, 2));
        panel.setBorder(BorderFactory.createTitledBorder("État des Perspectives"));
        panel.setPreferredSize(new Dimension(280, 0));

        zoomP1Label   = new JLabel("Zoom P1 : —");
        zoomP2Label   = new JLabel("Zoom P2 : —");
        offsetP1Label = new JLabel("Offset P1 : —");
        offsetP2Label = new JLabel("Offset P2 : —");
        stackLabel    = new JLabel("Stack undo : 0 commande(s)");

        panel.add(zoomP1Label);
        panel.add(offsetP1Label);
        panel.add(zoomP2Label);
        panel.add(offsetP2Label);
        panel.add(new JSeparator());
        panel.add(stackLabel);

        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Actions Contrôleur"));

        JButton zoomInP1  = new JButton("Zoom In  P1");
        JButton zoomOutP1 = new JButton("Zoom Out P1");
        JButton zoomInP2  = new JButton("Zoom In  P2");
        JButton zoomOutP2 = new JButton("Zoom Out P2");
        JButton transP1   = new JButton("Translate P1 (+20,+20)");
        JButton transP2   = new JButton("Translate P2 (-20,-20)");
        JButton undoBtn   = new JButton("⟲  UNDO");
        JButton refreshBtn = new JButton("Afficher état");

        undoBtn.setBackground(new Color(255, 200, 200));
        undoBtn.setFont(undoBtn.getFont().deriveFont(Font.BOLD));

        zoomInP1.addActionListener(e -> testZoom(0, 0.1));
        zoomOutP1.addActionListener(e -> testZoom(0, -0.1));
        zoomInP2.addActionListener(e -> testZoom(1, 0.1));
        zoomOutP2.addActionListener(e -> testZoom(1, -0.1));
        transP1.addActionListener(e -> testTranslate(0, 20, 20));
        transP2.addActionListener(e -> testTranslate(1, -20, -20));
        undoBtn.addActionListener(e -> testUndo());
        refreshBtn.addActionListener(e -> refreshState());

        panel.add(zoomInP1);
        panel.add(zoomOutP1);
        panel.add(zoomInP2);
        panel.add(zoomOutP2);
        panel.add(transP1);
        panel.add(transP2);
        panel.add(undoBtn);
        panel.add(refreshBtn);

        return panel;
    }

    private JPanel buildLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Log"));
        panel.setPreferredSize(new Dimension(300, 0));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        panel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        JButton clearBtn = new JButton("Effacer log");
        clearBtn.addActionListener(e -> logArea.setText(""));
        panel.add(clearBtn, BorderLayout.SOUTH);

        return panel;
    }

    private void testZoom(int perspectiveIndex, double delta) {
        ImageDocument doc = getDocument();
        if (doc == null) return;
        if (perspectiveIndex >= doc.getPerspectives().size()) {
            log("ERREUR : perspective " + perspectiveIndex + " inexistante.");
            return;
        }

        Perspective perspective = doc.getPerspectives().get(perspectiveIndex);
        double before = perspective.getZoomFactor();

        PerspectiveView fakeView = new PerspectiveView(
                "debug", doc.getImage(), perspective, new MouseController()
        );

        mouseController = new MouseController();
        mouseController.handleZoom(fakeView, delta);

        double after = perspective.getZoomFactor();
        log(String.format("ZOOM P%d : %.2f → %.2f  (delta=%.2f)",
                perspectiveIndex + 1, before, after, delta));
        refreshState();
    }

    private void testTranslate(int perspectiveIndex, int dx, int dy) {
        ImageDocument doc = getDocument();
        if (doc == null) return;
        if (perspectiveIndex >= doc.getPerspectives().size()) {
            log("ERREUR : perspective " + perspectiveIndex + " inexistante.");
            return;
        }

        Perspective perspective = doc.getPerspectives().get(perspectiveIndex);
        int beforeX = perspective.getOffsetX();
        int beforeY = perspective.getOffsetY();

        PerspectiveView fakeView = new PerspectiveView(
                "debug", doc.getImage(), perspective, new MouseController()
        );

        mouseController = new MouseController();
        mouseController.handleTranslation(fakeView, dx, dy, 0);

        int afterX = perspective.getOffsetX();
        int afterY = perspective.getOffsetY();

        if (beforeX == afterX && beforeY == afterY) {
            log(String.format("TRANSLATE P%d : bloquée (zoom=%.2f ≤ 1.0)",
                    perspectiveIndex + 1, perspective.getZoomFactor()));
        } else {
            log(String.format("TRANSLATE P%d : (%d,%d) → (%d,%d)",
                    perspectiveIndex + 1, beforeX, beforeY, afterX, afterY));
        }
        refreshState();
    }

    private void testUndo() {
        if (!CommandManager.getInstance().canUndo()) {
            log("UNDO : pile vide, rien à annuler.");
            return;
        }

        ImageDocument doc = getDocument();
        String before = doc != null ? snapshotState(doc) : "—";

        CommandManager.getInstance().undoLastCommand();

        String after = doc != null ? snapshotState(doc) : "—";
        log("UNDO exécuté");
        log("  avant : " + before);
        log("  après : " + after);
        refreshState();
    }

    private void refreshState() {
        ImageDocument doc = getDocument();

        if (doc == null) {
            String msg = "Aucun document chargé — charger une image d'abord.";
            zoomP1Label.setText("Zoom P1 : —");
            zoomP2Label.setText("Zoom P2 : —");
            offsetP1Label.setText("Offset P1 : —");
            offsetP2Label.setText("Offset P2 : —");
            stackLabel.setText("Stack undo : —");
            log("ÉTAT : " + msg);
            return;
        }

        if (doc.getPerspectives().size() < 2) {
            String msg = "Document chargé mais moins de 2 perspectives trouvées ("
                    + doc.getPerspectives().size() + ").";
            zoomP1Label.setText("Zoom P1 : —");
            zoomP2Label.setText("Zoom P2 : —");
            offsetP1Label.setText("Offset P1 : —");
            offsetP2Label.setText("Offset P2 : —");
            log("ÉTAT : " + msg);
            return;
        }

        Perspective p1 = doc.getPerspectives().get(0);
        Perspective p2 = doc.getPerspectives().get(1);

        zoomP1Label.setText(String.format("Zoom P1 : %.2f", p1.getZoomFactor()));
        offsetP1Label.setText(String.format("Offset P1 : (%d, %d)", p1.getOffsetX(), p1.getOffsetY()));
        zoomP2Label.setText(String.format("Zoom P2 : %.2f", p2.getZoomFactor()));
        offsetP2Label.setText(String.format("Offset P2 : (%d, %d)", p2.getOffsetX(), p2.getOffsetY()));

        int stackSize = getStackSize();
        stackLabel.setText("Stack undo : " + stackSize + " commande(s)");
        stackLabel.setForeground(stackSize > 0 ? new Color(0, 120, 0) : Color.GRAY);

        log(String.format("ÉTAT : P1[z=%.2f, x=%d, y=%d]  P2[z=%.2f, x=%d, y=%d]  stack=%d",
                p1.getZoomFactor(), p1.getOffsetX(), p1.getOffsetY(),
                p2.getZoomFactor(), p2.getOffsetX(), p2.getOffsetY(),
                stackSize));
    }

    private String snapshotState(ImageDocument doc) {
        if (doc.getPerspectives().size() < 2) return "—";
        Perspective p1 = doc.getPerspectives().get(0);
        Perspective p2 = doc.getPerspectives().get(1);
        return String.format("P1[z=%.2f,x=%d,y=%d] P2[z=%.2f,x=%d,y=%d]",
                p1.getZoomFactor(), p1.getOffsetX(), p1.getOffsetY(),
                p2.getZoomFactor(), p2.getOffsetX(), p2.getOffsetY());
    }

    private int getStackSize() {
        try {
            java.lang.reflect.Field f = CommandManager.class.getDeclaredField("undoStack");
            f.setAccessible(true);
            java.util.Deque<?> stack = (java.util.Deque<?>) f.get(CommandManager.getInstance());
            return stack.size();
        } catch (Exception e) {
            return -1;
        }
    }

    private ImageDocument getDocument() {
        try {
            java.lang.reflect.Field f = ImageViewerApp.class.getDeclaredField("imageDocument");
            f.setAccessible(true);
            return (ImageDocument) f.get(app);
        } catch (Exception e) {
            log("ERREUR : impossible d'accéder à imageDocument.");
            return null;
        }
    }

    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
}
package cz.fi.muni.pv168.todo;

import cz.fi.muni.pv168.todo.ui.main.MainWindow;
import cz.fi.muni.pv168.todo.wiring.ProductionDependencyProvider;

import javax.swing.UIManager;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        initNimbusLookAndFeel();
        EventQueue.invokeLater(() -> new MainWindow(new ProductionDependencyProvider()).show());
    }

    private static void initNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
    }
}

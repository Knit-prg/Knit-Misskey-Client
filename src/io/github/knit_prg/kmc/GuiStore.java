package io.github.knit_prg.kmc;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.JFrame;
import javax.swing.UIManager;


public final class GuiStore {

    public static JFrame mainFrame = new JFrame();

    public static void init() {
        FlatDarkLaf.setup();
        UIManager.put("TitlePane.menuBarEmbedded", false);
        UIManager.put("TitlePane.unifiedBackground", false);
        mainFrame.setSize(800, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}

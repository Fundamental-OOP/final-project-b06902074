package pages;

import java.util.HashMap;

import javax.swing.JFrame;

public class PageController {
    private static HashMap<String, ShowPage> Pages = new HashMap<>();
    private static JFrame frame;
    private static String curPage;

    public static void init(JFrame _frame, HashMap<String, ShowPage> newPages) {
        frame = _frame;

        newPages.forEach((k, v) -> Pages.put(k, v));
        Pages.forEach((k, v) -> frame.add(v));
    }

    public static void switchPanel(String panelName) {
        curPage = panelName;
        Pages.forEach((key, value) -> value.setVisible(false));
        Pages.get(panelName).setVisible(true);
        Pages.get(panelName).setFocusable(true);
        Pages.get(panelName).requestFocus();
        Pages.get(panelName).init();
    }

    public static void showPanel(String panelName) { // used with hidePanel()
        Pages.get(curPage).setVisible(false);
        Pages.get(panelName).setVisible(true);
        Pages.get(panelName).requestFocus();
        Pages.get(panelName).init();
    }

    public static void hidePanel() { // used with shoePanel()
        switchPanel(curPage);
    }

    public static void addPanel(String panelName, ShowPage Panel) {
        Pages.put(panelName, Panel);
        frame.add(Panel);
    }

    public static void removePanel(String panelName) {
        frame.remove(Pages.get(panelName));
        Pages.remove(panelName);
    }

    public JFrame getFrame() {
        return frame;
    }
}

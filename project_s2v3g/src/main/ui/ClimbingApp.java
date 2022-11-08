package ui;

import model.Event;
import model.EventLog;
import model.Journal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


// represents a climbing app, containing a RouteAddUI and a JournalUI, and a journal
public class ClimbingApp extends JPanel {

    private Journal journal;

    private JournalUI journalUI;
    private RouteAddUI routeAddUI;

    // MODIFIES: this
    // EFFECTS: creates a new instance of my ClimbingApp class
    public ClimbingApp() {
        super(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();

        journalUI = new JournalUI();
        journal = journalUI.getJournal();
        routeAddUI = new RouteAddUI(journal, journalUI.getJournalModel());

        tabbedPane.addTab("View Routes", null, journalUI,
                "View and delete routes");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Add New Route", null, routeAddUI,
                "add a new route");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        //Add the tabbed pane to this panel.
        add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    // EFFECTS: shows splash screen and then loads the rest of the GUI components into a frame and shows it
    protected static void createAndShowGUI() {
        splash();

        //Create and set up the window.
        JFrame frame = new JFrame("Climbing Journal");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("EVENT LOG:");
                if (EventLog.getInstance().iterator().hasNext()) {
                    for (Event event : EventLog.getInstance()) {
                        System.out.println(event.getDescription());
                    }
                } else {
                    System.out.println("No events logged - no routes were added or removed from your journal");
                }
            }
        });

        //Add content to the window.
        frame.add(new ClimbingApp(), BorderLayout.CENTER);

        //Display the window.
        frame.setVisible(true);
    }

    // EFFECTS: shows splash screen for 1000 milliseconds, and then closes it
    //          throws a RuntimeException if the path to the image for the splash screen doesn't work
    private static void splash() {
        JFrame frame = new JFrame("Loading App...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            BufferedImage dawnWallPic = ImageIO.read(new File("./data/DawnWallImage.jpg"));
            JLabel picLabel = new JLabel(new ImageIcon(dawnWallPic));
            frame.add(picLabel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException("Unable to Load Graphics");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // do nothing
        }

        frame.setVisible(false);
    }
}


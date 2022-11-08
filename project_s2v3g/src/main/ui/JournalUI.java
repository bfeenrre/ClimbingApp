package ui;

import model.Journal;
import model.Route;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static javax.swing.JOptionPane.YES_NO_OPTION;

// represents a UI for a list of Journals that is editable (i.e. individual journals can be deleted)
public class JournalUI extends JPanel
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel journalModel;

    private Journal journal;

    private JsonReader reader;
    private JsonWriter writer;

    private static final String deleteString = "Delete";
    private JButton deleteButton;

    private static final String saveString = "Save Journal";
    private JButton saveButton;

    // EFFECTS: creates a new instance of JournalUI
    public JournalUI() {
        super(new BorderLayout());
        initialize();
        list = new JList(journalModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);

        initButtons();

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(deleteButton);
        buttonPane.add(saveButton);
        saveButton.setAlignmentX(LEFT_ALIGNMENT);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // EFFECTS: initializes both buttons that are present in JournalUI
    private void initButtons() {
        deleteButton = new JButton(deleteString);
        deleteButton.setActionCommand(deleteString);
        deleteButton.addActionListener(new DeleteListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // EFFECTS: initializes list of routes, and the model of the list of routes, and loads a journal into journal field
    private void initialize() {
        journalModel = new DefaultListModel();
        journal = initJournal();
        for (Route r : journal.getClimbedRoutes()) {
            journalModel.addElement(r.getName() + ": " + r.getRating() + ", " + r.getClimbType() + " (climbed)");
        }
        for (Route r : journal.getRoutesInProgress()) {
            journalModel.addElement(r.getName() + ": " + r.getRating() + ", " + r.getClimbType() + " (inProgress)");
        }
        for (Route r : journal.getRoutesToClimb()) {
            journalModel.addElement(r.getName() + ": " + r.getRating() + ", " + r.getClimbType() + " (toClimb)");
        }
    }

    // EFFECTS: prompts user to choose whether they want to load their journal from save or load a new one
    private Journal initJournal() {
        reader = new JsonReader("./data/journalData.json");
        boolean loadJournal = promptLoadJournal();
        if (loadJournal) {
            try {
                return reader.read();
            } catch (IOException e) {
                System.out.println("Unable to load saved journal - closing application, please try again");
                throw new RuntimeException();
            }
        } else {
            return new Journal();
        }

    }

    // represents a listener for my delete button
    class DeleteListener implements ActionListener {

        // REQUIRES: index is not -1 (this precondition is ensured by valueChanged())
        // EFFECTS: deletes selected index from model list, and then if a route exists in the corresponding list in
        //          the journal field, it deletes that route from the journal as well
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            String entry = journalModel.get(index).toString();
            String list = entry.substring(entry.indexOf("(") + 1, entry.length() - 1);
            String name = entry.substring(0, entry.indexOf(":"));
            String rating = entry.substring(entry.indexOf(":") + 2, entry.indexOf(","));
            String type = entry.substring(entry.indexOf(",") + 2, entry.indexOf("(") - 1);
            journalModel.remove(index);
            Route routeToRemove = journal.findByNameRatingAndList(list, name, rating, type);
            if (routeToRemove != null) {
                journal.removeFromList(list, routeToRemove);
            }

            int size = journalModel.getSize();
            if (size == 0) { //No routes are left - disable route deletion button
                deleteButton.setEnabled(false);
            }
        }
    }

    // represents a listener for my save button
    class SaveListener implements ActionListener {

        // EFFECTS: saves journal and shows confirmation that journal was saved successfully
        public void actionPerformed(ActionEvent e) {
            updateSave();
            JOptionPane.showMessageDialog(null, "Journal Saved Successfully",
                    "Journal Saved", JOptionPane.PLAIN_MESSAGE);
            int checker = 1 + 1;
        }
    }

    //EFFECTS: after a new index in my list is selected, this method runs, and, if there is no selected index, it
    //         disables the delete button
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                // No Selection
                deleteButton.setEnabled(false);

            } else {
                //Selection
                deleteButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: returns journal field
    public Journal getJournal() {
        return journal;
    }

    // EFFECTS: sets journal field to journal
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    // EFFECTS: returns journalModel
    public DefaultListModel getJournalModel() {
        return journalModel;
    }

    // EFFECTS: shows a popup that asks the user if they want to load the journal from save or not, if user selects yes,
    //          returns true, else prompts user to confirm they want to load a new journal
    public boolean promptLoadJournal() {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Load Journal from Save?", "LOAD SAVE",
                YES_NO_OPTION);

        if (dialogButton == JOptionPane.YES_OPTION) {
            return true;
        } else if (dialogButton == JOptionPane.NO_OPTION) {
            return areYouSureAboutThat();
        } else {
            throw new RuntimeException("No journal to load");
        }
    }

    // EFFECTS: shows a popup explaining that loading a fresh journal will override the previously saved journal, and
    //          asks user if they wish to continue or not. If user selects yes, return false, else return true.
    public boolean areYouSureAboutThat() {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure? This will delete your old journal and "
                        + "load a fresh one.", "LOAD SAVE", YES_NO_OPTION);
        if (dialogButton == JOptionPane.YES_OPTION) {
            return false;
        } else {
            return promptLoadJournal();
        }
    }

    // EFFECTS: writes current state of journal field into ./data/journalData.json
    public void updateSave() {
        writer = new JsonWriter("./data/journalData.json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(journal);
        writer.close();
    }
}

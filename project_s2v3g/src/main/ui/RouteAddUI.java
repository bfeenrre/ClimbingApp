package ui;

import model.Journal;
import model.Route;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// represents a RouteAddUI, which allows a user to add a new route to their journal
public class RouteAddUI extends JPanel {
    private DefaultListModel journalModel;

    private Journal journal;

    private static final String saveString = "Save Route";
    private JButton saveButton;

    private static final String[] listIndexes = new String[]{"climbed", "toClimb", "inProgress"};
    private JComboBox<String> listSelector;

    private JTextField name;

    private JTextField rating;

    private static final String[] climbTypes = new String[]{"TRADITIONAL", "BOULDERING", "SPORT", "TOP ROPE", "OTHER"};
    private JComboBox<String> climbType;

    private JTextField location;

    private JTextField notes;

    // EFFECTS: initializes and adds all needed components to a new JPanel, creating a new instance of RouteAddUI class
    public RouteAddUI(Journal j, DefaultListModel journalModel) {
        super(new BorderLayout());

        journal = j;
        this.journalModel = journalModel;

        initComponents();

        //Create a panel that uses BoxLayout.
        JPanel routeBuilder = new JPanel();
        routeBuilder.setLayout(new BoxLayout(routeBuilder,
                BoxLayout.Y_AXIS));
        routeBuilder.add(name);
        routeBuilder.add(rating);
        routeBuilder.add(location);
        routeBuilder.add(notes);
        routeBuilder.add(climbType);
        routeBuilder.add(listSelector);
        routeBuilder.add(saveButton);

        add(routeBuilder, BorderLayout.CENTER);
    }

    // EFFECTS: initializes all needed components for RouteAddUI
    @SuppressWarnings("methodlength")
    private void initComponents() {
        name = new JTextField("New Route");
        name.setSize(50, 5);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        rating = new JTextField("V0");
        name.setSize(50, 5);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        climbType = new JComboBox<>();
        climbType.setSize(50, 5);
        for (String s : climbTypes) {
            climbType.addItem(s);
        }
        climbType.setAlignmentX(LEFT_ALIGNMENT);

        location = new JTextField("00.0000000, 00.0000000");
        location.setSize(50, 5);
        location.setToolTipText("please enter in the form 'latitude, longitude', and specify both latitude and "
                + "longitude to 7 decimal places");
        location.setAlignmentX(LEFT_ALIGNMENT);

        notes = new JTextField("Notes:");
        notes.setSize(50, 30);
        notes.setAlignmentX(LEFT_ALIGNMENT);

        listSelector = new JComboBox<>();
        listSelector.setSize(50, 5);
        for (String s : listIndexes) {
            listSelector.addItem(s);
        }
        listSelector.setAlignmentX(LEFT_ALIGNMENT);

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // represents a listener for my save button
    class SaveListener implements ActionListener {

        // EFFECTS: creates a route with all the current values indicated by RouteAddUI's components (respectively),
        //          and adds it to both journal and journalModel, so that JournalUI's list display updates accordingly.
        public void actionPerformed(ActionEvent e) {
            Route routeToSave = new Route();
            routeToSave.setName(name.getText());
            routeToSave.setRating(rating.getText());
            routeToSave.setClimbType(climbTypes[climbType.getSelectedIndex()]);
            routeToSave.setLatitude(location.getText().substring(0, location.getText().indexOf(",")));
            routeToSave.setLongitude(location.getText().substring(location.getText().indexOf(" ") + 1));
            routeToSave.setNotes(notes.getText());

            journal.addToListWhileRunning(listIndexes[listSelector.getSelectedIndex()], routeToSave);
            journalModel.addElement(routeToSave.getName() + ": " + routeToSave.getRating() + ", "
                    + routeToSave.getClimbType() + " (" + listIndexes[listSelector.getSelectedIndex()] + ")");

            name.setText("New Route");
            rating.setText("V0");
            location.setText("00.0000000, 00.0000000");
            notes.setText("Notes:");
            listSelector.setSelectedItem(listIndexes[2]);
            climbType.setSelectedItem(climbTypes[4]);
        }
    }
}

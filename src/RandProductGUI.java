//this is the first program, the rand product maker

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class RandProductGUI extends JFrame{
    JFrame frame = new JFrame();
    JPanel mainPnl;
    JPanel topPnl;
    JPanel midPnl;
    JPanel botPnl;

    JLabel titleLbl;

    JTextField idField;
    JTextField nameField;
    JTextField descField;
    JTextField costField;

    JLabel idLabel;
    JLabel nameLabel;
    JLabel descLabel;
    JLabel costLabel;

    JLabel fileLabel;
    JTextArea fileDisplay;

    JButton quitButton;
    JButton addButton;
    JButton saveButton;
    JScrollPane scroller;

    ArrayList<Product> productList = new ArrayList<Product>();

    final int MAX_ID_LENGTH = 6;
    final int MAX_NAME_LENGTH = 35;
    final int MAX_DESC_LENGTH = 75;

    int recordNumber = 0;

    public RandProductGUI()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        createTopPanel();
        mainPnl.add(topPnl, BorderLayout.NORTH);
        createMiddlePanel();
        mainPnl.add(midPnl, BorderLayout.CENTER);
        createBottomPanel();
        mainPnl.add(botPnl, BorderLayout.SOUTH);

        add(mainPnl);

        setSize(800,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createTopPanel()
    {
        topPnl = new JPanel();
        titleLbl = new JLabel("Product File Generator",JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 38));

        topPnl.add(titleLbl);
    }

    private void createMiddlePanel()
    {
        midPnl = new JPanel();
        midPnl.setLayout(new GridLayout(5,2));

        idLabel = new JLabel("ID (length 6): ",JLabel.CENTER);
        nameLabel = new JLabel("Name (length 35): ",JLabel.CENTER);
        descLabel = new JLabel("Description (length 75): ",JLabel.CENTER);
        costLabel = new JLabel("Cost: ",JLabel.CENTER);

        idField = new JTextField("", 6);
        nameField = new JTextField("", 35);
        descField = new JTextField("", 75);
        costField = new JTextField("", 10);

        fileLabel = new JLabel("Number of files:", JLabel.CENTER);
        fileDisplay = new JTextArea(30,30);
        Font font = new Font("Verdana", Font.BOLD, 100);
        fileDisplay.setFont(font);
        scroller = new JScrollPane(fileDisplay);


        midPnl.add(idLabel);
        midPnl.add(idField);
        midPnl.add(nameLabel);
        midPnl.add(nameField);
        midPnl.add(descLabel);
        midPnl.add(descField);
        midPnl.add(costLabel);
        midPnl.add(costField);
        midPnl.add(fileLabel);
        midPnl.add(fileDisplay);

    }

    private void createBottomPanel()
    {
        botPnl = new JPanel();
        botPnl.setLayout(new GridLayout(1,2));
        quitButton = new JButton("Quit");
        addButton = new JButton("Add Product");
        saveButton = new JButton("Save");

        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        addButton.addActionListener((ActionEvent ae) ->
        {
            String paddedID = padStringLength(idField.getText(), MAX_ID_LENGTH);
            String paddedName = padStringLength(nameField.getText(), MAX_NAME_LENGTH);
            String paddedDesc = padStringLength(descField.getText(), MAX_DESC_LENGTH);

            boolean failed = false;
            if(paddedID.equals("error!"))
            {
                JOptionPane.showMessageDialog(frame, "Error: the ID is too big.");
                failed = true;
            }
            if( paddedName.equals("error!"))
            {
                JOptionPane.showMessageDialog(frame, "Error: the Name is too big.");
                failed = true;
            }
            if(paddedDesc.equals("error!"))
            {
                JOptionPane.showMessageDialog(frame, "Error: the Description is too big.");
                failed = true;
            }
            try
            {
                productList.add(new Product(paddedID, paddedName, paddedDesc, Double.parseDouble(costField.getText())));
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(frame, "Error: you did not enter a double in the cost field.");
                failed = true;
            }
            if(!failed)
            {
                recordNumber += 1;
                fileDisplay.setText(recordNumber + "");
                clear();
            }


        });
        saveButton.addActionListener((ActionEvent ae )-> {
            JFileChooser chooser = new JFileChooser();
            File chosenFile;
            try {
                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    chosenFile = chooser.getSelectedFile();
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chosenFile));
                    out.writeObject(productList);
                    JOptionPane.showMessageDialog(frame, "Data file written!");
                    fileDisplay.setText("");
                    recordNumber = 0;

                }
            }
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(frame, "Error: file not found!");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        botPnl.add(addButton);
        botPnl.add(saveButton);
        botPnl.add(quitButton);
    }

    private String padStringLength(String toCheck, int lengthOfString)
    {
        if(toCheck.length() > lengthOfString)
        {
            return "error!";
        }
        else if (toCheck.length() < lengthOfString)
        {
            int padNumber = lengthOfString - toCheck.length();
            return String.format("%" + padNumber + "s", toCheck);
        }
        else
        {
            return toCheck;
        }
    }

    private void clear()
    {
        idField.setText("");
        nameField.setText("");
        descField.setText("");
        costField.setText("");
    }
}

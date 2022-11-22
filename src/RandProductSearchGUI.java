import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class RandProductSearchGUI extends JFrame{
    JFrame frame = new JFrame();
    JPanel mainPnl;
    JPanel topPnl;
    JPanel midPnl;
    JPanel enterPnl;
    JPanel displayPnl;
    JPanel botPnl;

    JLabel titleLbl;
    JLabel productLbl;
    JLabel displayLbl;

    JButton quitButton;
    JButton searchButton;

    JTextField productField;
    JTextArea displayText;

    ArrayList<Product> productList = new ArrayList<Product>();

    public RandProductSearchGUI()
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
        titleLbl = new JLabel("Product File Reader",JLabel.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 38));

        topPnl.add(titleLbl);
    }

    private void createMiddlePanel()
    {
        midPnl = new JPanel();
        midPnl.setLayout(new BorderLayout());
        createEnterPnl();
        createDisplayPnl();
        midPnl.add(enterPnl, BorderLayout.NORTH);
        midPnl.add(displayPnl, BorderLayout.CENTER);
    }

    private void createEnterPnl()
    {
        enterPnl = new JPanel();

        productLbl = new JLabel("Product to be searched:");
        productField = new JTextField("",20);
        enterPnl.add(productLbl);
        enterPnl.add(productField);
    }

    private void createDisplayPnl()
    {
        displayPnl = new JPanel();
        displayPnl.setLayout(new GridLayout(1,2));
        displayText = new JTextArea(60, 60);
        displayLbl = new JLabel("Display:");
        displayLbl.setFont(new Font("SansSerif", Font.BOLD, 28));

        displayPnl.add(displayLbl);
        displayPnl.add(displayText);
    }

    private void createBottomPanel()
    {
        botPnl = new JPanel();
        botPnl.setLayout(new GridLayout(1,2));

        quitButton = new JButton("Quit");
        searchButton = new JButton("Search");

        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));
        searchButton.addActionListener((ActionEvent ae) -> {

            JFileChooser chooser = new JFileChooser();
            File chosenFile;
            String rec = "";

                File workingDirectory = new File(System.getProperty("user.dir"));
                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    chosenFile = chooser.getSelectedFile();
                    Path file = chosenFile.toPath();
                    try {
                        ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosenFile));
                        productList = (ArrayList<Product>) in.readObject();
                        productList.forEach(p ->
                        {
                            if(p.getProductName().contains(productField.getText()))
                            {
                                displayText.append(String.format("\n%-15s%-25s%-30s%-30s",p.getIDNum(), p.getProductName(),p.getProductDesc(),"   " + p.getProductCost() ));
                            }
                        });
                    } catch (IOException e) {
                        System.out.println("Error: IO exception!");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Error: Class not found!");
                    }
                }

        });

        botPnl.add(searchButton);
        botPnl.add(quitButton);

    }
}

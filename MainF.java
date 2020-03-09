import javax.management.loading.MLet;
import javax.swing.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

public class MainF extends Reader {


    public static void main(String[] args) throws FileNotFoundException {
        new MainF();
    }


    public MainF() throws FileNotFoundException {
        super("Добре дошли!!");
        getCategories();
        createTree();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 810, 345);
        getContentPane().setLayout(null);
        tree.setBounds(1, 1, 156, 344);
        getContentPane().add(tree);
        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));
        mainPanel.setBounds(157,1, 630,340);
        JLabel Jlabel = new JLabel("Добре дошли!!");
        Jlabel.setHorizontalAlignment(SwingConstants.CENTER);
        Jlabel.setBounds(302, 2, 75, 39);
        getContentPane().add(Jlabel);
        setVisible(true);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
                tree.getSelectionModel().setSelectionMode
                        (TreeSelectionModel.SINGLE_TREE_SELECTION);

                /* if nothing is selected */
                if (node == null) return;
                /* retrieve the node that was selected */
                Object nodeInfo = node.getUserObject();
                getProducts(nodeInfo);
                /* React to the node selection. */
                panel.removeAll();
                DefaultListModel<String> listModel = new DefaultListModel<>();
                listModel.removeAllElements();
                for (int j = 0; j < products.size(); j++) {
                    StringBuilder nameOfProduct = new StringBuilder();
                    StringBuilder price = new StringBuilder();
                    StringBuilder pathImg = new StringBuilder();

                    String data = products.get(j);
                    int step = 0;
                    int letter = 0;
                    for (int c = 0; c <= products.size(); c++) {
                        if (step == 0) {
                            while (data.charAt(letter) != ',') {
                                nameOfProduct.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        } else if (step == 1) {
                            letter++;
                            while (data.charAt(letter) != ',') {
                                price.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        } else if (step == 2) {
                            letter++;
                            while (data.charAt(letter) != '.') {
                                pathImg.append(data.charAt(letter));
                                letter++;
                            }
                            step++;
                        }
                    }

                    JLabel prLabel = new JLabel(String.valueOf(nameOfProduct));
                    JLabel priceLabel = new JLabel(String.valueOf(price));
                    ImageIcon imageIcon = new ImageIcon("src/" + pathImg + ".jpg"); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);
                    prLabel.setIcon(imageIcon);
                    panel.setBounds(0,0, 630, 340);
                    panel.setLayout(new GridLayout(2, 1));
                    panel.add(prLabel);
                    panel.add(priceLabel);
                    System.out.println(pathImg);

                }
                getContentPane().remove(Jlabel);
                panel.setBackground(Color.WHITE);
                mainPanel.add(panel);
                mainPanel.repaint();
                getContentPane().add(mainPanel);
                getContentPane().repaint();
            }
        });
    }
}
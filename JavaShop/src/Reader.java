import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reader extends JFrame{
    ArrayList<String> categoriesList = new ArrayList<>();
    ArrayList<String> categoriesList2 = new ArrayList<>();
    ArrayList<String> products = new ArrayList<>();
    DefaultMutableTreeNode node_1;
    JTree tree = new JTree();

    public Reader(String welcome) {

    }

    public Reader(StringBuilder nameOfProduct, StringBuilder price, StringBuilder pathImg) {

    }

    public void getCategories() {
        try {
            File file = new File("src/Categories.file");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.contains("</")) {
                    data = data.replace("</", "");
                    data = data.replace(">", "");
                    categoriesList.add(data);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void createTree() throws FileNotFoundException {
        File file = new File("src/Categories.file");
        Scanner reader = new Scanner(file);
        tree.setModel(new DefaultTreeModel(
                new DefaultMutableTreeNode("JTree") {
                    {
                        int subCatInd = 0;
                        String data;
                        String previousLine = null;
                        for (int i = 0; i < categoriesList.size(); i++) {
                            node_1 = new DefaultMutableTreeNode(categoriesList.get(i));
                            add(node_1);
                            while (reader.hasNextLine()) {
                                if (previousLine!=null){
                                    data = previousLine;
                                } else {
                                    data = reader.nextLine();
                                }
                                if (data.contains("<" + categoriesList.get(i) + ">")){
                                    data = reader.nextLine();
                                    while(!data.contains("</" + categoriesList.get(i) + ">")){
                                        if (data.contains("_")){
                                            data = data.replace("_", "");
                                            categoriesList2.add(data);
                                            node_1.add(new DefaultMutableTreeNode(categoriesList2.get(subCatInd)));
                                            add(node_1);
                                            subCatInd++;
                                        }
                                        data = reader.nextLine();
                                        previousLine = null;
                                    }
                                } else{
                                    previousLine = data;
                                    break;
                                }
                            }
                            System.out.println(i);
                        }

                    }
                }
        ));
        reader.close();
    }


    public void getProducts(Object selected) {
        try {
            products.clear();

            File file = new File("src/Categories.file");
            Scanner reader = new Scanner(file);
            String cat = selected.toString().trim();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (data.contains("_" + cat)) {
                    data = reader.nextLine();
                    while (data.isEmpty()) {
                        data = reader.nextLine();
                    }
                    while (data.contains("*")) {
                        data = data.replace("*", "");
                        products.add(data);
                        data = reader.nextLine();
                    }
                }
            }
//            System.out.println(products);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void getList(){
        System.out.println(categoriesList);
    }

    public void getProductList(){
        for (int i = 0; i<products.size(); i++) {
            System.out.println(products.get(i).trim());
        }
    }

}

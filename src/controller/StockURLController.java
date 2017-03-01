/**
 * 
 */
package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * @author Baptiste
 *
 */
public class StockURLController implements Initializable {
	
	@FXML private Stage addRepository;
	@FXML private Stage addUrl;
	@FXML private TextField dirGetField;
	@FXML private TextField urlGetField;
	
	@FXML private Parent root;
	@FXML private Scene scene;
    public int filecount = 0;
    public int dircount = 0;
	@FXML private ListView<String> listeViewRep;
	@FXML private ListView<String> listeViewUrl;
	
	ArrayList<String> arborescence;
	ArrayList<String> arborescenceurl;
	
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	
    	System.out.println("Initialize");
    	
  
    	arborescence = listDirectory ("D:\\FileDownloadTest");

    	
    	ObservableList<String> items =FXCollections.observableArrayList ();
    	for(int i=0;i<arborescence.size();i++){
    		items.add(arborescence.get(i));
    	}
    	
    	listeViewRep.setItems(items);
    	
    	listeViewRep.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

    	    	arborescenceurl = listFiles("D:\\FileDownloadTest\\"+newValue);

    	    	ObservableList<String> items =FXCollections.observableArrayList ();
    	    	for(int i=0;i<arborescenceurl.size();i++){
    	    		items.add(arborescenceurl.get(i));
    	    	}

    	    	listeViewUrl.setItems(items);
    	        System.out.println("Selected item: " + newValue);
    	    }
    	});
    }

    private ArrayList<String> listDirectory(String dir) {
    	ArrayList<String> listeDirectory = new ArrayList<String>();
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() == true) {
                	listeDirectory.add(""+ files[i].getName());
                    System.out.println("Dossier: " + files[i].getAbsolutePath());
                }
            }
        }
        return listeDirectory;
    } 
    
    private ArrayList<String> listFiles(String dir) {
    	ArrayList<String> listeDirectory = new ArrayList<String>();
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() == true) {
                	listeDirectory.add(""+ files[i].getName());
                    //System.out.println("Dossier: " + files[i].getAbsolutePath());
                }
            }
        }
        return listeDirectory;
    } 
    
    @FXML
    public void pictoAddRepClicked(MouseEvent event) throws IOException {
	        System.out.println("Add repository + Clicked: "+event.getSource());
	        addRepository = new Stage();								
			root = FXMLLoader.load(getClass().getResource("/view/AddRep.fxml")); 
			scene = new Scene(root,800,480);											 
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			addRepository.setScene(scene);
			addRepository.setResizable(false);
			addRepository.show();
			//1er methode
    }


    @FXML
    public void pictoAddUrlClicked(MouseEvent event) throws IOException {
	        System.out.println("Add repository + Clicked: "+event.getSource());
	        addUrl = new Stage();								
			root = FXMLLoader.load(getClass().getResource("/view/AddURL.fxml")); 
			scene = new Scene(root,800,480);											 
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			addUrl.setScene(scene);
			addUrl.setResizable(false);
			addUrl.show();
			//1er methode
    }
    
    @FXML
    public void addRepParcourir (MouseEvent event) throws IOException {
	    	final DirectoryChooser dialog = new DirectoryChooser(); 
	        final File directory = dialog.showDialog(addRepository);
	        urlGetField.setText(""+directory);
	        System.out.println("addRepParcourir  "+addRepository);
	        //urlGetField.setText("");
    }
    
    @FXML
    public void addRepValiderClicked(MouseEvent event) throws IOException {
    	System.out.println(""+urlGetField.getText()+"\\"+dirGetField.getText());
    	File dir = new File (""+urlGetField.getText()+"\\"+dirGetField.getText());
    	dir.mkdirs();
    	//System.out.println("Addrepo"+addRepository);
    	//addRepository.close();
    	
    }
    
    @FXML
    public void addUrlValiderClicked(MouseEvent event) throws IOException {
        System.out.println("AddRepositoryValidatedClicker: "+event.getSource());
        // addRepository.close();
        /* URL oracle = new URL("http://www.univ-avignon.fr");
         URLConnection yc = oracle.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String inputLine;
         while ((inputLine = in.readLine()) != null) 
             System.out.println(inputLine);
         in.close();
         System.out.println("test");*/
         //final Button button = new Button("Cliquer"); 
         final DirectoryChooser dialog = new DirectoryChooser(); 
         final File directory = dialog.showDialog(addRepository); 
         
         if (directory != null) { 
             // Effectuer le traitement. 
         }
         
         try{
   		  BufferedReader urlReader = new BufferedReader(new InputStreamReader(new URL("http://www.univ-avignon.fr").openStream()));
   		  FileWriter localFile = new FileWriter(new File(directory+"/out.html"));
    
   		  String s;
    
   		  while((s = urlReader.readLine()) != null){
   		     localFile.write(s);
   		  }
   		  urlReader.close();
   		  localFile.close();
   		}
   		catch(Exception e){
   		  System.out.println("Erreur : " + e);
   		}
    	
    }
}

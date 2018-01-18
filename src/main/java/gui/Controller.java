package gui;

import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import data.ScenarioEntry;
import engine.Scenario;
import engine.ScenarioEngine;
import engine.Tuple;
import javafx.animation.PauseTransition;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Callback;
import javafx.util.Duration;
import org.bson.Document;
import weka.core.Attribute;
import weka.core.Instance;
import mongo.MongoManager;



import java.awt.*;
import java.awt.TextField;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Controller {

    public TableView tableView;
    public Label HCLabel;
    public Button Accept;
    public GridPane GridPane2;
    public CheckBox AgeBandCheckBox;
    public CheckBox BusinessCheckBox;
    public CheckBox CBCheckBox;
    public CheckBox ETypeCheckBox;
    public CheckBox SeniorityCheckBox;
    public GridPane GridPane3;
    public HBox HBox1;
    public HBox HBox2;
    public HBox HBoxSave;
    public HBox HBox4;
    public HBox HBox3;
    public ListView list;
    public GridPane GridPane4;
    private ObservableList<ObservableList> data;
    public Integer HC = 0;
    public CheckBox LegEntCheckBox;
    ArrayList <ChoiceBox> ArrChoiceBox=new ArrayList<ChoiceBox>();
    javafx.scene.control.TextField text=new javafx.scene.control.TextField();
    ArrayList<Tuple<String, String>> tupleArray=new ArrayList<Tuple<String, String>>();
    int OldValue=0;
    int NewValue=0;
    Button Save=new Button("");
    ObservableList<String> arraytotal=FXCollections.observableArrayList();
    int contador=1;
    Label labelNbHC=new Label();
    Button UpdtNbCases=new Button();


    public void initialize(){
        //Create last available engine.Scenario in MongoDB
        Label label=new Label("NbCases:");
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(0,0,60,-100));
        label.setFont(new Font("System",14));
        HBox2.setAlignment(Pos.CENTER);
        //HBox2.getChildren().add(label);
        text.setPadding(new Insets(0,0,10,0));
        text.setAlignment(Pos.CENTER);
        text.setFont(new Font("System",14));
        //text.setPrefSize(50,25);
        text.setMaxWidth(100);
        text.setEditable(true);

        HBox2.getChildren().addAll(text, label);


        UpdtNbCases=new Button("Update NbCases");
        //UpdtNbCases.setPadding(new Insets(0,0,0,10));
        UpdtNbCases.setAlignment(Pos.CENTER);
        UpdtNbCases.setMaxWidth(130);
        UpdtNbCases.setFont(new Font("System",14));
        HBox1.setAlignment(Pos.CENTER);
        HBox1.getChildren().add(UpdtNbCases);
        //GridPane3.add(HBox1, 0,0);

        HBox3.setAlignment(Pos.CENTER);
        HBox4.setAlignment(Pos.CENTER);
        Label labelHC=new Label("HeadCount: ");
        //labelHC.setAlignment(Pos.CENTER_RIGHT);
        labelHC.setPadding(new Insets(0,0,60,30));
        labelHC.setFont(new Font("System",14));

        Label labelCost=new Label("Cost: ");
        //labelCost.setAlignment(Pos.CENTER_RIGHT);
        labelCost.setPadding(new Insets(0,0,10,-50));
        labelCost.setFont(new Font("System",14));
        //GridPane3.add(labelCost, 0,1);

        //HC=sc1.HC();
        //labelNbHC.setText(HC.toString());
        labelNbHC.setAlignment(Pos.CENTER_RIGHT);
        labelNbHC.setPadding(new Insets(0,0,60,10));
        labelNbHC.setFont(new Font("System",14));
        //GridPane3.add(labelNbHC, 1,1);
        HBox3.getChildren().addAll(labelHC,labelCost);
        HBox4.getChildren().add(labelNbHC);

        Save=new Button("Save");
        Save.setAlignment(Pos.CENTER);
        Save.setMaxWidth(80);
        Save.setFont(new Font("System",14));
        HBoxSave.setAlignment(Pos.CENTER);
        HBoxSave.getChildren().add(Save);


    }
    public void AcceptButtonClicked(MouseEvent mouseEvent) {

        labelNbHC.setText(HC.toString());
        text.setText("");
        int cont=0;
        GridPane2.getChildren().clear();
        ArrChoiceBox=new ArrayList<>();



        if(AgeBandCheckBox.isSelected()){

            Label label=new Label("AgeBand");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("AgeBand");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }

        if(BusinessCheckBox.isSelected()){

            Label label=new Label("Business");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("Business");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }

        if(CBCheckBox.isSelected()){

            Label label=new Label("CareerBand");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("CB");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }

        if(ETypeCheckBox.isSelected()){

            Label label=new Label("EType");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("EType");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }
        if(LegEntCheckBox.isSelected()){

            Label label=new Label("LegEnt");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("LegEnt");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }
        if(SeniorityCheckBox.isSelected()){

            Label label=new Label("SeniorityBand");
            label.setAlignment(Pos.CENTER_LEFT);
            label.setPadding(new Insets(0,0,0,50));
            label.setFont(new Font("System",14));
            ChoiceBox ChoiceBox=new ChoiceBox(items);
            //ChoiceBox.setPadding(new Insets(0,0,0,50));
            ChoiceBox.setId("SeniorityBand");
            ChoiceBox.setPrefSize(130, 20);
            ArrChoiceBox.add(ChoiceBox);
            GridPane2.add(label, 0,cont);
            GridPane2.add(ChoiceBox,1, cont);
            cont+=1;
        }


        UpdtNbCases.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                tupleArray=new ArrayList<Tuple<String, String>>();
                //System.out.println("ARRAY TUPLA"+tupleArray);
                for(ChoiceBox ch:ArrChoiceBox){
                    Tuple t=new Tuple(ch.getId(),ch.getSelectionModel().getSelectedItem());
                    tupleArray.add(t);
                }
                


               text.setOnKeyPressed(new EventHandler<KeyEvent>() {
                   @Override
                   public void handle(KeyEvent event) {
                       if(event.getCode().equals(KeyCode.ENTER)){
                           try{
                               NewValue=Integer.parseInt(text.getText());
                               labelNbHC.setText(String.valueOf(Integer.parseInt(labelNbHC.getText()) - (OldValue - NewValue)));
                               //Update del coste también
                               sc1.deleteCases(tupleArray,OldValue-NewValue);
                               OldValue=NewValue;
                           }catch(NumberFormatException ex){
                               text.setText("0");
                           }
                       }
                   }
               });
               //CODE USING A CHANGELISTENER IN TEXTFIELD text
                //OldValue=Integer.parseInt(text.getText());
                //PauseTransition pause=new PauseTransition(Duration.seconds(10));
//                text.textProperty().addListener(new ChangeListener<String>() {
//                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                        if(newValue.matches("")) {
//                            text.setText(newValue.replaceAll("","0"));
//                        }
//                        //parseInt throws an Exception. We need to control if the values are integer or not.
//                        try{
//                            if(newValue.matches("")) {
//                                newValue.replaceAll("","0");
//                            }
//                            OldValue=Integer.parseInt(oldValue);
//                            NewValue=Integer.parseInt(newValue);
//                            labelNbHC.setText(String.valueOf(Integer.parseInt(labelNbHC.getText()) - (OldValue - NewValue)));
//
//                        }catch(NumberFormatException ex){
//                            newValue.replaceAll("","0");
//                        }
//                    }
//                });


                Save.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        String aux="SAVE "+contador+":   ";
                        for(Tuple t:tupleArray){
                            aux=aux+t.x+": "+t.y+"   ";
                        }
                        aux=aux+"\n";
                        arraytotal.add(aux);
                        list.setItems(arraytotal);
                        contador+=1;
                    }
                });

            }
        });



    }

    public void LastScenarioClicked(MouseEvent mouseEvent) {
        ScenarioEngine Engine = new ScenarioEngine();
        //Create last available engine.Scenario in MongoDB
        Scenario sc1=Engine.CreateLastAvailableScenario();
        ArrayList<Attribute> atts = sc1.getAtts();
        ArrayList<TableColumn<ScenarioEntry,String>> cols = new ArrayList();
        int z=0;
        tableView.getColumns().clear();
        for(Attribute att:atts){
            final int j = z;
            //We are using non property style for making dynamic table
            TableColumn col = new TableColumn(att.name());
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tableView.getColumns().addAll(col);
            z++;
        }


        final int j = atts.size();
        TableColumn col = new TableColumn("Number of cases");
        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                return new SimpleStringProperty(param.getValue().get(j).toString());
            }
        });
        tableView.getColumns().addAll(col);


        data = FXCollections.observableArrayList();
        int y = 0;
        for(Instance inst:sc1.getdata()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            String tmpAtt;
            for(int i=0; i<inst.numAttributes();i++) {
                if (inst.attribute(i).isNumeric()) {
                    //System.out.println(inst.value(i));
                    tmpAtt = String.valueOf(inst.value(i));
                } else {
                    tmpAtt = inst.stringValue(i);
                    //System.out.println(inst.stringValue(i));
                }
                row.add(tmpAtt);
            }
            row.add(String.valueOf(sc1.getNcases().get(y)));
            HC += sc1.getNcases().get(y);
            //System.out.println("Row [1] added "+row );
            data.add(row);
            y++;
        }


        HCLabel.setText("Headcount: "+sc1.getdata().size());
        tableView.setItems(data);
    }

}

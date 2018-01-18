import com.mongodb.client.DistinctIterable;
import mongo.MongoManager;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.classifiers.functions.MultilayerPerceptron;

import java.io.FileWriter;

import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.evaluation.NumericPrediction;

import java.text.SimpleDateFormat;
import java.util.*;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class WekaAxalta {

    public static void main(String[] args) {
        Date cal = null;
        Instances pa = null;
        try {
            String outputFile = "test"+".csv";
            CSVPrinter csvFilePrinter = null;
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter(';');
            FileWriter fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            MongoDatabase db = MongoManager.connect();
            MongoCollection<Document> collection = db.getCollection("empl_cost");
            DistinctIterable<String> PAs = collection.distinct("PArea", String.class);

            ArrayList atts = new ArrayList();
            atts.add(new Attribute("PA", 0));
            atts.add(new Attribute("Period", "yyyy/MM/dd", 1));
            atts.add(new Attribute("Cost", 2));

            for (String PA : PAs) {
                Calendar date = Calendar.getInstance();
                date.set(2014, 1, 1);
                Instances data = new Instances("test", atts, 0);

                AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                        new Document("$match", new Document("Period", new Document("$gte", date.getTime())).append("GL", new Document("$in", Arrays.asList("71101000", "71101300", "71101500", "71101530", "71301000"))).append("PArea", PA)),
                        new Document("$group", new Document("_id", new Document("PArea", "$PArea").append("Period", "$Period")).append("Cost_sum", new Document("$sum", "$Cost"))),
                        new Document("$project", new Document("PArea", "$_id.PArea").append("Period", "$_id.Period").append("Cost_sum", "$Cost_sum").append("_id", 0)),
                        new Document("$sort", new Document("Period", 1))
                ));

                for (Document a : output) {
                    double[] attValues = new double[3];
                    cal = a.getDate("Period");
                    attValues[0] = Float.parseFloat(PA);
                    attValues[1] = (double) cal.getTime();
                    attValues[2] = a.getDouble("Cost_sum");
                    DenseInstance inst = new DenseInstance(1.0, attValues);
                    data.add(inst);
                    //CSVLoader loader = new CSVLoader();
                    //loader.setOptions(new String[]{"-D", "2"});

                    //loader.setOptions(new String[]{"-format", "dd/MM/yyyy HH:mm"});
                    //loader.setSource(new File("C:\\Users\\tr5568\\Desktop\\PRUEBA\\DOC_PA_0071.csv"));

                    //Instances pa = loader.getDataSet();

                    //sort the instances based on "Period"
                    //pa.sort(pa.attribute("Period"));
                    //Calendar cal = Calendar.getInstance();
                    //double Lastdate = pa.lastInstance().value(1);
                    //cal.setTimeInMillis((long) Lastdate);

                    //System.out.println(cal.getTime());
                    //System.out.println(pa.lastInstance().value(1));

                }

                System.out.println(data);
                if(data.numInstances()>1) {
                    //new forecaster
                    WekaForecaster forecaster = new WekaForecaster();

                    //set the target to forecast
                    forecaster.setFieldsToForecast("Cost");

                    //we use MultilayerPerceptron
                    forecaster.setBaseForecaster(new MultilayerPerceptron());

                    forecaster.getTSLagMaker().setTimeStampField("Period");
                    forecaster.getTSLagMaker().setMinLag(1);
                    forecaster.getTSLagMaker().setMaxLag(12); //monthly data

                    //add a month of the year indicator field
                    forecaster.getTSLagMaker().setAddMonthOfYear(true);

                    //add a quarter or the year indicator field
                    forecaster.getTSLagMaker().setAddQuarterOfYear(true);

                    //build the model
                    forecaster.buildForecaster(data, System.out);
                    forecaster.primeForecaster(data);

                    // forecast for 12 units (months) beyond the end of the
                    // training data
                    List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

                    //create a matrix to save predicted data
                    ArrayList<PArea> arrayPA = new ArrayList();
                    // output the predictions. Outer list is over the steps; inner list is over
                    // the targets
                    for (int i = 0; i < 12; i++) {
                        Calendar tmp = Calendar.getInstance();
                        tmp.setTime(cal);
                        tmp.add(Calendar.MONTH, i + 1);
                        System.out.println(tmp.getTime());
                        List<NumericPrediction> predsAtStep = forecast.get(i);
                        NumericPrediction predForTarget = predsAtStep.get(0);
                        PArea tmpPArea = new PArea();

                        tmpPArea.Cost = predForTarget.predicted();

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String Lastdate_formatted = format.format(tmp.getTime());
                        tmpPArea.Period = Lastdate_formatted;
                        tmpPArea.PA = "0071";
                        arrayPA.add(tmpPArea);
                        //System.out.print("" + predForTarget.predicted() + " ");
                        //System.out.println();
                    }

                    for (PArea i : arrayPA) {
                        System.out.println(i.PA);
                        System.out.println(i.Period);
                        System.out.println(i.Cost);


                        csvFilePrinter.printRecord();


                        fileWriter.flush();
                        fileWriter.close();
                        csvFilePrinter.close();
                    }
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


}
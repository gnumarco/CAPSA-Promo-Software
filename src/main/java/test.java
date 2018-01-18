import com.mongodb.client.DistinctIterable;
import mongo.MongoManager;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class test {

    public static void main(String[] args) {
        Date cal = null;
        Instances pa = null;

        MongoDatabase db = MongoManager.connect();
        MongoCollection<Document> collection = db.getCollection("empl_cost");
        DistinctIterable<String> PAs = collection.distinct("PArea", String.class);

        ArrayList atts = new ArrayList();
        atts.add(new Attribute("PA", 0));
        atts.add(new Attribute("Period", 1));
        atts.add(new Attribute("Cost", 2));


        for (String PA : PAs) {
            Calendar date = Calendar.getInstance();
            date.set(2014, 1, 1);
            Instances data = new Instances("test", atts, 0);

            AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
                    new Document("$match", new Document("Period", new Document("$gte", date.getTime())).append("GL", new Document("$in", Arrays.asList("71101000", "71101300","71101500", "71101530", "71301000"))).append("PArea", PA)),
                    new Document("$group", new Document("_id", new Document("PArea", "$PArea").append("Period", "$Period")).append("Cost_sum", new Document("$sum", "$Cost"))),
                    new Document("$project", new Document("PArea", "$_id.PArea").append("Period", "$_id.Period").append("Cost_sum", "$Cost_sum").append("_id", 0)),
                    new Document("$sort", new Document("Period", 1))
            ));

            for (Document a : output) {


            }
        }


    }
}
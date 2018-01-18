package mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MongoManager {


        public static MongoDatabase connect() {


            List<ServerAddress> seeds = new ArrayList<ServerAddress>();
            seeds.add( new ServerAddress("10.35.32.99" , 27017 ));

            // Creating Credentials
            MongoCredential credential;
            credential = MongoCredential.createCredential("tw6318", "admin",
                    "Gordito2302".toCharArray());

            // Creating a Mongo client
            MongoClient mongo = new MongoClient(seeds , Arrays.asList(credential));
            System.out.println("Connected to the database successfully");

            // Accessing the database
            MongoDatabase database = mongo.getDatabase("test_db");
            System.out.println("Credentials ::"+ credential);
            return database;
        }
    }


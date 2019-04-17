/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connection.DatabaseManager;
import java.sql.SQLException;
import java.util.Random;
import org.apache.log4j.BasicConfigurator;


/**
 *
 * @author charl
 */
public class Sample {

    /**
     * @param args the command line arguments
     */
    private static Logger logger = LoggerFactory.getLogger(Sample.class);
    public static void main(String[] args) {
        
        BasicConfigurator.configure();
        logger.debug("Program Starting");
        DatabaseManager databaseManager = new DatabaseManager("database.properties");
        //DatabaseManager databaseManager = new DatabaseManager("C:\\Users\\charl\\Documents\\NetBeansProjects\\pkmonTCG\\sample\\src\\sample\\database.properties");

	Connection dataSource = databaseManager.establishConnection();//Create the connection
	if (null == dataSource) {
		System.exit(1); //exit if connection cannot be made
	}
        DatabaseManager newdb = new DatabaseManager();
        Lab labs = new Lab(dataSource);
        
        
        
        
        
        
        /*big lab start
        


        List<String> objWeights = new ArrayList();
        objWeights.add("50.5");
        objWeights.add("22.5");
        objWeights.add("15.7896");
        objWeights.add("5.5625");
       
        List<String> objNames = new ArrayList();
        objNames.add("Lead Ball");
        objNames.add("Aluminum Ball");
        objNames.add("Glass Ball");
        objNames.add("Plastic Ball");
        
        ArrayList locales = new ArrayList();//question 2
        locales.add("en_ZA");
        locales.add("en_US");
        locales.add("fr_FR");
        locales.add("fo_FO");
        locales.add("el_GR");
        locales.add("es_HN");
        locales.add("es_PE");
        locales.add("es_PY");
        locales.add("fo_FO");
        locales.add("gl_ES");
        
        
        List<String> dateformats = new ArrayList();//question 7
        dateformats.add("%W %M %d %Y");
        dateformats.add("%Y-%m-%d");
        dateformats.add("%a %M %d %Y");
        dateformats.add("");
        

        /*
        labs.errorByTypeQuery();//question 1
        
        labs.languageFormat(locales, objNames.get(0)); //question 2
        
        labs.addtoObject(objNames, objWeights);//question 3
        
        labs.add15ChildEntries(objNames);//question 4
        
        labs.delete1stChildRow();//question 5
        
        labs.add10toObjectWeightofMaxId();//question 6
        
        labs.dateFormatting(dateformats);//question 7
        
        labs.dateFormattingwithLocale(locales, dateformats);//question 8
        */


        //VIEWS AND PROCS HW
        /*
        labs.selectView();
        labs.procWithArgs(250);
        labs.procWithoutArgs();

        /*Triggers lab
        List<String> objRowIds = new ArrayList();
        objRowIds.add("12");
        objRowIds.add("20");
        objRowIds.add("32");
        objRowIds.add("41");
        objRowIds.add("22");
        
        labs.delete5fromObjectTypeData();
        labs.add5toObjectTypeData(objRowIds);
        labs.updateObjectTypeData();
        labs.returnAlltriggeredData();
        
        
        Triggers lab end
        */
        
        //Final project start


        
        List<String> fnames = new ArrayList();
        List<String> lnames = new ArrayList();
        List<String> addresses = new ArrayList();
        List<String> newAddresses = new ArrayList();
        List<String> cities = new ArrayList();
        List<String> states = new ArrayList();
        fnames.add("Charles");
        fnames.add("Jacob");
        fnames.add("Kayla");
        fnames.add("Kaylie");
        fnames.add("Jimmy");
        fnames.add("Jackson");
        fnames.add("Sarah");
        fnames.add("Josh");
        fnames.add("Eric");
        fnames.add("Sam");
        fnames.add("Kalese");
        fnames.add("Marquis");
        fnames.add("Mckenna");
        fnames.add("Aaron");
        fnames.add("Rodger");
        fnames.add("Carlos");
        fnames.add("Hose");
        fnames.add("Daniel");
        fnames.add("Nathan");
        fnames.add("Cloud");
        fnames.add("Karl");
        fnames.add("Samuel");
        
        lnames.add("Smith");
        lnames.add("Johnson");
        lnames.add("Rodgers");
        lnames.add("Whitacre");
        lnames.add("Culpepper");
        lnames.add("Jones");
        lnames.add("Mann");
        lnames.add("Gaylord");
        
        
        
        addresses.add(  "Alexis");
        addresses.add(  "home lane");
        addresses.add(  "Brooke");
        addresses.add( "Washington");
        addresses.add( "Larson");
        addresses.add(  "Smith");
        addresses.add ( "Springdale");
        addresses.add( "Lakeview");
        addresses.add(  "Cambridge");
        addresses.add( "Forestview");
        Random rand = new Random(); 
        for(String address: addresses){
            int rand_int2 = rand.nextInt(1000);
            newAddresses.add(rand_int2 + " " +  address);
        }
        cities.add("Toledo");
        cities.add("Port Clinton");
        cities.add("Napoleon");
        cities.add("Columbus");
        cities.add("Cleveland");
        
        states.add("OH");
        
        /*
        labs.add30uniqueCustomers(fnames, lnames, newAddresses, cities, states); //step 3
        */
        List<String> ordersdesc = new ArrayList();
        ordersdesc.add("Ipod");
        ordersdesc.add("Iphone");
        ordersdesc.add("Hoody");
        ordersdesc.add("Nfl jersey");
        ordersdesc.add("Baseball Cap");
        ordersdesc.add("Levi Jeans");
        ordersdesc.add("Sunglasses");
        ordersdesc.add("Video game");
        ordersdesc.add("BlueRay movie");
        ordersdesc.add("Power Drill");
        ordersdesc.add("Laptop");
        
        labs.insrt2Ordersfor15Customers(ordersdesc);
        
        
        

        
        
        
        
        
        
        
        
        
        //Final project end
        
        
        
        
        
        /* Close the database connection */
	databaseManager.closeConnection(dataSource);
	logger.debug("Program completed");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author charl
 */
public class Lab {
    private Connection dataSource;
    private static Logger logger = LoggerFactory.getLogger(Sample.class);
    Random rand = new Random();
    Lab(Connection dataSource){
        this.dataSource = dataSource;
        
    }
    Lab(){}
    
    public void lab1(){

        String sql1 = "SELECT max(Voltage), sum(Voltage), avg(Current), ObjectRowId from Voltage " +
"GROUP BY ObjectRowId";
        try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    double maxVoltage = rs.getDouble("max(Voltage)");
                    double sumVoltage = rs.getDouble("sum(Voltage)");
                    double avgCurrent = rs.getDouble("avg(Current)");
                    int ObjectRowId = rs.getInt("ObjectRowId");
                    logger.debug("max(Voltage" + maxVoltage + "   sum(Voltage):"+sumVoltage+ "    avg(Current)" + avgCurrent +   "    ObjectRowId:"+ ObjectRowId);
                   //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                }
            }
            
        }
        catch(SQLException e){
            logger.error("SQL Syntax Error: " + e.toString());
        }
        
        
        

        
    }
    
    public void lab2(){//updates many rows through a join after particular conditions are met
                String sql1 = "update AccountHolder " +
" Join Accounts on Accounts.AccHolderID = Accounts.AccHolderID " +
" set ActiveState = 0 " +
" where Accounts.AccountType = \"Checking\" and Accounts.Balance >= 0";
        try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    double maxVoltage = rs.getDouble("max(Voltage)");
                    double sumVoltage = rs.getDouble("sum(Voltage)");
                    double avgCurrent = rs.getDouble("avg(Current)");
                    int ObjectRowId = rs.getInt("ObjectRowId");
                    logger.debug("max(Voltage" + maxVoltage + "   sum(Voltage):"+sumVoltage+ "    avg(Current)" + avgCurrent +   "    ObjectRowId:"+ ObjectRowId);
                   //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                }
            }
            
        }
        catch(SQLException e){
            logger.error("SQL Syntax Error: " + e.toString());
        }
       
        /*update AccountHolder
Join Accounts on Accounts.AccHolderID = Accounts.AccHolderID
set ActiveState = 0
where Accounts.AccountType = "Checking" and Accounts.Balance >= 0*/

    }
    public void errorByTypeQuery(){
                        String sql1 = "select Object.ObjectName, count(Object_Error.ErrorId) from Object_Error " +
"  Join ObjectType on ObjectType.ObjectId = Object_Error.ObjectRowId" +
"  Join Object on ObjectType.ObjectId = Object.ObjectRowId" +
"  group by ObjectType.ObjectId";
        try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    String numberofErrors = rs.getString("count(Object_Error.ErrorId)");
                    String objectName = rs.getString("ObjectName");
     
                    System.out.println("Object Name: " + objectName + "Number of Errors: " + numberofErrors);
                   //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                }
            }
            
        }
        catch(SQLException e){
            logger.error("SQL Syntax Error: " + e.toString());
        }
        
    }
    
        
    public void languageFormat(List<String> languages, String objName){
        List<Integer> objRowIds = new ArrayList();
        for(String language: languages){
            String sql1 = "select FORMAT(ObjectWeight, 4, (?))" + " as Weight from Object " +
                            " where ObjectName = (?)"; //uses this select to generate insert
   
            try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                ps.setString(1, language);
                ps.setString(2, objName);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String weight = rs.getString("Weight");
                        System.out.println(objName + "'s weight using " + language + " format: " + weight);
                       //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                }
   
            }
            catch(SQLException e){
                logger.error("SQL Syntax Error: " + e.toString());
            }
            
        }

          
    }
    
    
    

    
    public void addtoObject(List<String> objname, List<String> weight){
            
        for (int i = 0; i<objname.size(); i++){
            
          String sql1 = "Insert into Object " +
                " (ObjectName, ObjectWeight)" +
                " Values(\"" + objname.get(i) + "\"" + "," + weight.get(i)+ ")";
            try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                ps.executeUpdate(sql1);

                

            }
            catch(SQLException e){
                logger.error("SQL Syntax Error: " + e.toString());
            }
            }
        }
    
    public void add15ChildEntries(List<String> objectNames){
        List<Integer> objRowIds = new ArrayList();
        for(String objname: objectNames){
            String sql1 = "Select ObjectRowId from Object where ObjectName = (?)"; //uses this select to generate insert
   
            try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                ps.setString(1, objname);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String objectRowId = rs.getString("ObjectRowId");
                        objRowIds.add(Integer.parseInt(objectRowId));
                        System.out.println("ObjectRowId: " + objectRowId + "added to list");
                       //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                }
   
            }
            catch(SQLException e){
                logger.error("SQL Syntax Error: " + e.toString());
            }
            
        }
        for (Integer objRowId: objRowIds){
            for(int i=0; i<15; i++){//insert 15 entries per id
                String sql1 = "Insert into ObjectType_Data" +
                                "  (ObjectRowId, Height, Duration)  " +
                                "  Values (?, 10.2, .33)  "; //uses this select to generate insert
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                        ps.setInt(1, objRowId);
                        ps.executeUpdate();
                        System.out.println("Inserted into ObjectType_Data: "+ objRowId);

                    }


                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
            }
             
        }
    }
        
        
        public void add10toObjectWeightofMaxId(){
                            String sql1 = " Update Object " +
                                " Set ObjectWeight = (ObjectWeight + 10) " +
                                "  where ObjectRowId = (Select Max(ObjectRowId)) "; //uses this select to generate insert
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                        ps.executeUpdate();
                        System.out.println("Added 10 to maximum ObjectRowId's weight");

                    }


                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
        }
        
        public void dateFormatting(List<String> formats){
            for(String format: formats){
                
                String sql1 = null;
                if(format.equals("")||format.equals(null)){
                    sql1 = "SELECT CURRENT_TIMESTAMP() as FormattedDate";
                }
                else{
                    sql1 = "SELECT DATE_FORMAT(CURRENT_TIMESTAMP(),(?)) as FormattedDate"; //uses this select to generate insert
                }
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    if(!(format.equals("") || format.equals(null))){
                          ps.setString(1, format);
                    }
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            String formattedDate = rs.getString("FormattedDate");
                            System.out.println("FormattedDate is: " + formattedDate);
                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                        }
                    }

                }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
                
            }
            
        }
        public void dateFormattingwithLocale(List<String> locales, List<String> formats){
            for(String locale: locales){
                
                String sql1 = "SET lc_time_names =(?)"; //uses this select to generate insert
   
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    ps.setString(1, locale);
                    ps.executeUpdate();
                    System.out.println("Using Locale: " + locale);
                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
                
                dateFormatting(formats);

                
            }
                
        }
        
        public void delete1stChildRow(){
                String sql1 = "SELECT RowId FROM charles.ObjectType_Data limit 1"; //uses this select to generate insert
                String rowId = null;
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            rowId = rs.getString("RowId");
                            
                        }
                    }
                    catch(SQLException e){
                        logger.error("SQL Syntax Error: " + e.toString());
                    }
                    

                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
                
                sql1 = "delete from ObjectType_Data " +" where RowId = (?)";

                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    ps.setString(1, rowId);
                    ps.executeUpdate();
                    System.out.println("Deleted first object in Object child table");
                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
               
            
        }
            
        

            
        

            

        
        
        
    
    
    	
    
    
    
    
    
    
    //procs and views start
    public void selectView() {

            String sql = "select * from GreaterThan250Errors";
            try(PreparedStatement ps = dataSource.prepareStatement(sql)){
                    try(ResultSet rs = ps.executeQuery()){
                            while(rs.next()) {
                                    System.out.println("Number of Errors: " + rs.getInt("count(Object_Error.ErrorId)") + "  Object Name: "+ rs.getString("ObjectName")+"\n");
                            }
                    }

            }
            catch(SQLException e) {
                    System.out.println("SQL Exception: " + e.getLocalizedMessage());
            }

    }
    public void dumpWithArg(int max) {
            String sql = "{ Call GetEvents(?) }";

            try(CallableStatement cs = dataSource.prepareCall(sql)){
                    cs.setInt(1, max);
                    try(ResultSet rs = cs.executeQuery()){
                            while(rs.next()){
                                    System.out.printf("ObjectRowId: %f\n", rs.getDouble("RowId"));
                            }
                    }	
            }
            catch(Exception e) {
                    System.out.println("SQL Error: " + e.getLocalizedMessage());
            }
    }
    public void procWithArgs(int i){
            String sql = "{ Call getObjsoverXerrors(?) }";

            try(CallableStatement cs = dataSource.prepareCall(sql)){
                    cs.setInt(1, i);
                    try(ResultSet rs = cs.executeQuery()){
                            while(rs.next()){
                                    System.out.println("ObjectRowId: " + rs.getInt("count(Object_Error.ErrorId)") + "  ObjectName: " + rs.getString("ObjectName")+ "\n");				}
                    }	
            }
            catch(Exception e) {
                    System.out.println("SQL Error: " + e.getLocalizedMessage());
            }


    }
    public void procWithoutArgs(){
                            String sql = "{ Call getObjsover500errors() }";

            try(CallableStatement cs = dataSource.prepareCall(sql)){
                    try(ResultSet rs = cs.executeQuery()){
                            while(rs.next()){
                                    System.out.println("ObjectRowId: " + rs.getInt("count(Object_Error.ErrorId)") + "  ObjectName: " + rs.getString("ObjectName")+ "\n");
                            }
                    }	
            }
            catch(Exception e) {
                    System.out.println("SQL Error: " + e.getLocalizedMessage());
            }



    }
    
        //procs and views end
    
    
    
    
    
    
    
    
    
    //triggers lab start, triggers have been created in database for after insert, before delete, and after update queries on table 'ObjectTypeData'
    public void delete5fromObjectTypeData(){
                
                String sql1 = "delete from ObjectType_Data " +
                                " where RowId >= 0 limit 5"; 
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    ps.executeUpdate();
                    System.out.println("Deleted first 5 rows from ObjectTypeData table");
                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }
               
            
    }
            
    public void add5toObjectTypeData(List<String> objRowIds){
        for(String objRowId: objRowIds){
            String sql = "INSERT INTO ObjectType_Data\n" +
                            "(" +
                            "ObjectRowId,  " +
                            "Height,  " +
                            "Duration,  " +
                            "PressureApplied,  " +
                            "EquipmentId)  " +
                            "VALUES  " +
                            "(  " +
                            "(?),  " +
                            ".32,  " +
                            ".32,  " +
                            ".82,  " +
                            "3)";

            try(CallableStatement cs = dataSource.prepareCall(sql)){
                    cs.setString(1, objRowId);
                    cs.executeUpdate();
                    System.out.println("Row inserted for ObjectType_Data table.");
            
                    
            }
            catch(Exception e) {
                    System.out.println("SQL Error: " + e.getLocalizedMessage());
            }
        }
           
           
    }
    
    public void updateObjectTypeData(){
        
                        String sql1 = "update ObjectType_Data " +
                                    " set Height = 12 " +
                                    " where RowId >= 30 limit 5"; 
                try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                    ps.executeUpdate();
                    System.out.println("Updated 5 rows.");
                           //ALTERNATE logger.debug("rowId: {}, objectRowId: {}", new Object[] {rowId, objectRowId});
                    }
                catch(SQLException e){
                    logger.error("SQL Syntax Error: " + e.toString());
                }

    }
    public void returnAlltriggeredData(){
        
                    String sql = "select * from ObjectType_Data_Audit";

            try(CallableStatement cs = dataSource.prepareCall(sql)){
                System.out.println("ObjectType_Data_Audit table data...");
                    try(ResultSet rs = cs.executeQuery()){
                            while(rs.next()){
                                    System.out.println(" RowId: "  +  rs.getInt("RowId") + " ObjectRowId: "  +  rs.getInt("ObjectRowId") +  " Operation: "  + rs.getString("Operation") + " FieldName: " + rs.getString("FieldName") + " OldValue: " + rs.getString("OldValue") + " NewValue: " + rs.getString("NewValue") + " LastDateUpDated: " + rs.getTimestamp("LastDateUpDated"));
                            }
                    }	
            }
            catch(Exception e) {
                    System.out.println("SQL Error: " + e.getLocalizedMessage());
            }
        }
        
    //triggers lab end   
    
    
    
    //final project start
    
    public void add30uniqueCustomers(List<String> fnames, List<String> lnames, List<String> addresses,  List<String> cities, List<String> states){
        

                for(int i = 0; i <=29; i++){
                    String afname = fnames.get(rand.nextInt(fnames.size()));
                    String alname = lnames.get(rand.nextInt(lnames.size()));
                    String anaddress = addresses.get(rand.nextInt(addresses.size()));
                    String acity = cities.get(rand.nextInt(cities.size()));
                    String astate = states.get(rand.nextInt(states.size()));
 
                                String sql1 = "Insert into Customer " +
                                                " (Fname, Lname, address, city, state) " +
                                                " Values( (?), (?), (?), (?), (?))";
                                System.out.println(afname + "   " + alname + "   " + anaddress + "   " + acity + "   " + astate);
                                
                               
                    try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                        ps.setString(1, afname);
                        ps.setString(2, alname);
                        ps.setString(3, anaddress);
                        ps.setString(4, acity);
                        ps.setString(5, astate);

                        ps.executeUpdate();



                    }
                    catch(SQLException e){
                        logger.error("SQL Syntax Error: " + e.toString());
                    }


                    
                    
                }
                System.out.println("Added 30 new unique Customers");
                
    }
    
    public void insrt2Ordersfor15Customers(List<String> ordersdesc){
        List<String> integers = new ArrayList();
        for (int j= 1; j<=15; j++){
            integers.add(Integer.toString(j));
        }
        for (int h= 1; h<=15; h++){
            String acustomernumber = integers.get(rand.nextInt(integers.size()));
            integers.remove(acustomernumber);

            for(int i= 1; i<=2;  i++){
                        String aorderdesc = ordersdesc.get(rand.nextInt(ordersdesc.size()));
 
                        System.out.println( acustomernumber  + "    " + aorderdesc);


                                    String sql1 = "Insert into Ordertbl " +
                                                    " (CustomerNo, OrderDesc)" +
                                                    " Values ((?), (?))";
                        
                        
                        try (PreparedStatement ps = dataSource.prepareStatement(sql1)){
                            ps.setString(1, acustomernumber);
                            ps.setString(2, aorderdesc);

                            ps.executeUpdate();



                        }
                        catch(SQLException e){
                            logger.error("SQL Syntax Error: " + e.toString());
                        }
                        
            }


                    
                 
            
            
        }
    }

    
    
    
}

    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

        
 
    


    


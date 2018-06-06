package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    public static final String dbName = "Appo2.db"; //database name
    public static final String tableName = "appointmentManagmentTable"; // table name
    //public static final String id_COLUMN_1 = "ID";
    public static final String title_COLUMN_2 = "Title"; // column title
    public static final String date_COLUMN_3 = "Date";  // column date
    public static final String time_COLUMN_4 = "Time";  // column time
    public static final String details_COLUMN_5 = "Details";    // column details


    public SQLiteDB(Context context) { // when the constructor call the db is create
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // sql query to create a table
        db.execSQL("create table " + tableName + "(Title TEXT PRIMARY KEY,Date TEXT,Time TEXT,Details TEXT)");
        //db.execSQL("create table " + tableName + "(ID  INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT,Date TEXT,Time INTEGER,Details TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);

    }

        // insert date to db
    public boolean inserData(String title,String date,String time,String details) {

        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        ContentValues contentValues = new ContentValues(); // create new object of the contentValues class
        contentValues.put(title_COLUMN_2, title); // used to add data to the particular columns using key and value
        contentValues.put(date_COLUMN_3, date);     // first add the column name and second the value gonna pass
        contentValues.put(time_COLUMN_4, time);
        contentValues.put(details_COLUMN_5, details);
        try {
            long result = db.insert(tableName, null, contentValues); // insert data to the database
                // insert method return -1 if the data didn't insert
            if (result == -1) {
                return false;  // so if result == -1 return false
            } else
                return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // get all data from db
    public Cursor getData(){ // cursor is a instance of the interface and used to read-write access to the result set returned by a database query.
        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        Cursor allData = db.rawQuery("select * from "+ tableName,null); // database query
                        // what rawQuery does is Runs the provided SQL and returns a cursor over the result set.
        return allData; //  return the data
    }

    // get all data from the db
    public List<String> getDataToDate(String date){

        List<String> list = new ArrayList<>();  // creating a string obj array

        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        Cursor allData = db.rawQuery("select * from "+ tableName,null); // data query

        //move the cursor to the first row of the results
        allData.moveToFirst();

        while (!allData.isAfterLast()) { //Returns whether the cursor is pointing to the position after the last row

            if (allData.getString(0) != null) { // check the column is null or not

                if (allData.getString(1).equals(date)){ // check the db data equals with the parameter value

                    // add the selected column value to the list
                    list.add(allData.getString(0)+" "+allData.getString(1)+" "+allData.getString(2)
                            +" "+allData.getString(3));
                }
            }
            allData.moveToNext(); // move the cursor to the next row
        }
             return list; // return the list
    }

    // update date in the db
    public boolean UpdateDB(String title,String date,String time,String details){

        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        ContentValues contentValues = new ContentValues(); // create new object of the contentValues class //This class is used to store a set of values
        contentValues.put(title_COLUMN_2,title);    // used to add data to the particular columns
        contentValues.put(date_COLUMN_3,date);  // first add the column name and second the value gonna pass
        contentValues.put(time_COLUMN_4,time);
        contentValues.put(details_COLUMN_5,details);

        db.update(tableName, contentValues,"Title = ?",new String[]{ title });  // used update method in SQLiteDatabase class
        return true;        //  condition what i want to pass is title(3rd argument in the update method parameter)
    }

    // update the date method (Move appointment)
    public boolean MoveData(String title,String date){

        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        ContentValues contentValues = new ContentValues(); // create new object of the contentValues class
        contentValues.put(title_COLUMN_2,title);    // used to add data to the particular columns
        contentValues.put(date_COLUMN_3,date);  // first add the column name and second the value gonna pass

        db.update(tableName, contentValues,"Title = ?",new String[]{ title }); // used update method in SQLiteDatabase class
        return true;       //  condition what i want to pass is title (3rd argument in the update method parameter)
    }

    // delete data by date method
    public Integer deleteDB(String date){

        SQLiteDatabase db = this.getWritableDatabase();  // used to write and read the data to the database
        return db.delete(tableName,"Date = ?", new String[]{date}); // used delete method in SQLiteDatabase class
    }


    // delete data by title method (by selecting)
    public Integer deleteDataBySelecting(String date){

        SQLiteDatabase db = this.getWritableDatabase();  // used to write and read the data to the database
        return db.delete(tableName,"Title = ?", new String[]{date});  // used delete method in SQLiteDatabase class
    }

    // search method
    public List<String> search(String text){

        List<String> list = new ArrayList<>(); // creating a string obj array

        SQLiteDatabase db = this.getWritableDatabase(); // used to write and read the data to the database
        Cursor allData = db.rawQuery("select * from "+ tableName,null); // data query

        //move the cursor to the first row of the results
        allData.moveToFirst();

        while (!allData.isAfterLast()) { //Returns whether the cursor is pointing to the position after the last row

            if (allData.getString(0) != null) { // check the column is null or not

                if (allData.getString(0).equals(text) || allData.getString(3).equals(text)){  // check the db data equals with the parameter value

                    // add the selected column value to the list
                        list.add(allData.getString(0) + " " + allData.getString(1) + " " + allData.getString(2)
                                + " " + allData.getString(3));
                }
            }
            allData.moveToNext();  // move the cursor to the next row
        }
            return list; // return the list
    }





}

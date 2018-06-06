package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private SQLiteDB appointmentDB; // create a instance of the SQLiteDB class

  // declaring the variables
  private CalendarView calendarView;
  private String date01;
  List<String> listArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appointmentDB = new SQLiteDB(this); // creating a new instance of the database class

        // initialization with the xml calenderView
        calendarView = (CalendarView) findViewById(R.id.calendar);

        // create the calender setOnDateChangeListner
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                date01 = (day) + "/" + (month+1) + "/" + year; // set the format for the date
            }
        });

    }

    // createAppointment button method
    public void bCreateAppointment(View view) {

        if(date01 == null){ // if the date is null show Toast
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
        }else { // if user select a date pass the select date to the createAppointment class and open the Activity
            Intent intent = new Intent(this, CreateAppointment.class);
            intent.putExtra("date", date01 + ""); // Returns the same Intent object to the other intent
            startActivity(intent); // start the activity
        }
    }

    // view button method
    public void bViewEdit(View view){

        if(date01==null) { // if date is null show a toast
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();

            }else {

            listArray = appointmentDB.getDataToDate(date01);  // search method in SQLiteDB takes one argument
            StringBuffer buffer = new StringBuffer(); // create obj of the string buffer
            for (int i = 0; i < listArray.size(); i++) {   // until the listArray size is finish the for loop will works

                String getListData = listArray.get(i);  // get the listArray value and set it to the String variable

                String[] Data = getListData.split(" ", 4);  // split the listArray to the 4 and it split using spaces

                // get data from the column index of the table and store in the butter
            buffer.append("Title " + Data[0] + "\n");
            buffer.append("Date " + Data[1] + "\n");
            buffer.append("Time " + Data[2] + "\n");
            buffer.append("Event " + Data[3] + "\n" + "----------------------------------" +
                    "----------------------------------------------------------" + "\n\n");
            }
            //when click the view button all saved data will show in the view_edit intent
            Intent intent = new Intent(this, View_EditAppointment.class);
            intent.putExtra("allData", buffer + ""); // Returns the same Intent object to the other intent
            startActivity(intent); //start activity
        }
    }

        // delete button method
    public void bDelete(View view){
        if(date01 == null){     // if date is null show a toast
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
        }else {  // create a new obj as alertDialog from AlertDialog class
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            View windowView = getLayoutInflater().inflate(R.layout.delete_popup_menu, null); // get the xml layout of the alter dialog
            TextView selectedDate = (TextView) windowView.findViewById(R.id.txtSeclectedDate); // textView of the alert dialog to show the date user selected
            selectedDate.setText(date01); // set the date to the textView

            alertDialog.setView(windowView); // set the xlm layout to the AlertDialog
            AlertDialog dialog = alertDialog.create(); // create the dialog
            dialog.show(); // show the alert dialog
        }
    }

     // delete all text method in the alert dialog
    public void txtDeleteAll(View view){

            Integer deleteData = appointmentDB.deleteDB(date01.toString()); // deleteDB method in SQLiteDB takes one argument
        // deleteDB method return 1 if the data is delete so
            if(deleteData >0){  // if deleteData greater than 0 data deleted and show toast
                Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();

            } else // show toast
                Toast.makeText(this, "Data not deleted", Toast.LENGTH_SHORT).show();
    }


    //delete by selecting text method in the alert dialog
    public void txtDeleteBySelecting(View view){

        if(date01==null) { // if date is null show a toast
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();

        }else {
            StringBuffer buffer = new StringBuffer();   // create obj of the string buffer
            listArray = appointmentDB.getDataToDate(date01);  // getDataToDate method in SQLiteDB takes one argument

            for (int i = 0; i < listArray.size(); i++) { // until the listArray size is finish the for loop will works

                String getListData = listArray.get(i);      // get the listArray value and set it to the String variable

                String[] Data = getListData.split(" ", 4);  // split the listArray to the 4 and it split using spaces

                // get data from the column index of the table and store in the butter
                buffer.append("Title " + Data[0] + "\n");
                buffer.append("Time " + Data[2] + "\n");
                buffer.append("Event " + Data[3] + "\n" + "----------------------------------" +
                        "----------------------------------------------------------" + "\n\n");
            }
            // when click the view button all saved data will show in the delete by selecting Activity
            Intent intent = new Intent(this, DeleteBySelecting.class);
            intent.putExtra("allData", buffer + ""); // Returns the same Intent object to the other intent
            startActivity(intent); // start activity
        }
    }

        // move button method
    public void bMove(View view){

        if(date01==null) { // if date is null show a toast
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();

        }else {
            StringBuffer buffer = new StringBuffer(); // create obj of the string buffer
            listArray = appointmentDB.getDataToDate(date01);  // getDataToDate method in SQLiteDB takes one argument

            for (int i = 0; i < listArray.size(); i++) { // until the listArray size is finish the for loop will works

                String getListData = listArray.get(i);  // get the listArray value and set it to the String variable

                String[] Data = getListData.split(" ", 4); // split the listArray to the 4 and it split using spaces

                // get data from the column index of the table and store in the butter
                buffer.append("Title " + Data[0] + "\n");
                buffer.append("Date "+ Data[1] + "\n");
                buffer.append("Event " + Data[3] + "\n" + "----------------------------------" +
                        "----------------------------------------------------------" + "\n\n");
            }
            // when click the view button all saved data will show in the delete by selecting Activity
            Intent intent = new Intent(this, MoveAppointment.class);
            intent.putExtra("allData", buffer + ""); // Returns the same Intent object to the other intent
            startActivity(intent); // start activity
        }
    }

    // search button method
    public void bSearch(View view){
        startActivity(new Intent(this, SearchWindow.class)); // start the searchWindow activity
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);   // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity

    }

}

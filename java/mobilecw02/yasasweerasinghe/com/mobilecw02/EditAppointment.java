package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditAppointment extends MainActivity {

    // declaring the variables
    private CalendarView calendarView;
    private TimePicker timePicker;
    private EditText txtTitle,txtDetails;
    private String date02,time;

    // create a instance of the SQLiteDB class
    private SQLiteDB appointmentDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_appointment);

        appointmentDB = new SQLiteDB(this); // creating a new instance of the database class

        // initialization with the xml editTexts, calender and timePicker
        calendarView = (CalendarView) findViewById(R.id.calendar02);
        timePicker = (TimePicker) findViewById(R.id.timePicker02);
        txtTitle = (EditText) findViewById(R.id.txtEditTitle02);
        txtDetails = (EditText) findViewById(R.id.editTextEvent02);

        // create the calender setOnDateChangeListner
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                date02 = (day) + "/" + (month+1) + "/" + year; // set the format for the date
                }
        });

        timePicker.setIs24HourView(true); // show the time digitally
        // perform set on time changed listener event
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minutes) {
                 time = ( hour + ":" + minutes); // set the format for the date
            }
        });

    }

    // update button method
    public void bUpdate(View view){

            // check all the fields are filled or not
        if(txtTitle.getText().toString().length() == 0 || time.equals(null)|| txtDetails.getText().toString().length() == 0){
            Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show(); // show toast
            if(date02 == null){ // if date null show a toast
                Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
            }

        }else {
            // use this boolean expression to make sure the data add to the db from the UpdateDB method
            boolean updateDB = appointmentDB.UpdateDB(txtTitle.getText().toString().toUpperCase(), // set title to upperCase
                    date02.toString(), time.toString().trim(), txtDetails.getText().toString());

            if (updateDB == true) {  // if updateDB method return true show Toast and open the Main Activity
                Toast.makeText(this, "Data Update", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));

            } else { // show toast
                Toast.makeText(this, "Data Not Update", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }
}

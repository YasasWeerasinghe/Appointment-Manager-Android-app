package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MoveAppointment extends MainActivity {

    private SQLiteDB appointmentDB; // create a instance of the SQLiteDB class

    // declaring the variables
    TextView txtAllData;
    EditText txtMoveTitle;
    CalendarView calendar03;
    String allData, date04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_appointment);

        appointmentDB = new SQLiteDB(this); // creating a new instance of the database class

        // initialization with the xml editTexts, textView and calenderView
        txtAllData = (TextView) findViewById(R.id.txtGonnaMove);
        txtMoveTitle = (EditText) findViewById(R.id.txtMoveTitle);
        calendar03 = (CalendarView) findViewById(R.id.calendar03);

        // take the value pass from the bMove method
        Bundle bundle = getIntent().getExtras();
        allData = bundle.getString("allData");
        txtAllData.setText(allData);     // store the passed value to the string

        // create the calender setOnDateChangeListner
        calendar03.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                date04 = (day) + "/" + (month + 1) + "/" + year; // set the format for the date
            }
        });
    }

    // moveAppointment method
    public void bMoveAppointment(View view) {

    try {  // check all the fields are fill or not
        if(txtMoveTitle.getText().toString().length()==0 || date04.equals(null)){
            Toast.makeText(this, "Give a title to the change the date", Toast.LENGTH_SHORT).show(); // show toast
    }else {
            // use this boolean expression to make sure the data add to the db from the MoveData method
        boolean MoveDate = appointmentDB.MoveData(txtMoveTitle.getText().toString().toUpperCase(), date04.toString()); // set title to upperCase

        if (MoveDate == true) { // if MoveData method return true show Toast and open the Main Activity
            Toast.makeText(this, "Data Update", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));

        } else { // show toast
            Toast.makeText(this, "Data Not Update", Toast.LENGTH_SHORT).show();
        }
    }
}catch (Exception e){  // if user didn't select date show toast
    Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
}
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent); // start the activity
        }
}

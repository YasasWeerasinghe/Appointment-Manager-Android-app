package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CreateAppointment extends MainActivity {

    private SQLiteDB appointmentDB; // create a instance of the SQLiteDB class

    // declaring the variables
    private TimePicker timePicker;
    private EditText txtTitle,txtDetails;
    private String calenderDate,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);

        appointmentDB = new SQLiteDB(this); // creating a new instance of the database class

        // initialization with the xml editTexts and timePicker
        txtTitle = (EditText) findViewById(R.id.txtEditTitle) ;
        txtDetails = (EditText) findViewById(R.id.editTextEvent);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        // take the value pass from the CreateAppointment method
        Bundle bundle = getIntent().getExtras();
        calenderDate = bundle.getString("date"); // store the passed value to the string


        timePicker.setIs24HourView(true); // show the time digitally
        // perform set on time changed listener event
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minutes) {
                 time = ( hour + ":" + minutes);  // show time in this format
            }
        });
    }


    // button save method
    public void bSave(View view) {
        try {  // used this try catch to prevent catch the app id the user click save button without adding any data
            // used check all the fields are fill or not
            if (txtTitle.getText().toString().length() == 0 || calenderDate.equals(null) || time.equals(null) || txtDetails.getText().toString().length() == 0) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();

            } else {
                try { // use this try catch to handel the database connection problems
                    // use this boolean expression to make sure the data add to the db from the insertData method
                    boolean insertToDB = appointmentDB.inserData(txtTitle.getText().toString().trim().toUpperCase(), calenderDate.toString(),
                            time.toString().trim(), txtDetails.getText().toString().trim());
                    if (insertToDB == false) { // if data adding fails it's return false and show a Toast
                        Toast.makeText(this, "Change the title. It's already used.", Toast.LENGTH_SHORT).show();
                    } else if (insertToDB == true) { // if data adding succeed return true and show Toast and going to the Main Activity
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));

                    } else { // show toast if data adding fails
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {  // show a toast if something went wrong with database
                    Toast.makeText(this, "Something went wrong. Please restart the app ", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) { // show a toast if user didn't select a time
            Toast.makeText(this, "Select a time", Toast.LENGTH_SHORT).show();
        }
    }

    // Thesaurus button click
    public void bThesaurusClick(View view){
        startActivity(new Intent(this, ThesaurusPopUp.class)); // start the Thesaurus pop up window
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent); // start the activity
    }

}

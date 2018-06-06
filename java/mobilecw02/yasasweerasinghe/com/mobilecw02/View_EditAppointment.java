package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class View_EditAppointment extends MainActivity {

    // declaring the variables
    TextView txtViewData;
    String viewAllData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_appointment);

        // initialization with the xml textView
        txtViewData = (TextView) findViewById(R.id.txtViewAllData);

        try {  // take the value pass from the bViewEdit method
        Bundle bundle = getIntent().getExtras();
        viewAllData = bundle.getString("allData"); // store passed data in to the string variable
        txtViewData.setText(viewAllData);  // set data sored string variable to the textView

        }catch (Exception e){  // if something went wrong with the data passing show a toast and pretend the app crash
             Toast.makeText(this, "Something went wrong while date selecting", Toast.LENGTH_SHORT).show();
        }
    }

    // edit button method
    public void bEdit(View view){

        // start the EditAppointment Activity
        startActivity(new Intent(this, EditAppointment.class));
        finish(); // finish the current activity
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() { // prevent the score window open
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }


}

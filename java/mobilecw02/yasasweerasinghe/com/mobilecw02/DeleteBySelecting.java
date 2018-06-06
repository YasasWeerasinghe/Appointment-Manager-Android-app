package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DeleteBySelecting extends MainActivity {

    private SQLiteDB appointmentDB; // create a instance of the SQLiteDB class

    // declaring the variables
    TextView txtSelectByDate;
    EditText txtSelectTitle;
    String viewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_by_selecting);

        appointmentDB = new SQLiteDB(this); // creating a new instance of the database class

        // initialization with the xml editTexts and textView
        txtSelectByDate = (TextView) findViewById(R.id.txtSortToDate);
        txtSelectTitle = (EditText) findViewById(R.id.txtEnterTitle);

        try {  // take the value pass from the txtDeleteBySelecting method
            Bundle bundle = getIntent().getExtras();
            viewAll = bundle.getString("allData"); // store the passed value to the string
            txtSelectByDate.setText(viewAll); // set String to the textView

        }catch (Exception e){  // show toast if something went wrong when data passing to the Activity to a activity
            Toast.makeText(this, "Something went wrong while date selecting", Toast.LENGTH_SHORT).show();
        }
    }

    // delete button method
    public void bDeleteBySelecting(View view) {

            // check title field is empty or not
        if (txtSelectTitle.getText().toString().length()==0) {
            Toast.makeText(this, "Enter a title to proceed.", Toast.LENGTH_SHORT).show(); // show toast
        } else {  // create a new obj as alertDialog from AlertDialog class
            AlertDialog.Builder confirmationAlert = new AlertDialog.Builder(this);
            confirmationAlert.setTitle("Delete Confirm");  //  dialog title
            confirmationAlert.setIcon(R.drawable.qmark); //dialog icon
            confirmationAlert.setMessage("Do you want to delete?"); // dialog message
            confirmationAlert.setCancelable(false); // the alert box not disappear when touch the out side

            // confirmation positive button
            confirmationAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Integer deleteData = appointmentDB.deleteDataBySelecting(txtSelectTitle.getText().toString().toUpperCase()); // deleteDB method in SQLiteDB takes one argument
                    // deleteDB method return 1 if the data is delete so
                    if (deleteData > 0) {       // if deleteData greater than 0 data deleted and show toast
                        Toast.makeText(DeleteBySelecting.this, "Data deleted", Toast.LENGTH_SHORT).show();

                    } else  // show toast
                        Toast.makeText(DeleteBySelecting.this, "Data not deleted", Toast.LENGTH_SHORT).show();

                }
            });
            // cancel the dialog (cancel the action)
            confirmationAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = confirmationAlert.create(); // create the alert bow
            alertDialog.show(); // show the alert dialog

        }
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }


}





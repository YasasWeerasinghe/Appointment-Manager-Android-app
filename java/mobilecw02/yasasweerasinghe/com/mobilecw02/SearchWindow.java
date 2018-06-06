package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchWindow extends MainActivity {

    private SQLiteDB appointmentDB; // create a instance of the SQLiteDB class
    // declaring the variables
    EditText txtSearchWidget;
    TextView txtSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_window);

        appointmentDB = new SQLiteDB(this);  // creating a new instance of the database class

        // initialization with the xml editTexts and textView
       txtSearchWidget = (EditText) findViewById(R.id.txtSearch);
       txtSearchResult = (TextView) findViewById(R.id.txtResualt);


}

        // search button method
    public void bSearch(View view) {

                   // check the search field is filled or not
        if(txtSearchWidget.getText().toString().length() == 0){
            Toast.makeText(this, "Enter text to search", Toast.LENGTH_SHORT).show(); //  show toast
        }else {

            String getListData = txtSearchWidget.getText().toString(); // set the editText value to the String

            StringBuffer buffer = new StringBuffer(); // create obj of the string buffer

            listArray = appointmentDB.search(getListData); // search method in SQLiteDB takes one argument

            for (int i = 0; i < listArray.size(); i++) { // until the listArray size is finish the for loop will works

                String arr = listArray.get(i); // get the listArray value and set it to the String variable

                String[] Data = arr.split(" ", 4); // split the listArray to the 4 and it split using spaces


               // String search = String.valueOf(buffer.append(Data[0])) + String.valueOf(buffer.append(Data[3]));

//                if (search.contains(txtSearchWidget.getText().toString().toUpperCase()) || search.contains(txtSearchWidget.getText().toString())) {
////

                    // get data from the column index of the table and store in the butter
                    buffer.append("Title " + Data[0] + "\n");
                    buffer.append("Date " + Data[1] + "\n");
                    buffer.append("Time " + Data[2] + "\n");
                    buffer.append("Event " + Data[3] + "\n" + "----------------------------------" +
                            "----------------------------------------------------------" + "\n\n");

                    txtSearchResult.setText(buffer.toString()); // set buffer value to the textView
                }
            }
//        txtSearchResult.setText("No search result"); // set text to the textView
//        Toast.makeText(this, "No search result", Toast.LENGTH_SHORT).show(); // show toast
    }


    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }

}

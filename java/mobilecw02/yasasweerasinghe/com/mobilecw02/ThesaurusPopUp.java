package mobilecw02.yasasweerasinghe.com.mobilecw02;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class ThesaurusPopUp extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thesaurus_pop_up);

        // give a size for the pop Up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //window size
        getWindow().setLayout((int) (width * .8), (int) (height * .6));


    }


    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  // This flag is used to create a new task and launch an activity into it.
        startActivity(intent); // start the activity
    }

}

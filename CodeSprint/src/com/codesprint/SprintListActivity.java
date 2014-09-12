package com.codesprint;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codesprint.R;
import com.codesprint.sprint.Sprint;
import com.codesprint.sprint.SprintModel;

/**
 * An activity representing a list of Sprints. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link SprintDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SprintListFragment} and the item details (if present) is a
 * {@link SprintDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link SprintListFragment.Callbacks} interface to listen for item selections.
 */
public class SprintListActivity extends FragmentActivity implements
		SprintListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
    final SprintModel sprintModel = new SprintModel(this);
    ArrayList<Sprint> sprints;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actionitems, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
	      // Get golf courses from a new data model, initialized from a file named courses.txt
	      //final SprintModel sprintModel = new SprintModel(this);
	      //sprintModel.updateSQLEntry(new Sprint("Complete new task", 40f, 40f));
	     	sprints = sprintModel.getSprints();
	 
	      FragmentManager fm = getSupportFragmentManager();
	      setContentView(R.layout.activity_sprint_list);
	 
	      if (findViewById(R.id.sprint_detail_container) != null) {
	        mTwoPane = true;
	        // Its a tablet, so create a new detail fragment if one does not already exist
	        SprintDetailFragment df = (SprintDetailFragment) fm.findFragmentByTag("Detail");
	        if (df == null) {
	            // Initialize new detail fragment
	            df = new SprintDetailFragment();
	            Bundle args = new Bundle();
	            args.putParcelable("sprint", new Sprint("Welcome to CodeSprint Sprint Details", 0f, 0f));
	            df.setArguments(args);
	            fm.beginTransaction().replace(R.id.sprint_detail_container, df, "Detail").commit();
	        }  
	      }
	 
	      // Initialize a new sprint list fragment, if one does not already exist     
	       SprintListFragment cf = (SprintListFragment) fm.findFragmentByTag("List");
	       if ( cf == null) {
	           cf = new SprintListFragment(this);
	           Bundle arguments = new Bundle();
	           arguments.putParcelableArrayList("sprints", sprints);
	           cf.setArguments(arguments);          
	           Log.v("myApp", "List Activity: Create a new List Fragment " + cf);
	           fm.beginTransaction().replace(R.id.sprint_list, cf, "List").commit();
	       }
	    }

	/**
	 * Callback method from {@link SprintListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
    @Override
    public void onItemSelected(Sprint c) {
    if (mTwoPane) {
      // It's a tablet, so update the detail fragment
      Bundle arguments = new Bundle();
      // Pass the selected Sprint object to the DetailFragment
      arguments.putParcelable("sprint", c);
      SprintDetailFragment fragment = new SprintDetailFragment();
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.sprint_detail_container, fragment, "Detail")
          .commit();
    } else {
      // It's a phone, so launch a new detail activity
      Intent detailIntent = new Intent(this, SprintDetailActivity.class);
      // Pass the selected Sprint object to the DetailActivity
      detailIntent.putExtra("sprint", c);
      startActivity(detailIntent);
    }
  }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.add_sprint:
            	if(item.isEnabled()==true)
            	{
            		openAdd(this);
            	}
                return true;
            case R.id.about_app:
                openAbout(this);
                return true;
            case R.id.delete_sprint:
	              Toast.makeText(this, "You can delete sprints by holding them for a few seconds.", Toast.LENGTH_LONG).show();
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void openAbout(final Context context) {
    LayoutInflater layoutInflater = LayoutInflater.from(this);
   	 View promptView = layoutInflater.inflate(R.layout.about, null);
   	 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
   	 // set prompts.xml to be the layout file of the alertdialog builder
   	 alertDialogBuilder.setView(promptView);
   	 
   	 Typeface custom_font_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
        Typeface custom_font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf");
        Typeface custom_font_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Italic.otf");

   	 final TextView labelAbout = (TextView) promptView.findViewById(R.id.labelAboutApp);
   	 labelAbout.setTypeface(custom_font_regular);

   	 // setup a dialog window
   	 alertDialogBuilder.setCancelable(false)
   	 					.setPositiveButton("OK", new DialogInterface.OnClickListener() {

   	 						public void onClick(DialogInterface dialog, int id) {
   	                         dialog.cancel();
   	                     }
   	                 })
   	         .setNegativeButton("Cancel",
   	                 new DialogInterface.OnClickListener() {
   	                     public void onClick(DialogInterface dialog, int id) {
   	                         dialog.cancel();
   	                     }
   	                 });
   	 // create an alert dialog
   	 AlertDialog alertD = alertDialogBuilder.create();
   	 alertD.show();
   	 
   	 Button bqn = alertD.getButton(DialogInterface.BUTTON_NEGATIVE);  
   	 bqn.setBackgroundColor(Color.parseColor("#FF9900"));
   	 
   	 Button bqp = alertD.getButton(DialogInterface.BUTTON_POSITIVE);  
   	 bqp.setBackgroundColor(Color.parseColor("#FF9900"));
   }
    
    public void openAdd(final Context context)
    {
    	// get prompts.xml view
    	 LayoutInflater layoutInflater = LayoutInflater.from(this);
    	 View promptView = layoutInflater.inflate(R.layout.sprint_form, null);
    	 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    	 // set prompts.xml to be the layout file of the alertdialog builder
    	 alertDialogBuilder.setView(promptView);
    	 
    	 Typeface custom_font_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
         Typeface custom_font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf");
         Typeface custom_font_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Italic.otf");

         
    	 final EditText inputTitle = (EditText) promptView.findViewById(R.id.sprintTitleInput);
    	 final EditText inputEstHours = (EditText) promptView.findViewById(R.id.sprintEstHoursInput);
    	 final EditText inputEstWeeks = (EditText) promptView.findViewById(R.id.sprintEstWeeksInput);
    	 final TextView labelTitle = (TextView) promptView.findViewById(R.id.labelTitle);
    	 final TextView labelEstWeeks = (TextView) promptView.findViewById(R.id.labelEstWeeks);
    	 final TextView labelEstHours = (TextView) promptView.findViewById(R.id.labelEstHours);
    	 labelTitle.setTypeface(custom_font_regular);
    	 labelEstWeeks.setTypeface(custom_font_regular);
    	 labelEstHours.setTypeface(custom_font_regular);


    	 // setup a dialog window
    	 alertDialogBuilder.setCancelable(false)
    	 					.setPositiveButton("Add", new DialogInterface.OnClickListener() {

    	 						public void onClick(DialogInterface dialog, int id) {

    	                         // get user input and set it to result
    	 							try
    	 							{
    	 						 sprintModel.updateSQLEntry(new Sprint(inputTitle.getText().toString(), 
    	 								 Float.parseFloat(inputEstWeeks.getText().toString()), 
    	 								 Float.parseFloat(inputEstHours.getText().toString())));
    	 						 sprints = sprintModel.getSprints();
    	 							}
    	 							catch (Exception ex)
    	 							{
    	 					              Toast.makeText(context, "Your Sprint item could not be created. Enter details in all appropriate fields.", Toast.LENGTH_LONG).show();
    	 							}
    	 						 FragmentManager fm = getSupportFragmentManager();
    	 					       SprintListFragment cf = (SprintListFragment) fm.findFragmentByTag("List");
    	 				           cf = new SprintListFragment(context);
    	 				           Bundle arguments = new Bundle();
    	 				           arguments.putParcelableArrayList("sprints", sprints);
    	 				           cf.setArguments(arguments);          
    	 				           Log.v("myApp", "List Activity: Create a new List Fragment " + cf);
    	 				           fm.beginTransaction().replace(R.id.sprint_list, cf, "List").commit();
    	 						 //Intent intent = getIntent();
    	 						 //finish();
    	 						 //startActivity(intent); 
    	 						 //SprintListFragment s = new SprintListFragment();
    	 					     //s.notifyFragment(sprints);
    	                     }
    	                 })
    	         .setNegativeButton("Cancel",
    	                 new DialogInterface.OnClickListener() {
    	                     public void onClick(DialogInterface dialog, int id) {
    	                         dialog.cancel();
    	                     }
    	                 });
    	 // create an alert dialog
    	 AlertDialog alertD = alertDialogBuilder.create();
    	 alertD.show();
    	 
    	 Button bqn = alertD.getButton(DialogInterface.BUTTON_NEGATIVE);  
    	 bqn.setBackgroundColor(Color.parseColor("#FF9900"));
    	 
    	 Button bqp = alertD.getButton(DialogInterface.BUTTON_POSITIVE);  
    	 bqp.setBackgroundColor(Color.parseColor("#FF9900"));
    }
    
    public Context getContext()
    {
    	return this;
    }
    
}

package com.codesprint;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codesprint.R;
import com.codesprint.sprint.Sprint;
import com.codesprint.sprint.SprintContent;
import com.codesprint.sprint.SprintModel;

/**
 * A fragment representing a single Sprint detail screen. This fragment is
 * either contained in a {@link SprintListActivity} in two-pane mode (on
 * tablets) or a {@link SprintDetailActivity} on handsets.
 */
public class SprintDetailFragment extends Fragment {
	
	private Sprint sprint;
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private SprintContent.SprintItem mItem;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SprintDetailFragment() {
	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
 
      // If intent arguments have a course object, get it
      if (getArguments().containsKey("sprint")) {
        sprint = getArguments().getParcelable("sprint");
      }
    }

    @SuppressLint("ShowToast")
	@Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
       final Bundle savedInstanceState) {
 
      final View rootView = inflater.inflate(R.layout.fragment_sprint_detail, container, false);
      // Display the selected sprint, or just a welcome message
      if (sprint != null) {
    	  

    	  
     	 Typeface custom_font_regular = Typeface.createFromAsset(this.getActivity().getApplicationContext().getAssets(), "fonts/Quicksand-Regular.otf");
         Typeface custom_font_bold = Typeface.createFromAsset(this.getActivity().getApplicationContext().getAssets(), "fonts/Quicksand-Bold.otf");
         Typeface custom_font_italic = Typeface.createFromAsset(this.getActivity().getApplicationContext().getAssets(), "fonts/Quicksand-Italic.otf");

         TextView vText = ((TextView) rootView.findViewById(R.id.sprintDetTitle));
        ((TextView) rootView.findViewById(R.id.sprintDetTitle)).setText(sprint.id);
        ((TextView) rootView.findViewById(R.id.sprintDetEstHours)).setText("Estimated Hours : " + Float.toString(sprint.estHours));
        ((TextView) rootView.findViewById(R.id.sprintDetEstWeeks)).setText("Estimated Weeks : " +Float.toString(sprint.estWeeks));
        ((TextView) rootView.findViewById(R.id.sprintDetCompletedHours)).setText("Completed Hours : " +Float.toString(sprint.completedHours));
        ((TextView) rootView.findViewById(R.id.sprintDetCompletedWeeks)).setText("Completed Weeks : " +Float.toString(sprint.completedWeeks));
        ((TextView) rootView.findViewById(R.id.sprintDetTitle)).setTypeface(custom_font_regular);
        ((TextView) rootView.findViewById(R.id.sprintDetEstHours)).setTypeface(custom_font_regular);
        ((TextView) rootView.findViewById(R.id.sprintDetEstWeeks)).setTypeface(custom_font_regular);
        ((TextView) rootView.findViewById(R.id.sprintDetCompletedHours)).setTypeface(custom_font_regular);
        ((TextView) rootView.findViewById(R.id.sprintDetCompletedWeeks)).setTypeface(custom_font_regular);
        ((TextView) rootView.findViewById(R.id.sprintDetProgress)).setTypeface(custom_font_regular);
        
        mProgress = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        
        while (mProgressStatus < 100) {
            mProgressStatus = (int) ((sprint.completedHours / sprint.estHours )* 100f);
            break;
        }
        
        mProgress.setProgress(mProgressStatus);

        // Start lengthy operation in a background thread
        /*
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = (int) ((sprint.completedHours / sprint.estHours )* 100f);
                    onCreateView(inflater, null, null);

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
        */
        Button button = (Button) rootView.findViewById(R.id.sprintEdit);
        
        button.setTypeface(custom_font_bold);

        button.setOnClickListener(new OnClickListener()
        {
          public void onClick(View v)
          {
        	  final Context context = v.getContext();
        	  final SprintModel sprintModel = new SprintModel(context);
          	// get prompts.xml view
         	 LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
         	 View promptView = layoutInflater.inflate(R.layout.sprint_edit_form, null);
         	 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
         	 // set prompts.xml to be the layout file of the alertdialog builder
         	 alertDialogBuilder.setView(promptView);
         	 
         	 final Typeface custom_font_regular = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Quicksand-Regular.otf");
              final Typeface custom_font_bold = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Quicksand-Bold.otf");
              final Typeface custom_font_italic = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/Quicksand-Italic.otf");

              
         	 final EditText inputEstHours = (EditText) promptView.findViewById(R.id.sprintEstHoursInputEdit);
         	 final EditText inputEstWeeks = (EditText) promptView.findViewById(R.id.sprintEstWeeksInputEdit);
         	 final EditText inputCompHours = (EditText) promptView.findViewById(R.id.sprintCompHoursInputEdit);
         	 final EditText inputCompWeeks = (EditText) promptView.findViewById(R.id.sprintCompWeeksInputEdit);
         	 final TextView labelEstWeeks = (TextView) promptView.findViewById(R.id.labelEstWeeksEdit);
         	 final TextView labelEstHours = (TextView) promptView.findViewById(R.id.labelEstHoursEdit);
         	 final TextView labelCompWeeks = (TextView) promptView.findViewById(R.id.labelCompWeeksEdit);
         	 final TextView labelCompHours = (TextView) promptView.findViewById(R.id.labelCompHoursEdit);
         	 labelEstWeeks.setTypeface(custom_font_regular);
         	 labelEstHours.setTypeface(custom_font_regular);
         	 labelCompWeeks.setTypeface(custom_font_regular);
         	 labelCompHours.setTypeface(custom_font_regular);


         	 // setup a dialog window
         	 alertDialogBuilder.setCancelable(false)
         	 					.setPositiveButton("Done", new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog, int id) {

         	                         // get user input and set it to result
         	 							try
         	 							{
         	 								sprint.estHours = Float.parseFloat(inputEstHours.getText().toString());
         	 								sprint.estWeeks = Float.parseFloat(inputEstWeeks.getText().toString());
         	 								sprint.completedHours = Float.parseFloat(inputCompHours.getText().toString());
         	 								sprint.completedWeeks = Float.parseFloat(inputCompWeeks.getText().toString());
         	 								sprintModel.updateSQLEntry(sprint);
         	 								
         	 								Update(rootView);
         	 							}
         	 							catch (Exception ex)
         	 							{
         	 					              Toast.makeText(context, "Please fill in all sprint details information", Toast.LENGTH_LONG).show();

         	 							}
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
        });
      }
      else {
      ((TextView) rootView.findViewById(R.id.sprintDetTitle)).setText("Welcome to CodeSprint Sprint Details");
      }
      return rootView;
    }
    
    public void Update(View rootView)
    {
    	
    	 final Typeface custom_font_regular = Typeface.createFromAsset(rootView.getContext().getAssets(), "fonts/Quicksand-Regular.otf");
         final Typeface custom_font_bold = Typeface.createFromAsset(rootView.getContext().getAssets(), "fonts/Quicksand-Bold.otf");
         final Typeface custom_font_italic = Typeface.createFromAsset(rootView.getContext().getAssets(), "fonts/Quicksand-Italic.otf");

         
         
        TextView vText = ((TextView) rootView.findViewById(R.id.sprintDetTitle));
       ((TextView) rootView.findViewById(R.id.sprintDetTitle)).setText(sprint.id);
       ((TextView) rootView.findViewById(R.id.sprintDetEstHours)).setText("Estimated Hours : " + Float.toString(sprint.estHours));
       ((TextView) rootView.findViewById(R.id.sprintDetEstWeeks)).setText("Estimated Weeks : " +Float.toString(sprint.estWeeks));
       ((TextView) rootView.findViewById(R.id.sprintDetCompletedHours)).setText("Completed Hours : " +Float.toString(sprint.completedHours));
       ((TextView) rootView.findViewById(R.id.sprintDetCompletedWeeks)).setText("Completed Weeks : " +Float.toString(sprint.completedWeeks));
       ((TextView) rootView.findViewById(R.id.sprintDetTitle)).setTypeface(custom_font_regular);
       ((TextView) rootView.findViewById(R.id.sprintDetEstHours)).setTypeface(custom_font_regular);
       ((TextView) rootView.findViewById(R.id.sprintDetEstWeeks)).setTypeface(custom_font_regular);
       ((TextView) rootView.findViewById(R.id.sprintDetCompletedHours)).setTypeface(custom_font_regular);
       ((TextView) rootView.findViewById(R.id.sprintDetCompletedWeeks)).setTypeface(custom_font_regular);
       
       while (mProgressStatus < 100) {
           mProgressStatus = (int) ((sprint.completedHours / sprint.estHours )* 100f);
           break;
       }
       
       mProgress.setProgress(mProgressStatus);
    }
}

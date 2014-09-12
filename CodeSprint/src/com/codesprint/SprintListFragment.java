package com.codesprint;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codesprint.R;
import com.codesprint.sprint.Sprint;
import com.codesprint.sprint.SprintModel;

/**
 * A list fragment representing a list of Sprints. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link SprintDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
@SuppressLint("ValidFragment")
public class SprintListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	public Context context;
	public SprintArrayAdapter adapter;
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
    public interface Callbacks {
    	 
        // Modify the callback interface to pass Sprint object
        public void onItemSelected(Sprint c);
      }
   
      private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        // Modify the local dummy callback to also pass Sprint
        public void onItemSelected(Sprint c) {
        }
      };

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
  	public SprintListFragment() {
	}
	public SprintListFragment(Context _context) {
		context = _context;
	}

	   private ArrayList<Sprint> sprints = new ArrayList<Sprint>();
	   
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        return inflater.inflate(R.layout.fragment_sprint_list, null);
	    }
	   
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	 
	      // Get courses array from argument
	      if (getArguments() != null && getArguments().containsKey("sprints")) {
	       sprints = getArguments().getParcelableArrayList("sprints");          
	      }
	      
	      // Initialize the display adapter
	      adapter = new SprintArrayAdapter(context, sprints);
	      setListAdapter(adapter);

	          
	      /*
	      setListAdapter(new SimpleCursorAdapter(getActivity(),
	                R.layout.sprint_listitem, null, new String[] {
	                        SQLTableEntry.COLUMN_NAME_SPRINT_ID, SQLTableEntry.COLUMN_NAME_EST_HOURS,
	                        SQLTableEntry.COLUMN_NAME_EST_WEEKS }, new int[] { R.id.sprintID,
	                        R.id.sprintEstHours, R.id.sprintEstWeeks }, 0));

	        // Load the content
	        getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {
	            @Override
	            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	                return new CursorLoader(getActivity(),
	                        SQLTableEntry.URI_SPRINTS, SQLTableEntry.FIELDS, null, null,
	                        null);
	            }

	            @Override
	            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
	                ((SimpleCursorAdapter) getListAdapter()).swapCursor(c);
	            }

	            @Override
	            public void onLoaderReset(Loader<Cursor> arg0) {
	                ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
	            }
	        });
	        */
	    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
		
	      getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

	          @Override
	          public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                  int arg2, long arg3) {
	        	  
	        	  final int position = arg2;
	        	  
	        	// get prompts.xml view
	         	 LayoutInflater layoutInflater = LayoutInflater.from(context);
	         	 View promptView = layoutInflater.inflate(R.layout.delete_sprint, null);
	         	 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	         	 // set prompts.xml to be the layout file of the alertdialog builder
	         	 alertDialogBuilder.setView(promptView);
	         	 
	         	 Typeface custom_font_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
	              Typeface custom_font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf");
	              Typeface custom_font_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Italic.otf");

	         	 final TextView labelDeleteSprint = (TextView) promptView.findViewById(R.id.labelDeleteSprint);
	         	 labelDeleteSprint.setTypeface(custom_font_regular);


	         	 // setup a dialog window
	         	 alertDialogBuilder.setCancelable(false)
	         	 					.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

	         	 						public void onClick(DialogInterface dialog, int id) {

	         	                         // get user input and set it to result
	         	 			        	  SprintModel sprintModel = new SprintModel(context);
	         	 			        	  sprintModel.deleteSQLEntry(sprints.get(position));
	         	 			        	  sprints.remove(position);
	         	 			        	  adapter = new SprintArrayAdapter(context, sprints);
	         	 			        	  adapter.notifyDataSetChanged();
	         	 			        	
	         	 			        
	         	 			  	       FragmentManager fm = SprintListFragment.this.getFragmentManager();
	       	 					       SprintListFragment cf = (SprintListFragment) fm.findFragmentByTag("List");
	       	 				           cf = new SprintListFragment(context);
	       	 				           Bundle arguments = new Bundle();
	       	 				           arguments.putParcelableArrayList("sprints", sprints);
	       	 				           cf.setArguments(arguments);          
	       	 				           Log.v("myApp", "List Activity: Create a new List Fragment " + cf);
	       	 				           fm.beginTransaction().replace(R.id.sprint_list, cf, "List").commit();
	       	 				           
	         	 			           Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
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
	              return true;
	          }
	      });
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
      super.onListItemClick(listView, view, position, id);
      // Modify callback to pass the selected course in callback instead of DummyContent
      adapter = new SprintArrayAdapter(context, sprints);
      Sprint sprint = adapter.getSprint(position);
      mCallbacks.onItemSelected((Sprint) sprint);
      Toast.makeText(context, sprint.id,Toast.LENGTH_SHORT).show();
    }
    

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
}

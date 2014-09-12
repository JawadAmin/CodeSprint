package com.codesprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.codesprint.R;

/**
 * An activity representing a single Sprint detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link SprintListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link SprintDetailFragment}.
 */
public class SprintDetailActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprint_detail);
   
        getActionBar().setDisplayHomeAsUpEnabled(true);
   
        if (savedInstanceState == null) {
         // Get the course to display from intent and add to fragment argument
          Bundle arguments = new Bundle();
          arguments.putParcelable("sprint", getIntent().getParcelableExtra("sprint"));
          SprintDetailFragment fragment = new SprintDetailFragment();
          fragment.setArguments(arguments);
          getSupportFragmentManager().beginTransaction()
              .add(R.id.sprint_detail_container, fragment)
              .commit();
        }
      }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					SprintListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

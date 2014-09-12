package com.codesprint;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codesprint.R;
import com.codesprint.sprint.Sprint;

public class SprintArrayAdapter extends BaseAdapter {

	public List<Sprint> sprintsList;
	Context context;
	
	public SprintArrayAdapter(Context _context, ArrayList sprints)
	{
		context = _context;
		sprintsList = sprints;
	}
	
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return sprintsList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return sprintsList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        if(arg1 == null)
        {
        		context = context.getApplicationContext();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.sprint_listitem, arg2,false);
        }

        Typeface custom_font_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
        Typeface custom_font_bold = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf");
        Typeface custom_font_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Italic.otf");

        TextView sprintID = (TextView)arg1.findViewById(R.id.sprintID);
        sprintID.setTypeface(custom_font_bold);
        TextView sprintEstHours = (TextView)arg1.findViewById(R.id.sprintEstHours);
        sprintEstHours.setTypeface(custom_font_regular);
        //TextView sprintEstWeeks = (TextView)arg1.findViewById(R.id.sprintEstWeeks);
        //sprintEstWeeks.setTypeface(custom_font_regular);
        TextView sprintCompHours = (TextView)arg1.findViewById(R.id.sprintCompletedHours);
        sprintCompHours.setTypeface(custom_font_regular);
        //TextView sprintCompWeeks = (TextView)arg1.findViewById(R.id.sprintCompletedWeeks);
        //sprintID.setTypeface(custom_font_bold);

        Sprint sprint = sprintsList.get(arg0);

        sprintID.setText(sprint.id);
        sprintEstHours.setText("Estimated Hours : " + Float.toString(sprint.estHours));
        sprintCompHours.setText("Completed Hours : " + Float.toString(sprint.completedHours));

        return arg1;
    }
    
    public Sprint getSprint(int position)
    {
        return sprintsList.get(position);
    }

} 

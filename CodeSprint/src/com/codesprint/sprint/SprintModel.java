package com.codesprint.sprint;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.codesprint.sprint.SQLTableDefn.SQLTableEntry;

public class SprintModel {

	private ArrayList<Sprint> sprintsArray = new ArrayList<Sprint>();
	SprintReaderDBHelper mDbHelper;
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + SQLTableEntry.TABLE_NAME + " (" +
	    		SQLTableEntry._ID + " INTEGER PRIMARY KEY," +
	    		SQLTableEntry.COLUMN_NAME_SPRINT_ID + TEXT_TYPE + COMMA_SEP +
	    SQLTableEntry.COLUMN_NAME_EST_HOURS + TEXT_TYPE + COMMA_SEP +
	    SQLTableEntry.COLUMN_NAME_EST_WEEKS + TEXT_TYPE + COMMA_SEP +
	    SQLTableEntry.COLUMN_NAME_COMP_HOURS + TEXT_TYPE + COMMA_SEP +
	    SQLTableEntry.COLUMN_NAME_COMP_WEEKS + TEXT_TYPE +
	    " )";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + SQLTableEntry.TABLE_NAME;
	
	public SprintModel(Context context){
		try {
			mDbHelper = new SprintReaderDBHelper(context);
			this.readSQLTable();
		}
		catch (Exception ex) {
			Log.d("SprintModel","Failed to instantiate SprintModel");
		}
	}
	
	public ArrayList<Sprint> getSprints(){
		sprintsArray = new ArrayList<Sprint>();
		this.readSQLTable();
		return sprintsArray;
	}
	
	public class SprintReaderDBHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "FeedReader.db";

	    public SprintReaderDBHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	
	public void updateSQLEntry(Sprint sprint)
	{
		
		this.deleteSQLEntry(sprint);
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(SQLTableEntry.COLUMN_NAME_SPRINT_ID, sprint.id);
		values.put(SQLTableEntry.COLUMN_NAME_EST_HOURS, sprint.estHours);
		values.put(SQLTableEntry.COLUMN_NAME_EST_WEEKS, sprint.estWeeks);
		values.put(SQLTableEntry.COLUMN_NAME_COMP_HOURS, sprint.completedHours);
		values.put(SQLTableEntry.COLUMN_NAME_COMP_WEEKS, sprint.completedWeeks);

		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(
		         SQLTableEntry.TABLE_NAME,
		         null,
		         values);
	}
	
	public void readSQLTable()
	{
		sprintsArray = new ArrayList<Sprint>();
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    SQLTableEntry.COLUMN_NAME_SPRINT_ID,
		    SQLTableEntry.COLUMN_NAME_EST_HOURS,
		    SQLTableEntry.COLUMN_NAME_EST_WEEKS,
		    SQLTableEntry.COLUMN_NAME_COMP_HOURS,
		    SQLTableEntry.COLUMN_NAME_COMP_WEEKS
		    };

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    SQLTableEntry.COLUMN_NAME_UPDATED + " DESC";

		Cursor c = db.query(
		    SQLTableEntry.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		for (int i = 0; i<c.getCount(); i++)
		{
			if(i== 0)
			{
			c.moveToFirst();
			Sprint item = new Sprint(null, 0f, 0f);
			item.id = c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_SPRINT_ID));
			item.estHours = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_EST_HOURS)));
			item.estWeeks = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_EST_WEEKS)));
			item.completedHours = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_COMP_HOURS)));
			item.completedWeeks = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_COMP_WEEKS)));
			sprintsArray.add(item);
			}
			else
			{
				if (!c.isAfterLast())
				{
					c.move(1);
					Sprint item = new Sprint(null, 0f, 0f);
					item.id = c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_SPRINT_ID));
					item.estHours = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_EST_HOURS)));
					item.estWeeks = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_EST_WEEKS)));
					item.completedHours = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_COMP_HOURS)));
					item.completedWeeks = Float.parseFloat(c.getString(c.getColumnIndexOrThrow(SQLTableEntry.COLUMN_NAME_COMP_WEEKS)));
					sprintsArray.add(item);
				}
			}
		}
	}
	
	public void deleteSQLEntry(Sprint sprint)
	{
		try
		{
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		db.delete(SQLTableEntry.TABLE_NAME, SQLTableEntry.COLUMN_NAME_SPRINT_ID + "=?", new String[]{sprint.id});
		}
		catch (Exception ex)
		{
			
		}
	}
}

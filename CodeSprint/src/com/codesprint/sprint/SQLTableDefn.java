package com.codesprint.sprint;

import android.net.Uri;
import android.provider.BaseColumns;

public final class SQLTableDefn {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public SQLTableDefn() {}

    /* Inner class that defines the table contents */
    public static abstract class SQLTableEntry implements BaseColumns {
        public static final String TABLE_NAME = "SprintsSQL";
        public static final String COLUMN_NAME_SPRINT_ID = "SprintID";
        public static final String COLUMN_NAME_EST_HOURS = "SprintEstHours";
        public static final String COLUMN_NAME_EST_WEEKS = "SprintEstWeeks";
        public static final String COLUMN_NAME_COMP_HOURS = "SprintCompHours";
        public static final String COLUMN_NAME_COMP_WEEKS = "SprintCompWeeks";
        // All URIs share these parts
        public static final String AUTHORITY = "com.example.codesprint.sprint";
        public static final String SCHEME = "content://";

        // URIs
        // Used for all persons
        public static final String SPRINTS = SCHEME + AUTHORITY + "/SprintsSQL";
        public static final Uri URI_SPRINTS = Uri.parse(SPRINTS);
        // Used for a single person, just add the id to the end
        public static final String SPRINT_BASE = SPRINTS + "/";
        public static final String[] FIELDS = { COLUMN_NAME_SPRINT_ID, COLUMN_NAME_EST_HOURS, COLUMN_NAME_EST_WEEKS,
        	COLUMN_NAME_COMP_HOURS, COLUMN_NAME_COMP_WEEKS};
		public static final String COLUMN_NAME_UPDATED = null;
    }
}

package com.codesprint.sprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SprintContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<SprintItem> ITEMS = new ArrayList<SprintItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, SprintItem> ITEM_MAP = new HashMap<String, SprintItem>();

	static {
		// Add 3 sample items.
		addItem(new SprintItem("Complete Login Screen", 4f, 35f));
		addItem(new SprintItem("Complete Activity Page", 5f, 50f));
	}

	private static void addItem(SprintItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class SprintItem {
		public String id;
		public float estWeeks;
		public float estHours;
		public float completedWeeks;
		public float completedHours;

		public SprintItem(String id, float estWeeks, float estHours) {
			this.id = id;
			this.estWeeks = estWeeks;
			this.estHours = estHours;
			this.completedHours = 0f;
			this.completedWeeks = 0f;
		}

		@Override
		public String toString() {
			return String.format(id.toString());
		}
	}
}

package com.codesprint.sprint;

import android.os.Parcel;
import android.os.Parcelable;

public class Sprint implements Parcelable{
	public String id;
	public float estWeeks;
	public float estHours;
	public float completedWeeks;
	public float completedHours;

	public Sprint(String id, float estWeeks, float estHours) {
		this.id = id;
		this.estWeeks = estWeeks;
		this.estHours = estHours;
		this.completedHours = 0f;
		this.completedWeeks = 0f;
	}
	
	public String toString() {
		return String.format(id.toString());
	}
	
    public int describeContents() {
        return 0;
      }
      public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
    	out.writeFloat(estHours);
    	out.writeFloat(estWeeks);
        out.writeFloat(completedHours);
        out.writeFloat(completedWeeks);
      }
      public static final Parcelable.Creator<Sprint> CREATOR
          = new Parcelable.Creator<Sprint>() {
        public Sprint createFromParcel(Parcel in) {
          return new Sprint(in);
        }
        public Sprint[] newArray(int size) {
          return new Sprint[size];
        }
      };
      private Sprint(Parcel in) {
        id = in.readString();
        estHours = in.readFloat();
        estWeeks = in.readFloat();
        completedHours = in.readFloat();
        completedWeeks = in.readFloat();
      }
}

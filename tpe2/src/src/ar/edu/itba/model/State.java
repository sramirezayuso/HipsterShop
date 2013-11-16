package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class State extends ModelObject{
	private String stateId;
	private String name;
	
	public State(String name){
		this.name = name;
	}
	public String getStateId() {
		return stateId;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
	}
	
	private State(Parcel in) {
	}

	public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {
		public State createFromParcel(Parcel in) {
			return new State(in);
		}

		public State[] newArray(int size) {
			return new State[size];
		}
	 };
	
}
/*
{
"stateId": "C",
"name": "Ciudad Autonoma de Buenos Aires"
}
*/
package ar.edu.itba.model;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;


public class GetAllStates extends MethodObject implements Parcelable{
	private Meta meta;
	private List<State> states;//el nombre de la clase no importa, pero el de las variables debe coincidir con el json
	
	public Meta getMeta() {
		return meta;
	}
	
	public List<State> getStates() {
		return states;
	}
	
	public String toString() {
		return meta.toString() + states.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.getStates());
	}
	
	private GetAllStates(Parcel in) {
		in.readTypedList(this.states, State.CREATOR);
	}

	public static final Parcelable.Creator<GetAllStates> CREATOR = new Parcelable.Creator<GetAllStates>() {
		public GetAllStates createFromParcel(Parcel in) {
			return new GetAllStates(in);
		}

		public GetAllStates[] newArray(int size) {
			return new GetAllStates[size];
		}
	 };	
}

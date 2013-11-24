package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetAllSubcategories extends MethodObject implements Parcelable {
	private Meta meta;
	private List<Subcategory> subcategories;
	
	public Meta getMeta() {
		return meta;
	}
	
	public String[] getNames() {
		String[] names = new String[subcategories.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = subcategories.get(i).getName();
		}
		return names;
	}

	public List<Subcategory> getSubcategories() {
		return subcategories;
	}

	public String toString() {
		return meta.toString() + subcategories.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(subcategories);
	}

	private GetAllSubcategories(Parcel in) {
		in.readTypedList(subcategories, Subcategory.CREATOR);
	}
	
	public static final Parcelable.Creator<GetAllSubcategories> CREATOR = new Parcelable.Creator<GetAllSubcategories>() {
		public GetAllSubcategories createFromParcel(Parcel in) {
			return new GetAllSubcategories(in);
		}

		public GetAllSubcategories[] newArray(int size) {
			return new GetAllSubcategories[size];
		}
	};
}

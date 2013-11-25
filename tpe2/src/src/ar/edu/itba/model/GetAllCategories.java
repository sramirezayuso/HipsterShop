package ar.edu.itba.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class GetAllCategories extends MethodObject implements Parcelable {
	private Meta meta;
	private List<Category> categories;
	
	public Meta getMeta() {
		return meta;
	}
	
	public String[] getNames() {
		String[] names = new String[categories.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = categories.get(i).getName();
		}
		return names;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public String toString() {
		return meta.toString() + categories.toString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(categories);
	}

	private GetAllCategories(Parcel in) {
		in.readTypedList(categories, Category.CREATOR);
	}
	
	public static final Parcelable.Creator<GetAllCategories> CREATOR = new Parcelable.Creator<GetAllCategories>() {
		public GetAllCategories createFromParcel(Parcel in) {
			return new GetAllCategories(in);
		}

		public GetAllCategories[] newArray(int size) {
			return new GetAllCategories[size];
		}
	};
}


package ar.edu.itba.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product extends ModelObject {

	private Integer id;
	private String name;
	private Float price;
	private String[] imageUrl;
	private Category category;
	private Category subcategory;
	private Attribute[] attributes;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getPrice() {
		return price;
	}

	public String[] getImageUrls() {
		return imageUrl;
	}
	
	public String getBrand() {
		Attribute brand = null;
		for(Attribute attr : attributes){
			if (attr.isBrand()){
				brand = attr;
			}
		}
		return brand.getValues()[0];
	}
	
	public String[] getColors(){
		Attribute color = null;
		for(Attribute attr : attributes){
			if (attr.isColor()){
				color = attr;
			}
		}
		return color.getValues();
	}
	
	public String[] getSizes(){
		for(Attribute attr : attributes){
			if (attr.isSize()){
				return attr.getValues();
			}
		}
		
		return null;
	}

	public Category getCategory() {
		return category;
	}

	public Category getSubcategory() {
		return subcategory;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}


	@Override
	public String toString() {
		return id + " - " + name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
	}

	public Product(String name, Float price, Attribute[] attributes) {
		super();
		this.name = name;
		this.price = price;
		this.attributes = attributes;
		imageUrl = new String[1];
		imageUrl[0] = "http://eiffel.itba.edu.ar/hci/service3/images/camver1.jpg";
	}

	private Product(Parcel in) {
	}

	public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}
	};
}

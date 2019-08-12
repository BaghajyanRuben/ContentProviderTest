package com.photo.contactsgettest;

import android.util.Log;

public class Contact {

	private String id;
	private String name;
	private String phone;

	public Contact() {
	}

	public Contact(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	public Contact(String name, String phone, String index) {
		this.name = name;
		this.phone = phone;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void print(){
//		Log.i("ContactTest","id : " + id);
		Log.i("ContactTest","Name : " + name);
		Log.w("ContactTest","Phone : " + phone);
		Log.d("ContactTest"," --------------------------------- ");
	}
}

package com.photo.contactsgettest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 123);
			}
		} else {
			showContacts();
		}

	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == 123 && permissions.length > 1 && grantResults.length > 1){
			showContacts();
		}
	}

	private void showContacts(){
		List<Contact> list = readContacts();

		for (Contact contact : list) {
			contact.print();
		}
	}

	private List<Contact> readContacts(){
		List<Contact> contacts = new ArrayList<>();

		Uri contactURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

		final String ID = ContactsContract.Contacts._ID;
		final String displayName = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

		final String[] projection = new String[]{ID, ContactsContract.CommonDataKinds.Phone.NUMBER, displayName};

		String orderDisplayName = ContactsContract.Contacts.DISPLAY_NAME + " ASC ";

		String selection = ContactsContract.CommonDataKinds.Phone.TYPE
				+ " = "
				+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;


		Cursor contactCursor = getContentResolver().query(contactURI,
				projection,
				selection,
				null,
				orderDisplayName);


		if (contactCursor == null){
			return contacts;
		}

		final int displayNameIndex = contactCursor.getColumnIndex(displayName);
//		final int idIndex = contactCursor.getColumnIndex(ID);
		final int phoneNumberIndex = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

		contactCursor.moveToFirst();

		while (contactCursor.moveToNext()){
			String name = contactCursor.getString(displayNameIndex);
			String phone = contactCursor.getString(phoneNumberIndex);
//			String id = contactCursor.getString(idIndex);

			contacts.add(new Contact(name, phone));

		}


		contactCursor.close();

		return contacts;
	}
}

package com.bignerdranch.android.criminalintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bignerdranch.android.criminalintent.yelpapi.APISearch;

public class RestoFragment extends Fragment {
    public static final String EXTRA_RESTO_ID = "favrestos.RESTO_ID";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_NOTE = 3;
    public static final String EXTRA_USER_ID = "fruin.brian.extra_user_id";


    protected LocationManager locationManager;
    private double mLongCurrent;
    private double mLatCurrent;


    Resto mResto;
    EditText mTitleField;
    EditText mCityField;
    EditText mPhoneField;
    EditText mLatLongField;
    EditText mAddressField;
    EditText mYelpField;
    Button mMapButton;
    Button mNavButton;
    Button mDialButton;
    Button mYelpButton;
    Button mNotesButton;
    Button mDateButton;
    CheckBox mSolvedCheckBox;
    ImageButton mPhotoButton;
    ImageView mPhotoView;
    Button mSuspectButton;
    Callbacks mCallbacks;

    public interface Callbacks {
        void onRestoUpdated(Resto resto);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static RestoFragment newInstance(long restoId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESTO_ID, restoId);

        RestoFragment fragment = new RestoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        
        Long restoId = (Long)getArguments().getSerializable(EXTRA_RESTO_ID);
        mResto = RestoDatabase.get(getActivity()).getCrime(restoId);


        setHasOptionsMenu(true);
    }

    public void updateDate() {
        //mDateButton.setText(mResto.getDate().toString());
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resto, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        mTitleField = (EditText)v.findViewById(R.id.resto_title);
        mTitleField.setText(mResto.getName());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setName(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mCityField = (EditText)v.findViewById(R.id.resto_city);
        mCityField.setText(mResto.getCity());
        mCityField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setCity(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mPhoneField = (EditText)v.findViewById(R.id.resto_phone);
        mPhoneField.setText(mResto.getPhone());
        mPhoneField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setPhone(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mLatLongField = (EditText)v.findViewById(R.id.resto_latlong);
        mLatLongField.setText(mResto.getLatLong());
        mLatLongField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setLatLong(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mAddressField = (EditText)v.findViewById(R.id.resto_address);
        mAddressField.setText(mResto.getAddress());
        mAddressField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setAddress(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mYelpField = (EditText)v.findViewById(R.id.resto_yelp);
        mYelpField.setText(mResto.getYelp());
        mYelpField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setYelp(c.toString());
                mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });

        mMapButton = (Button) v.findViewById(R.id.map_button);


        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateInput()){
                    return;
                }
                if (!validateMapInput()){
                    return;
                }

                double lat = 41.789173;
                double lon = -87.600126;

                String latlong = mLatLongField.getText().toString();

                int comma = latlong.indexOf(',');

                lat = Double.parseDouble(latlong.substring(0, comma));

                lon = Double.parseDouble(latlong.substring(comma+1));


                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);
            }
        });


        final LocationManager locationManager =
                (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        mNavButton = (Button) v.findViewById(R.id.navigate_button);

        mNavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateInput()){
                    return;
                }
                if (!validateMapInput()){
                    return;
                }

                //String locationProvider = LocationManager.NETWORK_PROVIDER;
                String locationProvider = LocationManager.GPS_PROVIDER;
                // Or use LocationManager.GPS_PROVIDER

                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

                mLatCurrent = lastKnownLocation.getLatitude();
                mLongCurrent = lastKnownLocation.getLongitude();

                double lat = 41.789173;
                double lon = -87.600126;

                String latlong = mLatLongField.getText().toString();

                int comma = latlong.indexOf(',');

                lat = Double.parseDouble(latlong.substring(0, comma));

                lon = Double.parseDouble(latlong.substring(comma+1));

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                        "saddr=" +  mLatCurrent + "," + mLongCurrent +
                        "&daddr=" + lat + "," + lon));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        mDialButton = (Button)v.findViewById(R.id.dial_button);
        mDialButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!validatePhoneInput()){
                    return;
                }
                if (!validateInput()){
                    return;
                }

                String number = mPhoneField.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(callIntent);

            }
        });

        mYelpButton = (Button)v.findViewById(R.id.yelp_button);
        mYelpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!validateInput()){
                    return;
                }

                String name = mTitleField.getText().toString();
                String location = mCityField.getText().toString();

                new APISearchTask().execute(name, location);

            }
        });
        

        
        mPhotoButton = (ImageButton)v.findViewById(R.id.resto_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!validateInput()){
                    return;
                }
                // launch the camera activity
                Intent i = new Intent(getActivity(), RestoCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });
        
        // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView)v.findViewById(R.id.resto_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Photo p = mResto.getPhoto();
                //Photo p = mResto.getImage();
//                if (p == null)
//                    return;
//
//                FragmentManager fm = getActivity()
//                    .getSupportFragmentManager();
//                String path = getActivity()
//                    .getFileStreamPath(p.getFilename()).getAbsolutePath();
//                ImageFragment.createInstance(path)
//                    .show(fm, DIALOG_IMAGE);
            }
        });



        mNotesButton = (Button)v.findViewById(R.id.notes_button);
        mNotesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!validateInput()){
                    return;
                }

                Intent i = new Intent(getActivity(), NotesActivity.class);
                long userId = mResto.getId();
                i.putExtra(RestoFragment.EXTRA_USER_ID, userId);
                //startActivity(i);
                startActivityForResult(i,REQUEST_NOTE);

            }
        });






        
        return v; 
    }


    private boolean validateInput(){
        if(mTitleField.getText().toString().matches("")){

            Context context = getActivity().getApplicationContext();
                CharSequence text = "Please Enter A Resto Name";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();
            return false;

        }

        if(mCityField.getText().toString().matches("")){

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please Enter A City";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
            return false;

        }
        return true;
    }

    private boolean validatePhoneInput(){
        if(mPhoneField.getText().toString().matches("")){

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please Enter A Phone Number";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
            return false;

        }
        return true;
    }

    private boolean validateMapInput(){
        if(mLatLongField.getText().toString().matches("")){

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please Enter a Lat/Long";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
            return false;
        }
        return true;
    }


    
    private void showPhoto() {
        // (re)set the image button's image based on our photo
//        Photo p = mResto.getPhoto();
//        BitmapDrawable b = null;
//        if (p != null) {
//            String path = getActivity()
//                .getFileStreamPath(p.getFilename()).getAbsolutePath();
//            b = PictureUtils.getScaledDrawable(getActivity(), path);

        BitmapDrawable bitmap = new BitmapDrawable(getResources(), mResto.getImage());

        if (bitmap == null){

            Log.d("showPhoto", "null in shotPhoto()");

        }

        mPhotoView.setImageDrawable(bitmap);



    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_NOTE) {
            if (data == null){
                return;
            }
            mResto.setNote(data.getStringExtra(NotesActivity.NOTES_FOR_RESTO));
        }



        else if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the resto
            String filename = data
                .getStringExtra(RestoCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                //Photo p = new Photo(filename);

                String path = getActivity().getFileStreamPath(filename).getAbsolutePath();
                BitmapDrawable bitmap = PictureUtils.getScaledDrawable(getActivity(), path);

                mResto.setImage(bitmap.getBitmap());

                //mResto.setPhoto(p);
                mCallbacks.onRestoUpdated(mResto);
                //showPhoto();
                mPhotoView.setImageDrawable(bitmap);
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
            Cursor c = getActivity().getContentResolver()
                .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return; 
            }

            c.moveToFirst();
            String suspect = c.getString(0);
            //mResto.setSuspect(suspect);
            mCallbacks.onRestoUpdated(mResto);
            mSuspectButton.setText(suspect);
            c.close();
        }
    }
    



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_resto, menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        RestoDatabase.get(getActivity()).saveRestos(mResto);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;

            case R.id.menu_item_yelp_detail:

                if (!validateInput()){
                    return true;
                }

                String name = mTitleField.getText().toString();
                String location = mCityField.getText().toString();

                new APISearchTask().execute(name, location);

                return true;

            case R.id.menu_item_yelp_page_detail:

                if (!validateInput()){
                    return true;
                }

                String url = mResto.getYelp();
                Intent yelpIntent = new Intent(Intent.ACTION_VIEW);
                yelpIntent.setData(Uri.parse(url));
                startActivity(yelpIntent);

                return true;

            case R.id.menu_item_notes_detail:

                if (!validateInput()){
                    return true;
                }

                Intent i = new Intent(getActivity(), NotesActivity.class);
                long userId = mResto.getId();
                i.putExtra(RestoFragment.EXTRA_USER_ID, userId);
                //startActivity(i);
                startActivityForResult(i,REQUEST_NOTE);

                return true;

            case R.id.menu_item_photo_detail:

                if (!validateInput()){
                    return true;
                }

                Intent photoIntent = new Intent(getActivity(), RestoCameraActivity.class);
                startActivityForResult(photoIntent, REQUEST_PHOTO);

                return true;

            case R.id.menu_item_map_detail:

                if (!validateInput()){
                    return true;
                }
                if (!validateMapInput()){
                    return true;
                }

                double lat = 41.789173;
                double lon = -87.600126;

                String latlong = mLatLongField.getText().toString();

                int comma = latlong.indexOf(',');

                lat = Double.parseDouble(latlong.substring(0, comma));

                lon = Double.parseDouble(latlong.substring(comma+1));


                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(mapIntent);

                return true;

            case R.id.menu_item_nav_detail:

                if (!validateInput()){
                    return true;
                }
                if (!validateMapInput()){
                    return true;
                }

                final LocationManager locationManager =
                        (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                //String locationProvider = LocationManager.NETWORK_PROVIDER;
                String locationProvider = LocationManager.GPS_PROVIDER;
                // Or use LocationManager.GPS_PROVIDER

                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

                mLatCurrent = lastKnownLocation.getLatitude();
                mLongCurrent = lastKnownLocation.getLongitude();

                double navLat = 41.789173;
                double navLon = -87.600126;

                String navLatlong = mLatLongField.getText().toString();

                int navComma = navLatlong.indexOf(',');

                lat = Double.parseDouble(navLatlong.substring(0, navComma));

                lon = Double.parseDouble(navLatlong.substring(navComma+1));

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                        "saddr=" +  mLatCurrent + "," + mLongCurrent +
                        "&daddr=" + lat + "," + lon));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

                return true;

            case R.id.menu_item_exit_detail:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } 
    }


    private class APISearchTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String name = strings[0];
            String location = strings[1];
            ArrayList<String> result = new ArrayList<String>();

            APISearch yelpApi = new APISearch();

            try {
                yelpApi.search(name, location);
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.add(yelpApi.getResName());
            result.add(yelpApi.getResAddress());
            result.add(yelpApi.getResPhone());
            result.add(yelpApi.getResLatLong());
            result.add(yelpApi.getResYelp());

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            mTitleField.setText(strings.get(0));
            mAddressField.setText(strings.get(1));
            mPhoneField.setText(strings.get(2));
            mLatLongField.setText(strings.get(3));
            mYelpField.setText(strings.get(4));


            String strAddress = mAddressField.getText().toString() + mCityField.getText().toString();

//            if (strAddress.equals("")){
//                return;
//            }

            Geocoder coder = new Geocoder(getActivity());
            List<Address> address;
            String lat;
            String lon;

            try {
                address = coder.getFromLocationName(strAddress,5);
                if(address.size() == 0){
                    return;
                }
                Address location = address.get(0);
                lat = String.valueOf(location.getLatitude());
                lon = String.valueOf(location.getLongitude());
                mLatLongField.setText(lat+","+lon);

            } catch (IOException e) {
                e.printStackTrace();
            }




        }

    }

}



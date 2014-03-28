package com.bignerdranch.android.proFavRestos;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RestoListFragment extends ListFragment {
    private ArrayList<Resto> mRestos;
    private boolean mSubtitleVisible = false;
    private Callbacks mCallbacks;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_NOTE = 3;


    public interface Callbacks {
        void onRestoSelected(Resto resto);
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

    public void updateUI() {
        ((RestoAdapter)getListAdapter()).notifyDataSetChanged();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.restos_title);
        //mRestos = RestoBank.get(getActivity()).getRestos();
        mRestos = RestoDatabase.get(getActivity()).getRestos();
        RestoAdapter adapter = new RestoAdapter(mRestos);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        

        
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        


        return v;
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the Resto from the adapter
        Resto c = ((RestoAdapter)getListAdapter()).getItem(position);
        mCallbacks.onRestoSelected(c);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((RestoAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_resto_list, menu);
//        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
//        if (mSubtitleVisible && showSubtitle != null) {
//            showSubtitle.setTitle("HEYHEYHEY");
//        }
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_resto:
                Resto resto = new Resto();
                //RestoBank.get(getActivity()).addResto(resto);
                //resto.setName("test");
                RestoDatabase.get(getActivity()).addRestaurant(resto);
                //RestoDatabase.get(getActivity()).insertResto();
                ((RestoAdapter)getListAdapter()).notifyDataSetChanged();
                mCallbacks.onRestoSelected(resto);
                return true;

            case R.id.menu_item_exit:

                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        } 
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.resto_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        RestoAdapter adapter = (RestoAdapter)getListAdapter();
        Resto resto = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_resto:
                RestoDatabase.get(getActivity()).deleteResto(resto);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_item_navigate_resto:

                if(!validateInput(resto)){
                    return true;
                }
                if(!validateMapInput(resto)){
                    return true;
                }

                final LocationManager locationManager =
                        (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

                String locationProvider = LocationManager.GPS_PROVIDER;
                // Or use LocationManager.GPS_PROVIDER

                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

                double latCurrent = lastKnownLocation.getLatitude();
                double longCurrent = lastKnownLocation.getLongitude();

                double lat = 41.789173;
                double lon = -87.600126;

                String latlong = resto.getLatLong();

                int comma = latlong.indexOf(',');

                lat = Double.parseDouble(latlong.substring(0, comma));

                lon = Double.parseDouble(latlong.substring(comma+1));

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
                        "saddr=" + latCurrent + "," + longCurrent +
                        "&daddr=" + lat + "," + lon));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

                return true;

            case R.id.menu_item_map_resto:

                if(!validateInput(resto)){
                    return true;
                }
                if(!validateMapInput(resto)){
                    return true;
                }

                double latMap = 41.789173;
                double lonMap = -87.600126;

                String latlongMap = resto.getLatLong();

                int commaMap = latlongMap.indexOf(',');

                lat = Double.parseDouble(latlongMap.substring(0, commaMap));

                lon = Double.parseDouble(latlongMap.substring(commaMap+1));


                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon);
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(i);


                return true;

            case R.id.menu_item_dial_resto:

                if(!validateInput(resto)){
                    return true;
                }
                if(!validatePhoneInput(resto)){
                    return true;
                }

                String number = resto.getPhone();

                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number));
                startActivity(callIntent);

                return true;

            case R.id.menu_item_detail_resto:

                resto = ((RestoAdapter)getListAdapter()).getItem(position);
                mCallbacks.onRestoSelected(resto);

                return true;

            case R.id.menu_item_yelp_resto:

                if(!validateInput(resto)){
                    return true;
                }

                String url = resto.getYelp();
                Intent yelpIntent = new Intent(Intent.ACTION_VIEW);
                yelpIntent.setData(Uri.parse(url));
                startActivity(yelpIntent);

                return true;

            case R.id.menu_item_photo_resto:

                if(!validateInput(resto)){
                    return true;
                }

                Intent photoIntent = new Intent(getActivity(), RestoCameraActivity.class);
                startActivityForResult(photoIntent, REQUEST_PHOTO);



                return true;


            case R.id.menu_item_notes_resto:

                if(!validateInput(resto)){
                    return true;
                }

                Intent noteIntent = new Intent(getActivity(), NotesActivity.class);
                long userId = resto.getId();
                noteIntent.putExtra(RestoFragment.EXTRA_USER_ID, userId);
                //startActivity(i);
                startActivityForResult(noteIntent,REQUEST_NOTE);

            return true;


        }
        return super.onContextItemSelected(item);
    }



    private boolean validateInput(Resto resto){
        if(resto.getName() == null){

            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please Enter A Resto Name";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
            return false;

        }

        if(resto.getCity() == null){

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

    private boolean validatePhoneInput(Resto resto){
        if(resto.getPhone() == null){

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

    private boolean validateMapInput(Resto resto){
        if(resto.getLatLong() == null){

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




    private class RestoAdapter extends ArrayAdapter<Resto> {
        public RestoAdapter(ArrayList<Resto> restos) {
            super(getActivity(), android.R.layout.simple_list_item_1, restos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_resto, null);
            }

            // configure the view for this Resto
            Resto c = getItem(position);

            TextView titleTextView =
                (TextView)convertView.findViewById(R.id.resto_list_item_titleTextView);
            titleTextView.setText(c.getName());

            TextView cityTextView =
                    (TextView)convertView.findViewById(R.id.resto_list_item_city);
            cityTextView.setText(c.getCity());

            ImageView restoImageView =
                    (ImageView)convertView.findViewById(R.id.resto_list_item_imageView);
            restoImageView.setImageBitmap(c.getImage());

//            TextView dateTextView =
//                (TextView)convertView.findViewById(R.id.resto_list_item_dateTextView);
//            dateTextView.setText(c.getDate().toString());
//            CheckBox solvedCheckBox =
//                (CheckBox)convertView.findViewById(R.id.resto_list_item_solvedCheckBox);
//            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}


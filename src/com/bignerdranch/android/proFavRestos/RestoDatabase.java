package com.bignerdranch.android.proFavRestos;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

public class RestoDatabase {
    private static final String TAG = "RestoDatabase";
    private static final String FILENAME = "crimes.json";
    private static final String PREFS_FILE = "restos";
    private static final String PREFS_CURRENT_RESTO_ID = "RestoDatabase.currentRestoId";

    private ArrayList<Resto> mRestos;
    //private FavRestoJSONSerializer mSerializer;

    private static RestoDatabase mRestoDatabase;
    private Context mAppContext;
    private RestoDatabaseHelper mHelper;
    private SharedPreferences mPrefs;
    private long mCurrentRestoId;

    private RestoDatabase(Context appContext) {
        mAppContext = appContext;
        //mSerializer = new FavRestoJSONSerializer(mAppContext, FILENAME);

        mHelper = new RestoDatabaseHelper(appContext);
        mPrefs = mAppContext.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mCurrentRestoId = mPrefs.getLong(PREFS_CURRENT_RESTO_ID, -1);

        try {
            mRestos = mHelper.loadRestos();
        } catch (Exception e) {
            mRestos = new ArrayList<Resto>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static RestoDatabase get(Context c) {
        if (mRestoDatabase == null) {
            mRestoDatabase = new RestoDatabase(c.getApplicationContext());
        }
        return mRestoDatabase;
    }

    public Resto getResto(Long id) {
        for (Resto c : mRestos) {
            if (c.getId() == id)
                return c;
        }
        return null;
    }

    public long addRestaurant(Resto c) {

        long result = mHelper.insertRestaurant(c);

        c.setId(result);

        mRestos.add(c);
        //saveRestos();

        return result;
    }

    public Resto insertResto(){
        Resto resto = new Resto();
        resto.setId(mHelper.insertRestaurant(resto));
        return resto;
    }

    public ArrayList<Resto> getRestos() {
        return mRestos;
    }

    public void deleteResto(Resto c) {

        mRestos.remove(c);
        String id = String.valueOf(c.getId());
        mHelper.deleteResto(id);
        //saveRestos();
    }

    public boolean saveRestos(Resto resto) {
//        try {
//            mSerializer.saveRestos(mRestos);
//            Log.d(TAG, "crimes saved to file");
//            return true;
//        } catch (Exception e) {
//            Log.e(TAG, "Error saving crimes: " + e);
//            return false;
//        }

        try {
            mHelper.updateRestaurant(resto);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving crimes");
            return false;
        }




    }
}
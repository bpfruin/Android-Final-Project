package com.bignerdranch.android.criminalintent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
//model for crimes
public class Resto {

//    private static final String JSON_ID = "id";
//    private static final String JSON_TITLE = "title";
//    private static final String JSON_DATE = "date";
//    private static final String JSON_SOLVED = "solved";
//    private static final String JSON_PHOTO = "photo";
//    private static final String JSON_SUSPECT = "suspect";
    
    private long mId;
    private String mName;
    //private Date mDate;
    //private boolean mSolved;
    private Photo mPhoto;
    //private String mSuspect;
    private byte[] mImgByte;
    //private long mIdDB;
    private String mCity;
    private String mImagePath;



    private Bitmap mImage;



    private String mPhone;
    private String mLatLong;
    private String mAddress;
    private String mYelp;
    private String mNote;
    
    public Resto() {
        //mId = UUID.randomUUID();
        mId = -1;
        //mDate = new Date();
    }

    public Resto(String id, String name, String city, String phone, String latlong, String address, String yelp, String note, byte[] img){

        mId = Long.valueOf(id).longValue();
        mName = name;
        mCity = city;
        mPhone = phone;
        mLatLong = latlong;
        mAddress = address;
        mYelp = yelp;
        mNote = note;

        if (img != null){
        Bitmap b = BitmapFactory.decodeByteArray(img, 0, img.length);
        mImage = Bitmap.createScaledBitmap(b, 120, 120, false);
        }

        //mImage = BitmapFactory.decodeByteArray(img, 0, img.length);

    }

//    public Resto(JSONObject json) throws JSONException {
//        //mId = UUID.fromString(json.getString(JSON_ID));
//        mName = json.getString(JSON_TITLE);
//        mSolved = json.getBoolean(JSON_SOLVED);
//        mDate = new Date(json.getLong(JSON_DATE));
//        if (json.has(JSON_PHOTO))
//            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
//        if (json.has(JSON_SUSPECT))
//            mSuspect = json.getString(JSON_SUSPECT);
//    }

//    public JSONObject toJSON() throws JSONException {
//        JSONObject json = new JSONObject();
//        //json.put(JSON_ID, mId.toString());
//        json.put(JSON_TITLE, mName);
//        json.put(JSON_SOLVED, mSolved);
//        json.put(JSON_DATE, mDate.getTime());
//        if (mPhoto != null)
//            json.put(JSON_PHOTO, mPhoto.toJSON());
//        json.put(JSON_SUSPECT, mSuspect);
//        return json;
//    }

    @Override
    public String toString() {
        return mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

//    public UUID getId() {
//        return mId;
//    }

//    public boolean isSolved() {
//        return mSolved;
//    }
//
//    public void setSolved(boolean solved) {
//        mSolved = solved;
//    }
//
//    public Date getDate() {
//        return mDate;
//    }
//
//    public void setDate(Date date) {
//        mDate = date;
//    }
    
//    public Photo getPhoto() {
//        //return BitmapFactory.decodeByteArray(mImgByte, 0, mImgByte.length);
//        return mPhoto;
//    }
//
//    public void setPhoto(Photo p) {
//        mPhoto = p;
//    }

//    public String getSuspect() {
//        return mSuspect;
//    }
//
//    public void setSuspect(String suspect) {
//        mSuspect = suspect;
//    }

    public byte[] getImgByte() {

        if(mImage != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mImage.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            return outputStream.toByteArray();
        } else {
            return null;
        }

    }

    public void setImgByte(byte[] imgByte) {
        mImgByte = imgByte;
    }

    public long getId() {
        return mId;
    }

    public void setId(long idDB) {
        mId = idDB;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getLatLong() {
        return mLatLong;
    }

    public void setLatLong(String latLong) {
        mLatLong = latLong;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getYelp() {
        return mYelp;
    }

    public void setYelp(String yelp) {
        mYelp = yelp;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

}

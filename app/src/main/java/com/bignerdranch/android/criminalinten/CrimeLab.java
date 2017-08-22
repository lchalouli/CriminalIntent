package com.bignerdranch.android.criminalinten;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalinten.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalinten.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalinten.database.CrimeDbSchema;
import com.bignerdranch.android.criminalinten.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lchalouli on 02/08/2017.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrime(CrimeTable.Cols.UUID + " = ?", new String[]{id.toString()});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }

    public void addCrime(Crime newCrime) {
        ContentValues contentValues = getContentValues(newCrime);
        long insert = mDatabase.insert(CrimeTable.NAME, null, contentValues);
    }

    public void updateCrime(Crime crimeToUpdate) {
        ContentValues contentValues = getContentValues(crimeToUpdate);
        String uuidString = crimeToUpdate.getId().toString();
        mDatabase.update(CrimeTable.NAME, contentValues, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public List<Crime> getCrimes() {
        ArrayList<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrime(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void delete(Crime crimeToDelete) {
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?", new String[]{crimeToDelete.getId().toString()});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        return values;
    }


    private CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTable.NAME, null, whereClause, whereArgs, null, null, null, null);
        return new CrimeCursorWrapper(cursor);
    }
}

package com.bignerdranch.android.criminalinten;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalinten.database.CrimeBaseHelper;

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
        return null;
    }

    public void addCrime(Crime newCrime) {
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    public void delete(Crime crime) {
    }
}

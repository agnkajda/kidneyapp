package com.example.agnieszka.kidneyapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Agnieszka on 29.09.2016.
 */

public class KidneyContract {

    // CZY TE DWIE ZMIENNE STATYCZNE MOGĄ SIĘ IDENTYCZNIE NAZYWAĆ W DWÓCH RÓŻNYCH KLASACH?
    public static final String CONTENT_AUTHORITY = "com.example.agnieszka.kidneyapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_JOURNAL= "journal";
    public static final String PATH_VALUES = "values";

    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    //tutaj tworzymy jedną tabelę

    public static final class JournalEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_JOURNAL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOURNAL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOURNAL;

        //nazwa tabeli
        public static final String TABLE_NAME = "journal";

        //ID robi się automatycznie
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_FOOD_NAME = "food_name";
        public static final String COLUMN_AMOUNT = "amount";

        public static Uri buildJournalUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
            }


        public static Uri buildJournalWithStartDate(long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
        }

        public static Uri buildJournalWithDate(long date) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(date)).build();
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
            }

    }

    //tutaj tworzymy drugą tabelę

    public static final class ValuesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VALUES).build();
        // tu będzie "content://com.example.agnieszka.kidneyapp/values

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VALUES;
        //vnd.android.cursor.dir/com.example.agnieszka.kidneyapp/values
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VALUES;

        public static final String TABLE_NAME = "values";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_KCAL = "kcal";
        public static final String COLUMN_CARBON = "carbon";
        public static final String COLUMN_FAT = "fat";
        public static final String COLUMN_PROTEIN = "protein";
        public static final String COLUMN_PHOSPHORUS= "phosphorus";
        public static final String COLUMN_SODIUM = "sodium";
        public static final String COLUMN_POTASSIUM= "potassium";
        public static final String COLUMN_FLUID = "fluid";
        public static final String COLUMN_IF_DIALYZED = "dialyzed";


        public static Uri buildValuesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
            }

        public static Uri buildValuesWithDate(long date) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(date)).build();
        }

        // get(1)? chyba tak?
        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static Uri buildValuesWithStartDate(long startDate) {
            long normalizedDate = normalizeDate(startDate);
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
        }

        public static long getStartDateFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }

        //ile ma byc tych content uriow? nie wiem!


    }
}

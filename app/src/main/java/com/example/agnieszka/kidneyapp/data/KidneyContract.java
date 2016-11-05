package com.example.agnieszka.kidneyapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Agnieszka on 29.09.2016.
 */

public class KidneyContract {

    // CZY TE DWIE ZMIENNE STATYCZNE MOGĄ SIĘ IDENTYCZNIE NAZYWAĆ W DWÓCH RÓŻNYCH KLASACH?
    public static final String CONTENT_AUTHORITY = "com.example.agnieszka.kidneyapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_JOURNAL= "journal";
    public static final String PATH_VALUES = "values";

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

        //ID najwyraźniej robi się automatycznie
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_FOOD_NAME = "food_name";
        public static final String COLUMN_AMOUNT = "amount";



        //tutaj jeszcze mają być jakieś funkcje :(

    }

    //tutaj tworzymy drugą tabelę

    public static final class ValuesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VALUES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VALUES;
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


    }
}

package com.chaacho.theguardiannewsapp.tools;

import android.content.Context;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.chaacho.theguardiannewsapp.R;

import static com.chaacho.theguardiannewsapp.tools.Tools.API_KEY;
import static com.chaacho.theguardiannewsapp.tools.Tools.API_KEY_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.FORMAT;
import static com.chaacho.theguardiannewsapp.tools.Tools.FORMAT_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.FROM_DATE_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.ORDER_BY_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.ORDER_DATE_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.PAGE_SIZE_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.QUERY_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.SECTION_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.SHOW_FIELDS;
import static com.chaacho.theguardiannewsapp.tools.Tools.SHOW_FIELDS_PARAM;
import static com.chaacho.theguardiannewsapp.tools.Tools.SHOW_TAGS;
import static com.chaacho.theguardiannewsapp.tools.Tools.SHOW_TAGS_PARAM;

public class SharedPreferences {


        public static Uri.Builder getPreferredUri(Context context) {
            android.content.SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);


            Uri baseUri = Uri.parse(Tools.NEWS_REQUEST_URL);

            Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendQueryParameter(QUERY_PARAM, "");
            //uriBuilder.appendQueryParameter(ORDER_BY_PARAM, orderBy);
            //uriBuilder.appendQueryParameter(PAGE_SIZE_PARAM, numOfItems);
            //uriBuilder.appendQueryParameter(ORDER_DATE_PARAM, orderDate);
            //uriBuilder.appendQueryParameter(FROM_DATE_PARAM, fromDate);
            uriBuilder.appendQueryParameter(SHOW_FIELDS_PARAM, SHOW_FIELDS);
            uriBuilder.appendQueryParameter(FORMAT_PARAM, FORMAT);
            uriBuilder.appendQueryParameter(SHOW_TAGS_PARAM, SHOW_TAGS);
            uriBuilder.appendQueryParameter(API_KEY_PARAM, API_KEY);
            return uriBuilder;
        }

        public static String getPreferredUrl(Context context, String section) {
            Uri.Builder uriBuilder = getPreferredUri(context);
            return uriBuilder.appendQueryParameter(SECTION_PARAM, section).toString();
        }
    }

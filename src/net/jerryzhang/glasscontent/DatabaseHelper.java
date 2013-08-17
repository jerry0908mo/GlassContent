/*
 * Copyright (C) 2009 Teleca Poland Sp. z o.o. <android@teleca.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jerryzhang.glasscontent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper  {

	private static final String TAG = "DatabaseHelper";

	private static final String DB_NAME = "gc_db";

	private static final String TABLE_GCRESULT = "table_gc_result";

	private Context mActivity;

	private static DatabaseHelper instance;
	
	public static DatabaseHelper getInstance(Context activity){
		if(null == instance){
			instance =  new DatabaseHelper(activity);
		}
		return instance;
	}
	
	private DatabaseHelper(Context activity) {
		this.mActivity = activity;
		create();
	}

	/**
	 * Initializes database and tables
	 */
	private void create() {
		SQLiteDatabase db = mActivity.openOrCreateDatabase(DB_NAME,
				Context.MODE_PRIVATE, null);

		db.execSQL("CREATE TABLE IF NOT EXISTS "
				+ TABLE_GCRESULT
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"precent_b_no_combustible DOUBLE, " +
				"weight_g_x_copple_befor DOUBLE, " +
				"weight_g_y_matter_before DOUBLE," +
				"weight_g_z_matter_and_copple_after DOUBLE," +
				"precent_glass_content_Result CHAR," +
				"create_time LONG" +
				" );"
				);

		db.close();
	}

	private SQLiteDatabase getDb() {
		return mActivity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,
				null);
	}
	
	public void addGcResult(GcResult gcr){
		if(null == gcr){
			return;
		}
		SQLiteDatabase db = getDb();

		ContentValues values = new ContentValues();

		values.putAll(GcResult.build(gcr));

		List<GcResult>  gcResultLists = loadGcResults();
		for(GcResult gc : gcResultLists){
			if(gc.equals(gcr)){
				return;
			}
		}
		
		db.insert(TABLE_GCRESULT, null, values);

		db.close();
	}
	
	
	
	public List<GcResult> loadGcResults(){
		SQLiteDatabase db = getDb();
		Cursor query = db.query(false, TABLE_GCRESULT, null, null, null, null, null, null,null);

		List<GcResult> clsList = new ArrayList<GcResult>();
		if (query != null) {
			query.moveToFirst();

			while (!query.isAfterLast()) {
				GcResult gcr = GcResult.build(query);
				clsList.add(gcr);
				query.moveToNext();
			}
		}

		db.close();

		return clsList;
	}
	
}

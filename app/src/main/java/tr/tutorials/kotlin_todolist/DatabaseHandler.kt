package tr.tutorials.kotlin_todolist

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(Context: Context) :
		SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION){

	companion object {
		private const val DATABASE_NAME = "todoDB"
		private const val DATABASE_VERSION = 1
	}


}
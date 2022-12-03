package tr.tutorials.kotlin_todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.ArrayList
import kotlin.math.log

class DatabaseHandler(Context: Context) :
		SQLiteOpenHelper(Context, DATABASE_NAME, null, DATABASE_VERSION){

	companion object {
		private const val DATABASE_NAME = "todoDB"
		private const val DATABASE_VERSION = 1
	}

	override fun onCreate(db: SQLiteDatabase?) {
		val UsersTableCreate = ("CREATE TABLE users ( " +
		 	"id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, passw text )")
		db?.execSQL(UsersTableCreate)
		val ContentTableCreate = ("CREATE TABLE contents ( " +
		 	"fromuser INTEGER, content TEXT NOT NULL, FOREIGN KEY(fromuser) REFERENCES users (id))")
		db?.execSQL(ContentTableCreate)
	}

	fun firstdata(){
		addUser(usersModelClass(1, "Not Login", "Elements"))
	}

	override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
		TODO("Not yet implemented")
	}

	fun getContentlist(id: Int = 1): ArrayList<contentModelClass> {
		val DB = this.writableDatabase
		val	ret: ArrayList<contentModelClass> = ArrayList<contentModelClass>()
		var cursor: Cursor? = null
		val viewlist = "SELECT * FROM contents"

		try {
			cursor = DB.rawQuery(viewlist,null)
		} catch (e: SQLiteException) {
			DB.execSQL(viewlist)
			DB.close()
			return ArrayList()
		}

		var fromuser: Int
		var text: String
		var td: contentModelClass

		if (cursor.moveToFirst()) {
			do {
				fromuser = cursor.getInt(cursor.getColumnIndexOrThrow("fromuser"))
				text = cursor.getString(cursor.getColumnIndexOrThrow("content"))

				td = contentModelClass(fromuser, text)
				ret.add(td)
			} while (cursor.moveToNext())
		}
		DB.close()
		return  ret
	}

	fun viewTODO(id: Int = 1): MutableList<String>{
		val ret: MutableList<String> = mutableListOf()
		val lst = getContentlist(id)

		for (x in lst){
			ret.add(x.content)
		}
		return ret
	}

	fun deleteTODO(con: contentModelClass): Int{
		val DB = this.writableDatabase
		val contnt = ContentValues()
		contnt.put("content", con.content)

		val success = DB.delete("contents", "content = \"${con.content}\"", null)
		DB.close()
		return success
	}

	fun addUser(usr: usersModelClass): Long {
		val DB = this.writableDatabase
		val contentValues = ContentValues()
		contentValues.put("name", usr.name)
		contentValues.put("passw",  usr.passwd)

		val success = DB.insert("users", null, contentValues)
		DB.close()
		return success
	}

	fun addTODO(todo: contentModelClass): Long {
		val DB = this.writableDatabase
		val contentValues = ContentValues()
		contentValues.put("fromuser", todo.fromuser)
		contentValues.put("content", todo.content)

		val success = DB.insert("contents", null, contentValues)
		DB.close()
		return success
	}
}
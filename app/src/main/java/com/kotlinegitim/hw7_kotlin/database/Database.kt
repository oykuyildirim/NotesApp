package com.kotlinegitim.hw7_kotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kotlinegitim.hw7_kotlin.models.Notes

class Database(context: Context) : SQLiteOpenHelper(context, DBName, null, Version  ) {

    companion object {
        private val DBName = "noteApp.db"
        private val Version = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val noteTable = "CREATE TABLE \"notes\" (\n" +
                "\t\"nid\"\tINTEGER,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"detail\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\t\"time\"\tTEXT,\n" +
                "\t\"important\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"nid\")\n" +
                ");"
        p0?.execSQL(noteTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val noteTableDrop = "DROP TABLE IF EXISTS notes"
        p0?.execSQL(noteTableDrop)
        onCreate(p0)
    }


    fun deleteNote(nid: Int) : Int {
        val db = this.writableDatabase
        val status = db.delete("notes", "nid = $nid", null )
        db.close()
        return status


    }



    fun addNote( title:String, detail: String, date: String,time : String, important: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("detail", detail)
        values.put("date", date)
        values.put("time", time)
        values.put("important", important)

        db.insert("notes", null, values)
        db.close()

    }


    fun allNote() : List<Notes> {
        val db = this.readableDatabase
        val arr = mutableListOf<Notes>()
        val cursor = db.query("notes",null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val nid = cursor.getInt(0)
            val title = cursor.getString(1)
            val detail = cursor.getString(2)
            val date = cursor.getString(3)
            val time = cursor.getString(4)
            val important = cursor.getString(5)

            val note = Notes(nid, title, detail, date, time, important)
            arr.add(note)
        }
        db.close()
        return arr
    }

}
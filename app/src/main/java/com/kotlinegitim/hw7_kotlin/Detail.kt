package com.kotlinegitim.hw7_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kotlinegitim.hw7_kotlin.database.Database


class Detail : AppCompatActivity() {

    lateinit var obj : Database

    lateinit var dateTXT : TextView
    lateinit var detailTXT : TextView
    lateinit var timeTXT : TextView
    lateinit var importantTXT : TextView
    lateinit var deleteBtn : Button
    lateinit var toast : Toast
    lateinit var customToastLayout: View
    lateinit var massageTXT : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        obj = Database(this)

        toast = Toast(applicationContext)
        customToastLayout = layoutInflater.inflate(R.layout.toast_custom_layout,null)

        deleteBtn =  findViewById(R.id.delete)

        dateTXT = findViewById(R.id.datedetail)
        detailTXT = findViewById(R.id.detailtext)
        importantTXT = findViewById(R.id.important)
        timeTXT = findViewById(R.id.time)
        massageTXT = customToastLayout.findViewById(R.id.textView)

        var detailtext = intent.getStringExtra("detail")
        var datetext = intent.getStringExtra("date")
        var timetext = intent.getStringExtra("time")
        var important = intent.getStringExtra("important")
        var id = intent.getIntExtra("id",0)

        detailTXT.text = detailtext
        detailTXT.setMovementMethod(ScrollingMovementMethod())

        dateTXT.text = datetext
        timeTXT.text = timetext
        importantTXT.text = important



        deleteBtn.setOnClickListener{

            obj.deleteNote(id)
            var toast = ToastController("Note is deleted",toast,  customToastLayout, massageTXT, this)

            toast.ShowAddedValue()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)



        }




    }
}
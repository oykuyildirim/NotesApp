package com.kotlinegitim.hw7_kotlin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.kotlinegitim.hw7_kotlin.customadaptors.NoteListCustomAdaptor
import com.kotlinegitim.hw7_kotlin.customadaptors.SpinnerCustomAdaptor
import com.kotlinegitim.hw7_kotlin.database.Database
import com.kotlinegitim.hw7_kotlin.models.Notes
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var setTime: Button
    lateinit var setDate: Button
    lateinit var saveBtn: Button
    lateinit var title: EditText
    lateinit var detail: EditText
    lateinit var db: Database
    lateinit var toast : Toast
    lateinit var massageTXT : TextView
    lateinit var customToastLayout: View

    var time = ""
    var date = ""
    var important = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTime = findViewById(R.id.Time)
        setDate = findViewById(R.id.date)
        saveBtn = findViewById(R.id.save)
        title = findViewById(R.id.noteTitle)
        detail = findViewById(R.id.noteDetail)

        customToastLayout = layoutInflater.inflate(R.layout.toast_custom_layout,null)
        massageTXT = customToastLayout.findViewById(R.id.textView)
        toast = Toast(applicationContext)


       getTime()

       setDate.setOnClickListener(View.OnClickListener {
            getDate()
        })


        val importance = resources.getStringArray(R.array.Importance)

        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = SpinnerCustomAdaptor(
                this,
               R.layout.spinner_custom_layout, importance
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {


                        important = importance.get(position)


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    important = importance.get(0)
                }
            }
        }



        db = Database(this)

        saveBtn.setOnClickListener {
            SaveNote()
        }



    }

    fun timeController(hour: Int, minute : Int) : String{

        var clock = ""

        if(hour<10){

            clock ="0"+"$hour:"+"$minute"

            if (minute<10){
                clock ="0"+"$hour:"+"0"+"$minute"
            }
            return clock

        }
        if (hour > 10){
            if (minute < 10){
                clock ="$hour:"+"0"+"$minute"
            }
            return clock
        }


        clock ="$hour:"+"$minute"
        return clock
    }

    override fun onStart() {
        super.onStart()

        val ls = db.allNote()
        Log.d("ls", ls.toString())

        val arrayAdapter: ArrayAdapter<*>
        var mListView = findViewById<ListView>(R.id.notes)
        arrayAdapter = NoteListCustomAdaptor(this,
            R.layout.listview_custom_layout, ls as MutableList<Notes>
        )
        mListView.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()

    }

    fun SaveNote(){

        var title_data = title.text.toString()
        var detail_data = detail.text.toString()



        if (time.isNullOrEmpty() || date.isNullOrEmpty())
        {
            Toast.makeText(this,"Date or Time isn't selected !",Toast.LENGTH_LONG).show()
        }
        else {
            title.setText("")
            detail.setText("")

            db.addNote(title_data, detail_data, date, time, important)
            val ls = db.allNote()

            val arrayAdapter: ArrayAdapter<*>
            var mListView = findViewById<ListView>(R.id.notes)
            arrayAdapter = NoteListCustomAdaptor(
                this,
                R.layout.listview_custom_layout, ls as MutableList<Notes>
            )
            mListView.adapter = arrayAdapter
            arrayAdapter.notifyDataSetChanged()


            title.requestFocus()


            var toast = ToastController(
                "${title_data} is added",
                toast,
                customToastLayout,
                massageTXT,
                this
            )


            toast.ShowAddedValue()


            time = ""
            date = ""
        }

    }


    fun getDate(){

        val calender = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                Log.d("i", i.toString())
                Log.d("i2", (i2 + 1).toString())
                Log.d("i3", i3.toString())
                var ay = "${i2+1}"
                if ( i2+1 < 10 ) {
                    ay = "0${i2+1}"
                }
                date = "$i3.$ay.$i"
            },
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH),
        )
        datePickerDialog.show()
    }

    fun getTime(){
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

                time =  timeController(hourOfDay,minute)

                println(time)
            }
        }, hour, minute, false)



        setTime.setOnClickListener({ v ->
            mTimePicker.show()
        })
    }

}
package com.kotlinegitim.hw7_kotlin.customadaptors

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlinegitim.hw7_kotlin.Detail
import com.kotlinegitim.hw7_kotlin.R
import com.kotlinegitim.hw7_kotlin.models.Notes

class NoteListCustomAdaptor(private val context: Activity,private val resource: Int, private val objects: MutableList<Notes>) :
    ArrayAdapter<Notes>(context, resource, objects) {

    lateinit var titleTxt : TextView
    lateinit var Image : ImageView

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val root = context.layoutInflater.inflate(R.layout.listview_custom_layout,null,true)

        titleTxt = root.findViewById(R.id.notetitle)
        Image = root.findViewById(R.id.impImage)
        val notes = objects.get(position)

        val image = context.resources.getStringArray(R.array.Images)


        var imageNo = 0
        when(notes.important){

            "Less Important" -> imageNo = 0
            "Important" -> imageNo = 1
            "Very Important" -> imageNo = 2
        }

        val imagePos = image.get(imageNo)


        val resId = context.resources.getIdentifier(
            imagePos,
            "drawable",
            context.packageName
        )
        titleTxt.text = notes.title
        Image.setImageResource(resId)

        root.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(context, Detail::class.java)


                intent.putExtra("date", notes.date )
                intent.putExtra("detail", notes.detail)
                intent.putExtra("time", notes.time)
                intent.putExtra("id", notes.nid)
                intent.putExtra("important", notes.important)

                context.startActivity(intent)

            }
        })


        return root
    }
}



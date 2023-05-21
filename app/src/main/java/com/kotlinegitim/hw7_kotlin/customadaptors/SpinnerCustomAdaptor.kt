package com.kotlinegitim.hw7_kotlin.customadaptors

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kotlinegitim.hw7_kotlin.R

class SpinnerCustomAdaptor(private val context: Activity, private val resource: Int, private val objects: Array<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    lateinit var image : ImageView
    lateinit var text : TextView

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return dropdownCustom(position,convertView,parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return dropdownCustom(position,convertView,parent)
    }


    fun dropdownCustom(position: Int, convertView: View?, parent: ViewGroup) : View{

        val root = context.layoutInflater.inflate(R.layout.spinner_custom_layout, parent,false)

        text = root.findViewById(R.id.spinner_imp)
        val obj = objects.get(position)

        text.text = obj








        return root
    }

}
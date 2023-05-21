package com.kotlinegitim.hw7_kotlin

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast

class ToastController {

    var massage: String
    var toast_data : Toast
    var customtoastlayout : View
    var massageText : TextView
    var applicationContext : Context

    constructor(Massage:String, Toast_data: Toast, CustomToastLayout : View, massagetext: TextView, ApplicationContex: Context){

        massage = Massage
        toast_data = Toast_data
        customtoastlayout = CustomToastLayout
        massageText = massagetext
        applicationContext = ApplicationContex

    }

    fun ShowAddedValue(){

        toast_data = Toast(applicationContext)
        toast_data.duration = Toast.LENGTH_SHORT
        toast_data.view = customtoastlayout
        massageText = customtoastlayout.findViewById(R.id.textView)
        massageText.text = massage
        toast_data.show()
    }
}
package com.example.scanner

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnScanner = findViewById<Button>(R.id.btnScan)
        val txtCode = findViewById<EditText>(R.id.txtCode)
        btnScanner.setOnClickListener {
            initScanner()
        }
        txtCode.setOnLongClickListener {
            copyText()

            return@setOnLongClickListener true
        }
    }

    private fun initScanner(){
        var integrator = IntentIntegrator(this)
        integrator.initiateScan()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        val txtCode = findViewById<EditText>(R.id.txtCode)

        if(result != null) {//Si es diferente a null viene de la pantalla del scanner
            if(result.contents == null){
                Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show()
            }
            else{
                txtCode.setText(result.contents)
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun copyText(){
        val txtCode = findViewById<EditText>(R.id.txtCode)

        //Copy
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("simple text", txtCode.text)
        //assign the text to the clipboard
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied text", Toast.LENGTH_SHORT).show()
    }
}
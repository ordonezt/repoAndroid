package com.utn.miprimerapp

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

//Elementos
lateinit var textoMedio : TextView

lateinit var botonColorFondo : Button
lateinit var botonColorLetra : Button
lateinit var botonVisualizacion : Button

//Constantes
val strBorrarTexto : String = "BORRAR"
val strVerTexto : String = "VER"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textoMedio = findViewById(R.id.textoMedio)

        botonColorFondo = findViewById(R.id.botonColorFondo)
        botonColorLetra = findViewById(R.id.botonColorLetra)
        botonVisualizacion = findViewById(R.id.botonVisualizacion)
        botonVisualizacion.text = strBorrarTexto

        botonVisualizacion.setOnClickListener {
            if (textoMedio.visibility == View.VISIBLE) {
                textoMedio.visibility = View.INVISIBLE
                botonVisualizacion.text = strVerTexto
            } else {
                textoMedio.visibility = View.VISIBLE
                botonVisualizacion.text = strBorrarTexto
            }
        }

        var rojo: Int = 0
        var verde: Int = 0
        var azul: Int = 0
        var color: Int = 0

        botonColorLetra.setOnClickListener {
            rojo = Random.nextInt(0, 255)
            verde = Random.nextInt(0, 255)
            azul = Random.nextInt(0, 255)
            color = Color.rgb(rojo, verde, azul)

            textoMedio.setTextColor(color)
        }

        botonColorFondo.setOnClickListener {
            rojo = Random.nextInt(0, 255)
            verde = Random.nextInt(0, 255)
            azul = Random.nextInt(0, 255)
            color = Color.rgb(rojo, verde, azul)

            textoMedio.setBackgroundColor(color)
        }
    }
}
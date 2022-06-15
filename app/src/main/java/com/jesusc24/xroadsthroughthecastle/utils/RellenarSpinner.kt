package com.jesusc24.xroadsthroughthecastle.utils

import android.R
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment

/**
 * Clase que se usa para rellenar cualquier Spinner usando programación generalizada
 */
object RellenarSpinner {
    /**
     * Requiere la información para rellenar un spinner de un fragment en concreto
     * @param spinner Introducir el spinner que se desea rellenar
     * @param string_array El id que tiene el value de spinner_items.xml donde se encuentran los datos para rellenar
     * @param fragment Para poder coger el contexto del fragment en que se localiza el spinner
     */
    @JvmStatic
    fun information(spinner: Spinner, string_array: Int, fragment: Fragment) {
        //Crea un ArrayAdapter usando el array de string y un layout spinner por defect
        val adapter = ArrayAdapter.createFromResource(
            fragment.context!!,
            string_array, R.layout.simple_spinner_item
        )
        //Especificamos el layout que usartá cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        //Aplica el adapter al spinner
        spinner.adapter = adapter
    }
}
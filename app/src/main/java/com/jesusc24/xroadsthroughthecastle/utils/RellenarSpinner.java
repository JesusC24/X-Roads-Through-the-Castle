package com.jesusc24.xroadsthroughthecastle.utils;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

/**
 * Clase que se usa para rellenar cualquier Spinner usando programación generalizada
 */
public class RellenarSpinner {
    /**
     * Requiere la información para rellenar un spinner de un fragment en concreto
     * @param spinner Introducir el spinner que se desea rellenar
     * @param string_array El id que tiene el value de spinner_items.xml donde se encuentran los datos para rellenar
     * @param fragment Para poder coger el contexto del fragment en que se localiza el spinner
     */
    public static void information(Spinner spinner, int string_array, Fragment fragment) {
        //Crea un ArrayAdapter usando el array de string y un layout spinner por defect
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(fragment.getContext(),
                string_array, android.R.layout.simple_spinner_item);
        //Especificamos el layout que usartá cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Aplica el adapter al spinner
        spinner.setAdapter(adapter);
    }
}

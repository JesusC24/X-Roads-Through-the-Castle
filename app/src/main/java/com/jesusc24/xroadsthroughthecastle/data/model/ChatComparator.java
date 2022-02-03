package com.jesusc24.xroadsthroughthecastle.data.model;

import java.util.Comparator;

public class ChatComparator implements Comparator {

    //Ordena por si es privado o público el chat
    @Override
    public int compare(Object o1, Object o2) {
        return ((Chat)o2).getTipo().compareTo(((Chat)o2).getTipo());
    }
}

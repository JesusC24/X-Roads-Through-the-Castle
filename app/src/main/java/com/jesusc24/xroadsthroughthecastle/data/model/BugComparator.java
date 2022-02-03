package com.jesusc24.xroadsthroughthecastle.data.model;


import java.util.Comparator;

public class BugComparator implements Comparator {

    //Ordena por la gravedad del bug
    @Override
    public int compare(Object o1, Object o2) {
        return ((Bug)o2).getGravedad().compareTo(((Bug)o2).getGravedad());
    }
}

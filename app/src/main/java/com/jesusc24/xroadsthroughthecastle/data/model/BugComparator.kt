package com.jesusc24.xroadsthroughthecastle.data.model

class BugComparator : Comparator<Bug> {
    override fun compare(p0: Bug, p1: Bug): Int {
        val estado0 = p0.estado
        val estado1 = p1.estado
        return estado0?.compareTo(estado1.toString())!!
    }
}
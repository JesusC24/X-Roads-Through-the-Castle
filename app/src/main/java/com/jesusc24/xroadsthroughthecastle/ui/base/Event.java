package com.jesusc24.xroadsthroughthecastle.ui.base;

public final class Event {
    public static final int onLoginError = 0;
    public static final int onSingUpError = 1;
    public static final int onLoginSuccess = 2;
    public static final int onSingUpSuccess = 3;

    private int eventType;
    private String message;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.grandgroup.model;

public class EventsModel {
    private String event_date;
    private String event_title;
    private String event_desc;

    public EventsModel(String event_date, String event_title, String event_desc) {
        this.event_date = event_date;
        this.event_title = event_title;
        this.event_desc = event_desc;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }


}

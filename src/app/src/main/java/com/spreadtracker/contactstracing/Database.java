package com.spreadtracker.contactstracing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private final SQLiteDatabase database;

    public Database(String filename) {
        database = SQLiteDatabase.openOrCreateDatabase(filename, null);

        try (SQLiteStatement statement = database.compileStatement("select 1 from person")){
            statement.execute();
        } catch (SQLiteException e) {
            // ok, so person doesn't exist
            Database.createClasses(database);
        }
    }

    public void createPerson(Person person) {
        try (SQLiteStatement statement = database.compileStatement("insert into person (firstName, lastName, dateOfBirth) values(?, ?, ?)")) {
            person.bindForInsert(statement);
            statement.execute();
        }
    }

    public void createEvent(Event event) {
        try (SQLiteStatement statement = database.compileStatement("insert into event (date, latitude, longitude) values(?, ?, ?)")) {
            event.bindForInsert(statement);
            statement.execute();
        }
    }

    public void createPersonEvent(long personId, long eventId) {
//        create an association between a person and event
        try (SQLiteStatement statement = database.compileStatement("insert into personEvent values(?, ?)")) {
            statement.bindLong(1, personId);
            statement.bindLong(2, eventId);
            statement.execute();
        }
    }

    public List<Long> getEventIdsBeforeDate(long personId, Date date){
//        returns the eventIds of all events that a person was member to before the given date.
        List<Long> eventIds = new ArrayList<>();
        String selectStatement = "SELECT e.ROWID From event e, personEvent pe, person p where " +
                "pe.personRowid = p.ROWID " +
                "and pe.eventRowid = e.ROWID " +
                "and p.ROWID = ? " +
                "and e.time < ? " +
                "order by time Desc";
        try (Cursor cursor = database.rawQuery(selectStatement, new String[]{Long.toString(personId), Long.toString(date.getTime())})) {
            while (cursor.moveToNext()) {
                eventIds.add(cursor.getLong(0));
            }
        }
        return(eventIds);
    }

    public static void createClasses(SQLiteDatabase db) {
        String[] ddlStatements = new String[] {
                "CREATE TABLE event (id NUMBER PRIMARY KEY, date NUMBER, latitude NUMBER, longitude NUMBER)",
                "CREATE TABLE person (id NUMBER PRIMARY KEY, firstName TEXT, lastName TEXT, dateOfBirth NUMBER)",
                "CREATE TABLE personEvent (personId, eventId)",
                "CREATE INDEX event_date on event (date)",
                "CREATE INDEX personEventEvent on personEvent(eventId)",
                "CREATE INDEX personEventPerson on personEvent(personId)",
                "CREATE INDEX person_lastName on person (lastName, firstName)"
        };

        for (String ddl : ddlStatements) {
            db.execSQL(ddl);
        }
    }
}

package com.spreadtracker.contactstracing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private final SQLiteDatabase database;

    public Database(File filename) {
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

    public void createPersonTest(Test test){
        try (SQLiteStatement statement = database.compileStatement("insert into personTest values(?, ?, ?, ?)")) {
            statement.bindLong(1, test.getPersonId());
            statement.bindString(2, test.getDisease());
            if (test.isPositive()){
                statement.bindLong(3, 1);
            } else {
                statement.bindLong(3, 0);
            }
            statement.bindLong(4, test.getDate());
        }
    }

    public List<Connection> getConnectionsBeforeDate(long personId, long date){
//        returns Connections for every person that a person interacted with before the given date.
//        Connections contain eventId, eventDate, eventWeight, personId
//        returns in order of descending date
        List<Connection> connections = new ArrayList<Connection>();
        String selectStatement = "SELECT e.rowid, e.date, e.weight, p.personId From event e, personEvent p, personEvent s where " +
                "p.eventROWID = s.eventROWID " +
                "and p.eventROWID = e.ROWID " +
                "and s.personROWID = ? " +
                "and e.date < ? " +
                "order by date Desc";
        try (Cursor cursor = database.rawQuery(selectStatement, new String[]{Long.toString(personId), Long.toString(date)})) {
            while (cursor.moveToNext()) {
                Connection connection = new Connection(cursor.getLong(0), cursor.getLong(1), cursor.getDouble(2), cursor.getLong(3));
                connections.add(connection);
            }
        }
        return(connections);
    }

    public List<Test> getTestsBeforeDate(long personId, long date) {
//        returns all the tests for a person before a given date
        List<Test> tests = new ArrayList<Test>();
        String selectStatement = "Select t.disease, t.result, t.date from personTest t where "+
                "t.personId = ? "+
                "and t.date < ? "+
                "order by date Desc";
        try (Cursor cursor = database.rawQuery(selectStatement, new String[]{Long.toString(personId), Long.toString(date)})){
            while(cursor.moveToNext()) {
                boolean result;
                if(cursor.getLong(1) == 0) {
                    result = false;
                } else {
                    result = true;
                }
                Test test = new Test(personId, cursor.getString(0), result, cursor.getLong(2));
                tests.add(test);
            }
        }
        return(tests);
    }

    public static void createClasses(SQLiteDatabase db) {
        String[] ddlStatements = new String[] {
                "CREATE TABLE event (id NUMBER PRIMARY KEY, date NUMBER, latitude NUMBER, longitude NUMBER)",
                "CREATE TABLE person (id NUMBER PRIMARY KEY, firstName TEXT, lastName TEXT, dateOfBirth NUMBER)",
                "CREATE TABLE personEvent (personId NUMBER, eventId NUMBER)",
                "CREATE TABLE personTest (personId NUMBER, disease TEXT, result NUMBER, date NUMBER)",
                "CREATE INDEX event_date on event (date)",
                "CREATE INDEX personEventEvent on personEvent(eventId)",
                "CREATE INDEX personEventPerson on personEvent(personId)",
                "CREATE INDEX personTestPerson on personTest(personId)",
                "CREATE INDEX personTestDate on personTest(date)",
                "CREATE INDEX person_lastName on person (lastName, firstName)"
        };

        for (String ddl : ddlStatements) {
            db.execSQL(ddl);
        }
    }

    public class Connection{
        public long eventId;
        public long eventDate;
        public double eventWeight;
        public long personId;

        public Connection(long eventId, long eventDate, double eventWeight, long personId){
            this.eventId = eventId;
            this.eventDate = eventDate;
            this.eventWeight = eventWeight;
            this.personId = personId;
        }
    }
}

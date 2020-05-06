package com.spreadtracker.contactstracing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;




public class Database {
    private final SQLiteDatabase database;

    private static final Random RANDOM = new Random();

    public static long getTotalInfected() {
        return TOTAL_INFECTED;
    }

    public static long getTotalEvents() {
        return TOTAL_EVENTS;
    }

    public static long getTotalPeople() {
        return TOTAL_PEOPLE;
    }


    private static final long TOTAL_INFECTED = 5;
    private static final long TOTAL_EVENTS = 50;
    private static final long TOTAL_PEOPLE = 100;

    private static final long ONE_YEAR = 3600L * 1000 * 24 * 365;
    private static final long LAST_YEAR = 50L * ONE_YEAR;

    public Database(File filename) {
        database = SQLiteDatabase.openOrCreateDatabase(filename, null);

        try (SQLiteStatement statement = database.compileStatement("select 1 from person")){
            statement.execute();
        } catch (SQLiteException e) {
            // ok, so person doesn't exist
            createClasses();
        }
    }

    public void createPerson(Person person) {
        try (SQLiteStatement statement = database.compileStatement("insert into person (firstName, lastName, dateOfBirth) values(?, ?, ?)")) {
            person.bindForInsert(statement);
            statement.execute();
        }
    }

    public void createEvent(Event event) {
        try (SQLiteStatement statement = database.compileStatement("insert into event (id, date, weight, latitude, longitude) values(?, ?, ?, ?, ?)")) {
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
            statement.execute();
        }
    }

    public long countEvents () {
        return TOTAL_EVENTS;
    }

    public long countPersons () {
        return TOTAL_PEOPLE;
    }

    public long countInfected () { return TOTAL_INFECTED; }

    public Person getRandomPerson () {
        Person person = new Person();
        long id = RANDOM.nextInt((int) countTable("person")) + 1;
        String selectStatement = "SELECT p.firstName, p.lastName, p.dateOfBirth FROM person p WHERE p.id = ?";
        try (Cursor cursor = database.rawQuery(selectStatement, new String[] {Long.toString(id)})) {
            cursor.moveToNext();
            person.setId(id);
            person.setFirstName(cursor.getString(0));
            person.setLastName(cursor.getString(1));
            person.setDateOfBirth(new Date(cursor.getLong(2)));
        }
        return person;
    }

    public List<String> getPersonNames(){
        List<String> names = new ArrayList<String>();
        String selectStatement = "SELECT p.firstName From person p order by ROWID asc";
        try (Cursor cursor = database.rawQuery(selectStatement, null)){
            while (cursor.moveToNext()) {
                names.add(cursor.getString(0));
            }
        }
        return(names);
    }

    public List<Long> getEventDates(){
        List<Long> dates = new ArrayList<Long>();
        String selectStatement = "SELECT e.date From event e order by ROWID asc";
        try (Cursor cursor = database.rawQuery(selectStatement, null)){
            while (cursor.moveToNext()) {
                dates.add(cursor.getLong(0));
            }
        }
        return(dates);
    }

    public List<Connection> getConnectionsBeforeDate(long personId, long date){
//        returns Connections for every person that a person interacted with before the given date.
//        Connections contain eventId, eventDate, eventWeight, personId
//        returns in order of descending date
        List<Connection> connections = new ArrayList<Connection>();
        String selectStatement = "SELECT e.rowid, e.date, e.weight, p.personId From event e, personEvent p, personEvent s where " +
                "p.eventID = s.eventID " +
                "and p.eventID = e.ROWID " +
                "and s.personID = ? " +
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

    public void createClasses() {
        String[] ddlStatements = new String[] {
                "CREATE TABLE event (id NUMBER PRIMARY KEY, date NUMBER, weight NUMBER, latitude NUMBER, longitude NUMBER)",
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

        try {
            for (String ddl : ddlStatements) {
                database.execSQL(ddl);
            }
        } catch (SQLiteException e) {
            // do nothing - this means the tables are there already
        }

        if (!(countTable("event") == TOTAL_EVENTS)){
            clearTable("event");
            addRandomEvents(TOTAL_EVENTS);
        }

        if (!(countTable("personEvent") == TOTAL_EVENTS*2)) {
            clearTable("personEvent");
            addRandomPersonEvents(TOTAL_PEOPLE, TOTAL_EVENTS);
        }

        if (!(countTable("personTest") == TOTAL_INFECTED)) {
            clearTable("personTest");
            addPositivePersonTests(TOTAL_PEOPLE, TOTAL_INFECTED);
            long count = countTable("personTest");
        }
    }

    private void addRandomEvents(long totalEvents){

        int i = 1;
        while (i <= totalEvents){
            double weight = Math.random();
            long date = LAST_YEAR + (long) (Math.random() * (double) ONE_YEAR);
            createEvent(new Event(i, 0, 0, date, weight));

            i++;
        }
    }

    private void addRandomPersonEvents(long totalPeople, long totalEvents){
        int i = 1;
        while (i <= totalEvents){
            long[] participants = getRandomEventParticipants(totalPeople);
            createPersonEvent(participants[0], i);
            createPersonEvent(participants[1], i);

            i++;
        }
    }

    private void addPositivePersonTests(long totalPeople, long numberOfTests){
        int i = 1;

        while (i <= numberOfTests){
            Test test = new Test(i, Test.DISEASE_COVID19, true, LAST_YEAR);
            createPersonTest(test);

            i++;
        }
    }

    private long countTable(String table){
        StringBuilder s = new StringBuilder("SELECT count(*) from ")
                .append(table);

        long count = 0;
        try (Cursor cursor = database.rawQuery(s.toString(), null)){
            while (cursor.moveToNext()) {
                count = cursor.getLong(0);
            }
        }
        return(count);
    }

    private void clearTable(String table){
        StringBuilder s = new StringBuilder("DELETE from ")
                .append(table);

        database.execSQL(s.toString());
    }

    private static long[] getRandomEventParticipants(long totalPeople){
//        gets two random and unique numbers between 1 and the totalPeople
        Random rand = new Random();
        long p1 = rand.nextInt((int) totalPeople) + 1;
        long p2 = rand.nextInt((int) totalPeople - 1) + 1;
        if (p2 >= p1){
            p2 += 1;
        }
        long[] participants = new long[]{p1, p2};
        return(participants);
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

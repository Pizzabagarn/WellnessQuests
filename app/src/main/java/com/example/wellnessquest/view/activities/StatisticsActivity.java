package com.example.wellnessquest.view.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.wellnessquest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class StatisticsActivity extends BaseDrawerActivity {

    private TextView textMonday;
    private TextView textTuesday;
    private TextView textWednesday;
    private TextView textThursday;
    private TextView textFriday;
    private TextView textSaturday;
    private TextView textSunday;

    private final int[] completedPerDay = new int[7];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(
                R.layout.activity_statistics,
                drawerBinding.contentFrame,
                true
        );

        connectViews();
        loadWeeklyStatistics();
    }

    /**
     * Connects the TextViews in activity_statistics.xml
     * to this activity.
     */
    private void connectViews() {
        textMonday = findViewById(R.id.textMonday);
        textTuesday = findViewById(R.id.textTuesday);
        textWednesday = findViewById(R.id.textWednesday);
        textThursday = findViewById(R.id.textThursday);
        textFriday = findViewById(R.id.textFriday);
        textSaturday = findViewById(R.id.textSaturday);
        textSunday = findViewById(R.id.textSunday);
    }

    /**
     * Loads diary entries created during the current week
     * and counts completed quests for each day.
     */
    private void loadWeeklyStatistics() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = firebaseUser.getUid();

        long startOfWeek = getStartOfWeek();
        long startOfNextWeek = getStartOfNextWeek();

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .collection("diaryEntries")
                .whereGreaterThanOrEqualTo("timestamp", startOfWeek)
                .whereLessThan("timestamp", startOfNextWeek)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    // Clear previous values before counting.
                    for (int i = 0; i < completedPerDay.length; i++) {
                        completedPerDay[i] = 0;
                    }

                    queryDocumentSnapshots.getDocuments().forEach(document -> {
                        Long timestamp = document.getLong("timestamp");

                        if (timestamp != null) {
                            int dayIndex = getDayIndex(timestamp);

                            if (dayIndex >= 0 && dayIndex < completedPerDay.length) {
                                completedPerDay[dayIndex]++;
                            }
                        }
                    });

                    displayStatistics();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(
                                this,
                                "Could not load statistics",
                                Toast.LENGTH_SHORT
                        ).show()
                );
    }

    /**
     * Returns the timestamp for Monday at 00:00
     * during the current week.
     */
    private long getStartOfWeek() {
        Calendar calendar = Calendar.getInstance();

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    /**
     * Returns the timestamp for next Monday at 00:00.
     */
    private long getStartOfNextWeek() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(getStartOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        return calendar.getTimeInMillis();
    }

    /**
     * Converts a timestamp into an array position:
     * Monday = 0, Tuesday = 1, ..., Sunday = 6.
     */
    private int getDayIndex(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
            default:
                return -1;
        }
    }

    /**
     * Displays the number of completed quests for each day.
     */
    private void displayStatistics() {
        textMonday.setText("Monday: " + completedPerDay[0]);
        textTuesday.setText("Tuesday: " + completedPerDay[1]);
        textWednesday.setText("Wednesday: " + completedPerDay[2]);
        textThursday.setText("Thursday: " + completedPerDay[3]);
        textFriday.setText("Friday: " + completedPerDay[4]);
        textSaturday.setText("Saturday: " + completedPerDay[5]);
        textSunday.setText("Sunday: " + completedPerDay[6]);
    }
}
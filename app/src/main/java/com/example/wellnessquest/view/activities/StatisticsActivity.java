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

    private TextView valueMonday;
    private TextView valueTuesday;
    private TextView valueWednesday;
    private TextView valueThursday;
    private TextView valueFriday;
    private TextView valueSaturday;
    private TextView valueSunday;
    private TextView textWeeklyTotal;

    private android.view.View barMonday;
    private android.view.View barTuesday;
    private android.view.View barWednesday;
    private android.view.View barThursday;
    private android.view.View barFriday;
    private android.view.View barSaturday;
    private android.view.View barSunday;

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
        valueMonday = findViewById(R.id.valueMonday);
        valueTuesday = findViewById(R.id.valueTuesday);
        valueWednesday = findViewById(R.id.valueWednesday);
        valueThursday = findViewById(R.id.valueThursday);
        valueFriday = findViewById(R.id.valueFriday);
        valueSaturday = findViewById(R.id.valueSaturday);
        valueSunday = findViewById(R.id.valueSunday);

        barMonday = findViewById(R.id.barMonday);
        barTuesday = findViewById(R.id.barTuesday);
        barWednesday = findViewById(R.id.barWednesday);
        barThursday = findViewById(R.id.barThursday);
        barFriday = findViewById(R.id.barFriday);
        barSaturday = findViewById(R.id.barSaturday);
        barSunday = findViewById(R.id.barSunday);

        textWeeklyTotal = findViewById(R.id.textWeeklyTotal);
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
        TextView[] valueViews = {
                valueMonday,
                valueTuesday,
                valueWednesday,
                valueThursday,
                valueFriday,
                valueSaturday,
                valueSunday
        };

        android.view.View[] bars = {
                barMonday,
                barTuesday,
                barWednesday,
                barThursday,
                barFriday,
                barSaturday,
                barSunday
        };

        int maximumValue = 1;
        int weeklyTotal = 0;

        for (int value : completedPerDay) {
            weeklyTotal += value;

            if (value > maximumValue) {
                maximumValue = value;
            }
        }

        for (int i = 0; i < completedPerDay.length; i++) {
            int value = completedPerDay[i];
            valueViews[i].setText(String.valueOf(value));

            int maximumBarHeightDp = 180;
            int minimumBarHeightDp = 4;

            int barHeightDp;

            if (value == 0) {
                barHeightDp = minimumBarHeightDp;
            } else {
                barHeightDp = Math.max(
                        30,
                        value * maximumBarHeightDp / maximumValue
                );
            }

            android.view.ViewGroup.LayoutParams parameters =
                    bars[i].getLayoutParams();

            parameters.height = dpToPixels(barHeightDp);
            bars[i].setLayoutParams(parameters);
        }

        textWeeklyTotal.setText(weeklyTotal + " completed quests");
    }

    private int dpToPixels(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
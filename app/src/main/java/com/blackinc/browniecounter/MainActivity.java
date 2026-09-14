package com.blackinc.browniecounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NUMBER_OF_TIMES = "numberOfTimes";
    private static final String STATE_NUMBER = "state_number";

    private int browniePoints = 0;
    private SharedPreferences sharedPreferences;

    private TextView quantityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_main);

        // Bind views
        View root = findViewById(R.id.main_root);
        quantityTextView = findViewById(R.id.quantity_text_view);
        View btnReset = findViewById(R.id.btn_reset);
        View counterCard = findViewById(R.id.counter_card);

        // Apply window insets for status and navigation bars
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        // Initialize preferences and load stored points
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (savedInstanceState != null) {
            browniePoints = savedInstanceState.getInt(STATE_NUMBER, 0);
        } else {
            browniePoints = sharedPreferences.getInt(KEY_NUMBER_OF_TIMES, 0);
        }

        updateDisplay(false);

        // Setup button click listeners
        View btnIncrease = findViewById(R.id.btn_increase);
        View btnDecrease = findViewById(R.id.btn_decrease);

        if (btnIncrease != null) {
            btnIncrease.setOnClickListener(this::increaseNumber);
        }
        if (btnDecrease != null) {
            btnDecrease.setOnClickListener(this::decreaseNumber);
        }

        // Setup reset button and card long-click
        if (btnReset != null) {
            btnReset.setOnClickListener(v -> showResetConfirmationDialog());
        }

        if (counterCard != null) {
            counterCard.setOnLongClickListener(v -> {
                showResetConfirmationDialog();
                return true;
            });
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_NUMBER, browniePoints);
    }

    public void increaseNumber(View view) {
        if (view != null) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        }
        browniePoints++;
        savePoints();
        updateDisplay(true);
    }

    public void decreaseNumber(View view) {
        if (view != null) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        }
        browniePoints--;
        savePoints();
        updateDisplay(true);
    }

    private void savePoints() {
        sharedPreferences.edit()
                .putInt(KEY_NUMBER_OF_TIMES, browniePoints)
                .apply();
    }

    private void updateDisplay(boolean animate) {
        if (quantityTextView != null) {
            quantityTextView.setText(String.valueOf(browniePoints));

            if (animate) {
                quantityTextView.setScaleX(0.85f);
                quantityTextView.setScaleY(0.85f);
                quantityTextView.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(220)
                        .setInterpolator(new OvershootInterpolator(2.0f))
                        .start();
            }
        }
    }

    private void showResetConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.reset_dialog_title)
                .setMessage(R.string.reset_dialog_message)
                .setPositiveButton(R.string.reset_confirm, (dialog, which) -> {
                    browniePoints = 0;
                    savePoints();
                    updateDisplay(true);
                })
                .setNegativeButton(R.string.reset_cancel, null)
                .show();
    }
}

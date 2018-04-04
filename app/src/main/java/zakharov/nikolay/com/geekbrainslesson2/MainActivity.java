package zakharov.nikolay.com.geekbrainslesson2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int REQUEST_CODE = 0;
    private static final String EXTRA_REPS_COUNT =
            "zakharov.nikolay.com.geekbrainslesson2.MainActivity.repsCount";
    private static final String EXTRA_LAST_RECORD_DATE =
            "zakharov.nikolay.com.geekbrainslesson2.MainActivity.lastRecordDate";

    int repsCount = 0;
    String lastRecordDate = dataFormat(new Date());

    Button pullUpsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            repsCount = savedInstanceState.getInt(EXTRA_REPS_COUNT, 0);
            lastRecordDate = savedInstanceState.getString(EXTRA_LAST_RECORD_DATE);
        }

        setListeners();
    }

    private void setListeners() {
        pullUpsButton = findViewById(R.id.pull_up_button);
        pullUpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkoutDetailActivity.newIntent(MainActivity.this, repsCount, lastRecordDate); //возваращаем интент созданный newIntent в CheatActivity
                startActivityForResult(intent, REQUEST_CODE); //запуск новой активности с результатом
            }
        });
    }

    private String dataFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy 'в' HH:mm:ss", new Locale("ru"));
        return dateFormat.format(date).toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            if (data == null) {
                return;
            }
            repsCount = WorkoutDetailActivity.getRepsCountForResult(data);
            lastRecordDate = WorkoutDetailActivity.getLastRecordDateForResult(data);
        }
    }// возварещение результата дочерней активности

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(EXTRA_REPS_COUNT, repsCount);
        savedInstanceState.putString(EXTRA_LAST_RECORD_DATE, lastRecordDate);
    }
}
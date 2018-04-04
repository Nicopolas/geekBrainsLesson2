package zakharov.nikolay.com.geekbrainslesson2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkoutDetailActivity extends AppCompatActivity {
    public static final String TAG = "WorkoutDetailActivity";
    private static final String  EXTRA_REPS_COUNT =
            "zakharov.nikolay.com.geekbrainslesson2.WorkoutDetailActivity.repsCount";
    private static final String EXTRA_LAST_RECORD_DATE =
            "zakharov.nikolay.com.geekbrainslesson2.WorkoutDetailActivity.answer_shown";

    Button saveRecordButton;
    SeekBar repsSeekBar;
    TextView repsCountTextView;
    TextView recordDateTextView;
    TextView recordRepsCountTextView;
    ImageView pullUpsImageView;

/*    int repsCount = 0;
    String lastRecordDate = dataFormat(new Date());*/

    int repsCount;
    String lastRecordDate;

    public static Intent newIntent(Context packageContext, int repsCount, String lastRecordDate) {
        Intent intent = new Intent(packageContext, WorkoutDetailActivity.class);// создаем интент
        intent.putExtra(EXTRA_LAST_RECORD_DATE, lastRecordDate);//добавляем в интент lastRecordDate
        intent.putExtra(EXTRA_REPS_COUNT, repsCount);//добавляем в интент repsCount
        return intent;
    }//статичный метод возвращающий интент (при создании)

    public static int getRepsCountForResult(Intent result) {
        return result.getIntExtra(EXTRA_REPS_COUNT, 0);
    }//методы для вызова переменных из результата

    public static String getLastRecordDateForResult(Intent result) {
        return result.getStringExtra(EXTRA_LAST_RECORD_DATE);
    }//методы для вызова переменных из результата

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate(Bundle) called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        initGUI();

        if (savedInstanceState != null) {
            repsCount = savedInstanceState.getInt(EXTRA_REPS_COUNT, 0); //присвоение repsCount значение из сохранной переменной
            lastRecordDate = savedInstanceState.getString(EXTRA_LAST_RECORD_DATE); //присвоение lastRecordDateCount значение из сохранной переменной
            setDataRecord();
        } else {
            repsCount = getIntent().getIntExtra(EXTRA_REPS_COUNT, 0);//получение repsCount из родительской активности
            lastRecordDate = getIntent().getStringExtra(EXTRA_LAST_RECORD_DATE);//получение lastRecordDate из родительской активности
            if (repsCount != 0) {
                setDataRecord();
            }
        }

        setListeners();
    }

    private void setListeners() {
        repsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                repsCountTextView.setText("Количество повторов: " + String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reps = Integer.parseInt(repsCountTextView.getText().toString().replaceAll("\\D", ""));
                if (recordRepsCountTextView.getText() != null && repsCount < reps) {
                    repsCount = reps;
                    lastRecordDate = dataFormat(new Date());
                    setDataRecord();
                }
            }
        });
    }

    private void setDataRecord() {
        recordRepsCountTextView.setText("Повторов: " + String.valueOf(repsCount));
        recordDateTextView.setText("Дата: " + lastRecordDate); //Отформатировать дату рекорда
        setResult();
    }

    private void initGUI() {
        saveRecordButton = findViewById(R.id.save_record_button);
        repsSeekBar = findViewById(R.id.reps_seek_bar);
        repsCountTextView = findViewById(R.id.reps_count_text_view);
        recordDateTextView = findViewById(R.id.record_date_text_view);
        recordRepsCountTextView = findViewById(R.id.record_reps_text_view);
    }

    private String dataFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy 'в' HH:mm:ss", new Locale("ru"));
        return dateFormat.format(date).toString();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(EXTRA_REPS_COUNT, repsCount);
        savedInstanceState.putString(EXTRA_LAST_RECORD_DATE, lastRecordDate);
    }

    private void setResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_LAST_RECORD_DATE, lastRecordDate);//добавляем в интент lastRecordDate
        data.putExtra(EXTRA_REPS_COUNT, repsCount);//добавляем в интент repsCount
        setResult(RESULT_OK, data);
    } //заполнение интента результата
}

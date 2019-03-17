package www.androidcitizen.com.androidlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    private final static String KEY_JOKE_INTENT = "KEY_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView jokeView = findViewById(R.id.jokeView);

        Intent intent = getIntent();

        if (null != intent) {

            if(intent.hasExtra(KEY_JOKE_INTENT)) {
                jokeView.setText(intent.getStringExtra(KEY_JOKE_INTENT));
            } else {
                Toast.makeText(this, "No Jokes around, buddy.", Toast.LENGTH_SHORT).show();
            }

        }

    }
}

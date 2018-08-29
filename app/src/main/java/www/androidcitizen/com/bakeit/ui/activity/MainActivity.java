package www.androidcitizen.com.bakeit.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import www.androidcitizen.com.bakeit.R;
import www.androidcitizen.com.bakeit.data.custominterface.RecipeClickListenerInterface;

public class MainActivity extends AppCompatActivity implements RecipeClickListenerInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected() {
        Toast.makeText(this, "Selected Message", Toast.LENGTH_SHORT).show();
    }

}
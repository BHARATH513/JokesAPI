package com.acer.jokesapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count=findViewById(R.id.cnt);
    }

    public void getjokes(View view) {
        Intent intent=new Intent(this,JokesActivity.class);
        intent.putExtra("count",count.getText().toString());
        startActivity(intent);
    }
}

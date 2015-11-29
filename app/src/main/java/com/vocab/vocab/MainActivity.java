package com.vocab.vocab;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wireUpLayout();
        new getWordsAndDefinitionsTask(this).execute();
    }
    public void wireUpLayout(){
        Button vocalizeButton=(Button) findViewById(R.id.VocalizeButton);
        Button visualizeButton=(Button) findViewById(R.id.VisualizeButton);
        Button verifyButton=(Button) findViewById(R.id.VerifyButton);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "pacifico.ttf");
        vocalizeButton.setTypeface(typeface);
        visualizeButton.setTypeface(typeface);
        verifyButton.setTypeface(typeface);
        visualizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ArrayList<String>> dictionary = DefinitionsSingelton.getInstance().getWordsAndDefs();
                Intent openDictionary = new Intent(MainActivity.this, WordListActivity.class);
                openDictionary.putExtra("dictionary", (Serializable) dictionary);
                startActivity(openDictionary);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

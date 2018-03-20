package com.bachelors.unige.navigationunige;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> listCollab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ArrayList pour pointer sur les elements JSON voulus
        listCollab = new ArrayList<>();
        listCollab = catchJSONElement();

        // Crée la listView pour pouvoir afficher la liste sur l'app
        mListView = (ListView) findViewById(R.id.LesBatiments);
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, listCollab);
        mListView.setAdapter(adapter);
    }

    public ArrayList catchJSONElement() {
        ArrayList<String> listCollabo = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray json_array = obj.getJSONArray("site");

            for (int i = 0; i < json_array.length(); i++) {
                JSONObject j_inside = json_array.getJSONObject(i);
                String site = j_inside.optString("nomSite");

                String nomSite = "Uni " + site;

                listCollabo.add(nomSite);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listCollabo;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("InfrastructureUNIGE.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}

package birthday.flower;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainPage extends AppCompatActivity
{
    List<String> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper helper = new DataBaseHelper(this);
        try
        {
                helper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("not working can't make database");
        }

        try
        {
            helper.openDataBase();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        flowers = helper.GrabAllNames();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, flowers);
        ListView listView= (ListView) findViewById(R.id.names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String herb = ((TextView)view).getText().toString();

                Toast.makeText(MainPage.this, "the clicked item had " + herb + " as the text", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

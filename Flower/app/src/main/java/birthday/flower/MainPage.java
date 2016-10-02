package birthday.flower;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

public class MainPage extends AppCompatActivity
{
    List<String> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DataBaseHelper helper = new DataBaseHelper(this);
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
        final ListView listView= (ListView) findViewById(R.id.names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String herb = ((TextView)view).getText().toString();
                FlowerObject flower = helper.GrabSpecificFlower(herb);
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow( popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                Toast.makeText(MainPage.this, "the clicked item had " + herb + " as the text", Toast.LENGTH_SHORT).show();
                TextView name = (TextView)popupWindow.getContentView().findViewById(R.id.HerbName);
                name.setText(flower.GetFlowerName());
                TextView indications = (TextView)popupWindow.getContentView().findViewById(R.id.Indications);
                indications.setText(flower.GetIndications());
                TextView cleansing = (TextView)popupWindow.getContentView().findViewById(R.id.Cleansing);
                TextView keyWords = (TextView)popupWindow.getContentView().findViewById(R.id.KeyWords);


                cleansing.setText(flower.GetCleansing());
                keyWords.setText(flower.GetKeyWords());
                popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);

                Button closeButton = (Button)popupView.findViewById(R.id.closeButton);

                closeButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        popupWindow.dismiss();
                    }
                });

            }
        });
    }
}



package birthday.flower;

import android.database.SQLException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

import birthday.flower.Database.DataBaseHelper;
import birthday.flower.Models.FlowerObject;

public class MainPage extends AppCompatActivity {
    List<String> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DataBaseHelper helper = new DataBaseHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("not working can't make database");
        }

        try {
            helper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        flowers = helper.GrabAllNames();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, flowers);
        final ListView listView = (ListView) findViewById(R.id.names);
        listView.setAdapter(adapter);
        EditText search = (EditText) findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER || i == EditorInfo.IME_ACTION_DONE) {
                    String input = textView.getText().toString();
                    adapter.clear();
                    flowers = helper.Searching(input);
                    if (flowers.isEmpty())
                    {
                        adapter.clear();
                        flowers = helper.GrabAllNames();
                        if (!input.isEmpty())
                        {
                            Toast t = Toast.makeText(MainPage.this, "The search gave no results please try again" , Toast.LENGTH_LONG);
                            t.setGravity(Gravity.TOP, 0, 0);
                            t.show();
                        }
                        return true;
                    }
                    adapter.addAll(flowers);
                }
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String herb = ((TextView) view).getText().toString();
                FlowerObject flower = helper.GrabSpecificFlower(herb);
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                TextView name = (TextView) popupWindow.getContentView().findViewById(R.id.HerbName);

                TextView indications = (TextView) popupWindow.getContentView().findViewById(R.id.Indications);
                TextView cleansing = (TextView) popupWindow.getContentView().findViewById(R.id.Cleansing);
                TextView keyWords = (TextView) popupWindow.getContentView().findViewById(R.id.KeyWords);
                indications.setText(flower.GetIndications());
                name.setText(flower.GetFlowerName());
                cleansing.setText(flower.GetCleansing());
                keyWords.setText(flower.GetKeyWords());
                popupWindow.showAtLocation(listView, Gravity.CENTER, 0, 0);

                Button closeButton = (Button) popupView.findViewById(R.id.closeButton);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    int orgX, orgY;
                    int offsetX, offsetY;

                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                orgX = (int) motionEvent.getX();
                                orgY = (int) motionEvent.getY();
                                //Log.d("test", "x: " + orgX + " y: " + orgY);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                offsetX = (int) motionEvent.getRawX() - orgX;
                                offsetY = (int) motionEvent.getRawY() - orgY;
                                popupWindow.update(offsetX, offsetY, -1, -1, true);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }
}


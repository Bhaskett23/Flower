package birthday.flower;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainPage extends AppCompatActivity
{
    List<FlowerObject> flowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_simple_spinner_dropdown_item);
        DataBaseHelper helper = new DataBaseHelper(this);
        try {
                helper.createDataBase();
        }
        catch (IOException ioe)
        {
            throw new Error("not working can't make database");
        }

        try{
            helper.openDataBase();
        }
        catch (SQLException sqle)
        {
            throw sqle;
        }

        flowers = helper.GrabAllNames();
        Toast.makeText(MainPage.this, "Success", Toast.LENGTH_SHORT).show();
        for (FlowerObject flower: flowers)
        {
            Toast.makeText(MainPage.this, flower._FlowerName, Toast.LENGTH_LONG).show();
        }
    }
}

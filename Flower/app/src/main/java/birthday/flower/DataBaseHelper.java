package birthday.flower;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 9/2/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    private static String DB_PATH = "/Flower/Flower/app/src/main/assests";
    private static String DB_NAME = "Flowers";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException
    {
        boolean dbExists = checkDataBase();

        if (!dbExists)
        {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying Database");
            }
        }
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB;

        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLException e)
        {
            throw new Error("DB Doesn't Exist");
        }

        checkDB.close();

        if (checkDB != null)
        {
            return true;
        }
        return false;
    }

    private void copyDataBase() throws IOException
    {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException
    {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public synchronized void close()
    {
        if (myDataBase != null)
        {
            myDataBase.close();
        }
        super.close();
    }

    //grab all names for drop down
    public List<FlowerObject> GrabAllNames()
    {
        List<FlowerObject> flowers = new ArrayList<FlowerObject>();
        openDataBase();
       Cursor cursor = myDataBase.rawQuery("select Name from Flowers", null);
       cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            FlowerObject temp = new FlowerObject(0, cursor.getString(1), null, null, null);
            flowers.add(temp);
        }

        return flowers;
    }

    public FlowerObject GrabSpecificFlower(String name)
    {

        Cursor cursor = myDataBase.rawQuery("select Name, KeyWords, Indications, Cleansing from Flowers WHERE Name = ?",
                new String[]{name});


        FlowerObject flower = new FlowerObject(0, name, );
        return flower;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}
}

package birthday.flower;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static String DB_PATH;
    private static String DB_NAME = "Flowers.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    ArrayList<String> flowerNames = new ArrayList<>();

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
            this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
            Log.e("Path 1", DB_PATH);
    }

    public void createDataBase() throws IOException
    {
        //hack: will always create new db when inserting new flowers and deleting is needed will need to add in else updates will not be read until the app is run again
       // boolean dbExists = checkDataBase();

       // if (!dbExists)
       // {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying Database");
            }
      //  }
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
        if (checkDB != null)
        {
            checkDB.close();
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
    public List<String> GrabAllNames()
    {
        if (flowerNames.isEmpty())
        {
            String[] columns = {"Name"};
            Cursor cursor = myDataBase.query("Flowers", null, null, null, null, null, null, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast())
            {
                flowerNames.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }

        return flowerNames;
    }

    //grab specific Flower by name
    public FlowerObject GrabSpecificFlower(String name)
    {
        Cursor cursor = myDataBase.rawQuery("select Name, KeyWords, Indications, Cleansing from Flowers WHERE Name = ?",
                new String[]{name});

        FlowerObject flower = new FlowerObject(0, name, null, null, null);
        return flower;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        if (newVersion > oldVersion)
        {
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

package birthday.flower;

/**
 * Created by Brandon on 9/1/2016.
 */
public class FlowerObject
{
    //properties
    int _id;
    String _FlowerName;
    String _KeyWords;
    String _Indications;
    String _Cleansing;

    public FlowerObject(){}
// main constructor for the FlowerObject
    //look into making different constructors
    public FlowerObject(int id,
                        String FlowerName,
                        String KeyWords,
                        String Indications,
                        String Cleansing)
    {
        _id = id;
        _FlowerName = FlowerName;
        _KeyWords = KeyWords;
        _Indications = Indications;
        _Cleansing = Cleansing;
    }

    public int GetID()
    {
        return _id;
    }

    public String GetFlowerName()
    {
        return _FlowerName;
    }

    public String GetKeyWords()
    {
        return _KeyWords;
    }

    public String GetIndications()
    {
        return _Indications;
    }

    public String GetCleansing(){return _Cleansing;}
}

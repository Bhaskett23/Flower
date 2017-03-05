package birthday.flower.Models;

/**
 * Created by Brandon on 9/1/2016.
 */
public class FlowerModel
{
    //properties
    private int _id;
    private String _FlowerName;
    private String _KeyWords;
    private String _Indications;
    private String _Cleansing;

    // main constructor for the FlowerModel
    //look into making different constructors
    public FlowerModel(int id,
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

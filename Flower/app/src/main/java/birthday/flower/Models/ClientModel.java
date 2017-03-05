package birthday.flower.Models;

/**
 * Created by Brandon on 3/4/2017.
 */

public class ClientModel
{
    private int _id;
    private String _firstName;
    private String _lastName;

    public ClientModel(int id, String firstName, String lastName)
    {
        _id = id;
        _firstName = firstName;
        _lastName = lastName;
    }

    public int GetID()
    {
        return _id;
    }

    public String GetFirstName()
    {
        return _firstName;
    }

    public String GetLastName()
    {
        return _lastName;
    }

    public String GetFullName()
    {
        return _firstName + _lastName;
    }
}

import java.util.ArrayList;
import java.util.HashMap;

public class Location 
{
    private String name;
    private String description;
    private HashMap <String, Location> connections;
    private ArrayList <Item> list;

    public Location(String name, String description)
    {
        this.name = name;
        this.description = description;
        list = new ArrayList<Item>();
        connections = new HashMap<>();
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void addItem(Item item)
    {
        list.add(item);
    }

    public boolean hasItem(String name)
    {
        for(int i=0;i<list.size();i++)
        {
            if (list.get(i).getName().compareToIgnoreCase(name) == 0)
            {
                return true;
            }
        }
        return false;
    }
    

    public Item getItem(String name)
    {
        for(int i=0; i<list.size(); i++)
        {
            if(list.get(i).getName().compareToIgnoreCase(name) == 0)
            {
                return list.get(i);
            }
        }
    return null;
    }

    public Item getItem(int index)
    {
        if(index >= 0 && index < list.size())
        {
            return list.get(index);
        }
        else 
        {
            return null;
        }
    }

    public int numItems()
    {
        return list.size();
    }

    public Item removeItem(String name)
    {
        if(hasItem(name) == true)
        {
            for(int i=0; i<list.size(); i++)
            {
                if (list.get(i).getName().compareToIgnoreCase(name) == 0)
                {
                    return list.remove(i);
                }
            }
        }
        return null;
    }

    public void connect(String direction, Location target)
    {
        this.connections.put(direction, target);
    }

    public boolean canMove(String direction)
    {
        return connections.containsKey(direction);
    }

    public Location getLocation(String direction)
    {
        if(canMove(direction))
        {
            return connections.get(direction);
        }
        return null;
    }
}


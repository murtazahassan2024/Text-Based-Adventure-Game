import java.util.ArrayList;

public class ContainerItem extends Item
{
    private ArrayList<Item> items;
    
    public ContainerItem(String name, String type, String description)
    {
        super(name, type, description);
        items = new ArrayList<>();
    }

    public void addItem(Item newItem)
    {
        items.add(newItem);
    }

    public boolean hasItem(String itemName)
    {
        for(int i=0; i<items.size(); i++)
        {
            if(items.get(i).getName().equalsIgnoreCase(itemName))
            {
                return true;
            }
        }
    return false;
    }

    public Item removeItem(String itemName)
    {
        
        for(int i=0; i<items.size(); i++)
        {
            if(items.get(i).getName().equalsIgnoreCase(itemName))
            {
                return items.remove(i);
            }
        }
        return null;   
    }

    @Override
    public String toString()
    {
        String info = super.toString();
        String itemList = "";
        for (int i = 0; i < items.size(); i++)
        {
            itemList += "+ " + items.get(i).getName() + "\n";
        }    
        return info + itemList;
    }
}

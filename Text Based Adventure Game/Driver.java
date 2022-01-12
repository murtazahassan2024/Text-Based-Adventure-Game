import java.util.Scanner; 

public class Driver 
{   
    public static Location currLocation;
    private static ContainerItem myInventory = new ContainerItem("Inventory", "bag", "A bag where your items, which are picked, are stored ");
    public static void help()
    {
        System.out.println("Useful commands:\n The 'quit' command finishes the game. \n The 'inventory' command lists the objects in your possession. A mask already exists in your inventory at the beginning of the game. \n The 'look' command prints a description of your surroundings. \n Actions for picking up or dropping items: take, drop \n To move to a direction use command 'go' with a direction (north, south, east, west). \n To examine an item, type 'examine' with the item name. \n There might be items that can contain other items. These items are called container items. Use 'take' command with 'from' command to take an item from a container item. \n Use 'put' command with 'in' command to store an item in a container item.");    
    }
    public static void main(String[] args)
    {
        createWorld();

        Scanner scan = new Scanner(System.in);
        int i = 0;
        myInventory.addItem(new Item("Compas", "device", "To show cardinal directions used for navigation."));
        System.out.println("Welcome to the game. Enter command 'help' for instructions");

        loop:while(i>=0)
        {
            System.out.println("Enter command:"); 
            String input = scan.nextLine();
            String[] inputs = input.split(" ");
            
            switch(inputs[0])
            {
                case "quit":
                    break loop;

                case "look":
                    System.out.println(currLocation.getName() + "-" + currLocation.getDescription());
                    for(int j=0; j<currLocation.numItems(); j++)
                    {
                        System.out.println(currLocation.getItem(j).getName());
                    }
                    break;

                case "examine":
                if(inputs.length > 1)
                {
                    if(currLocation.hasItem(inputs[1]))
                    {
                        System.out.println(currLocation.getItem(inputs[1]).toString()); 
                    }
                    else
                    {
                        System.out.println("Cannot find that item");
                    }
                }
                else
                {
                    System.out.println("Enter");
                }
                break;

                case "go":
                    if(inputs.length > 1)
                    {
                        String direction = inputs[1];
                        if(direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("south") || direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("west"))
                        {    
                            if(currLocation.canMove(direction))
                            {
                                currLocation = currLocation.getLocation(direction);
                                System.out.println("You are in a new location now. Use command 'look' to look around the place");
                            }
                            else{
                                    System.out.println("There is nothing here. Please choose another direction");
                                }
                        }
                        else{
                            System.out.println("Please enter a valid direction, which are north, south, east, west");
                        }
                    }
                    else{
                        System.out.println("You must add a direction with the command go");
                    }
                    break;

            case "inventory":
                    System.out.println(myInventory);
                    break;

                    case "take":
                    if(inputs.length == 1)
                    {
                        System.out.println("Please add item name with the command take");
                    }
                    else if(inputs.length == 2)
                    {
                        if(currLocation.hasItem(inputs[1]))
                        {                                
                            myInventory.addItem(currLocation.getItem(inputs[1]));
                            currLocation.removeItem(inputs[1]);
                            System.out.println("The item has been taken");
                        }
                        else{
                            System.out.println("Cannot find that item here");
                        }
                    }
                    else if(inputs.length == 3)
                    {
                        if(inputs[2].equals("from"))
                        {
                            System.out.println("Please mention the container item (in the command) from which the pre-mentioned item should be taken.");
                        }
                    }
                    else if(inputs[2].equals("from") && inputs.length == 4)
                    {
                        if(currLocation.hasItem(inputs[3]))
                        {                
                            if(currLocation.getItem(inputs[3]) instanceof ContainerItem)
                            {
                                ContainerItem curr = (ContainerItem) currLocation.getItem(inputs[3]);
                                if(curr.hasItem(inputs[1]))
                                {
                                    myInventory.addItem(curr.removeItem(inputs[1]));
                                    System.out.println("The item has been taken");
                                }
                                else
                                {
                                    System.out.println("This item does not exist");
                                }
                            }
                            else
                            {
                                System.out.println("Please choose an item from which the items contained in it are allowed to be taken");
                            }
                        }    
                    }
                    else
                    {
                        System.out.println("Invalid command. Type 'help' for instructions");
                   }
                    break;

            case "put": 
                    if(inputs.length != 4)
                    {
                        System.out.println("Invalid command. Type 'help' for instructions");
                    }
                    else if(inputs[2].equalsIgnoreCase("in") && inputs.length == 4)
                    {
                        if(myInventory.hasItem(inputs[1]))
                        {
                            if(currLocation.getItem(inputs[3]) instanceof ContainerItem)
                            {
                                ContainerItem currItem = (ContainerItem)currLocation.getItem(inputs[3]);
                                currItem.addItem(myInventory.removeItem(inputs[1]));
                                System.out.println("The item has been added");
                            }
                            else
                            {
                                System.out.println("Please choose a valid container item");
                            } 
                        }
                        else
                        {
                            System.out.println("Your inventory does not have this item");
                        }
                    }
                    break;

            case "drop":
                if(inputs.length > 1)
                {
                    if(myInventory.hasItem(inputs[1]))
                    {
                        Item removed = myInventory.removeItem(inputs[1]);
                        currLocation.addItem(removed);   
                        System.out.println("The item has been dropped");    
                    }
                    else
                    {
                        System.out.println("Cannot find that item in the inventory");
                    }        
                } 
                else
                {
                    System.out.println("You must add what item to drop from the inventory");
                }  
                break; 

            case "help":
                help();
                break;
                
            default:
            System.out.println("I dont know how to do that");
        break;
        }
    }
}

    public static void createWorld()
    {
        Location office = new Location("Office", "You are in the home's office right now. You see a table and some other items.");
        Location bedroom = new Location("Bedroom", "You are in the bedroom now. You can a see a bed and some other items.");
        Location store = new Location("Store", "There is a room with a lot of old ancient stuff.");
        Location road = new Location("Road", "This road is near a house. Enter the house.");
        
        office.connect("south", store);
        office.connect("west", bedroom);

        bedroom.connect("south", road);
        bedroom.connect("east", office);

        store.connect("north", office);
        store.connect("west", road);

        road.connect("north", bedroom);
        road.connect("east", store);

        ContainerItem table = new ContainerItem("Galyatable", "Furniture", "There is a wooden table in the corner of the office.");
        table.addItem(new Item("Bonsai", "Tree", "A 2 ft chinease tree placed at the corner of the table" ));
        table.addItem(new Item("Razer", "Keyboard", "Razer Cynosa Chroma Gaming Keyboard is placed in the middle of the table"));

        office.addItem(table);
        office.addItem(new Item("Laptop", "Computer", "This is a HP envy x360 with B&O speakers."));
        office.addItem(new Item("Mat", "Rug", "Hand woven mat in the center of the location."));
        office.addItem(new Item("Notes", "Stationery", "Notebook with OOSD Day 30 notes open on the table."));

        bedroom.addItem(new Item("Flag", "Decor", "It is a green and white Pakistan flag."));
        bedroom.addItem(new Item("Bed", "Furniture", "Basic dorm bed with weighted blanket."));
        bedroom.addItem(new Item("Poster", "Decor", "A larger printed picture of X-man."));
        bedroom.addItem(new Item("Shirt", "Clothing", "A multi-color polo shirt lying on the bed."));
       
        store.addItem(new Item("Screwdriver", "Tool", "There is a hammer lying in front of you"));
        store.addItem(new Item("Vinebottle", "Bottle", "There is a broken vine bottle on the floor with an encrypted message inside"));
        store.addItem(new Item("Vase", "Decor", "An 18th century vase approximately 16 inches tall and decorated with images of fishes"));
        store.addItem(new Item("Super Mario", "Game", "The first super mari game which was released in September,13,1985"));
       
        ContainerItem car = new ContainerItem("BMWi8", "vehicle", "A plug-in hybrid super sports car");
        car.addItem(new Item("Airwick", "Carfragnance", "Help enrich your car by bringing in your favorite fresh fragrances"));
        car.addItem(new Item("PatekPhilippe", "Watch", "A Patek Philippe Geneve watch in the dashboard can be seen from the side window"));


        road.addItem(car);
        road.addItem(new Item("Halalguys", "Food cart", "A hala desi food cart"));
        road.addItem(new Item("Wallnuttree", "tree", "There is a wallnut tree in the corner of the location, and people are gathered tehre"));
        currLocation = road;  
    }
}


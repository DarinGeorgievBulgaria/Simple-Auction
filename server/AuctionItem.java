public class AuctionItem implements java.io.Serializable {
    private int itemID;
    private String name;
    private String description;
    private int highestBid;

    public AuctionItem(int itemId, String name, String description, int reservePric) {
        this.itemID = itemId;
        this.name = name;
        this.description = description;
        this.highestBid = reservePric;
    }

    public AuctionItem() {
    }
    public int getItemID() {
        return itemID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setPrice(int price) {
        this.highestBid = price;
    }
    public int getHighestBid() {
        return highestBid;
    }
    
    
}


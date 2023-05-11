public class Bid {
    int userId;
    int itemId;
    int price;

    public Bid(int userID, int itemID, int price) {
        this.userId = userID;
        this.itemId = itemID;
        this.price = price;
    }

    public int getUserId(){
        return userId;
    }
    
    
    public int getitemId(){
        return itemId;
    }
   
    public int getPriceId(){
        return price;
    }
    public void setBid(int userId, int price) {
        this.userId = userId;
        this.price = price;
    }


}

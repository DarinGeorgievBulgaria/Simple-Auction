public class AuctionSaleItem implements java.io.Serializable {
    private String name;
    private String description;
    private int reservePrice;

    public AuctionSaleItem(String name, String description, int reservePrice) {
        this.name = name;
        this.description = description;
        this.reservePrice = reservePrice;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public int getReservePrice() {
        return reservePrice;
    }
    public void setReserve(int reserve){
        this.reservePrice = reserve;
    }
}

public class AuctionCloseInfo implements java.io.Serializable {
    private String winningEmail;
    private int winningPrice;

    public AuctionCloseInfo(String winningEmail, int winningPrice) {
        this.winningEmail = winningEmail;
        this.winningPrice = winningPrice;
    }

    public int getWinningPrice() {
        return winningPrice;
    }
    public String getWinningEmail() {
        return winningEmail;
    }
    
}

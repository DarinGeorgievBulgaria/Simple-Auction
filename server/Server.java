import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Auction {
    // list of users
    List<User> usersList = new ArrayList<>();
    // list of sale items
    List<AuctionSaleItem> saleItemsList = new ArrayList<>();
    // list of auctions
    Map<Integer, List<AuctionItem>> auctionItemsMap = new HashMap<>();
    // list of bids
    List<Bid> bidsList = new ArrayList<>();

    public Server() throws RemoteException {
    }

    /**
     * This method returns an item of type AuctionItem.
     * @param itemID - using that the correct item is found.
     */
    public AuctionItem getSpec(int itemID) throws RemoteException {
        AuctionItem item = null;
        for (List<AuctionItem> i : auctionItemsMap.values()) {
            for (AuctionItem ai : i) {
                if (ai.getItemID() == itemID) {
                    item = ai;
                }
            }
        }
        return item;
    }

    /**This function adds new users to the list called usersList.
     * User is an object taking email as constructor.
     * @param email - parameter of constructor of User
     */
    @Override
    public int newUser(String email) throws RemoteException {
        int wow = 0;
        User user = new User(email);
        usersList.add(user);
        int userId = usersList.indexOf(user);
        if(!auctionItemsMap.containsKey(userId)){
            auctionItemsMap.put(userId, new ArrayList<AuctionItem>());
            wow = userId;
        }

        
        return wow;
    }

    /**This function is to create a new auction
     * @param userID - integer for the ID of the user
     * @param item - this is an object of AuctionSale type
     */
    @Override
    public int newAuction(int userID, AuctionSaleItem item) throws RemoteException {
        saleItemsList.add(item);
        int itemId = saleItemsList.indexOf(item);
        AuctionItem newItem = new AuctionItem(itemId, item.getName(), item.getDescription(), item.getReservePrice());
        auctionItemsMap.get(userID).add(newItem);
        return itemId;
    }

    /**This method returns all the items from the list of type AuctionItem
     * converting them to array.
     */
    @Override
    public AuctionItem[] listItems() throws RemoteException {
        List<AuctionItem> returAuctionItems = new ArrayList<>();
        for (List<AuctionItem> i : auctionItemsMap.values()) {
            returAuctionItems.addAll(i);
        }
        return returAuctionItems.toArray(new AuctionItem[0]);
    }

    /**This method is to close an auction
     * @param userID - this is the ID of the user who initiates the closing action
     * @param itemID - this it the ID of the item the user wants to close
     */
    @Override
    public AuctionCloseInfo closeAuction(int userID, int itemID) throws RemoteException {
        AuctionItem removeItem = null;
        AuctionCloseInfo returnAuctionCloseInfo = null;
        if (auctionItemsMap.containsKey(userID)) {
            List<AuctionItem> list = auctionItemsMap.get(userID);
            for (AuctionItem auctionItem : list) {
                if (auctionItem.getItemID() == itemID) {
                    for (Bid bid : bidsList) {
                        if (bid.itemId == itemID) {
                            int userId = bid.userId;
                            String email = usersList.get(userId).getEmail();
                            returnAuctionCloseInfo = new AuctionCloseInfo(email, auctionItem.getHighestBid());
                            removeItem = auctionItem;
                        }
                    }
                }
            }
        }
        if (removeItem != null) {
            auctionItemsMap.get(userID).remove(removeItem);
        }
        return returnAuctionCloseInfo;
    }

    /**This method is used to place a bid.
     *
     * */ 
    @Override
    public boolean bid(int userID, int itemID, int price) throws RemoteException {
        for (List<AuctionItem> list : auctionItemsMap.values()) {
            for (AuctionItem auctionItem : list) {
                if (auctionItem.getItemID() == itemID) {
                    if (price > auctionItem.getHighestBid()) {
                        auctionItem.setPrice(price);
                        //updateBid(userID, itemID, price);
                        boolean newBid = true;
                        for (Bid bid : bidsList) {
                            if (bid.itemId == itemID) {
                            newBid = false;
                            bid.setBid(userID, price);
                            }
                        }
                        if (newBid) {
                        bidsList.add(new Bid(userID, itemID, price));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    

    public static void main(String[] args) {
        try {
            Server s = new Server();
            String name = "Auction";
            Auction stub = (Auction) UnicastRemoteObject.exportObject(s, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("The server is ready");
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }

    }
}

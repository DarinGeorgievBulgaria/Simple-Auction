//package client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client{
     public static void main(String[] args) {
       if (args.length < 1) {
       System.out.println("Usage: java Client n");
       return;
       }

         int n = Integer.parseInt(args[0]);
         try {
              String name = "Auction";
              Registry registry = LocateRegistry.getRegistry("localhost");
              Auction server = (Auction) registry.lookup(name);
              Scanner newScanner = new Scanner(System.in);
              System.out.println("Enter your email: ");
              String email =newScanner.nextLine();
              int newUserId = server.newUser(email);
              while(true){
                if(newUserId==0){
                  System.out.println("There is such email. Try again");
                  String emailAgain = newScanner.nextLine();
                  newUserId = server.newUser(emailAgain);
      
                }
                else if(newUserId !=0){
                  System.out.println("Your ID is "+ newUserId);
                  //newScanner.close();
                  break;
                }
              }
              while(true){
                System.out.println("If you would like to place a bid on an item press 1.");
                System.out.println("If you would like to create an auction press 2.");
                System.out.println("If you would like to close an auction press 3. ");
                System.out.println("If you would like to re-list the auctions press 4.");
                System.out.println("If you would like to exit press 5. ");

                String oneTwo = newScanner.nextLine();


                if(oneTwo.equals("1")){
                  System.out.println("Enter the ID of the item: ");
                  int newItemID = newScanner.nextInt();
                  System.out.println("Place your bet: ");
                  int newBid = newScanner.nextInt();
                  boolean bidResult = server.bid(newUserId, newItemID, newBid);
                    if(bidResult ==true){
                      System.out.println("Bid is placed.");
                    }
                    else if(bidResult ==false){
                      System.out.println("Either the new bid is lower than the new price or there is no item with such ID");
                    }
                }
                else if(oneTwo.equals("2")){
                  System.out.println("Enter the name of the item: ");
                  String newAuctionName = newScanner.nextLine();
                  System.out.println("Enter description: ");
                  String newAuctionDescription = newScanner.nextLine();
                  System.out.println("Enter reserve: ");
                  int newAuctionReserve = newScanner.nextInt();
    
                  AuctionSaleItem auctionSaleItem = new AuctionSaleItem(newAuctionName,newAuctionDescription, newAuctionReserve);
                  int newAuctionID = server.newAuction(newUserId, auctionSaleItem);
                  System.out.println("The ID of your auction is: "+ newAuctionID);
    
                }
                else if(oneTwo.equals("3")){
                  System.out.println("Enter the ID of the auction you want to close: ");
                  int newCloseID = newScanner.nextInt();
                  AuctionCloseInfo auctionClose = server.closeAuction(newUserId, newCloseID);
                  if(auctionClose==null){
                    System.out.println("Something went wrong. Double check.");
                  }
                  System.out.println("The winner is: "+ auctionClose.getWinningEmail() +" the price of: "+ auctionClose.getWinningPrice());
                }
                else if(oneTwo.equals("4")){
                  AuctionItem[] returnedAuctionItems = server.listItems();
                  for(AuctionItem auctionItem: returnedAuctionItems){
                    System.out.println("Item Number: "+auctionItem.getItemID()
                    +" Product Name: "+ auctionItem.getName()
                    +" Product Description: "+ auctionItem.getDescription()
                    +" Highest bid: "+auctionItem.getHighestBid());
                  }
                }
                else if(oneTwo.equals("5")){
                  System.out.println("Goodbye");
                  break;
                }
              }


              /*
               * AuctionItem result = server.getSpec(n);
                System.out.println("Item ID: "+result.getItemID()+", "
                +"Product Name: "+result.getName()+", "
                +"Description: "+result.getDescription());
               */
               
              }
              catch (Exception e) {
               System.err.println("Exception:");
               e.printStackTrace();
               }
      }
}
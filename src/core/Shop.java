package core;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Shop {
    private HashMap<String, Integer> items;
    private boolean shopOpen = false; // Determines if shop is active

    public Shop() {
        items = new HashMap<>();
        items.put("T. Skip Turn (Common)", 50);
        items.put("E. Force Draw (Uncommon)", 100);
        items.put("B. Elimination Block (Rare)", 200);
        items.put(" D. Double EXP Next Round (Legendary)", 500);
    }

    public void openShop() {
        shopOpen = true;
        System.out.println("\n--- SHOP IS OPEN! ---");
    }

    public void closeShop() {
        shopOpen = false;
    }

    public boolean isShopOpen() {
        return shopOpen;
    }
    public void showItems() {
        System.out.println("\n--- Available Shop Items ---");
        for (String item : items.keySet()) {
            System.out.println(item + " - Cost: " + items.get(item) + " EXP");
        }
    }

    public void purchaseItem(Player player, String itemName) {
        if (items.containsKey(itemName) && player.getExp() >= items.get(itemName)) {
            player.spendExp(items.get(itemName));
            player.setUpgrade(itemName, true); // Activate upgrade
            System.out.println(player.getName() + " purchased " + itemName + "!");
        } else {
            System.out.println("Not enough EXP or invalid item.");
        }
    }




    public void render(Graphics g) {
        if (shopOpen) {
            g.setColor(Color.blue);
            g.fillRect(300, 200, 400, 300); // Shop background
            g.setColor(Color.white);
            g.drawString("SHOP MENU", 400, 220);

            int yOffset = 250;
            for (String item : items.keySet()) {
                g.drawString(item + " - Cost: " + items.get(item) + " EXP", 320, yOffset);
                yOffset += 40;
            }

            g.drawString("Press 'B' to Buy, 'C' to Close", 320, yOffset + 40);
        }
    }

}

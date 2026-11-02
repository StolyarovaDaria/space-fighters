package core;

import entities.Player;

public class UpgradeManager {

    private int coins = 0;

    public void addCoins(int amount) {
        coins += amount;
    }

    public void reset() {
        coins = 0;
    }

    public boolean buyUpgrade(int type, Player player) {
        int cost = 0;
        if (type == 1) cost = 50;
        else if (type == 2) cost = 100;
        else if (type == 3) cost = 75;

        if (coins >= cost) {
            coins -= cost;
            if (type == 1) player.upgradeMaxHp(20);
            if (type == 2) player.upgradeDamage(5);
            if (type == 3) player.upgradeSpeed(0.5);
            return true;
        }
        return false;
    }

    public int getCoins() { return coins; }
}

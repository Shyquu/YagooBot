package de.koo.api;

import de.koo.other.CoinType;
import de.koo.other.SQLite;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shyqu
 * @created 30/03/2021 - 21:12
 * @project HololiveBot
 */
public class CoinSystem {

    public Integer getCoins(String userid, CoinType coinType) {
        if(coinType == CoinType.BANK) {
            ResultSet set = SQLite.onQuery("SELECT bank FROM coinsystem WHERE userid = '" + userid + "' ORDER BY id DESC LIMIT 1");
            try {
                assert set != null;
                if (set.next()) {
                    return set.getInt("bank");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
        if(coinType == CoinType.WALLET) {
            ResultSet set = SQLite.onQuery("SELECT wallet FROM coinsystem WHERE userid = '" + userid + "' ORDER BY id DESC LIMIT 1");
            try {
                assert set != null;
                if (set.next()) {
                    return set.getInt("wallet");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addCoins(int coins, String userid, CoinType coinType) {
        if(coinType == CoinType.BANK) {

        } else
        if(coinType == CoinType.WALLET) {

        }
    }

}

package cnu.org.paycut;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.realm.Realm;

/**
 * Created by corona10 on 2016. 5. 16..
 */
public class CostManager {
  private Realm realm;
  private Context context;

  public CostManager(Context ctx)
  {
    realm = Realm.getInstance(ctx);
    context = ctx;
  }

  public int getMaxCost()
  {
    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
    int maxCost = Integer.parseInt(p.getString("maxCost","0"));
    return maxCost;
  }

  public boolean insertLog(SmsLog log)
  {
    try{
      realm.beginTransaction();
      realm.copyToRealm(log);
    }catch(Exception e)
    {
      return false;
    }finally {
      realm.commitTransaction();
    }
    return true;
  }

  public int getCurrentCost()
  {
    realm.beginTransaction();
    Number totalprice = realm.where(SmsLog.class).sum("price");
    realm.commitTransaction();
    return totalprice.intValue();
  }
}

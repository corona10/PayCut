package cnu.org.paycut;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by corona10 on 2016. 5. 1..
 */
public class SmsLog extends RealmObject{
  private float price;
  private String store;
  private String date;
  private boolean isSent;
  @Ignore
  private boolean isValid;

  public SmsLog()
  {
    isValid = false;
  }

  public SmsLog(String phonenumber, String contents)
  {

  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setIsSent(boolean isSent) {
    this.isSent = isSent;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setStore(String store) {
    this.store = store;
  }

  public String getDate() {
    return date;
  }

  public boolean isSent() {
    return isSent;
  }

  public float getPrice() {
    return price;
  }

  public String getStore() {
    return store;
  }
}

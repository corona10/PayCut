package cnu.org.paycut;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  private ArrayAdapter<String> m_Adapter;
  private TextView m_maxCostTextview;
  private TextView m_currentCostTextView;
  private Realm realm;

  private CostManager costManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d("Activity", "Activity Created..");
    realm = Realm.getInstance(getApplicationContext());

    listView = (ListView)findViewById(R.id.menu_list);
    m_maxCostTextview = (TextView)findViewById(R.id.status_text);
    m_currentCostTextView = (TextView)findViewById(R.id.current_cost);

    m_maxCostTextview.setText(getMaxCost() + "원");
    m_currentCostTextView.setText(getCurrentCost()+"원");

    m_Adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
    listView.setAdapter(m_Adapter);
    ArrayList<String> menus = new ArrayList<String>();
    m_Adapter.add("그래프1");
    m_Adapter.add("그래프2");
    m_Adapter.add("그래프3");
  }

  public void onPause()
  {
    super.onPause();
    m_maxCostTextview.setText(getMaxCost() + "원");
    m_currentCostTextView.setText(getCurrentCost()+"원");
  }

  public void onResume()
  {
    super.onResume();
    m_maxCostTextview.setText(getMaxCost() + "원");
    m_currentCostTextView.setText(getCurrentCost()+"원");
  }
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {

    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private int getCurrentCost()
  {
    realm.beginTransaction();
    Number totalprice = realm.where(SmsLog.class).sum("price");
    realm.commitTransaction();
    return totalprice.intValue();
  }

  private int getMaxCost()
  {
    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
    int maxCost = Integer.parseInt(p.getString("maxCost","0"));
    return maxCost;
  }
}

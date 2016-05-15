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

public class MainActivity extends AppCompatActivity {

  private ListView listView;
  private ArrayAdapter<String> m_Adapter;
  private TextView m_textView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d("Activity", "Activity Created..");
    listView = (ListView)findViewById(R.id.menu_list);
    m_textView = (TextView)findViewById(R.id.status_text);
    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
    String maxCost = p.getString("maxCost","0");
    m_textView.setText(maxCost+"원");

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
    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
    String maxCost = p.getString("maxCost","0");
    m_textView.setText(maxCost + "원");
  }

  public void onResume()
  {
    super.onResume();
    SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
    String maxCost = p.getString("maxCost","0");
    m_textView.setText(maxCost + "원");
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
}

package vn.edu.usth.weather;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import vn.edu.usth.weather.adapter.WeatherPagerAdapter;

public class WeatherActivity extends AppCompatActivity {
    static final String TAG = "Weather";
    final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
        // This method is executed in main thread
            String content = msg.getData().getString("server_response");
            Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
        }
    };

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.weather_view_pager);

        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        mediaPlayer = MediaPlayer.create(this, R.raw.vtv_forecast);
        mediaPlayer.start();
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // this method is run in a worker thread
                try {
                    // wait for 5 seconds to simulate a long network access
                    Thread.sleep(5000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Assume that we got our data from server
                Bundle bundle = new Bundle();
                bundle.putString("server_response", "some sample json here");
                // notify main thread
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_refresh:
                Toast.makeText(this, getString(R.string.refresh), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, PrefActivity.class);
                startActivity(intent);
                return  true;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

}
package newapp.com.example.dipanshugupta.truecar;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    public Button button;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ActionBar ab=getSupportActionBar();
        //ab.setDisplayShowHomeEnabled(true);


        toolbar=(Toolbar) findViewById(R.id.app_bar) ;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        button=(Button) findViewById(R.id.button_map);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent("newapp.com.example.dipanshugupta.truecar.MainActivity");
                        startActivity(intent);
                    }
                }

        );
        NavigationDrawerFragment drawerFragment=(NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.alert:
                Toast.makeText(getApplicationContext(),"notification is selected",Toast.LENGTH_SHORT).show();
            case R.id.star:
                Toast.makeText(getApplicationContext(),"feedback is selected",Toast.LENGTH_SHORT).show();
            case R.id.Bulletin:
                Toast.makeText(getApplicationContext(),"Bulletin is selected",Toast.LENGTH_SHORT).show();
            case R.id.Terms:
                Toast.makeText(getApplicationContext(),"Terms is selected",Toast.LENGTH_SHORT).show();
            case R.id.recommend:
                Toast.makeText(getApplicationContext(),"recommendation is selected",Toast.LENGTH_SHORT).show();
            case R.id.share:
                Toast.makeText(getApplicationContext(),"share is selected",Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}

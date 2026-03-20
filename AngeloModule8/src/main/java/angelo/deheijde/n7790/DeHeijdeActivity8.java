//Angelo De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DeHeijdeActivity8 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deheijde);

        Toolbar toolbar = findViewById(R.id.angToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.angDrawerLayout);
        NavigationView navigationView = findViewById(R.id.angNavigationView);
        contentTextView = findViewById(R.id.angTextView);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
            contentTextView.setText(R.string.menu_home);
        }

        // Fix for deprecated onBackPressed()
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                    setEnabled(true);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            contentTextView.setText(R.string.menu_home);
        } else if (id == R.id.nav_settings) {
            contentTextView.setText(R.string.menu_settings);
        } else if (id == R.id.nav_share) {
            contentTextView.setText(R.string.menu_share);
        } else if (id == R.id.nav_about) {
            contentTextView.setText(R.string.menu_about);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            contentTextView.setText(R.string.menu_logout);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

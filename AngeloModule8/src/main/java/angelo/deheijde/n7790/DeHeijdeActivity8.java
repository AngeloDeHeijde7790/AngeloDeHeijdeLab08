//Angelo De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
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

        // Handle user clicks on Android Back Key
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    // 43.a: If the sliding window is open, close the window.
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    // 43.b: If sliding window already closed, display AlertDialog
                    showExitAlertDialog();
                }
            }
        });
    }

    // 43.b.i-vi & 44: AlertDialog implementation
    private void showExitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher_round); // 43.b.i: Icon (not default)
        builder.setTitle("Angelo De Heijde Diaz"); // 43.b.ii: Title (Full Name)
        builder.setMessage("Do you want to exit the app ?"); // 43.b.iii: Message
        
        // 43.b.iv: Yes action
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Launch YouTube app with a specific video
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:dQw4w9WgXcQ"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    // Fallback to web browser if YouTube app is not installed
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                    startActivity(intent);
                }
                finish(); // Optional: Close the app after launching YouTube
            }
        });

        // 43.b.v: No action
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Stays in the app
            }
        });

        // 43.b.vi: User must not be able to dismiss without answering Yes or No
        builder.setCancelable(false);

        builder.create().show();
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
            // 44: If user clicks Logout, display the same AlertDialog
            showExitAlertDialog();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

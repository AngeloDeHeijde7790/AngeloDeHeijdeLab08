// Angelo De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutUsFragment extends Fragment {

    private static int counter = 0;
    private TextView angAboutData;
    private ToggleButton angAboutToggle;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        angAboutData = view.findViewById(R.id.angAboutData);
        angAboutToggle = view.findViewById(R.id.angAboutToggle);

        // 53.g & h: Toggle button to lock device orientation
        angAboutToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // 53.g: If ON, lock to portrait
                requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                // 53.h: If OFF, set to auto orientation (SENSOR)
                requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 53.b: Display counter every time the user access this screen
        counter++;
        String toastMessage = "Counter: " + counter + ", Angelo De Heijde Diaz";
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show();

        // 53.e & f: Display data from SharedPreferences
        displaySharedPrefsData();
    }

    private void displaySharedPrefsData() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AngeloPrefs", Context.MODE_PRIVATE);

        // Check if data exists by checking for a key that should be there
        if (sharedPreferences.contains("user_email")) {
            boolean isChecked = sharedPreferences.getBoolean("checkbox_state", false);
            String email = sharedPreferences.getString("user_email", "");
            int id = sharedPreferences.getInt("user_id", 0);

            String displayText = "CheckboxChecked: " + isChecked + "\n" +
                                 "Email: " + email + "\n" +
                                 "ID: " + id;
            angAboutData.setText(displayText);
        } else {
            // 53.f: If no data, show "NO DATA"
            angAboutData.setText("NO DATA");
        }
    }
}

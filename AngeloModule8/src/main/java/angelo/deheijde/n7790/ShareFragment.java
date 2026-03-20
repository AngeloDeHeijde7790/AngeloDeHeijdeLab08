// Angelo Noel De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ShareFragment extends Fragment {

    private CheckBox angShareCheckbox;
    private EditText angShareEmail;
    private EditText angShareId;
    private ImageButton angShareImageButton;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        // Initialize views
        angShareCheckbox = view.findViewById(R.id.angShareCheckbox);
        angShareEmail = view.findViewById(R.id.angShareEmail);
        angShareId = view.findViewById(R.id.angShareId);
        angShareImageButton = view.findViewById(R.id.angShareImageButton);

        // j. When user clicks on the button
        angShareImageButton.setOnClickListener(v -> validateAndSave());

        return view;
    }

    private void validateAndSave() {
        String email = angShareEmail.getText().toString().trim();
        String idStr = angShareId.getText().toString().trim();
        boolean isChecked = angShareCheckbox.isChecked();

        boolean isValid = true;

        // j.i. Validate valid email
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            angShareEmail.setError("Invalid email format (e.g., abc@abc.com)");
            isValid = false;
        }

        // j.ii. Validate minimum of 6 digits in the ID field
        if (idStr.length() < 6) {
            angShareId.setError("ID must be at least 6 digits");
            isValid = false;
        }

        // j.iv. If valid input
        if (isValid) {
            int id = Integer.parseInt(idStr);

            // 1 & 2. Save using SharedPreference
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("AngeloPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("checkbox_state", isChecked);
            editor.putString("user_email", email);
            editor.putInt("user_id", id);
            editor.apply();

            // 3 & 4. Display user info on a snackbar
            String snackbarText = "checkbox: " + isChecked + ",\nemail: " + email + ",\nid: " + id;
            
            Snackbar snackbar = Snackbar.make(requireView(), snackbarText, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Dismiss", v -> snackbar.dismiss());
            
            // 5. Have the text on two lines or more (using \n above)
            snackbar.show();

            // Clear all fields
            clearFields();
        }
    }

    private void clearFields() {
        angShareCheckbox.setChecked(false);
        angShareEmail.setText("");
        angShareId.setText("");
        angShareEmail.setError(null);
        angShareId.setError(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 52.b: Everytime user access this screen, display AlertDialog
        showShareAlertDialog();
    }

    private void showShareAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        
        builder.setIcon(R.drawable.ic_share);
        builder.setTitle("Angelo De Heijde Diaz");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String currentTime = sdf.format(new Date());
        builder.setMessage("Date and GMT Time: " + currentTime);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        
        dialog.setOnShowListener(d -> {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#90EE90")));
        });

        dialog.show();
    }
}

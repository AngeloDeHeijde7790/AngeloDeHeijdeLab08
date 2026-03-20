// Angelo Noel De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> pickMediaLauncher;
    private ImageView imageView;
    private int denialCount = 0; // Track the number of denials

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the permission launcher
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    boolean allGranted = true;
                    String deniedPermission = "";
                    for (String key : isGranted.keySet()) {
                        if (Boolean.FALSE.equals(isGranted.get(key))) {
                            allGranted = false;
                            deniedPermission = key;
                            break;
                        }
                    }

                    if (allGranted) {
                        denialCount = 0; // Reset on success
                        Toast.makeText(getContext(), "permission allowed", Toast.LENGTH_SHORT).show();
                        openGallery();
                    } else {
                        denialCount++; // Increment denial count
                        
                        String apiInfo = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                ? "API 33 and higher" : "API 32 and lower";
                        Toast.makeText(getContext(), "permission denied. " + apiInfo, Toast.LENGTH_SHORT).show();

                        // 50 & 51: Show dialog only after two denials
                        if (denialCount >= 2) {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), deniedPermission)) {
                                showSettingsDialog();
                            }
                        }
                    }
                });

        // Initialize the media picker launcher
        pickMediaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null && imageView != null) {
                            imageView.setImageURI(selectedImageUri);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.angSettingsImageView);
        Button btnAccessPhotos = view.findViewById(R.id.angSettingsButton);

        btnAccessPhotos.setOnClickListener(v -> {
            checkPermissionsAndOpenGallery();
        });
    }

    private void checkPermissionsAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            boolean hasImages = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
            boolean hasVideo = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;

            if (hasImages && hasVideo) {
                openGallery();
            } else {
                requestPermissionLauncher.launch(new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                });
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        }
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Permission Required")
                .setMessage("This permission is needed to access your photos. Please enable it in the app settings.")
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    openAppSettings();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/* video/*");
        pickMediaLauncher.launch(intent);
    }
}

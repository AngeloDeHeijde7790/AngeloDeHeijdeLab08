// Angelo Noel De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private int clickCount = 0;
    private int[] drawables = {
            R.drawable.emperor,
            R.drawable.galaxia3,
            R.drawable.galaxia4,
            R.drawable.warhammer
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 47.a: Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.angHomeImageView);
        Button button = view.findViewById(R.id.angHomeButton);

        // 47.f: Handle button clicks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                // 47.f.i: Rotate between 4 different images
                int imageIndex = (clickCount - 1) % drawables.length;
                imageView.setImageResource(drawables[imageIndex]);

                // 47.f.ii: Display a snackbar with short duration
                String message = "Angelo De Heijde Diaz - " + clickCount;
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

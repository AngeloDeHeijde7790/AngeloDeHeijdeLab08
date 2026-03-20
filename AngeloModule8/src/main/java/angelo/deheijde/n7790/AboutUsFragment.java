// Angelo De Heijde Diaz - N01727790
package angelo.deheijde.n7790;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutUsFragment extends Fragment {

    private static int counter = 0;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 53.b: Display counter every time the user access this screen
        counter++;
        String message = "Counter: " + counter + " - Angelo De Heijde Diaz";
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}

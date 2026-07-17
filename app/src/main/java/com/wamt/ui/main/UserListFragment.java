package com.wamt.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wamt.R;
import com.wamt.databinding.FragmentMainPageBinding;
import com.wamt.ui.main.user.CreateUserFragment;

public class UserListFragment extends Fragment {

 private FragmentMainPageBinding binding;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.createUserButton.setEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * Liaison entre la classe Java et le fichier xml*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.createUserButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_page, CreateUserFragment.newInstance())
                    .addToBackStack(null) //Revenir sur le fragment précédent plutôt que fermer l'app
                    .commit();
        });
    }
}

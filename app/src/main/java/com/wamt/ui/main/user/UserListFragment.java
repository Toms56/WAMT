package com.wamt.ui.main.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wamt.R;
import com.wamt.data.model.User;
import com.wamt.databinding.FragmentMainPageBinding;
import com.wamt.ui.main.user.create.CreateUserFragment;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserListFragment extends Fragment {

    private FragmentMainPageBinding binding;
    private UserViewModel userViewModel;

    private UserAdapter userAdapter;

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

        // requireActivity() : même instance que celle utilisée dans
        // CreateUserFragment, pour que les deux fragments partagent le même UserViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        userAdapter = new UserAdapter(); //Sera rempli au fur et a mesure que Room donnera des users à afficher
        binding.userPseudoRecyclerView.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        );
        binding.userPseudoRecyclerView.setAdapter(userAdapter); //Association de l'adapter au RecyclerView

        binding.createUserButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_main_page, CreateUserFragment.newInstance())
                    .addToBackStack(null) //Revenir sur le fragment précédent plutôt que fermer l'app
                    .commit();
        });

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), this::updateUi);
    }

    private void updateUi(List<User> users){
        if(users == null || users.isEmpty()){
            binding.userPseudoRecyclerView.setVisibility(View.GONE);
            return;
        }

        binding.userPseudoRecyclerView.setVisibility(View.VISIBLE);
        userAdapter.setUsers(users);

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        // Evite les fuites mémoire liées au binding
        binding = null;
    }
}

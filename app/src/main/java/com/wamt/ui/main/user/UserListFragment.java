package com.wamt.ui.main.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.wamt.R;
import com.wamt.data.model.User;
import com.wamt.databinding.FragmentMainPageBinding;
import com.wamt.ui.main.user.create.CreateUserFragment;
import com.wamt.ui.main.user.update.UpdateUserFragment;

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
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);


        // requireActivity() : même instance que celle utilisée dans
        // CreateUserFragment, pour que les deux fragments partagent le même UserViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Création de l'adapter, en lui passant une lambda qui implémente
        // l'interface OnUserEditListener (méthode onEditUser(User user))
        // -> cette lambda sera exécutée à chaque clic sur le crayon d'un item
        userAdapter = new UserAdapter(user-> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_page, UpdateUserFragment.newInstance(user.getId()))
                .addToBackStack(null)
                .commit()); //Sera rempli au fur et a mesure que Room donnera des users à afficher
        binding.userPseudoRecyclerView.setLayoutManager(layoutManager);
        binding.userPseudoRecyclerView.setAdapter(userAdapter); //Association de l'adapter au RecyclerView

        binding.createUserButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_page, CreateUserFragment.newInstance())
                .addToBackStack(null) //Revenir sur le fragment précédent plutôt que fermer l'app
                .commit());

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), this::updateUi);
        userViewModel.getUserCount().observe(getViewLifecycleOwner(), this::updateCreateUserButtonState);
    }

    private void updateUi(List<User> users){
        if(users == null || users.isEmpty()){
            binding.userPseudoRecyclerView.setVisibility(View.GONE);
            return;
        }

        binding.userPseudoRecyclerView.setVisibility(View.VISIBLE);
        userAdapter.setUsers(users);

    }

    private void updateCreateUserButtonState(Integer userCount) {
        if (userCount != null && userCount < 6) {
            binding.createUserButton.setEnabled(true);
        } else {
            binding.createUserButton.setEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        // Evite les fuites mémoire liées au binding
        binding = null;
    }
}

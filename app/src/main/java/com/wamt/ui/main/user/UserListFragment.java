package com.wamt.ui.main.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.wamt.ui.home.HomePageFragment;
import com.wamt.ui.main.user.create.CreateUserFragment;
import com.wamt.ui.main.user.update.UpdateUserFragment;
import android.app.AlertDialog;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserListFragment extends Fragment {

    private FragmentMainPageBinding binding;
    private UserViewModel userViewModel;

    private UserAdapter userAdapter;

    private boolean setAsDefaultUserOnNextSelection = false;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(requireContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);


        // requireActivity() : même instance que celle utilisée dans
        // CreateUserFragment, pour que les deux fragments partagent le même UserViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Création de l'adapter, en lui passant deux actions :
        // edition ou suppression d'un utilisateur
        userAdapter = new UserAdapter(
                //Clic sur le crayon d'un item : ecran de modification
                user-> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_page, UpdateUserFragment.newInstance(user.getId()))
                .addToBackStack(null)
                .commit(),

                //Definition de comportement stockée dans UserAdapter au moment du click
                //Implémentation de l'interface OnUserDeleteListener de l'Adapter
                //user --> Objet User attendu,
                //userViewModel.deleteUser(user) sera executé
                //setDeleteMode sera executé pour masquer le bouton de suppression

                this::showDeleteConfirmationDialog,

                //TODO Modifier selon la gestion des userPrefs
                user->{
                    if(setAsDefaultUserOnNextSelection){
                        userViewModel.setDefaultUser(user.getId());
                    }
                    userViewModel.selectUser(user);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_main_page, HomePageFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                }

        );
        binding.userPseudoRecyclerView.setLayoutManager(layoutManager);
        binding.userPseudoRecyclerView.setAdapter(userAdapter); //Association de l'adapter au RecyclerView

        binding.createUserButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_page, CreateUserFragment.newInstance())
                .addToBackStack(null) //Revenir sur le fragment précédent plutôt que fermer l'app
                .commit());


        //Activation du mode suppression si un utilisateur existe
        binding.deleteUserTextView.setOnClickListener(v-> {
            if(userAdapter != null && userAdapter.hasUsers()) {
                userAdapter.toogleDeleteMode();
                if(userAdapter.deleteMode){
                    binding.deleteUserTextView.setText(getString(R.string.cancel_delete_user));
                }else{
                    binding.deleteUserTextView.setText(getString(R.string.delete_user));
                }
            }
        });

        binding.setDefaultProfileCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setAsDefaultUserOnNextSelection = isChecked;
            Log.d("UserListFragment", "Checkbox state changed: " + isChecked);
        });

        userViewModel.getAllUsers().observe(getViewLifecycleOwner(), this::updateUi);
        userViewModel.getUserCount().observe(getViewLifecycleOwner(), this::updateCreateUserButtonState);
    }

    private void updateUi(List<User> users){

        //Si la liste est vide, on masque ce qui concerne les utilisateurs
        if(users == null || users.isEmpty()){
            binding.userPseudoRecyclerView.setVisibility(View.GONE);

            binding.deleteUserTextView.setVisibility(View.GONE);

            if(userAdapter != null) {
                userAdapter.setDeleteMode(false);
            }
            return;
        }

        binding.userPseudoRecyclerView.setVisibility(View.VISIBLE);
        binding.deleteUserTextView.setVisibility(View.VISIBLE);
        userAdapter.setUsers(users);

    }

    private void showDeleteConfirmationDialog(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Supprimer l'utilisateur");
        builder.setMessage("Êtes-vous sûr ? Cette action est irréversible.");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userViewModel.deleteUser(user);
                binding.deleteUserTextView.setText(getString(R.string.delete_user));
                if (userAdapter != null) {
                    userAdapter.setDeleteMode(false);
                }
            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                binding.deleteUserTextView.setText(getString(R.string.delete_user));
                userAdapter.setDeleteMode(false);
            }
        });

        builder.show();
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

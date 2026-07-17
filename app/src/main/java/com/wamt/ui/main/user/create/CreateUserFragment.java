package com.wamt.ui.main.user.create;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wamt.data.model.User;
import com.wamt.databinding.FragmentCreateUserBinding;
import com.wamt.ui.main.user.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateUserFragment extends Fragment {

    private FragmentCreateUserBinding binding;
    private CreateUserViewModel createUserViewModel;
    private UserViewModel userViewModel;


    public static CreateUserFragment newInstance() {
        return new CreateUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createUserViewModel = new ViewModelProvider(this).get(CreateUserViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        binding.closeButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        binding.usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                createUserViewModel.setUsername(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        binding.createUserButton.setOnClickListener(v -> {
            String pseudo = binding.usernameEditText.getText().toString();
            userViewModel.insertUser(new User(0, pseudo)); //Convention Room, l'id est généré automatiquement, donc on peut mettre 0 ou null
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //getViewLifecycleOwner() permet de lier l'observation du LiveData au cycle de vie du fragment, évitant ainsi les fuites de mémoire.
        createUserViewModel.getIsUsernameValid().observe(getViewLifecycleOwner(), isValid ->
            binding.createUserButton.setEnabled(isValid));
    }
}

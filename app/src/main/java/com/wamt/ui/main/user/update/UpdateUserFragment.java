package com.wamt.ui.main.user.update;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wamt.data.model.User;
import com.wamt.databinding.FragmentUpdateUserBinding;
import com.wamt.ui.main.user.UserViewModel;

public class UpdateUserFragment extends Fragment {


    private FragmentUpdateUserBinding binding;

    private UpdateUserViewModel updateUserViewModel;
    private UserViewModel userViewModel;

    public static UpdateUserFragment newInstance(long userId) {
        UpdateUserFragment fragment = new UpdateUserFragment();
        Bundle args = new Bundle();
        args.putLong("user_id", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateUserViewModel = new ViewModelProvider(this).get(UpdateUserViewModel.class);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        long userId = requireArguments().getLong("user_id");

        binding.closeButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());

        binding.usernameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateUserViewModel.setUsername(s.toString());
            }
        });

        //Pré-remplissage du champ prenant le pseudo actuel
        userViewModel.getUserById(userId).observe(getViewLifecycleOwner(), user -> {
            if (user != null && !updateUserViewModel.isInitialized()) {
                binding.usernameEditText.setText(user.getPseudo());
                updateUserViewModel.markInitialized();
            }
        });

        updateUserViewModel.getIsUsernameValid().observe(getViewLifecycleOwner(), isValid -> {
            if (isValid != null) {
                binding.updateUserButton.setEnabled(isValid);
            }
        });

        binding.updateUserButton.setOnClickListener(v -> {
            String newPseudo = binding.usernameEditText.getText().toString().trim();
            userViewModel.upsertUser(new User(userId, newPseudo));
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }


}
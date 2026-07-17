package com.wamt.ui.main.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wamt.databinding.FragmentCreateUserBinding;

public class CreateUserFragment extends Fragment {

    private FragmentCreateUserBinding binding;

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

        binding.closeButton.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().popBackStack());
    }
}

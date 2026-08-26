package com.wamt;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.wamt.data.WamtDatabase;
import com.wamt.databinding.ActivityMainBinding;
import com.wamt.ui.main.user.UserViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ImageView topBarAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Todo A supprimer pour ne pas vider la base de données à chaque lancement de l'application
        WamtDatabase db = WamtDatabase.getDatabase(getApplicationContext());
        new Thread(db::clearAllTables).start();

        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        topBarAvatar = findViewById(R.id.topBarAvatar);
        topBarAvatar.setVisibility(View.GONE);

        userViewModel.getSelectedUser().observe(this, user -> {
            if (user != null) {
                //TODO checker une fois que l'avatar ne sera plus un placehodler
                topBarAvatar.setVisibility(View.VISIBLE);
            } else {
                topBarAvatar.setVisibility(View.GONE);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingLeft(), bars.top, v.getPaddingRight(), bars.bottom);
            return insets;
        });

    }
}

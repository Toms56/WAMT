package com.wamt.ui.main.user;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexboxLayoutManager;
import com.wamt.R;
import com.wamt.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * L'Adapter fait le lien entre :
 * <p>
 * - La liste d'utilisateurs venant de Room
 * - Les éléments graphiques affichés dans le RecyclerView
 * <p>
 * Objectifs :
 * 1. Créer une ligne d'affichage (ViewHolder)
 * 2. Remplir cette ligne avec les données d'un utilisateur
 * 3. Indiquer combien d'éléments doivent être affichés
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface OnUserEditListener {
        void onEditUser(User user);
    }

    // Callback appelé quand on clique sur le bouton de suppression
    public interface OnUserDeleteListener {
        void onDeleteUser(User user);
    }
    private final OnUserEditListener editListener;
    private final OnUserDeleteListener deleteListener;

    // Liste des utilisateurs à afficher
    private List<User> users = new ArrayList<>();

    private boolean deleteMode = false;

    /**
     * Constructeur vide.
     * <p>
     * Le Fragment crée l'adapter au démarrage.
     */
    public UserAdapter(OnUserEditListener editListener, OnUserDeleteListener deleteListener) {
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public boolean hasUsers(){
        return !users.isEmpty();
    }

    //Activation/Desactivation du mode suppression
    @SuppressLint("NotifyDataSetChanged")
    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        notifyDataSetChanged();
    }

    public void toogleDeleteMode(){
        setDeleteMode(!deleteMode);
    }


    /**
     * Reçoit la nouvelle liste depuis le Fragment.
     * <p>
     * Exemple :
     * [Alice, Bob, Charlie]
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setUsers(List<User> users) {

        this.users = users;

        // Force le RecyclerView à se rafraîchir
        notifyDataSetChanged();
    }


    /**
     * Crée une nouvelle ligne.
     * <p>
     * Ici on charge item_user.xml.
     */
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_user,
                        parent,
                        false
                );


        return new UserViewHolder(view);
    }


    /**
     * Remplit une ligne avec les données d'un utilisateur.
     * <p>
     * position correspond à l'utilisateur dans la liste.
     */
    @Override
    public void onBindViewHolder(
            @NonNull UserViewHolder holder,
            int position) {


        User user = users.get(position);
        Log.d(
                "ADAPTER",
                "Affichage : " + user.getPseudo()
        );

        // Affiche le pseudo dans la ligne
        holder.pseudoTextView.setText(user.getPseudo());

        if (deleteMode) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.GONE);
        }


        //Ouverture de la modification
        holder.editButton.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEditUser(user);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDeleteUser(user);
            }
        });


        // Force chaque item à occuper 1/3 de la largeur du RecyclerView
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams flexParams) {
            flexParams.setFlexBasisPercent(0.33f);
            holder.itemView.setLayoutParams(flexParams);
        }
    }


    /**
     * Nombre de lignes à afficher.
     */
    @Override
    public int getItemCount() {

        return users.size();
    }



    /**
     * Représente une ligne du RecyclerView.
     */
    static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView pseudoTextView;
        ImageButton editButton;
        ImageButton deleteButton;

        public UserViewHolder(@NonNull View itemView) {

            super(itemView);

            // Récupération du TextView dans item_user.xml
            pseudoTextView = itemView.findViewById(
                    R.id.itemUserPseudo
            );
            editButton = itemView.findViewById(R.id.itemUserEditButton);
            deleteButton = itemView.findViewById(R.id.itemUserDeleteButton);
        }
    }
}

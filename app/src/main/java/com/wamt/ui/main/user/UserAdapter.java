package com.wamt.ui.main.user;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wamt.R;
import com.wamt.data.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * L'Adapter fait le lien entre :
 *
 * - La liste d'utilisateurs venant de Room
 * - Les éléments graphiques affichés dans le RecyclerView
 *
 * Objectifs :
 * 1. Créer une ligne d'affichage (ViewHolder)
 * 2. Remplir cette ligne avec les données d'un utilisateur
 * 3. Indiquer combien d'éléments doivent être affichés
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    // Liste des utilisateurs à afficher
    private List<User> users = new ArrayList<>();


    /**
     * Constructeur vide.
     *
     * Le Fragment crée l'adapter au démarrage.
     */
    public UserAdapter() {

    }


    /**
     * Reçoit la nouvelle liste depuis le Fragment.
     *
     * Exemple :
     * [Alice, Bob, Charlie]
     */
    public void setUsers(List<User> users) {

        this.users = users;

        // Force le RecyclerView à se rafraîchir
        notifyDataSetChanged();
    }


    /**
     * Crée une nouvelle ligne.
     *
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
     *
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


        public UserViewHolder(@NonNull View itemView) {

            super(itemView);


            // Récupération du TextView dans item_user.xml
            pseudoTextView = itemView.findViewById(
                    R.id.itemUserPseudo
            );
        }
    }
}

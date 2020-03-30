package tech.minthura.carecovid.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.NumberFormatter;
import tech.minthura.caresdk.model.Country;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.CountryViewHolder> {

    private ArrayList<Country> mCountries;
    private Context mContext;
    private int position;

    public int getPosition() {
        return position;
    }

    private void setPosition(int position) {
        this.position = position;
    }

    public CountriesRecyclerViewAdapter(Context context, ArrayList<Country> countries) {
        mContext = context;
        mCountries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.country_item_view, parent,false);
        return new CountryViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = mCountries.get(position);
        Picasso.get().load(country.getCountryInfo().getFlag()).into(holder.imgFlag);
        holder.txtCountryName.setText(country.getCountry());
        holder.txtCases.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getCases()));
        holder.txtDeaths.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getDeaths()));
        holder.txtRecovered.setText(NumberFormatter.INSTANCE.formatToUnicode(country.getRecovered()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull CountryViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public void addCountries(ArrayList<Country> countries) {
        this.mCountries.clear();
        this.mCountries.addAll(countries);
        notifyDataSetChanged();
    }

    public Country getCountryAtPosition(int position) {
        return mCountries.get(position);
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView txtCountryName;
        TextView txtCases;
        TextView txtDeaths;
        TextView txtRecovered;
        ImageView imgFlag;
        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountryName = itemView.findViewById(R.id.txt_country);
            txtCases = itemView.findViewById(R.id.txt_cases);
            txtDeaths = itemView.findViewById(R.id.txt_deaths);
            txtRecovered = itemView.findViewById(R.id.txt_recovered);
            imgFlag = itemView.findViewById(R.id.img_flag);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.ctx_menu_add_preferred,
                    Menu.NONE, R.string.app_add_preferred);
        }
    }

}

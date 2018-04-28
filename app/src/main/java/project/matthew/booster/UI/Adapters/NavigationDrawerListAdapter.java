package project.matthew.booster.UI.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import project.matthew.booster.R;

/**
 * Created by Matthew on 28/04/2018.
 */


public class NavigationDrawerListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;

    private String[] mNavOptions;

    private String selectedItem;

    private Context context;

    private Boolean readyToSubmit;


    public NavigationDrawerListAdapter(Context context) {
        super(context, R.layout.list_row_nav_drawer);
        this.context = context;
        setupOptions(context);

        mInflater = LayoutInflater.from(context);
    }


    private void setupOptions(Context context) {

        mNavOptions = context.getResources().getStringArray(R.array.nav_items);

        for (String opt : mNavOptions) {
            add(opt);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_row_nav_drawer, null);
        }

        String option = mNavOptions[position];
        boolean selected = (option.equals(selectedItem));

        TextView tvTitle = (TextView) convertView.findViewById(R.id.nav_title);
        tvTitle.setText(option);
        tvTitle.setSelected(selected);

        // tint the icon white or grey based on selected state
        if (selected) {
            tvTitle.setTextColor(getContext().getResources().getColor(R.color.white));
        } else {
            tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        return convertView;
    }

    public void setSelectedItem(int position) {
        selectedItem = getItem(position);
        notifyDataSetChanged();
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setReadyToSubmit(boolean readyToSubmit) {
        this.readyToSubmit = readyToSubmit;
    }

    public boolean getReadyToSubmit() {
        return readyToSubmit;
    }
}

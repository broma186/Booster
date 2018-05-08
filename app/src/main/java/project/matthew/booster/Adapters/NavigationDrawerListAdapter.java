package project.matthew.booster.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.matthew.booster.R;
import project.matthew.booster.Helper.Constants;

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
        Resources res = getContext().getResources();
        if (selected) {
            tvTitle.setTextColor(res.getColor(R.color.boosterGreen));
        } else {
            tvTitle.setTextColor(res.getColor(R.color.white));
        }


        // If selected investor type title or questionnaire title or submit title, change the font to bold, make text larger
        // underline and make not as nested as other entries.
        if (tvTitle.getText().equals(res.getString(R.string.investor_types_header)) ||
                tvTitle.getText().equals(res.getString(R.string.questionnaire_title)) ||
                tvTitle.getText().equals(res.getString(R.string.submit_title))) {

            Typeface font = Typeface.createFromAsset(context.getAssets(),"circular_book_bold.ttf");
            tvTitle.setTypeface(font);
            tvTitle.setTextSize(22);

            // Make underlined
            SpannableString content = new SpannableString(tvTitle.getText());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvTitle.setText(content);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(42, 60, 0, 0);
            tvTitle.setLayoutParams(lp);
        } else {
            SpannableString content = new SpannableString(tvTitle.getText());
            tvTitle.setText(content);
        }
        // Hide Submit option if the questionnaire has not been completed yet.
        if (tvTitle.getText().equals(res.getString(R.string.submit_title)) && !PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.QUESTIONNAIRE_COMPLETE, false)) {
            Log.d("navo", "getView: setting text colour");
            tvTitle.setTextColor(res.getColor(R.color.text_disabled));
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

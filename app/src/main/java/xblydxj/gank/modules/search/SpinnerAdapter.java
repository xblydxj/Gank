package xblydxj.gank.modules.search;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import xblydxj.gank.R;

/**
 * Created by xblydxj.
 * on 2016/8/13/013
 */
public class SpinnerAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mStringArray;

    public SpinnerAdapter(Context context, String[] stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mStringArray = stringArray;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.spinner_drop_down_item, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.text1);
        tv.setText(mStringArray[position]);
        tv.setTextSize(12f);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.md_deep_orange_500_color_code));
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray[position]);
        tv.setTextSize(12f);
        tv.setTextColor(ContextCompat.getColor(mContext, R.color.md_blue_grey_800_color_code));
        return convertView;
    }
}

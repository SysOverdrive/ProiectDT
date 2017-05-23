package kamaradu.tudor.smartkineto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tudor on 17.05.2017.
 */

public class PacientAdaptor extends ArrayAdapter {
    private List<Users> names;
    //todo make it scrollable
    public PacientAdaptor(Context context, int resource, List<Users>objects) {
        super(context, resource, objects);
        names = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = vi.inflate(R.layout.contact_row, parent, false);
        TextView name = (TextView)rootView.findViewById(R.id.contactName);
        Users contact = names.get(position);
        name.setText(contact.getName());

        return rootView;
    }

    @Override
    public int getCount() {
        return names.size();
    }
}

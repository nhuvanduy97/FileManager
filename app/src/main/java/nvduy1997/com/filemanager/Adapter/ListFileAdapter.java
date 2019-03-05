package nvduy1997.com.filemanager.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;

import nvduy1997.com.filemanager.R;

public class ListFileAdapter  extends ArrayAdapter<File> {

    private Context context;
    private File[] files;

    public ListFileAdapter(Context context, File[] files){
        super(context,R.layout.list_files,files);
        this.context = context;
        this.files = files;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_files,null);
        File file = files[position];
        TextView textViewFileName = view.findViewById(R.id.textViewFileName);
        textViewFileName.setText(file.getName());
        TextView textViewSize = view.findViewById(R.id.textViewSize);
        textViewSize.setText(String.valueOf(file.length() + "byte(s)"));
        return view;
    }
}

package nvduy1997.com.filemanager;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import nvduy1997.com.filemanager.Adapter.ListFileAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView listViewFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewFile = findViewById(R.id.listViewFile);
        registerForContextMenu(listViewFile);
        loadData();
    }

    private void loadData() {
        File dir = getFilesDir();
        ListFileAdapter listFileAdapter = new ListFileAdapter(getApplicationContext(),dir.listFiles());
        listViewFile.setAdapter(listFileAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listViewFile){
            menu.add(0,0,0,getText(R.string.delete));
            menu.add(1,1,1,getText(R.string.editFile));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0){
            deleteFile(item);
        }
        if (item.getItemId() == 1){
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void deleteFile(MenuItem item) {
        try {
            AdapterView.AdapterContextMenuInfo  adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            View view = adapterContextMenuInfo.targetView;
            TextView textViewFileName = view.findViewById(R.id.textViewFileName);
            String fileName = textViewFileName.getText().toString();
            for (File file : getFilesDir().listFiles()){
                if (file.getName().equalsIgnoreCase(fileName)){
                    file.delete();
                    break;
                }
            }
            loadData();
        }catch (Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.file_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create){
            onpenCreateFileDialog();
        }
        return true;
    }

    private void onpenCreateFileDialog() {
        final View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.create_file_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getText(R.string.create_file));
        builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.show();
        Button btnCancels = view.findViewById(R.id.btnCancel);
        btnCancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile(view);
                dialog.dismiss();
                loadData();
            }
        });
    }

    private void saveFile(View view) {
        try {
            EditText editTextFileName = view.findViewById(R.id.edtTextFileName);
            EditText editTextContent = view.findViewById(R.id.edtTextContent);
            File file = new File(getFilesDir() + File.separator + editTextFileName.getText().toString());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(editTextContent.getText().toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

package net.jerryzhang.glasscontent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import net.jerryzhang.glasscontent.shoppinglist.ExportCsv;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HistroyActvity extends Activity {
	
	private static final String TAG = "HistroyActvity";
	
	private ListView mHistoryList;
	private GcListAdapter mGcListAdapter;
	private List<GcResult> gcLists ;
	
	private Button exportDataButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_history);
		mHistoryList  = (ListView) findViewById(R.id.gc_list);
		
		exportDataButton = (Button) findViewById(R.id.export_data);
		exportDataButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				startExport();
			}
		});
		
		gcLists = DatabaseHelper.getInstance(this).loadGcResults();
		mGcListAdapter = new  GcListAdapter(this, gcLists);
				
		mHistoryList.setAdapter(mGcListAdapter);
		mHistoryList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				GcResult  gc =  gcLists.get(position);
				if(gc!= null){
					gc.setSelect(!gc.isSelect());
				}
				mGcListAdapter.notifyDataSetChanged();
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	
	
	private   File  mexportFile;
	
	 public void startExport() {
		 File sdcardPathFile = null;
		 String filename = "gc_export_data_" +  System.currentTimeMillis() + ".cvs";
			String state = Environment.getExternalStorageState();
			if(Environment.MEDIA_MOUNTED.equals(state)){
				sdcardPathFile = Environment.getExternalStorageDirectory(); 
			}else{
				Toast.makeText(this, "请检查你的SD卡", Toast.LENGTH_SHORT).show();
			}
	    	
			if(sdcardPathFile != null){
				mexportFile = new File(sdcardPathFile, filename);
				if (mexportFile.exists() ) {
					Toast.makeText(this, "文件已经存在。", Toast.LENGTH_SHORT).show();
				} else {
					doExport();
				}
			}
	    	
	    }

		/**
		 * @param file
		 */
		public void doExport() {
//			String fileName = getAndSaveFilename();
//	    	final File file = new File(fileName);
	    	if(mexportFile!= null){
	    		try{
					FileWriter writer = new FileWriter(mexportFile);
					
					doExport(writer);
					writer.close();
					
					AlertDialog.Builder dialog =  new AlertDialog.Builder(this);
					dialog.setTitle("导出完成");
					dialog.setMessage("文件保存在:" + mexportFile.getAbsolutePath());
					dialog.setPositiveButton("好，知道了。", null);
					dialog.show();
				} catch (IOException e) {
					Toast.makeText(this, "导出错误  ", Toast.LENGTH_SHORT).show();
					Log.i(TAG, "IO exception", e);
					
				}
	    	}
		}
		
		/**
		 * @param writer
		 * @throws IOException
		 */
		public void doExport(FileWriter writer) throws IOException {
			ExportCsv ec = new ExportCsv(this);
			ec.exportCsv(writer);
		}
		
}

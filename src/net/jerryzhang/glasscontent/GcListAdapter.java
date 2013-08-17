package net.jerryzhang.glasscontent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GcListAdapter extends BaseAdapter{
	
	private List<GcResult>  gcdataLists ;
	private Activity mContext;
	
	private SimpleDateFormat mSimpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	public GcListAdapter( Activity context,List<GcResult>  gcLists) {
		this.gcdataLists =    gcLists;
		this.mContext = context;
	}
	

	public int getCount() {
		if(null == gcdataLists ){
			return 0;
		}
		return gcdataLists.size();
	}

	public GcResult getItem(int position) {
		if(null == gcdataLists ){
			return null;
		}
		
		return gcdataLists.get(position);
	}

	public long getItemId(int position) {
		GcResult gc  = getItem(position);
		if(null != gc){
			return gc.getCreate_time();
		}
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		ViewHolder holder;

		if (row == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row = inflater.inflate(R.layout.list_item_gcresult, null);

			holder = new ViewHolder();
			holder.precent_b = (TextView) row.findViewById(R.id.precent_b);
			holder.precent_gc = (TextView) row.findViewById(R.id.precent_gc);
			holder.weight_x = (TextView) row.findViewById(R.id.wight_x);
			holder.weight_y = (TextView) row.findViewById(R.id.wight_y);
			holder.weight_z = (TextView) row.findViewById(R.id.wight_z);
			holder.createTime = (TextView) row.findViewById(R.id.create_time);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		GcResult gcr = getItem(position);
		if(null != gcr){
			if(gcr.isSelect()){
				row.setBackgroundColor(Color.GREEN);
			}else{
				row.setBackgroundResource(R.drawable.line_bg);
			}
			
			holder.precent_b.setText(gcr.getPrecent_b_no_combustible()+"");
			holder.precent_gc.setText(gcr.getPrecent_glass_content_Result());
			holder.weight_x.setText(gcr.getWeight_g_x_copple_befor()+"");
			holder.weight_y.setText(gcr.getWeight_g_y_matter_before()+"");
			holder.weight_z .setText(gcr.getWeight_g_z_matter_and_copple_after()+"");
			holder.createTime.setText(mSimpleDateFormat.format(new Date(gcr.getCreate_time())));
			holder.createTime.setVisibility(View.VISIBLE);
		}

		return row;
	}
	
	static class  ViewHolder{
		TextView precent_b;
		TextView weight_x;
		TextView weight_y;
		TextView weight_z;
		TextView precent_gc;
		TextView createTime;
	}

}

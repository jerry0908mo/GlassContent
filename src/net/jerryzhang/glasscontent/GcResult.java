package net.jerryzhang.glasscontent;

import android.content.ContentValues;
import android.database.Cursor;

public class GcResult {
	private double precent_b_no_combustible;
	private double weight_g_x_copple_befor;
	private double weight_g_y_matter_before;
	private double weight_g_z_matter_and_copple_after;
	private String precent_glass_content_Result;
	private long create_time;
	
	private boolean select;
	
	GcResult(){
		
	}
	
	
	
	public boolean isSelect() {
		return select;
	}




	public void setSelect(boolean select) {
		this.select = select;
	}




	GcResult(double precent_b_no_combustible,
			double weight_g_x_copple_befor,
	  double weight_g_y_matter_before,
	  double weight_g_z_matter_and_copple_after,
	  String precent_glass_content_Result){
		
	this(precent_b_no_combustible,
				weight_g_x_copple_befor,
				  weight_g_y_matter_before,
				  weight_g_z_matter_and_copple_after,
				  precent_glass_content_Result,
				  System.currentTimeMillis());
	}

	GcResult(double precent_b_no_combustible,
			double weight_g_x_copple_befor,
	  double weight_g_y_matter_before,
	  double weight_g_z_matter_and_copple_after,
	  String precent_glass_content_Result,
	  long create_time){
		this.precent_b_no_combustible = precent_b_no_combustible;
		this.weight_g_x_copple_befor = weight_g_x_copple_befor;
		this.weight_g_y_matter_before = weight_g_y_matter_before;
		this.weight_g_z_matter_and_copple_after = weight_g_z_matter_and_copple_after;
		this.precent_glass_content_Result = precent_glass_content_Result;
		this.create_time =create_time;
		
	}
	
	public double getPrecent_b_no_combustible() {
		return precent_b_no_combustible;
	}
	public void setPrecent_b_no_combustible(double precent_b_no_combustible) {
		this.precent_b_no_combustible = precent_b_no_combustible;
	}
	public double getWeight_g_x_copple_befor() {
		return weight_g_x_copple_befor;
	}
	public void setWeight_g_x_copple_befor(double weight_g_x_copple_befor) {
		this.weight_g_x_copple_befor = weight_g_x_copple_befor;
	}
	public double getWeight_g_y_matter_before() {
		return weight_g_y_matter_before;
	}
	public void setWeight_g_y_matter_before(double weight_g_y_matter_before) {
		this.weight_g_y_matter_before = weight_g_y_matter_before;
	}
	public double getWeight_g_z_matter_and_copple_after() {
		return weight_g_z_matter_and_copple_after;
	}
	public void setWeight_g_z_matter_and_copple_after(
			double weight_g_z_matter_and_copple_after) {
		this.weight_g_z_matter_and_copple_after = weight_g_z_matter_and_copple_after;
	}
	public String getPrecent_glass_content_Result() {
		return precent_glass_content_Result;
	}
	public void setPrecent_glass_content_Result(String precent_glass_content_Result) {
		this.precent_glass_content_Result = precent_glass_content_Result;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	
	public static ContentValues build(GcResult  gcr){
		ContentValues cv  =  new  ContentValues();
		cv.put("precent_b_no_combustible", gcr.getPrecent_b_no_combustible());
		cv.put("weight_g_x_copple_befor", gcr.getWeight_g_x_copple_befor());
		cv.put("weight_g_y_matter_before", gcr.getWeight_g_y_matter_before());
		cv.put("weight_g_z_matter_and_copple_after", gcr.getWeight_g_z_matter_and_copple_after());
		cv.put("precent_glass_content_Result", gcr.getPrecent_glass_content_Result());
		cv.put("create_time", gcr.getCreate_time());
		return  cv;
	}
	
	public static GcResult build(Cursor query){
		GcResult  gcr =  new  GcResult();
		gcr.setPrecent_b_no_combustible(query.getDouble(query.getColumnIndex("precent_b_no_combustible")));
		gcr.setWeight_g_x_copple_befor(query.getDouble(query.getColumnIndex("weight_g_x_copple_befor")));
		gcr.setWeight_g_y_matter_before(query.getDouble(query.getColumnIndex("weight_g_y_matter_before")));
		gcr.setWeight_g_z_matter_and_copple_after(query.getDouble(query.getColumnIndex("weight_g_z_matter_and_copple_after")));
		gcr.setPrecent_glass_content_Result(query.getString(query.getColumnIndex("precent_glass_content_Result")));
		gcr.setCreate_time(query.getLong(query.getColumnIndex("create_time")));
		return  gcr;
	}
	
	
	@Override
	public String toString() {
		StringBuilder  sb  = new StringBuilder();
		sb.append(getCreate_time()+"_");
		sb.append(getPrecent_b_no_combustible()+"_");
		sb.append(getPrecent_glass_content_Result()+"_");
		sb.append(getWeight_g_x_copple_befor()+"_");
		sb.append(getWeight_g_y_matter_before()+"_");
		sb.append(getWeight_g_z_matter_and_copple_after());
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		GcResult gc = null;
		if(o instanceof GcResult){
			gc = (GcResult) o;
		}else{
			return false;
		}
		
		return gc.getPrecent_b_no_combustible()== getPrecent_b_no_combustible() &&
				gc.getWeight_g_x_copple_befor() == getWeight_g_x_copple_befor() &&
				gc.getWeight_g_y_matter_before() == getWeight_g_y_matter_before() &&
				gc.getWeight_g_z_matter_and_copple_after() == getWeight_g_z_matter_and_copple_after()
				&& gc.getPrecent_glass_content_Result().equals(getPrecent_glass_content_Result());
	}
	
}

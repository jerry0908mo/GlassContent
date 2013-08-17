package net.jerryzhang.glasscontent;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,TextWatcher  {
	
	private EditText input_x;
	private EditText input_y;
	private EditText input_z;
	
	private TextView show_input_a;
	
	private EditText input_b;
	
	private Button do_Gc_button;
	private Button showGclistBtn;
	
	private TextView show_Gc_result;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initData();
    }
    
    private void initData(){
    	showGCResult("-");
    }
    
    private void findViews(){
    	show_input_a = (TextView) findViewById(R.id.input_a);
    	
    	input_b = (EditText) findViewById(R.id.input_b);
    	
    	input_x = (EditText) findViewById(R.id.input_x);
    	input_y = (EditText) findViewById(R.id.input_y);
    	input_z = (EditText) findViewById(R.id.input_z);
    	
    	input_b.addTextChangedListener(this);
    	
    	input_x.addTextChangedListener(this);
    	input_y.addTextChangedListener(this);
    	input_z.addTextChangedListener(this);
    	
    	
    	show_Gc_result = (TextView)findViewById(R.id.gc_result);
    	
    	do_Gc_button = (Button)findViewById(R.id.do_gc_btn);
    	showGclistBtn = (Button) findViewById(R.id.show_gc_list_btn);
    	
    	showGclistBtn.setOnClickListener(this);
    	do_Gc_button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() ==  R.id.menu_settings){
    		
    	}
    	return super.onOptionsItemSelected(item);
    }

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.do_gc_btn:
			doGcResult();
			break;
			
		case R.id.show_gc_list_btn:
			launchActivity(HistroyActvity.class);
			break;

		default:
			break;
		}
	}
	
	private void launchActivity(Class<?> cls){
		Intent  i =  new Intent(this, cls);
		startActivity(i);
	}
	
	private  double stringToDouble(String str){
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
		}
		return 0;
	}
	
	private void showGCResult(String gcShowString){
		String resultStr =String.format(getResources().getString(R.string.gc_result_string),
				gcShowString);
		show_Gc_result.setText(resultStr);
	}
	
	private void doGcResult(){
		double b  = stringToDouble(input_b.getText().toString());
		
		double x = stringToDouble(input_x.getText().toString());
		double y = stringToDouble(input_y.getText().toString());
		double z = stringToDouble(input_z.getText().toString());
		
		showGCResult("-");
		
		if(x <= 0 || y <= 0 || z <= 0 || z >= (x + y) ){
			Toast toast = Toast.makeText(this, "请输入合理的数据.", Toast.LENGTH_LONG);
			toast.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
			toast.show();
			return ;
		}
		
		if(b >= 100){
			Toast toast = Toast.makeText(this, "数据不合理，检查B。", Toast.LENGTH_LONG);
			toast.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
			toast.show();
			return ;
		}
		
		if( x >= z ){
			Toast toast = Toast.makeText(this, "数据不合理。坩埚被你烧坏了？检查X,Z。", Toast.LENGTH_LONG);
			toast.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
			toast.show();
			return ;
		
		}
		
		if(x+y <= z ){
			Toast toast = Toast.makeText(this, "数据不合理。越烧越多？检查X，Y，Z。", Toast.LENGTH_LONG);
			toast.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
			toast.show();
			return ;
		}
		
		if(b * y  >=  (z-x)*100){
			Toast toast = Toast.makeText(this, "数据不合理。", Toast.LENGTH_LONG);
			toast.setGravity( Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,0);
			toast.show();
			return ;
		}
		
		
		String gcShowString = doGcResult(b, x, y, z);

		DatabaseHelper.getInstance(this).addGcResult(
				new GcResult(b, x, y, z, gcShowString));
		
		show_input_a.setText(Double.valueOf(100 -b ).toString()+"%");
		
		showGCResult(gcShowString);
	}
	
	/**   
	  * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指   
	   *   定精度，以后的数字四舍五入。   
	   *   @param   v1   被除数   
	   *   @param   v2   除数   
	   *   @param   scale   表示表示需要精确到小数点以后几位。   
	   *   @return   两个参数的商   
	   */   
	 public   static   double   div(double   v1,double   v2,int   scale){   
	     BigDecimal   b1   =   new   BigDecimal(Double.toString(v1));   
	     BigDecimal   b2   =   new   BigDecimal(Double.toString(v2));   
	     return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();   
	}
	
	
	/**
	 * 
	 * @param b 配方不可燃物质比例
	 * @param x 坩埚重
	 * @param y 烧前样重
	 * @param z 烧后坩埚重
	 * @return
	 */
	private String doGcResult(double b,double x,double y ,double z){
		//｛【 (Z-X)-Y】*B%/A%+Z-X｝/Y
		double gc = 0;
		
		double tmp_z_x_y = (z - x) - y;
		double tmp_z_x = z - x;
		
		double a_percent = 100 - b;
		double tmp_a_div_b_ =  div(b,a_percent,20) ; ///丢失精度
		
		double tmp_gc = (tmp_z_x_y * tmp_a_div_b_ ) + tmp_z_x ;
		
		gc = div(tmp_gc,y,20); ///再次丢失精度
		
		DecimalFormat df1 = new DecimalFormat("##.00%");      
		String gcShowString = df1.format(gc);
		
		return gcShowString;
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		showGCResult("-");
	}
	
}

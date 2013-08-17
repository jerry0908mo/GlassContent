/* 
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jerryzhang.glasscontent.shoppinglist;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.jerryzhang.glasscontent.DatabaseHelper;
import net.jerryzhang.glasscontent.GcResult;
import net.jerryzhang.glasscontent.opencsv.CSVWriter;

import android.content.Context;
import android.database.Cursor;

public class ExportCsv {

	Context mContext;
	
	public ExportCsv(Context context) {
		mContext = context;
	}
	
	private SimpleDateFormat mSimpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	/**
	 * @param dos
	 * @throws IOException
	 */
	public void exportCsv(Writer writer) throws IOException {

		CSVWriter csvwriter = new CSVWriter(writer);
		
		csvwriter.write("Time");
		csvwriter.write("Product Name");
		csvwriter.write("NO.");
		csvwriter.write("Z(g)");
		csvwriter.write("X(g)");
		csvwriter.write("Y(g)");
		csvwriter.write("A(%)");
		csvwriter.write("B(%)");
		csvwriter.write("GC(%)");
		csvwriter.write("Tester");
		csvwriter.write("主机转速(转/分钟)");
		csvwriter.write("下料转速(转/分钟)");
		csvwriter.write("玻纤股数");
		csvwriter.write("机型");
		csvwriter.write("备注");
		
		
		csvwriter.writeNewline();

	
		List<GcResult> gcLists = DatabaseHelper.getInstance(mContext).loadGcResults();
		
		if (gcLists != null && !gcLists.isEmpty()) {
			for (GcResult gc : gcLists) {
				
	
		        if (gc != null) {
		        	csvwriter.write("" + mSimpleDateFormat.format(new Date(gc.getCreate_time())) );
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		csvwriter.write(""+ gc.getWeight_g_z_matter_and_copple_after() );
		        		csvwriter.write("" + gc.getWeight_g_x_copple_befor());
		        		csvwriter.write("" + gc.getWeight_g_y_matter_before());
		        		csvwriter.write("" + (100 - gc.getPrecent_b_no_combustible()));
		        		csvwriter.write("" + gc.getPrecent_b_no_combustible());
		        		csvwriter.write("" + gc.getPrecent_glass_content_Result());
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		csvwriter.write("");
		        		
				    	csvwriter.writeNewline();
		        }
			}
		}
		
		csvwriter.close();
	}

}

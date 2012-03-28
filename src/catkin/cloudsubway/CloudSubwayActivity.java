package catkin.cloudsubway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CloudSubwayActivity extends Activity {
    private Button start;
    private Button stop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        final Intent intent = new Intent(getBaseContext(), SubwayService.class);
        
        start.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				
				startService(intent);
			}
        });
        
        stop.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				stopService(intent);
			}
        });
    }
}
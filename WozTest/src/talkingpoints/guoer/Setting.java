package talkingpoints.guoer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Setting extends Activity {

	private int closeRange;
	private int nearbyRange;
	private int flashRange;
	private Spinner s1;
	private Spinner s2;
	private Spinner s3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner);

		// Spinner s1 = (Spinner) findViewById(R.id.spinner_close);
		// ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		// this, R.array.close_array, android.R.layout.simple_spinner_item);
		//
		// adapter
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// s1.setAdapter(adapter);

		s1 = (Spinner) findViewById(R.id.spinner_close);

		ArrayList items = new ArrayList();
		for (int i = 3; i < 21; i++)
			items.add(i);

		ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, items);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(spinnerArrayAdapter);

		s2 = (Spinner) findViewById(R.id.spinner_nearby);
		ArrayList items2 = new ArrayList();
		for (int i = 0; i < 51; i++)
			items2.add(i);
		spinnerArrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, items2);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s2.setAdapter(spinnerArrayAdapter);

		s3 = (Spinner) findViewById(R.id.spinner_flash);
		ArrayList items3 = new ArrayList();
		for (int i = 20; i < 101; i++)
			items3.add(i);
		spinnerArrayAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, items3);
		spinnerArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s3.setAdapter(spinnerArrayAdapter);

		Button go = (Button) findViewById(R.id.button_go);
		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Setting.this, BTlist.class);
				intent.putExtra("closeLocation_range", Integer.parseInt(s1
						.getSelectedItem().toString()));
				intent.putExtra("nearbyLocation_range", Integer.parseInt(s2
						.getSelectedItem().toString()));
				intent.putExtra("flashLocation_range", Integer.parseInt(s3
						.getSelectedItem().toString()));
				startActivity(intent);
			}

		});
	}

}

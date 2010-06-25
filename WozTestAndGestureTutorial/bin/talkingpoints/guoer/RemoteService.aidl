package talkingpoints.guoer;
//import com.android.widget.ArrayAdapter;

interface RemoteService {
	int getCounter();
	List<String> getBTList();
	String getLac();
	String getLng();
	List<String> getWIFI();
}

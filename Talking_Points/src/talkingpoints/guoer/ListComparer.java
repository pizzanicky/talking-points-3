package talkingpoints.guoer;

import java.util.ArrayList;
import java.util.List;

//to see if the newlist contains new POI, if yes, return a list of new POI
public class ListComparer {
	List<String> newItems;
	boolean hasNew = false;

	public ListComparer(List<String> newList, List<String> oldList) {
		newItems = new ArrayList<String>();
		for (int i = 0; i < newList.size(); i++) {
			if (oldList.size() == 0) {
				newItems = newList;
				break;
			}
			for (int j = 0; j < oldList.size(); j++) {
				if (oldList.get(j).substring(oldList.get(j).length() - 17)
						.equals(
								newList.get(i).substring(
										newList.get(i).length() - 17)))
					break;
				if (j == oldList.size() - 1)
					hasNew = true;
			}
			if (hasNew) {
				newItems.add(newList.get(i));
				hasNew = false;
			}

		}
	}

	public List<String> getNewItems() {
		return newItems;
	}
}

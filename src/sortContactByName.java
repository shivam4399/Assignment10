import java.util.Comparator;

public class sortContactByName implements Comparator<Contact> {

	@Override
	public int compare(Contact o1, Contact o2) {
		
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}

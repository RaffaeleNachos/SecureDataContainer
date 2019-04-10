import java.util.ArrayList;
import java.util.Iterator;

public class User<E> {
	
	private String Username;
	private String Password;
	private ArrayList<E> Database;
	
	public User(String User, String Pass) {
		Username = User;
		Password = Pass;
		Database = new ArrayList<E>();
	}
	
	public String getUsername() {
		return Username;
	}

	public int checkLogin(String Ux, String Px) {
		if (Ux.equals(Username)==true && Px.equals(Password)==true) return 0;
		if (Ux.equals(Username)==true && Px.equals(Password)==false) return 1;
		return -1;		
	}

	public void addElement(E data) {
		Database.add(data);
	}
	
	public E getElement(E data) {
		for (int i = 0; i < Database.size(); i++) {
			if(Database.get(i).equals(data)==true) {
				return Database.get(i);
			}
		}
		return null;
	}
	
	public E removeElement(E data) {
		E deletedElem = null;
		for (int i = 0; i < Database.size(); i++) {
			if(Database.get(i).equals(data)==true) {
				deletedElem=Database.get(i);
				Database.remove(i);
				i--;
			}
		}
		return deletedElem;
		
		/*
		 * oppure 
		 * 
		 * E deletedElem=null;
		 * deletedElem=getElement(data);
		 * Database.removeAll(Collections.singleton(data));
		 * return deletedElem;
		 * 
		 */
	}
	
	public int getDBSize() {
		return Database.size();
	}
	
	public Iterator<E> newIterator(){
		return new MyIterator<E>(Database.iterator());
	}
	
	private class MyIterator<E> implements Iterator<E> {
		private Iterator<E> javaitr;
		
		public MyIterator(Iterator<E> itr) {
			if (itr==null) throw new NullPointerException();
			else javaitr=itr;
		}
		@Override
		public boolean hasNext() {
			return javaitr.hasNext();
		}

		@Override
		public E next() {
			return javaitr.next();
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Remove non permessa");
		}
	}
	
}

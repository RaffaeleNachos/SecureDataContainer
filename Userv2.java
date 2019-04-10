import java.util.Iterator;
import java.util.Vector;

public class Userv2<E> {
	
	private String Password;
	private Vector<E> Database;
	
	public Userv2(String Pass) {
		Password = Pass;
		Database = new Vector<E>();
	}
	
	public String getPassword() {
		return Password;
	}
	
	public int getDBSize() {
		return Database.size();
	}
	
	public void addElement(E data) {
		Database.add(data);
	}
	
	public E getElement(E data) {
		int index = Database.indexOf(data);
		if (index!=-1) return Database.get(index);
		else return null;
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
	}

	
	public Iterator<E> getIterator(){
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

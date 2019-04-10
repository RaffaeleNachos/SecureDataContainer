import java.util.Iterator;
import java.util.HashMap;

public class SecureDataContainerImplv2<E> implements SecureDataContainer<E>{
	
	//AF: {utente_0,...,utente_k} tali che f(utente_i) -> <password_i,{<dati_0,...,dati_N>}>
	
	//IR: users!=null
	//	  users.size()>=0
	//	  for all i . i appartiene all'insieme delle chiavi => users.get(i)!=null
	//	  for all i . i appartiene all'insieme delle chiavi => users.get(i).getDBSize()>=0
	//	  proprietà della hashmap è che key_i!=key_j

	protected HashMap<String,Userv2<E>> users;
	
	public SecureDataContainerImplv2() {
		users = new HashMap<String,Userv2<E>>();
	}
	
	@Override
	public void createUser(String Id, String passw)
			throws NullPointerException, IllegalArgumentException, PasswordNotSecureException {
		if (Id == null || passw == null) throw new NullPointerException();
		if (users.containsKey(Id)) throw new IllegalArgumentException("Utente già presente");
		if (CheckPass(passw)==false) throw new PasswordNotSecureException("Password non sicura, deve contenere almeno un numero");
		Userv2<E> newUser = new Userv2<>(passw);
		users.put(Id,newUser);
	}

	@Override
	public void RemoveUser(String Id, String passw) throws NullPointerException, IllegalArgumentException, LoginFailException{
		if (Id == null || passw == null) throw new NullPointerException();
		if (users.containsKey(Id)==false) throw new IllegalArgumentException("Utente non presente");
		if (users.get(Id).getPassword().equals(passw)) {
			users.remove(Id);
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public int getSize(String Owner, String passw) throws NullPointerException, LoginFailException, IllegalArgumentException {
		if (Owner == null || passw == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new IllegalArgumentException("Utente non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			return users.get(Owner).getDBSize();
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public boolean put(String Owner, String passw, E data) throws NullPointerException, LoginFailException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) return false;
		if (users.get(Owner).getPassword().equals(passw)) {
			users.get(Owner).addElement(data);
			return true;
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public E get(String Owner, String passw, E data)
			throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new LoginFailException("Utente non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			E tmp;
			if ((tmp=users.get(Owner).getElement(data)) == null)
					throw new NoElemFoundException("Elemento non trovato");
			else return tmp;
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public E remove(String Owner, String passw, E data)
			throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new LoginFailException("Utente non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			E tmp;
			if ((tmp=users.get(Owner).removeElement(data)) == null)
					throw new NoElemFoundException("Elemento non trovato");
			else return tmp;
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public void copy(String Owner, String passw, E data)
			throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new LoginFailException("Utente non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			E copiedData;
			copiedData = users.get(Owner).getElement(data);
			if (copiedData == null) throw new NoElemFoundException("Elemento non trovato");
			users.get(Owner).addElement(copiedData);
		}
		else throw new LoginFailException("Password Errata");
	}

	@Override
	public void share(String Owner, String passw, String Other, E data)
			throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || Other==null || data == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new LoginFailException("Utente Owner non trovato");
		if (users.containsKey(Other)==false) throw new LoginFailException("Utente Other non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			E sharedData;
			sharedData = users.get(Owner).getElement(data);
			if (sharedData == null) throw new NoElemFoundException("Elemento non trovato");
			users.get(Other).addElement(sharedData);
		}
		else throw new LoginFailException("Password Errata");
	}
	
	@Override
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, LoginFailException {
		if (Owner == null || passw == null) throw new NullPointerException();
		if (users.containsKey(Owner)==false) throw new LoginFailException("Utente non trovato");
		if (users.get(Owner).getPassword().equals(passw)) {
			return users.get(Owner).getIterator();
		}
		else throw new LoginFailException("Password Errata");
	}
	
	private boolean CheckPass(String passw) {
		return passw.matches("[a-zA-Z ]*\\d+.*");
	}

}

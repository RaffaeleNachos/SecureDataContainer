import java.util.ArrayList;
import java.util.Iterator;

public class SecureDataContainerImpl<E> implements SecureDataContainer<E>{
	
	//AF: {<nomeutente_0,password_0,{<dati_0...dati_N>}>...<nomeutente_M,password_M,{<dati_0...dati_K>}>}
	
	//IR: users!=null
	//	  users.size()>=0
	//	  for all i . 0<=i<users.size() => users.get(i)!=null
	//	  for all i,j . 0<=i<users.size() && i!=j => users.get(i).getUsername()!=users.get(j).getUsername()
	//	  for all i . 0<=i<users.size() => users.get(i).getSize()>=0
	
	protected ArrayList<User<E>> users;
	private boolean found = false;
	
	public SecureDataContainerImpl() {
		users = new ArrayList<User<E>>();
	}

	@Override
	public void createUser(String Id, String passw)
			throws NullPointerException, IllegalArgumentException, PasswordNotSecureException {
		if (Id == null || passw == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).getUsername().equals(Id)==true) throw new IllegalArgumentException("Utente già presente");
		}
		if (CheckPass(passw)==false) throw new PasswordNotSecureException("Password non sicura, deve contenere almeno un numero");
		User<E> newUser = new User<>(Id,passw);
		users.add(newUser);
	}

	@Override
	public void RemoveUser(String Id, String passw) throws NullPointerException, LoginFailException {
		if (Id == null || passw == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Id, passw)==1) {
				throw new LoginFailException("Password errata");
			}
			if (users.get(i).checkLogin(Id, passw)==0) {
				users.remove(i);
				found=true;
			}
		}
		if (found==false) throw new LoginFailException("Utente non prensente");	
	}

	@Override
	public int getSize(String Owner, String passw) throws NullPointerException, LoginFailException{
		if (Owner == null || passw == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Owner, passw)==0){
				return users.get(i).getDBSize();
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente non presente");
	}

	@Override
	public boolean put(String Owner, String passw, E data) throws NullPointerException, LoginFailException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Owner, passw)==0){
				users.get(i).addElement(data);
				return true;
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		return false;
	}

	@Override
	public E get(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Owner, passw)==0){
				E tmp;
				if ((tmp=users.get(i).getElement(data)) == null) {
					throw new NoElemFoundException("Elemento non trovato");
				}
				else return tmp;
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente non presente");
	}

	@Override
	public E remove(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Owner, passw)==0){
				if (users.get(i).getElement(data) == null) {
					throw new NoElemFoundException("Elemento non trovato");
				}
				else return users.get(i).removeElement(data);
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente non presente");
	}

	@Override
	public void copy(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || data == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).checkLogin(Owner, passw)==0){
				E Elemcopy = null;
				if ((Elemcopy=users.get(i).getElement(data)) == null) {
					throw new NoElemFoundException("Elemento non trovato");
				}
				else {
					put(Owner,passw,Elemcopy);
					return;
				}
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente non presente");
	}

	@Override
	public void share(String Owner, String passw, String Other, E data)
			throws NullPointerException, LoginFailException, NoElemFoundException {
		if (Owner == null || passw == null || Other==null || data == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).checkLogin(Owner, passw)==0){
				if (users.get(i).getElement(data) == null) {
					throw new NoElemFoundException("Elemento non trovato");
				}
				else {
					E Elemcopy = null;
					boolean x = false;
					Elemcopy = users.get(i).getElement(data);
					for (int j = 0; j < users.size(); j++) {
						if (users.get(j).getUsername().equals(Other)==true) {
							users.get(j).addElement(Elemcopy);
							x=true;
						}
					}
				if (x==false) throw new LoginFailException("Utente Other non presente");
				return;
				}
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente Owner non presente");
	}

	@Override
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, LoginFailException {
		if (Owner == null || passw == null) throw new NullPointerException();
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).checkLogin(Owner, passw)==0){
				return users.get(i).newIterator();
			}
			if(users.get(i).checkLogin(Owner, passw)==1){
				throw new LoginFailException("Password errata");
			}
		}
		throw new LoginFailException("Utente non presente");
	}

	
	private boolean CheckPass(String passw) {
		return passw.matches("[a-zA-Z ]*\\d+.*");
	}
	
}

import java.util.Iterator;

public interface SecureDataContainer<E>{
	// Overview: SecureDataContainer è un tipo di dato modificabile che implementa la gestione di collezioni di oggetti (di tipo E) condivisibili
	//			 appartenenti ad utenti identificabili attraverso nome utente e password
    // Typical Element: <nomeutente,password,<dati>>
	//					nomeutente è la stringa che idetifica l'univoco nome dell'utente 
	//					password è la stringa contentente almeno un numero che identifica la password dell'utente
	//					dati è la collezione di dati associata all' utente

	public void createUser(String Id, String passw) throws NullPointerException, IllegalArgumentException, PasswordNotSecureException;
	/*
    REQUIRES: Id != null && passw!=null
    THROWS: NullPointerException se Id || passw == null
    		IllegalArgumentException se Id è già presente
    		PasswordNotSecureException se la password non è sicura
    MODIFIES: this
    EFFECTS: aggiunge un nuovo utente con nome Id e associa passw come password
    */
	
	public void RemoveUser(String Id, String passw) throws NullPointerException, LoginFailException;
	/*
    REQUIRES: Id != null && passw!=null && this.contains(Id)
    THROWS: NullPointerException se Id || passw == null
    		LoginFailException se l'utente non è presente nella collezione o la password è errata
    MODIFIES: this
    EFFECTS: rimuove l'utente esistente con nome Id e password passw
    RETURNS: none
    */

	public int getSize(String Owner, String passw) throws NullPointerException, LoginFailException;
	/*
    REQUIRES: Owner != null && passw!=null
    THROWS: NullPointerException se Id || passw == null
    		LoginFailException se l'utente non è presente nella collezione o la password è errata
    MODIFIES: none
    RETURNS: restituisce il numero degli elementi appartententi all'utente Owner
    */
	
	public boolean put(String Owner, String passw, E data) throws NullPointerException, LoginFailException; 
	/*
    REQUIRES: Owner != null && passw!=null && data!=null
    THROWS: NullPointerException se Id || passw || data == null
    		LoginFailException se la password è errata
    MODIFIES: this
    EFFECTS: inserisce data nella collezione se vengono rispettati i controlli dell'identità
    RETURNS: true se l'elemento è stato inserito
    		 false se l'utente non è presente
    */
	
	public E get(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException; 
	/*
    REQUIRES: Owner != null && passw!=null && data!=null
    THROWS: NullPointerException se Id || passw || data == null
    		LoginFailException se l'utente non è presente o la password è errata
    		NoElemFoundException se data non è stato trovato
    MODIFIES: none
    RETURNS: restituisce una copia di data se vengono rispettati i controlli dell'identità
    */
	
	public E remove(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException; 
	/*
    REQUIRES: Owner != null && passw!=null && data!=null
    THROWS: NullPointerException se Id || passw || data == null
    		LoginFailException se l'utente non è presente o la password è errata
    		NoElemFoundException se data non è stato trovato
    MODIFIES: this
    EFFECTS: rimuove data nella collezione se vengono rispettati i controlli dell'identità
    RETURNS: restituisce data che è stato eliminato
    */
	
	public void copy(String Owner, String passw, E data) throws NullPointerException, LoginFailException, NoElemFoundException;
	/*
    REQUIRES: Owner != null && passw!=null && data!=null
    THROWS: NullPointerException se Id || passw || data == null
    		LoginFailException se l'utente non è presente o la password è errata
    		NoElemFoundException se data non è stato trovato
    MODIFIES: this
    EFFECTS: crea una copia del dato nella collezione di dati dell'utente
    */
	
	public void share(String Owner, String passw, String Other, E data) throws NullPointerException, LoginFailException, NoElemFoundException;
	/*
    REQUIRES: Owner != null && passw!=null && data!=null && Other!=null
    THROWS: NullPointerException se Id || passw || data || Other == null
    		LoginFailException se l'utente Other/Owner non è presente o la password di Owner è errata
    		NoElemFoundException se data non è stato trovato
    MODIFIES: this
    EFFECTS: condivide il dato con un altro utente Other inserendo il dato anche nella collezione di Other
    */

	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, LoginFailException;
	/*
    REQUIRES: Owner != null && passw!=null
    THROWS: NullPointerException se Id || passw == null
    		LoginFailException se l'utente non è presente o la password è errata
    MODIFIES: none
    EFFECTS: restituisce un iteratore che genera tutti i dati dell'utente Owner senza il metodo remove
    RETURNS: none
    */	
}

	@SuppressWarnings("serial")
	class PasswordNotSecureException extends Exception {

		public PasswordNotSecureException() {
			super();
		}
		
		public PasswordNotSecureException(String s) {
			super(s);
		}
	}

	@SuppressWarnings("serial")
	class LoginFailException extends Exception {

		public LoginFailException() {
			super();
		}
    
		public LoginFailException(String s) {
			super(s);
		}
	}


	@SuppressWarnings("serial")
	class NoElemFoundException extends Exception {

		public NoElemFoundException() {
			super();
		}
    
		public NoElemFoundException(String s) {
			super(s);
		}
	}


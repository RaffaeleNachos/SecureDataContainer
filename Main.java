import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		SecureDataContainerImplv2<String> Dropbox = new SecureDataContainerImplv2<>();
		String U1 = "Raffaele";
		String U2 = "Daniele";
		String U3 = "Alessandro";
		String U4 = "Rimuovimi";
		String U5 = null;
		String U7 = "NonSonoSicuro";
		String P1 = "39caffè";
		String P2 = "c4n3";
		String P3 = "dj000";
		String P4 = "codice7";
		String P5 = null;
		String P7 = "password";
		
		try {
			Dropbox.createUser(U1, P1);
			Dropbox.createUser(U2, P2);
			Dropbox.createUser(U3, P3);
			Dropbox.createUser(U4, P4);
			//Dropbox.createUser(U5, P5); NullPointExc
			//Dropbox.createUser(U1, P3); IllArgExc
			//Dropbox.createUser(U7, P7); PswNotSecExc
		} catch (NullPointerException | IllegalArgumentException | PasswordNotSecureException e) {
			e.printStackTrace();
		}
		
		try {
			Dropbox.RemoveUser(U4, P4);
			//Dropbox.RemoveUser(U5, P5); NullPointExc
			//Dropbox.RemoveUser(U4, P4); IllArgExc utentnotpres
			//Dropbox.RemoveUser(U1, P3); IllArgExc passerr
		} catch (NullPointerException | LoginFailException e2) {
			e2.printStackTrace();
		}		
		
		try {
			System.out.println(Dropbox.put(U1, P1, "dato1"));
			Dropbox.put(U1, P1, "dato2");
			Dropbox.put(U1, P1, "dato3");
			Dropbox.put(U1, P1, "datorimosso");
			Dropbox.put(U2, P2, "dato01");
			Dropbox.put(U2, P2, "dato02");
			Dropbox.put(U2, P2, "dato03");
			Dropbox.put(U3, P3, "dato001");
			Dropbox.put(U3, P3, "dato002");
			Dropbox.put(U3, P3, "dato003");
		} catch (NullPointerException | LoginFailException e1) {
			e1.printStackTrace();
		}
		try {
			System.out.println(Dropbox.getSize(U1, P1));
			System.out.println(Dropbox.getSize(U2, P2));
			System.out.println(Dropbox.getSize(U3, P3));
		} catch (NullPointerException | IllegalArgumentException | LoginFailException e) {
			e.printStackTrace();
		}
		
		String datoricevuto = null;
		
		try {
			datoricevuto = Dropbox.get(U1, P1, "dato1");
			System.out.println(datoricevuto);
		} catch (NullPointerException | LoginFailException | NoElemFoundException e) {
			e.printStackTrace();
		}
		
		try {
			datoricevuto = Dropbox.remove(U1, P1, "datorimosso");
			System.out.println(datoricevuto);
		} catch (NullPointerException | LoginFailException | NoElemFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//Dropbox.copy(U1, P1, "dato5"); Noelemfoundexc
			Dropbox.copy(U2, P2, "dato01");
			Dropbox.copy(U3, P3, "dato001");
		} catch (NullPointerException | LoginFailException | NoElemFoundException e) { 
			e.printStackTrace();
		}
		
		try {
			System.out.println("numero dati U1:" + Dropbox.getSize(U1, P1));
			System.out.println("numero dati U2:" + Dropbox.getSize(U2, P2));
			System.out.println("numero dati U3:" + Dropbox.getSize(U3, P3));
		} catch (NullPointerException | IllegalArgumentException | LoginFailException e) {
			e.printStackTrace();
		}

		try {
			//Dropbox.share(U1, P1, U2, "dato01"); Noelemfoundexc
			//Dropbox.share(U1, P1, P4, "dato1"); LoginFailexc Other not found
			Dropbox.share(U1, P1, U2, "dato1");
			Dropbox.share(U2, P2, U3, "dato1");
		} catch (NullPointerException | LoginFailException | NoElemFoundException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("numero dati U1 dopo share:" + Dropbox.getSize(U1, P1));
			System.out.println("numero dati U2 dopo share:" + Dropbox.getSize(U2, P2));
			System.out.println("numero dati U3 dopo share:" + Dropbox.getSize(U3, P3));
		} catch (NullPointerException | IllegalArgumentException | LoginFailException e) {
			e.printStackTrace();
		}
		
		try {
			Dropbox.remove(U1, P1, "dato1");
		} catch (NullPointerException | LoginFailException | NoElemFoundException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("numero dati U1 dopo remove:" + Dropbox.getSize(U1, P1));
			System.out.println("numero dati U2 dopo remove:" + Dropbox.getSize(U2, P2));
			System.out.println("numero dati U3 dopo remove:" + Dropbox.getSize(U3, P3));
		} catch (NullPointerException | IllegalArgumentException | LoginFailException e) {
			e.printStackTrace();
		}
		
		try {
			//Iterator<String> itr = Dropbox.getIterator(U1, P2); LoginFailexc Password errata
			//Iterator<String> itr = Dropbox.getIterator(U4, P4); LoginFailexc utente non trovato
			Iterator<String> itr1 = Dropbox.getIterator(U1, P1);
			System.out.println("Dati presenti nella collezione di U1: ");
			while(itr1.hasNext()) {
				System.out.println(itr1.next());
			}

			Iterator<String> itr2 = Dropbox.getIterator(U2, P2);
			System.out.println("Dati presenti nella collezione di U2: ");
			while(itr2.hasNext()) {
				System.out.println(itr2.next());
			}

			Iterator<String> itr3 = Dropbox.getIterator(U3, P3);
			System.out.println("Dati presenti nella collezione di U3: ");
			while(itr3.hasNext()) {
				System.out.println(itr3.next());
			}
		} catch (NullPointerException | LoginFailException e) {
			e.printStackTrace();
		}
		
	}
	
}

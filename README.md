# SecureDataContainer
PR2 @ UniPi first project in Java 2018/19

## Basics
Si faccia riferimento a SecureDataContainer.java e all'overview in esso contenuta per capire il funzionamento generale.

Compilare sulla propria macchina i file sorgenti ed eseguire il comando “java Main”.

### In sostanza
SecureDataContainer<E> è un contenitore di oggetti di tipo E. Intuitivamente la collezione si comporta come una specie Data Storage per la memorizzazione e condivisione di dati (rappresentati nella simulazione da oggetti di tipo E). La collezione deve garantire un meccanismo di sicurezza dei dati fornendo un proprio meccanismo di gestione delle identità degli utenti. Inoltre, la collezione deve fornire un meccanismo di controllo degli accessi che permette al proprietario del dato di eseguire una restrizione selettiva dell'accesso ai suoi dati inseriti nella collezione. Alcuni utenti possono essere autorizzati dal proprietario ad accedere ai dati, mentre altri non possono accedervi senza autorizzazione.

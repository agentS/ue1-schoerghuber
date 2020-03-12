# SVE2 - Übung 1: Jakarta EE

## Allgemeine Hinweise zur Übung
* Checken Sie Ihre Lösung schon während der Übung laufend in das zur Verfügung
  gestellte GitHub-Repository ein.
* Wir können während der Übung über einen Chat und mittels Microsoft Teams
  kommunizieren. 
* Zur Ausarbeitung dieser Übung stelle ich Ihnen ein Zip-Archiv mit Scripts und
  Quelltexten zur Verfügung. Dieses befindet sich in Moodle oder ist Bestandteil
  des bereitgestellten Repositories. 

## Einrichten der Arbeitsumgebung

### Java-Entwickler-Tools installieren
* JavaSE 11 (<https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>).
* Umgebungsvariable `JAVA_HOME` setzen.
* Apache Maven (<https://maven.apache.org/download.cgi>)

### Wildfly installieren
* Wildfly 18.0.1 von <https://wildfly.org/downloads/> herunterladen
* Zip-Archiv auspacken
* Environment-Variable `JBOSS_HOME` auf Wurzelverzeichnis der
  Wildfly-Installation setzen.
* Account mit Administratorrechten hinzufügen: `add-admin-user.ps1`
  (`templates/config`) ausführen. 
  + _Username/Password_ auf `admin`/`admin123!` setzen.
* Auf `$Env:JBOSS_HOME` gehen und Wildfly mit `bin/standalone.bat` starten. 
* Überprüfen, ob der Applikationsserver über `http://localhost:8080` ansprechbar
  ist.
* In Administrator-Konsole gehen (Login mit obigen Benutzernamen und Passwort)
  + Verschaffen Sie sich einen Überblick über die Konfigurationsmöglichkeiten.
  + Gehen Sie insbesondere auf _Configuration -> Subsystems -> Datasources &
    Drivers_. Standardmäßig ist nur die Datasource `ExampleDS` konfiguriert.
* Anschließend über eine PowerShell-Konsole eine neue DataSource (h2) hinzufügen:
  + `add-datasource.ps1` (`templates/config`) ausführen (bei laufendem
    Applikationsserver).
  + In der Management-Konsole (Web-Interface) überprüfen, ob eine neue
    Datasource `FhBayDS` hinzugekommen ist.
  + Wie man erkennen kann, werden die der h2-Datenbank in Home-Verzeichnis
    abgelegt.
* JBoss wieder beenden.

### Wildfly in IntelliJ konfigurieren
* _Settings -> Build, Execution, Deployment -> Application Servers_
	+ "+" -> JBoss Server
    - JBoss Home: Wurzelverzeichnis von Wildfly (`%JBOSS_HOME%`)
	+ Wildfly in IntelliJ starten
		- Edit Configurations (Toolbar rechts oben)  -> "+" -> "JBoss Server"
			- Name: JBoss 18
			- Application Server: Installierte Version ion ComboBox auswählen.
			- URL: `http://localhost:8080`
			- Username/Passwort: `admin`/`admin123!`
    - Neu erstellte Konfiguration _JBoss 18_ starten.
		  - Einstiegsseite von JBoss sollte gestartet werden.
    - Damit experimentieren, wie man Wildfly startet und stoppt.
  
### Projekt anlegen
* In IntelliJ neues Maven-Projekt `fhbay` anlegen (_File -> New -> Project... ->
  Maven_)
* `pom.xml` durch bereitgestellte Version ersetzen (`templates/config`).
*	Framework-Support hinzufügen (_Kontextmenü von Projekt -> Add Framework Support_)
	- Bean Validation
	- CDI
	- JSON-B
	- JSON Processing
	- JTA
  - JavaEE Persistence (Version 2.2 von `persistence.xml`)
* `beans.xml` anpassen
  + Überprüfen, ob sich `beans.xml` in `WEB-INF` befindet
  + `xsi:schemaLocation` auf Version 2.0 umstellen.
  + `bean-discovery-mode="annotated"` 
* `index.html` anlegen (in Verzeichnis `webapp`)
* Neue Run-Konfiguration hinzufügen
  + '+' -> _JBoss Server_
  + Reiter _Server_
    - Name: `fhbay`
    - Applikationsserver auswählen
    - On Update action: _Update resources and classes_
    - Username/Password eingeben
  + Reiter `Deployment`
		- "+" -> `fhbay:war exploded`
		- Überprüfen ob URL angepasst wurde: `http://localhost:8080/fhbay`
* Application Server starten bzw. Deployment durchführen
  - Hinweis: Alles, was sich im Verzeichnis `target/fhbay` befindet, ist für den
    Applikationsserver sichtbar.
* Redeployment
  - Änderung an `index.html` vornehmen
  - In Ansicht _Services_ Button _Deploy All_ klicken. Deployment sollte sehr
    rasch vor sich gehen. 
  - Web-Seite in Browser neu laden. 
  
### Einfaches JAX-RS-Service implementieren
* Klasse `RestMain` implementieren: 
  ```java
  package sve2.fhbay.rest;

  @ApplicationPath("/api")
  public class RestMain extends Application {
  }
  ```

* Klasse `HelloResource` implementieren:
  ```java
  package sve2.fhbay.rest;

  @Path("/")
  @RequestScoped
  public class HelloResource {

    // Path mapped to this method:
    // http://localhost:8080/fhbay/api/hello

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHelloMessage() {
      return "Hello from JAX-RS";
    }
  }
  ```
  + Testen (Deploy, Refresh im Browser)
  
### Logging-Bean implementieren

* Interface `Logger` hinzufügen
   ```java
   package sve2.fhbay.util;

   public interface Logger {
     public void info(String message);
   }
   ```
  
* Klasse `SimpleLogger` implementieren, die Log-Ausgabe mit `System.out.println`
  durchführt. `SimpleLogger` implementiert `Logger`.
* Logger in `HelloResource` injizieren.
  - Deployment produziert Fehler. Fehlerursache analysieren. Wenn nicht, haben
    Sie `discovery-mode` auf `all` gesetzt.
  - `SimpleLogger` mit Annotation `@Dependent` versehen. Wirkungsweise
    analysieren. Mögliche Alternativen überlegen.
* Klasse `Slf4jLogger` implementieren, die Log-Ausgabe mit der
  Logging-Bibliothek `slf4j` durchführt.
  - Auch diese Klasse mit `@Dependent` versehen.
  - Deployment produziert Fehler. Fehlerursache analysieren.
  - _Qualifier-Annotation_ `@Log` implementieren. `@Log` hat einen Parameter vom
    Typ
    ```java
    public enum Type { SIMPLE, ADVANCED }
    ```
  - `@Log` so anwenden, dass `Slf4jLogger` verwendet wird.
* Klasse `LoggerProducer` hinzufügen:
  ```java
  package sve2.fhbay.util;

  @ApplicationScoped
  public class LoggerProducer {
    @Produces
    public org.slf4j.Logger produceLogger(InjectionPoint injectionPoint) {
      return LoggerFactory.getLogger(
        injectionPoint.getMember().getDeclaringClass());
    }
  }
  ```    
* `org.slf4j.Logger` in `HelloResource` injizieren.
* Analysieren, was die Methode `produceLogger` macht.
* Zu `HelloResource` Lifecycle-Methoden (`@PostConstruct`, `@PreDestroy`)
  hinzufügen und mithilfe des Loggers Trace-Ausgaben durchführen.

### Einfaches Bean implementieren
* Klasse `sve2.fhbay.domain.Customer` hinzufügen
  ```java
  public class Customer implements Serializable {
    private Long   id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;

    // constructors
    // getters and setters
    // toString
  }
  ```
 
 * Interface `sve2.fhbay.CustomerAdmin` hinzufügen
  + Mit `@Local` annotieren
  + `IdNotFoundException` als _Checked Exception_ definieren.
  ```java
  @Local
  public interface CustomerAdmin {
    boolean existsCustomer(Long id);
    Customer findCustomerById(Long id) throws IdNotFoundException;
    List<Customer> findAllCustomers();
    Long saveCustomer(Customer customer);
  }
  ```

* `sve2.fhbay.logic.CustomerAdminBean` implementieren
  + Mit `@Stateless` annotieren
  ```java
  @Stateless
  public class CustomerAdminBean implements CustomerAdmin {
    ...
  }
  ```

  + `Logger` injizieren
  + Methoden zunächst so implementieren, dass mithilfe des Loggers die
    Methodenaufrufe protokolliert werden.
  + Damit haben wir ein vollständiges EJB implementiert.
  + Beachten Sie, dass alle Operationen automatisch in Transaktionen
    durchgeführt werden.

* REST-Service `sve2.fhbay.rest.CustomerResource` hinzufügen
  + Geschäftslogik-Bean `CustomerAdmin` injizieren.
  + Methoden implementieren, indem an `CustomerAdmin` delegiert wird.
  ```java
  @Path("/customers")
  public class CustomerResource {

    @EJB
    private CustomerAdmin customerAdmin;

    // URL: http://localhost:8080/fhbay/api/customers
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers() {
      
      List<Customer> customers = customerAdmin.findAllCustomers();
      return customers;
    }
  }
  ```

  + Weitere Methoden (`getCustomerById`, `addCustomer`) auf dieselbe Art und
    Weise implementieren.

* Deploy All
  + Log-Konsole von Wildfly auf Fehlermeldungen überprüfen.
* Mit Postman testen
  + REST-Endpunkte aufrufen und überprüfen, ob in Log-Konsole Trace-Meldungen
    angezeigt werden.

### Einfaches DAO implementieren
* `sve2.fhbay.dao.SimpleCustomerDao` hinzufügen.
  ```java
  @Local
  public interface SimpleCustomerDao {

    boolean        entityExists(Long id);
    Customer       findById(Long id);
    List<Customer> findAll();

    Customer       merge  (Customer entity);
    void           persist(Customer entity);
    void           remove (Customer entity);
  }
  ```

* `sve2.fhbay.dao.SimpleCustomerDaoBean` implementieren.
  + `EntityManager` injizieren
  ```java
  @Stateless
  public class SimpleCustomerDaoBean implements SimpleCustomerDao {

    @PersistenceContext
    private EntityManager em;

    ...
  }
  ```
  + Mithilfe von `EntityManager` alle Interface-Methoden implementieren.

* Customer um minimale JPA-Annotationen ergänzen (`@Entity`, `@Id`, `@GeneratedValue`)
* Deployment-Descriptor `resources/META-INF/persistence.xml` durch
  bereitgestellte Version ersetzen (`templates/config`). 
* CustomerDao in `CustomerAdmin` injizieren.
	```java
   @Stateless
   public class CustomerAdminBean implements CustomerAdmin {

    @EJB
    private SimpleCustomerDao customerDao;
  }
  ```
  + Methoden auf Basis von `customerDao` implementieren.
  + Parameter von `@EJB` analysieren. Beispielsweise könnte man mit
    `@EJB(beanName = "SimpleCustomerDaoBean")` eine bestimmte Implementierung
    des Bean-Interfaces ansprechen.
* Deploy All
* Testen
* Datenbank mit Testdaten befüllen: Da die Datenbank bei jedem Deployment
  gelöscht wird, benötigt man eine bequeme Möglichkeit, diese zu Initialisieren.
  Eine einfache Variante ist ein REST-Endpunkt, der das erledigt:

  ```java
  @POST
  @Path("/init")
  public void init() {
    customerAdmin.saveCustomer(new Customer(...));
    ...
  }
  ```

### EJBs in CDI-Beans transformieren
* `@Local` entfernen (`SimpleCustomerDao`, `CustomerAdmin`)
* `@Stateless` beseitigen (`CustomerDaoBean`, `CustomerAdminBean`) und durch
  `@RequestScoped @Transactional` ersetzen.
* `@EJB` durch `@Inject` ersetzen (`CustomerAdminBean`, `CustomerResource`).
* Ein wesentlicher Unterschied zwischen EJBs und CDI-Beans ist, dass CDI-Beans
  nicht automatisch transaktional sind. Deswegen ist die explizite Angabe von
  `@Transactional` erforderlich.
* Testen

### Deployment mit Maven
* `mvn package`: Artefakte für Deployment bauen
* `mvn wildfly:deploy`: Erstes Deployment
* `mvn wildfly:redeploy`: Neue Version deployen
* `mvn wildfly:undeploy`: Deployte Version am Applikationsserver beseitigen

### Refactoring: Herausziehen der gemeinsamen Funktionalität der DB-Zugriffsschicht
* Sehr viele CRUD-Methoden eines DAO-Interfaces können generisch implementiert
  werden. Aufgrund von Type-Erasure ist dies in Java zwar schwierig, aber trotzdem
  möglich.
* Generisches Interface `Dao<T, ID>` sowie die Klassen `AbstractDaoBean<T, ID>`
  und `TypeUtil` (`templates/src`) zum Projekt hinzufügen.
* Implementierung analysieren.
* Interface `CustomerDao` hinzufügen 
  + `CustomerDao` erweitert `Dao<Customer, Long>`.
* Klasse `CustomerDaoBean` hinzufügen. 
  + `CustomerDaoBean` leitet von `AbstractDaoBean<Customer, Long>` ab und
  + implementiert `CustomerDao`
  + `CustomerDaoBean` zu einem transaktionalen CDI-Bean machen (`@RequestScoped
    @Transactional`).
* In `CustomerAdminBean` `SimpleCustomerDao` durch `CustomerDao` ersetzen.
* Testen
* Analysieren Sie diese Implementierung der Persistenzschicht. Erstellen Sie ein
  UML-Diagramm für die Klassenhierarchie.

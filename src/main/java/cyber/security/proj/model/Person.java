package cyber.security.proj.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PERSON database table.
 * 
 */
@Entity
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERSON_PERSID_GENERATOR", sequenceName="PERSON_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERSON_PERSID_GENERATOR")
	@Column(name="PERS_ID")
	private long persId;

	@Column(name="PERS_ADDRESS")
	private String persAddress;

	@Column(name="PERS_CONTACTNUMBER")
	private String persContactnumber;

	@Column(name="PERS_EMAIL")
	private String persEmail;

	@Column(name="PERS_EXTID")
	private String persExtid;

	@Column(name="PERS_IDDOCUMENT")
	private String persIddocument;

	@Column(name="PERS_ISACTIVE")
	private String persIsactive;

	@Column(name="PERS_LASTNAME")
	private String persLastname;

	@Column(name="PERS_LATITUDE")
	private String persLatitude;

	@Column(name="PERS_LOCALDATA")
	private String persLocaldata;

	@Column(name="PERS_LONGITUDE")
	private String persLongitude;

	@Column(name="PERS_NAME")
	private String persName;

	@Temporal(TemporalType.DATE)
	@Column(name="PERS_ONSETDATE")
	private Date persOnsetdate;

	@Column(name="PERS_POLITICSACCEPTED")
	private String persPoliticsaccepted;

	@Temporal(TemporalType.DATE)
	@Column(name="PERS_POLITICSACCEPTEDDATE")
	private Date persPoliticsaccepteddate;

	//bi-directional many-to-one association to PersonRole
	@OneToMany(mappedBy="person")
	private List<PersonRole> personRoles;

	//bi-directional many-to-one association to Userr
	@OneToMany(mappedBy="person")
	private List<Userr> userrs;

	public Person() {
	}

	public long getPersId() {
		return this.persId;
	}

	public void setPersId(long persId) {
		this.persId = persId;
	}

	public String getPersAddress() {
		return this.persAddress;
	}

	public void setPersAddress(String persAddress) {
		this.persAddress = persAddress;
	}

	public String getPersContactnumber() {
		return this.persContactnumber;
	}

	public void setPersContactnumber(String persContactnumber) {
		this.persContactnumber = persContactnumber;
	}

	public String getPersEmail() {
		return this.persEmail;
	}

	public void setPersEmail(String persEmail) {
		this.persEmail = persEmail;
	}

	public String getPersExtid() {
		return this.persExtid;
	}

	public void setPersExtid(String persExtid) {
		this.persExtid = persExtid;
	}

	public String getPersIddocument() {
		return this.persIddocument;
	}

	public void setPersIddocument(String persIddocument) {
		this.persIddocument = persIddocument;
	}

	public String getPersIsactive() {
		return this.persIsactive;
	}

	public void setPersIsactive(String persIsactive) {
		this.persIsactive = persIsactive;
	}

	public String getPersLastname() {
		return this.persLastname;
	}

	public void setPersLastname(String persLastname) {
		this.persLastname = persLastname;
	}

	public String getPersLatitude() {
		return this.persLatitude;
	}

	public void setPersLatitude(String persLatitude) {
		this.persLatitude = persLatitude;
	}

	public String getPersLocaldata() {
		return this.persLocaldata;
	}

	public void setPersLocaldata(String persLocaldata) {
		this.persLocaldata = persLocaldata;
	}

	public String getPersLongitude() {
		return this.persLongitude;
	}

	public void setPersLongitude(String persLongitude) {
		this.persLongitude = persLongitude;
	}

	public String getPersName() {
		return this.persName;
	}

	public void setPersName(String persName) {
		this.persName = persName;
	}

	public Date getPersOnsetdate() {
		return this.persOnsetdate;
	}

	public void setPersOnsetdate(Date persOnsetdate) {
		this.persOnsetdate = persOnsetdate;
	}

	public String getPersPoliticsaccepted() {
		return this.persPoliticsaccepted;
	}

	public void setPersPoliticsaccepted(String persPoliticsaccepted) {
		this.persPoliticsaccepted = persPoliticsaccepted;
	}

	public Date getPersPoliticsaccepteddate() {
		return this.persPoliticsaccepteddate;
	}

	public void setPersPoliticsaccepteddate(Date persPoliticsaccepteddate) {
		this.persPoliticsaccepteddate = persPoliticsaccepteddate;
	}

	public List<PersonRole> getPersonRoles() {
		return this.personRoles;
	}

	public void setPersonRoles(List<PersonRole> personRoles) {
		this.personRoles = personRoles;
	}

	public PersonRole addPersonRole(PersonRole personRole) {
		getPersonRoles().add(personRole);
		personRole.setPerson(this);

		return personRole;
	}

	public PersonRole removePersonRole(PersonRole personRole) {
		getPersonRoles().remove(personRole);
		personRole.setPerson(null);

		return personRole;
	}

	public List<Userr> getUserrs() {
		return this.userrs;
	}

	public void setUserrs(List<Userr> userrs) {
		this.userrs = userrs;
	}

	public Userr addUserr(Userr userr) {
		getUserrs().add(userr);
		userr.setPerson(this);

		return userr;
	}

	public Userr removeUserr(Userr userr) {
		getUserrs().remove(userr);
		userr.setPerson(null);

		return userr;
	}

}
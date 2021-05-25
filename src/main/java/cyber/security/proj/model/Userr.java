package cyber.security.proj.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The persistent class for the USERR database table.
 * 
 */
@Entity
@NamedQuery(name="Userr.findAll", query="SELECT u FROM Userr u")
public class Userr implements Serializable {
	private static final long serialVersionUID = 1L;

	public interface AddUser{}
	
	@Id
	@SequenceGenerator(name="USERR_USERID_GENERATOR", sequenceName="USERR_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERR_USERID_GENERATOR")
	@Column(name="USER_ID")
	private long userId;

	@Column(name="INST_INST_ID")
	private BigDecimal instInstId;

	@Column(name="USER_NAME")
	@NotBlank(message="Must not be a blank String", groups= {AddUser.class})
	@Size(min=6, max=20, groups=AddUser.class)
	private String userName;

	@Column(name="USER_PASSWORD")
	@NotBlank(message="Must not be a blank String", groups= {AddUser.class})
	@Size(min=6, max=12, groups=AddUser.class)
	private String userPassword;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="PERS_PERS_ID")
	private Person person;

	private LocalDateTime lastLog;
	
	private LocalDateTime lastLastLog;
	
	public Userr() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getInstInstId() {
		return this.instInstId;
	}

	public void setInstInstId(BigDecimal instInstId) {
		this.instInstId = instInstId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LocalDateTime  getLastLog() {
		return lastLog;
	}

	public void setLastLog(LocalDateTime lastLog) {
		this.lastLog = lastLog;
	}

	public LocalDateTime  getLastLastLog() {
		return lastLastLog;
	}

	public void setLastLastLog(LocalDateTime lastLastLog) {
		this.lastLastLog = lastLastLog;
	}
	
}
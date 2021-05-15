package cyber.security.proj.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the ROLE_PERMISSION database table.
 *
 */
@Entity
@Table(name = "ROLE_PERMISSION")
@NamedQuery(name = "RolePermission.findAll", query = "SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RolePermissionPK id;

	private String dumy;

	// bi-directional many-to-one association to Rolee
	@ManyToOne
	@JoinColumn(name = "ROLE_ROLE_ID", insertable = false, updatable = false)
	private Rolee rolee;

	public RolePermission() {
	}

	public String getDumy() {
		return this.dumy;
	}

	public RolePermissionPK getId() {
		return this.id;
	}

	public Rolee getRolee() {
		return this.rolee;
	}

	public void setDumy(String dumy) {
		this.dumy = dumy;
	}

	public void setId(RolePermissionPK id) {
		this.id = id;
	}

	public void setRolee(Rolee rolee) {
		this.rolee = rolee;
	}

}
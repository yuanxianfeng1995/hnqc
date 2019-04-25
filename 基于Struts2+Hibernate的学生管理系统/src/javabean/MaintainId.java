package javabean;

/**
 * MaintainId entity. @author MyEclipse Persistence Tools
 */

public class MaintainId implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;

	// Constructors

	/** default constructor */
	public MaintainId() {
	}

	/** full constructor */
	public MaintainId(Integer id, User user) {
		this.id = id;
		this.user = user;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MaintainId))
			return false;
		MaintainId castOther = (MaintainId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getUser() == castOther.getUser()) || (this.getUser() != null
						&& castOther.getUser() != null && this.getUser()
						.equals(castOther.getUser())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getUser() == null ? 0 : this.getUser().hashCode());
		return result;
	}

}
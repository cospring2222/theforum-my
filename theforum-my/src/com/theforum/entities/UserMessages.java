package com.theforum.entities;
// Generated Feb 18, 2018 12:44:44 PM by Hibernate Tools 4.3.1.Final

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.theforum.util.DateUtils;

/**
 * UserMessges generated by hbm2java
 */
@Entity
@Table(name = "user_messages", catalog = "myforumweb")
public class UserMessages implements java.io.Serializable {

	private long id;
	private Users usersByUsermsgsToUserid;
	private Users usersByUsermsgsFromUserid;
	private Date usermsgsDate;
	private String usermsgsText;

	public UserMessages() {
		setUsermsgsDate(new Date());
	}

	public UserMessages(long id) {
		this.id = id;
	}

	public UserMessages(long id, Users usersByUsermsgsToUserid, Users usersByUsermsgsFromUserid, Date usermsgsDate,
						String usermsgsText) {
		this.id = id;
		this.usersByUsermsgsToUserid = usersByUsermsgsToUserid;
		this.usersByUsermsgsFromUserid = usersByUsermsgsFromUserid;
		this.usermsgsDate = usermsgsDate;
		this.usermsgsText = usermsgsText;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usermsgs_to_userid")
	@Fetch (FetchMode.SELECT)
	public Users getUsersByUsermsgsToUserid() {
		return this.usersByUsermsgsToUserid;
	}

	public void setUsersByUsermsgsToUserid(Users usersByUsermsgsToUserid) {
		this.usersByUsermsgsToUserid = usersByUsermsgsToUserid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usermsgs_from_userid")
	@Fetch (FetchMode.SELECT)
	public Users getUsersByUsermsgsFromUserid() {
		return this.usersByUsermsgsFromUserid;
	}

	public void setUsersByUsermsgsFromUserid(Users usersByUsermsgsFromUserid) {
		this.usersByUsermsgsFromUserid = usersByUsermsgsFromUserid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "usermsgs_date", length = 19)
	public Date getUsermsgsDate() {
		return this.usermsgsDate;
	}

	public void setUsermsgsDate(Date usermsgsDate) {
		this.usermsgsDate = usermsgsDate;
	}

	@Column(name = "usermsgs_text")
	public String getUsermsgsText() {
		return this.usermsgsText;
	}

	public void setUsermsgsText(String usermsgsText) {
		this.usermsgsText = usermsgsText;
	}

}

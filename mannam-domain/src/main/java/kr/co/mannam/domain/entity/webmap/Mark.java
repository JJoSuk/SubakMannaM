package kr.co.mannam.domain.entity.webmap;

import kr.co.mannam.domain.entity.member.User;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="MARK")
public class Mark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mid;

	@Column(nullable = false, length = 50, unique = true)
	private String markname;


	private String markimage;

	private String markimagepath;

	@Column(nullable = false, length = 100)
	private String markaddress;

	@Column(nullable = false, length = 100)
	private String markainfo;

	@Column(nullable = false, length = 100)
	private String category;

	@Column(nullable = false, length = 100)
	private String tel;

	@Column(length = 100)
	private String latitude;

	@Column(length = 100)
	private String longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;


	@Builder
	public Mark(String markname, String markaddress, String markainfo, String category, String tel, String latitude, String longitude, String markimage,String markimagepath, User user) {
		this.markname = markname;
		this.markaddress = markaddress;
		this.markainfo = markainfo;
		this.category = category;
		this.tel = tel;
		this.latitude = latitude;
		this.longitude = longitude;
		this.markimage = markimage;
		this.markimagepath = markimagepath;
		this.user = user;

	}


	public interface MarkMapping {

		Long getMid();

		String getMarkname();

		String getMarkimage();
		String getMarkimagepath();
		String getMarkaddress();
		String getMarkainfo();
		String getCategory();
		String getTel();
		String getLatitude();
		String getLongitude();

	}

	public interface MarkNameMapping {

		String getMarkname();


	}

}

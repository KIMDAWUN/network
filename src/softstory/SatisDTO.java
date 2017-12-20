package softstory;
/*
 * StatisDTO is used to when surveyed the satisfaction, give some stars 
 */
public class SatisDTO {
	String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	int star = 0;

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

}

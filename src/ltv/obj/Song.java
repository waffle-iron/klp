package ltv.obj;

public class Song {
	private String id;
	private String name;
	private String summary;
	private String lyric;
	private String author;
	private String favorite;
	private String link;
	private String name_unsign;
	private int catid;
	
	public Song() {
		
	}
	
    public Song(String id, String name, String summary, String lyric,
			String author, String favorite, String link, String name_unsign, int catid) {
		this.id = id;
		this.name = name;
		this.summary = summary;
		this.lyric = lyric;
		this.author = author;
		this.favorite = favorite;
		this.link = link;
		this.name_unsign = name_unsign;
		this.catid = catid;
	}

	@Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%d", 
                this.id, this.name, this.summary, this.lyric, this.author, this.favorite, 
                this.link, this.name_unsign, this.catid);
    }	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getFavorite() {
		return favorite;
	}

	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName_unsign() {
		return name_unsign;
	}

	public void setName_unsign(String name_unsign) {
		this.name_unsign = name_unsign;
	}
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}		
}

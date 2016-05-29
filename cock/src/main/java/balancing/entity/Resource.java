package balancing.entity;

public class Resource {

	private String id;
	
	private Node node;
	
	private int group;

	public Resource(String id) {
		super();
		this.id = id;
	}

	public Resource(String id, int group) {
		super();
		this.id = id;
		this.group = group;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public boolean equals(Object obj) {
		return null != obj && this.id.equals(((Resource)obj).getId());
	}

	@Override
	public String toString() {
		return this.id;
	}
	
}

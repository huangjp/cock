package balancing.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

	private String id;
	
	private int resourceCount;
	
	private List<Resource> resources;

	public Node(String id) {
		super();
		this.id = id;
		this.resources = new ArrayList<Resource>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResourceCount() {
		return this.resources.size();
	}
	
	public void addResource(Resource resource) {
		if(null != resource) {
			if(!this.resources.contains(resource)){
				if(!this.equals(resource.getNode()))
					resource.setNode(this);
				this.resources.add(resource);
			}
		}
	}
	
	public Resource getResource(int index) {
		return this.resources.get(index);
	}
	
	public boolean isExist(Resource resource) {
		return this.resources.contains(resource);
	}
	
	public void addAllResource(Node node) {
		if(null != node) {
			List<Resource> resources = node.resources;
			for(Resource r : resources) {
				addResource(r);
			}
		}
	}
	
	public List<String> getResources() {
		List<String> list = new ArrayList<>();
		for(Resource r : this.resources) {
			list.add(r.getId());
		}
		return list;
	}
	
	public List<Resource> resourceMinGroup() {
		Map<Integer, List<Resource>> map = new HashMap<Integer, List<Resource>>(this.resourceCount);
		int minGroup = 0;
		for(Resource r : this.resources) {
			int group = r.getGroup();
			if(group == 0 || minGroup == 0 || group < minGroup) {
				minGroup = group;
				map.put(group, new ArrayList<>());
				map.get(group).add(r);
			} else if(group == minGroup) {
				map.get(group).add(r);
			}
		}
		return map.get(minGroup);
	}
	
	public void clearResources() {
		this.resources.clear();
	}

	public void removeResources(Resource resource) {
		this.resources.remove(resource);
	}
	
	public void removeAllResources() {
		for(Resource r : this.resources) {
			removeResources(r);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return null != obj && this.id.equals(((Node)obj).getId());
	}

	@Override
	public String toString() {
		return this.id + ": " + this.resources;
	}
}

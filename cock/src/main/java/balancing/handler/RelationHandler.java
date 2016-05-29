package balancing.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import balancing.entity.Node;
import balancing.entity.Resource;

public class RelationHandler {
	
	private static final RelationHandler INSTANCE = new RelationHandler();

	private List<Node> nodes;
	private List<Node> lastNodes;
	
	private List<Resource> resources;
	private List<Resource> lastResources;
	private RelationHandler() {
		this.nodes = new ArrayList<Node>();
		this.lastNodes = new ArrayList<>();
		this.resources = new ArrayList<>();
		this.lastResources = new ArrayList<>();
	}
	
	public static RelationHandler instance(List<Node> nodes, List<Resource> resources){
		return INSTANCE.firstBalancing(nodes, resources);
	}
	
	public synchronized RelationHandler fullBalancing(List<Node> nodes, List<Resource> resources) {
		if(this.lastNodes.equals(nodes) && this.lastResources.equals(resources)) {
			return INSTANCE.firstBalancing(nodes, resources);
		} else if(this.lastNodes.equals(nodes)) {
			changeResource(resources);
		} else if(this.lastResources.equals(resources)) {
			changeNode(nodes);
		} else {
			changeNode(nodes);
			changeResource(resources);
		}
		checkDataLength();
		checkBalancing();
		return INSTANCE;
	}
	
	public synchronized List<Node> result() {
		return this.lastNodes;
	}
	
	private RelationHandler firstBalancing(List<Node> nodes, List<Resource> resources){
		this.nodes.clear();
		this.lastNodes.clear();
		this.resources.clear();
		this.lastResources.clear();
		this.nodes.addAll(nodes);
		this.resources.addAll(resources);
		this.lastNodes.addAll(nodes);
		this.lastResources.addAll(resources);
		
		for(int i = 0; i < this.lastResources.size(); i++)	{
			Resource r = this.lastResources.get(i);
			Node n = sameGroup(r);
			if(null == n) {
				n = this.lastNodes.get(i % this.lastNodes.size());
			}
			n.addResource(r);
		}
		return INSTANCE;
	}
	
	/**
	 * 节点不变，资源发生变化
	 * @param resources
	 */
	private void changeResource(List<Resource> resources) {
		if(null == resources) {
			return;
		}
		List<Resource> all = new ArrayList<>(resources);
		List<Resource> newList = new ArrayList<>();
		List<Resource> delList = new ArrayList<>();
		
		for (Resource r : this.lastResources) {
			int index = all.indexOf(r);
			if(index != -1) {
				all.remove(index);
			} else {
				delList.add(r);
			}
		}
		
		newList.addAll(all);
		
		for(int i = 0; i < newList.size(); i++) {
			Resource newResource = newList.get(i);
			Node node = null;
			int index = this.resources.indexOf(newResource);
			if(index != -1) {
				node = this.resources.get(index).getNode();
			}
			if(i < delList.size()) {
				Resource oldResource = delList.remove(i);
				if(null == node) {
					node = oldResource.getNode();
				}
				oldResource.getNode().removeResources(oldResource);
				this.lastResources.remove(oldResource);
			} else if(null == node){
				node = loadMin();
			}
			node.addResource(newResource);
			this.lastResources.add(newResource);
		}
		
		for(Resource r : delList) {
			r.getNode().removeResources(r);
		}
		
		this.lastResources.removeAll(delList);
	}
	
	/**
	 * 节点变更，域不变时，调用该方法
	 * 方法逻辑：
	 * 	1、对比分类新来的，删除的
	 * 	2、新来的节点先在上删除的节点里找资源，再到节点库里找之前是否有负责过某些资源，最后在去当前最大压力的节点时找资源
	 * @param nodes
	 */
	private void changeNode(List<Node> nodes) {
		if(null == nodes) {
			return ;
		}
		List<Node> all = new ArrayList<Node>(nodes);
		List<Node> newList = new ArrayList<Node>();
		List<Node> changeList = new ArrayList<Node>();
		List<Node> delList = new ArrayList<Node>();
		
		for(Node node : this.lastNodes) {
			int index = all.indexOf(node);
			if(index != -1) {
				all.remove(index);
				changeList.add(node);
			} else {
				delList.add(node);
			}
		}
		
		newList.addAll(all);
		for(int i = 0; i < newList.size(); i++) {
			Node n = newList.get(i);
			n.clearResources();
			if(i < delList.size()) {
				Node oldNode = delList.remove(i);
				takeOver(n, oldNode);
				this.lastNodes.remove(oldNode);
			} else {
				int index = this.nodes.indexOf(n);
				if(index != -1) {
					takeOver(n, this.nodes.get(index));
					for(Node changeNode : changeList) {
						for (int j = 0; j < n.getResourceCount(); j++) {
							changeNode.removeResources(n.getResource(j));
						}
					}
				} else {
					Node node = loadMax();
					moveMinResources(node, n);
				}
			}
			this.lastNodes.add(n);
		}
		
		this.lastNodes.removeAll(delList);
		
		for(Node n : delList) {
			for (int i = 0; i < n.getResourceCount(); i++) {
				Node minNode = loadMin();
				minNode.addResource(n.getResource(i));
			}
		}
	}
	
	private void checkDataLength() {
		int count = 0;
		for(Node node : this.lastNodes) {
			count += node.getResourceCount();
		}
		if(this.lastResources.size() != count) {
			//TODO 异常,记录出现异常时当时的缓存数据，和全部的缓存数据，记录日志进行问题定位

			List<Resource> errorResources = new ArrayList<>();
			Map<Resource, List<Node>> map = new HashMap<Resource, List<Node>>();
			for(Node node : this.lastNodes) {
				for(int i = 0; i < node.getResourceCount(); i++) {
					Resource r = node.getResource(i);
					List<Node> list = map.get(r);
					if(null == list) {
						list = new ArrayList<Node>();
					} else {//如果能被取出来，说明该节点已经存在
						errorResources.add(r);
					}
					list.add(node);
					map.put(r, list);
				}
			}
			
			for(Resource r : errorResources) {
				List<Node> errorNodeList = map.get(r);
				//去掉第一个之后的 全部多余的，TODO 后期优化成去掉非最小的节点
				for(int i = 1; i < errorNodeList.size(); i++) {
					Node node = errorNodeList.get(i);
					node.removeResources(r);
				}
			}
		}
	}
	
	/*
	 * 均衡检查，如非最大平衡，将循环执行资源转移直到均衡
	 */
	private void checkBalancing() {
		boolean isBalancing = false;
		while (!isBalancing) {
			Node maxNode = loadMax();
			Node minNode = loadMin();
			
			float average = average();
			
			if(maxNode.getResourceCount() - minNode.getResourceCount() >= average) {
				moveMinResources(maxNode, minNode);
			} else {
				isBalancing = true;
			}
		}
	}
	
	/*
	 * 节点的接管
	 */
	private void takeOver(Node newNode, Node oldNode) {
		newNode.addAllResource(oldNode);
		oldNode.clearResources();
	}
	
	/*
	 * 从最大压力的节点往最小压力的节点转移一次最小资源束
	 */
	private void moveMinResources(Node maxNode, Node minNode) {
		List<Resource> minResources = new ArrayList<>(maxNode.resourceMinGroup());
		for(Resource r : minResources) {
			minNode.addResource(r);
			maxNode.removeResources(r);
		}
	}
	
	/*
	 * 找到所有资源中拥有相同组的节点
	 */
	private Node sameGroup(Resource resource) {
		int group = resource.getGroup();
		if(group == 0) {
			return null;
		}
		
		Node node = null;
		for(Resource r : this.resources){
			if(group == r.getGroup()){
				node = r.getNode();
				break;
			}
		}
		return node;
	}
	
	/*
	 * 找到负载压力最大的节点
	 */
	private Node loadMax() {
		
		Node node = null;
		int count = 0;
		for(Node n : this.lastNodes) {
			int size = n.getResourceCount();
			if(count == 0 || size > count) {
				count = size;
				node = n;
			}
		}
		return node;
	}
	
	/*
	 * 找到负载压力最小的节点
	 */
	private Node loadMin() {
		
		Node node = null;
		int count = 0;
		for(Node n : this.lastNodes) {
			int size = n.getResourceCount();
			if(size == 0) {
				node = n;
				break;
			}
			if(count == 0 || size < count) {
				count = size;
				node = n;
			}
		}
		return node;
	}
	
	/*
	 * 计算平均负载
	 */
	private float average() {
		float nodeCount = this.lastNodes.size();
		float resourceCount = this.lastResources.size();
		return resourceCount / nodeCount;
	}
}

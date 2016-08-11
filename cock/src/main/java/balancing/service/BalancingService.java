package balancing.service;

import java.util.ArrayList;
import java.util.List;

import balancing.entity.Node;
import balancing.entity.Resource;
import balancing.handler.RelationHandler;

public class BalancingService {

	public static void main(String[] args) {
		List<Node> nodes = getNodes(new String[]{"member1", "member2", "member3"});
		List<Resource> resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		RelationHandler handler = RelationHandler.instance(nodes , resources);
		System.out.println("初始化结果");
		System.out.println(handler.result());
		System.out.println("\n");
		System.out.println("----------------resource change------------------");
		
		System.out.println("*********删除第一个域************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除中间任意一个域*******************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除后面一个域**********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除前面两个域**********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除后面两个域**********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除中间两个域**********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除在同一节点的两个域****************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H2", "H3", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********删除大半域************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("----------------resource change------------------");
		System.out.println("\n");
		System.out.println("----------------node change------------------");
		
		System.out.println("*********删除一个节点***********************");
		nodes = getNodes(new String[]{"member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********添加一个节点***********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3", "member4"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********删除一个节点***********************");
		nodes = getNodes(new String[]{"member1", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********还原上次删除的节点，并添加一个新节点***");
		nodes = getNodes(new String[]{"member4", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********添加一个节点***********************");
		nodes = getNodes(new String[]{"member1", "member2", "member3", "member4"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********删除两个节点***********************");
		nodes = getNodes(new String[]{"member3", "member4"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());

		System.out.println("*********还原******************************");
		nodes = getNodes(new String[]{"member1", "member2", "member3"});
		resources = getResources(new String[]{"H1", "H2", "H3", "H4", "H5", "H6"});
		handler.fullBalancing(nodes, resources);
		System.out.println(handler.result());
		
		System.out.println("----------------node change------------------");
		System.out.println("\n");
		System.out.println("----------------node change------------------");
	}

	private static List<Node> getNodes(String[] strings) {
		List<Node> list = new ArrayList<Node>();
		for(String s : strings) {
			list.add(new Node(s));
		}
		return list;
	}

	private static List<Resource> getResources(String[] strings) {
		List<Resource> list = new ArrayList<Resource>();
		for(String s : strings) {
			list.add(new Resource(s));
		}
		return list;
	}
}

package com.boothby.web_navigate;

import java.util.ArrayList;
import java.util.List;


/**
We have a Java class that models navigation items for our client websites.
There is a root level item which is not displayed, but contains a list of child items.
These child items may have child items, which may also have child items, to an arbitrary level of depth.
 
Write a method in Java that takes in the root level NavigationItem and prints out the navigation structure in a format that a 
non-developer could understand and use to help them troubleshoot issues with a site. You can assume that all Lists of children 
are non-null and have length of zero when they are empty.
 */
public class NavigationItem {

	private static String ROOT_URL = "http://cnn.com";
	private static String NATION_URL = ROOT_URL + "/national";
	private static String STATE_URL = NATION_URL + "/state";
	private static String COUNTY_URL = STATE_URL + "/county";
	private static String TOWN_URL = COUNTY_URL + "/town";
	
	private static String WORLD_URL = ROOT_URL + "/world";
	private static String UK_URL = WORLD_URL + "/united-kindgom";
	
	public String url;
	public String label;
	public List<NavigationItem> children;

	public static void main(String[] args) {
		// Create navigation tree
		NavigationItem root = createNavigationTree();
		// Print it out
		printChildren(root, 0);
	}
	
	private static NavigationItem createNavigationTree() {
		NavigationItem root = createNavItem(ROOT_URL, "root");

		NavigationItem child1 = createNavItem(NATION_URL, "US National News");
		root.children.add(child1);
		NavigationItem child2 = createNavItem(STATE_URL, "State news");
		child1.children.add(child2);
		NavigationItem child3 = createNavItem(COUNTY_URL, "County News");
		child2.children.add(child3);
		NavigationItem child4 = createNavItem(TOWN_URL, "Town News");
		child3.children.add(child4);
		
		NavigationItem child5 = createNavItem(WORLD_URL, "World News");
		root.children.add(child5);
		NavigationItem child6 = createNavItem(UK_URL, "United Kingdom News");
		child5.children.add(child6);
		
		return root;
	}

	private static NavigationItem createNavItem(String url, String label) {
		NavigationItem navItem = new NavigationItem();
		navItem.url = url;
		navItem.label = label;
		navItem.children = new ArrayList<NavigationItem>();
		return navItem;
	}
	
	private static void printChildren(NavigationItem navItem, int level) {
		if (level > 0) {
			String indent = "";
			for(int i=1; i<level; i++) {
				indent += "\t";
			}
			System.out.println(String.format("%sLabel: %s; URL: %s", indent, navItem.label, navItem.url));
		}
		for(int j=0; j<navItem.children.size(); j++) {
			printChildren(navItem.children.get(j), ++level);
		}
	}
}

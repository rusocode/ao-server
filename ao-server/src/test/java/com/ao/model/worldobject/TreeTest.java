

package com.ao.model.worldobject;

import org.junit.Before;

import com.ao.model.worldobject.properties.ResourceSourceProperties;

public class TreeTest extends AbstractResourceSourceTest {

	private static final ResourceSourceType resourceSourceType = ResourceSourceType.TREE;

	private Tree tree1;

	@Before
	public void setUp() throws Exception {
		ResourceSourceProperties props1 = new ResourceSourceProperties(WorldObjectType.TREE, 1, "Elven Tree", 1, 5, resourceSourceType);
		tree1 = new Tree(props1);

		object = tree1;
		objectProps = props1;
	}
}

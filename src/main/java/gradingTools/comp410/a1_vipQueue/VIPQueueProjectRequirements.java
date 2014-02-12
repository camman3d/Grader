package gradingTools.comp410.a1_vipQueue;

import framework.grading.FrameworkProjectRequirements;
import gradingTools.comp410.a1_vipQueue.tests.NoJavaCollectionsTestCase;
import gradingTools.comp410.a1_vipQueue.tests.UsesGenericsTestCase;
import gradingTools.comp410.a1_vipQueue.tests.queue.*;
import gradingTools.comp410.a1_vipQueue.tests.stack.*;
import gradingTools.comp410.a1_vipQueue.tests.vipQueue.*;
import tools.classFinder2.MethodDescription;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/11/14
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class VIPQueueProjectRequirements extends FrameworkProjectRequirements {

    public VIPQueueProjectRequirements() {

        // Register what stuff we will be looking for
        registerNeededClass("Queue");
        registerNeededMethods("Queue",
                new MethodDescription("enqueue", void.class, Object.class),
                new MethodDescription("dequeue", Object.class),
                new MethodDescription("peek", Object.class),
                new MethodDescription("isEmpty", boolean.class),
                new MethodDescription("isFull", boolean.class));
        registerNeededClass("Stack");
        registerNeededMethods("Stack",
                new MethodDescription("push", void.class, Object.class),
                new MethodDescription("pop", Object.class),
                new MethodDescription("peek", Object.class),
                new MethodDescription("isEmpty", boolean.class),
                new MethodDescription("isFull", boolean.class));
        registerNeededClass("Node");
        registerNeededClass("VipQueue");
        registerNeededMethods("VipQueue",
                new MethodDescription("enqueue", void.class, Object.class),
                new MethodDescription("vipEnqueue", void.class, Object.class),
                new MethodDescription("dequeue", Object.class),
                new MethodDescription("peek", Object.class),
                new MethodDescription("isEmpty", boolean.class),
                new MethodDescription("isFull", boolean.class));

        // Due dates

        // Checks
        addFeature("Queue is correctly implemented",
                "Ensure that there is a Queue(int capacity) constructor; there are enqueue, dequeue, isEmpty, isFull, and peek methods that work correctly; and that it uses an array internally.",
                15,
                new QueueEnqueueDequeueTestCase(),
                new QueueIsEmptyTestCase(),
                new QueueIsFullTestCase(),
                new QueuePeekTestCase(),
                new QueueUsesArrayTestCase(),
                new QueueConstructorTestCase());

        addFeature("Stack is correctly implemented",
                "Ensure that there is a Stack(int capacity) constructor; there are push, pop, isEmpty, isFull, and peek methods that work correctly; and that it uses a linked list internally.",
                15,
                new StackPushPopTestCase(),
                new StackIsEmptyTestCase(),
                new StackIsFullTestCase(),
                new StackPeekTestCase(),
                new StackUsesLinkedListTestCase(),
                new StackConstructorTestCase());

        addFeature("Use of generics",
                "Ensure that there is a Node class which is used in the linked list.",
                10,
                new UsesGenericsTestCase("Queue"),
                new UsesGenericsTestCase("Stack"),
                new UsesGenericsTestCase("VipQueue"),
                new UsesGenericsTestCase("Node"));

        addFeature("VipQueue enqueue method",
                "Ensure that the enqueue method in VipQueue adds to the internal Queue and that the student's Queue is being used.",
                15,
                new VipQueueEnqueueTestCase(),
                new VipQueueUsesQueueTestCase());

        addFeature("VipQueue vipEnqueue method",
                "Ensure that the vipEnqueue method in VipQueue adds to the internal Stack and that the student's Stack is being used.",
                15,
                new VipQueueVipEnqueueTestCase(),
                new VipQueueUsesStackTestCase());

        addFeature("Other VipQueue methods",
                "Ensure that there is a VipQueue(int capacity) constructor; there are isEmpty, isFull, and peek methods that work correctly with both enqueue and vipEnqueue.",
                20,
                new VipQueueIsEmptyTestCase(),
                new VipQueueIsFullTestCase(),
                new VipQueuePeekTestCase(),
                new VipQueueVipPeekTestCase(),
                new VipQueueConstructorTestCase());

        addFeature("Clean code", 10);

        // Penalize for using java collections
        addRestriction("No Java collections",
                "Students should implement their own collections. They should never use List, Set, Map, or Queue.",
                -50, new NoJavaCollectionsTestCase());
    }

}

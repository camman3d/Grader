package gradingTools.comp410.a1_vipQueue;

import framework.grading.FrameworkProjectRequirements;
import gradingTools.comp410.a1_vipQueue.tests.UsesGenericsTestCase;
import gradingTools.comp410.a1_vipQueue.tests.queue.*;
import gradingTools.comp410.a1_vipQueue.tests.stack.*;
import gradingTools.comp410.a1_vipQueue.tests.vipQueue.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/11/14
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class VIPQueueProjectRequirements extends FrameworkProjectRequirements {

    public VIPQueueProjectRequirements() {

        // Due dates

        // Checks
        addFeature("Queue is correctly implemented", 15,
                new QueueEnqueueDequeueTestCase(),
                new QueueIsEmptyTestCase(),
                new QueueIsFullTestCase(),
                new QueuePeekTestCase(),
                new QueueUsesArrayTestCase());

        addFeature("Stack is correctly implemented", 15,
                new StackPushPopTestCase(),
                new StackIsEmptyTestCase(),
                new StackIsFullTestCase(),
                new StackPeekTestCase(),
                new StackUsesLinkedListTestCase());

        addFeature("Use of generics", 10,
                new UsesGenericsTestCase("Queue"),
                new UsesGenericsTestCase("Stack"),
                new UsesGenericsTestCase("VipQueue"),
                new UsesGenericsTestCase("Node"));

        addFeature("VipQueue enqueue method", 15,
                new VipQueueEnqueueTestCase(),
                new VipQueueUsesQueueTestCase());

        addFeature("VipQueue vipEnqueue method", 15,
                new VipQueueVipEnqueueTestCase(),
                new VipQueueUsesStackTestCase());

        addFeature("Other VipQueue methods", 20,
                new VipQueueIsEmptyTestCase(),
                new VipQueueIsFullTestCase(),
                new VipQueuePeekTestCase());

        addFeature("Clean code", 10);

    }

}

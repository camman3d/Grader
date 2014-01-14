package gradingTools.comp410.a1_vipQueue;

import framework.grading.FrameworkProjectRequirements;
import gradingTools.comp410.a1_vipQueue.tests.queue.*;

/**
 * Created with IntelliJ IDEA.
 * User: josh
 * Date: 1/11/14
 * Time: 2:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class VIPQueueProjectRequirements extends FrameworkProjectRequirements {

    public VIPQueueProjectRequirements() {

        // Checks
//        addFeature("Correct output", 15, new CorrectOutputTestCase());
//        addFeature("Uses an array", 20, new UsesArrayTestCase());
//        addFeature("Enqueue adds to array", 20, new UsesArrayTestCase());
//        addFeature("Well documented code", 10);

        addFeature("Queue is correctly implemented", 15,
                new QueueEnqueueDequeueTestCase(),
                new QueueIsEmptyTestCase(),
                new QueueIsFullTestCase(),
                new QueuePeekTestCase(),
                new QueueUsesArrayTestCase());

    }
}

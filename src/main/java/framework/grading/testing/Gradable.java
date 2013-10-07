package framework.grading.testing;

/**
 * Anything that is gradable should have a name and a point value
 */
public interface Gradable extends Describable {

    /**
     * @return The name of the gradable item
     */
    public String getName();

    /**
     * @return The max possible point that can be awarded
     */
    public double getPoints();
}

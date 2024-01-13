package agh.ics.oop.model.visualization;

public interface StatsSubscriber<T> {
    void updateStats(T statsEvent);
}

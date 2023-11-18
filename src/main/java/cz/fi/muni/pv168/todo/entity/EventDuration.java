package cz.fi.muni.pv168.todo.entity;

public enum EventDuration {
    M0(0),
    M15(15),
    M30(30),
    M45(45),
    M60(60),
    M120(120),
    M240(240);

    final Integer duration;

    private EventDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("%d minutes", this.duration);
    }

    public Integer getDuration() {
        return this.duration;
    }
}
